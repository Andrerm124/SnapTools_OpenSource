package com.ljmu.andre.snaptools.ModulePack;

import android.app.Activity;
import android.util.Pair;

import com.ljmu.andre.ConstantDefiner.Constant;
import com.ljmu.andre.snaptools.Exceptions.ModulePackLoadAborted;
import com.ljmu.andre.snaptools.Fragments.FragmentHelper;
import com.ljmu.andre.snaptools.Framework.MetaData.LocalPackMetaData;
import com.ljmu.andre.snaptools.Framework.Module;
import com.ljmu.andre.snaptools.Framework.ModulePack;
import com.ljmu.andre.snaptools.Framework.Utils.LoadState;
import com.ljmu.andre.snaptools.Framework.Utils.LoadState.State;
import com.ljmu.andre.snaptools.Framework.Utils.ModuleLoadState;
import com.ljmu.andre.snaptools.Framework.Utils.PackLoadState;
import com.ljmu.andre.snaptools.ModulePack.Caching.SnapDiskCache;
import com.ljmu.andre.snaptools.ModulePack.Fragments.GeneralSettingsFragment;
import com.ljmu.andre.snaptools.ModulePack.Fragments.KnownBugsFragment;
import com.ljmu.andre.snaptools.ModulePack.ModulesDef.Modules;
import com.ljmu.andre.snaptools.Utils.Constants;
import com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import hugo.weaving.DebugLog;
import timber.log.Timber;

import static com.ljmu.andre.GsonPreferences.Preferences.getCreateDir;
import static com.ljmu.andre.GsonPreferences.Preferences.getPref;
import static com.ljmu.andre.snaptools.ModulePack.Utils.ModulePreferenceDef.FILTERS_PATH;
import static com.ljmu.andre.snaptools.Utils.AuthenticationTrigger.triggerPackAuthCheck;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.DISABLED_MODULES;
import static com.ljmu.andre.snaptools.Utils.PreferenceHelpers.collectionContains;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public class ModulePackImpl extends ModulePack {
	private static final int MINIMUM_FRAMEWORK_VERSION = 56;

	public ModulePackImpl(LocalPackMetaData packMetaData, PackLoadState loadState) throws ModulePackLoadAborted {
		super(packMetaData, loadState);

		checkFrameworkVersion();
	}

	private void checkFrameworkVersion() throws ModulePackLoadAborted {
		if (Constants.getApkVersionCode() < MINIMUM_FRAMEWORK_VERSION)
			throw new ModulePackLoadAborted("Pack requires newer APK version");
	}

	/**
	 * ===========================================================================
	 * Helper method to quickly determine if a settings UI can be expected
	 * ===========================================================================
	 */
	@Override public boolean hasGeneralSettingsUI() {
		return true;
	}

	/**
	 * ===========================================================================
	 * Pull the Settings UI for this Pack
	 * ===========================================================================
	 */
	@DebugLog @Override public FragmentHelper[] getStaticFragments() {
		GeneralSettingsFragment settingsFragment = new GeneralSettingsFragment();
		settingsFragment.setPackName(getPackName());

		for (Modules moduleData : ModulesDef.INST.values()) {
			if (moduleData.canBeDisabled()) {
				settingsFragment.addDisplayHolder(
						Pair.create(
								moduleData.getModuleName(),
								moduleData.getDescription()
						)
				);
			}
		}

		return new FragmentHelper[]{settingsFragment, new KnownBugsFragment().buildMetaData(this)};
	}

	/**
	 * ===========================================================================
	 * Attempt to load instantiate and cache the {@link Module}'s located in
	 * {@link ModulesDef} whilst skipping those that are marked
	 * {@link Modules#canBeDisabled()} and are found in
	 * {@link FrameworkPreferencesDef#DISABLED_MODULES}. If skipped, the module
	 * will be assigned a {@link LoadState.State#SKIPPED} state and a
	 * {@link LoadState.State#FAILED} if any error occurred during instantiation.
	 * <p>
	 * Successful modules are cached to {@link this#modules} and can be retrieved
	 * using {@link this#getModules()} or {@link this#getModule(String)}
	 * <p>
	 * It should be noted that this function will ensure that the
	 * {@link Modules#HOOK_RESOLVER} module is loaded FIRST by providing it with a
	 * sorting index of 1 in the {@link Constant#Constant(int, String)} constructor.
	 * This is to ensure that the Hooks and HookClasses are resolved before any
	 * subsequent modules that rely upon them are loaded.
	 * ===========================================================================
	 */
	@Override public Map<String, ModuleLoadState> loadModules() {
		getCreateDir(FILTERS_PATH);

		Map<String, ModuleLoadState> moduleLoadStates = new LinkedHashMap<>();
		HashSet<String> disabledModules = getPref(DISABLED_MODULES);

		for (Modules moduleData : ModulesDef.INST.values()) {
			ModuleLoadState loadState = new ModuleLoadState(moduleData.getModuleName());
			moduleLoadStates.put(loadState.getName(), loadState);

			// Check if module should be skipped \\
			if (moduleData.canBeDisabled() &&
					collectionContains(
							DISABLED_MODULES,
							moduleData.getModuleName()
					)) {
				loadState.setState(State.SKIPPED);
				continue;
			}

			// ===========================================================================

			try {
				Class<? extends Module> moduleClass = moduleData.getModuleClass();
				Constructor<? extends Module> constructor = moduleClass.getConstructor(String.class, boolean.class);
				Module module = constructor.newInstance(moduleData.getModuleName(), moduleData.canBeDisabled());
				modules.add(module);

				loadState.setState(State.SUCCESS);
			} catch (Throwable e) {
				Timber.e(e, "Failed loading module: "
						+ moduleData.getClassName());
				loadState.setState(State.FAILED);
			}
		}

		hasLoaded = true;

		return moduleLoadStates;
	}

	/**
	 * ===========================================================================
	 *
	 * ===========================================================================
	 *
	 * @return A shallow duplication of the ModuleLoadStates of this ModulePack.
	 */
	@Override public List<ModuleLoadState> injectAllHooks(ClassLoader snapClassLoader, Activity snapActivity) {
		if (!hasLoaded)
			throw new IllegalStateException("Module Pack not loaded!");

		if (hasInjected) {
			Timber.d("Tried to re-inject all hooks");
			return null;
		}

		// Asynchronously purge the temp directory ===================================
		SnapDiskCache.getInstance().destroyTempDir();

		// You were looking for this? ================================================
		// Check that the user actually has access to the pack like they say they do =
		triggerPackAuthCheck(snapActivity, getPackName(), getPackSCVersion(), getPackVersion());
		// ===========================================================================

		List<ModuleLoadState> hookResults = new ArrayList<>();

		// Retrieve the load states of the modules so we can iterate through them and
		// ensure that we only attempt to load SUCCESSful Modules ====================
		Map<String, ModuleLoadState> moduleLoadStateMap = getPackLoadState().getModuleLoadStates();

		for (ModuleLoadState moduleLoadState : moduleLoadStateMap.values()) {
			hookResults.add(moduleLoadState);

			if (moduleLoadState.getState() != State.SUCCESS)
				continue;

			// Resolve the Module object found in the modules variable ===================
			Module module = getModule(moduleLoadState.getName());
			if (module == null) {
				moduleLoadState.setState(State.FAILED);
				continue;
			}

			try {
				module.injectHooks(snapClassLoader, snapActivity, moduleLoadState);
			} catch (Throwable t) {
				Timber.e(t);
				moduleLoadState.fail();
			}
		}

		// Refresh the load state of the ModulePack so that we can display to the user
		// when there has been an error during the injection phase ===================
		packLoadState.refreshPackLoadState();

		hasInjected = true;
		return hookResults;
	}

	@Override public String isPremiumCheck() {
		return "A SnapTools Pack";
	}
}
