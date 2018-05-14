package com.ljmu.andre.snaptools.Framework;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.ljmu.andre.snaptools.Fragments.FragmentHelper;
import com.ljmu.andre.snaptools.Framework.Utils.LoadState.State;
import com.ljmu.andre.snaptools.Framework.Utils.ModuleLoadState;
import com.ljmu.andre.snaptools.R;
import com.ljmu.andre.snaptools.UIComponents.Adapters.StatefulEListAdapter.StatefulListable;
import com.ljmu.andre.snaptools.Utils.ContextHelper;
import com.ljmu.andre.snaptools.Utils.PreferenceHelpers;

import java.util.HashSet;
import java.util.List;

import hugo.weaving.DebugLog;
import timber.log.Timber;

import static android.view.View.GONE;
import static com.ljmu.andre.GsonPreferences.Preferences.getPref;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.DISABLED_MODULES;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public abstract class Module implements StatefulListable<Void> {
	protected ModuleLoadState moduleLoadState;
	private String name;
	private boolean hasInjected = false;
	private boolean canBeDisabled;

	public Module(String name, boolean canBeDisabled) {
		this.name = name;
		this.canBeDisabled = canBeDisabled;
	}

	public abstract FragmentHelper[] getUIFragments();

	public ModuleLoadState getModuleLoadState() {
		return moduleLoadState;
	}

	@DebugLog public ModuleLoadState injectHooks(ClassLoader snapClassLoader, Activity snapActivity,
	                                             ModuleLoadState moduleLoadState) {
		if (hasInjected) {
			Timber.d("Tried to reapply hook: "
					+ name());
			return null;
		}

		hasInjected = true;

		this.moduleLoadState = moduleLoadState;

		if (canBeDisabled()) {
			HashSet<String> moduleSet = getPref(DISABLED_MODULES);

			if (moduleSet.contains(name())) {
				moduleLoadState.setState(State.SKIPPED);
				return moduleLoadState;
			}
		}

		loadHooks(snapClassLoader, snapActivity);

		if (moduleLoadState.getFailedHooks() <= 0 &&
				moduleLoadState.getSuccessfulHooks() > 0)
			moduleLoadState.setState(State.SUCCESS);

		return moduleLoadState;
	}

	public String name() {
		return name;
	}

	protected boolean canBeDisabled() {
		return canBeDisabled;
	}

	public abstract void loadHooks(ClassLoader snapClassLoader, Activity snapActivity);

	@Override public List<Void> getChildren() {
		return null;
	}

	@Override public void updateHeaderText(View container, TextView headerHolder) {
		headerHolder.setText(name());
	}

	@Override public void updateMessageText(View container, TextView messageHolder, Void child) {
	}

	@Override public void updateHeaderStateHolder(TextView stateHolder) {
		boolean isActive = !PreferenceHelpers.collectionContains(DISABLED_MODULES, name());

		if (isActive) {
			stateHolder.setText("Active");
			stateHolder.setBackgroundResource(R.color.success);
			int color = ContextHelper.getModuleResources(stateHolder.getContext()).getColor(R.color.textSecondary);
			stateHolder.setTextColor(color);
		} else {
			stateHolder.setText("Inactive");
			stateHolder.setBackgroundResource(R.color.error);
			int color = ContextHelper.getModuleResources(stateHolder.getContext()).getColor(R.color.textPrimary);
			stateHolder.setTextColor(color);
		}
	}

	@Override public void updateMessageStateHolder(TextView stateHolder, Void child) {
		stateHolder.setVisibility(GONE);
	}
}
