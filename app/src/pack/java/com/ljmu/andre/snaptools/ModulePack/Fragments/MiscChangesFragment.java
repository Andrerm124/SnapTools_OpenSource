package com.ljmu.andre.snaptools.ModulePack.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.ljmu.andre.snaptools.Fragments.FragmentHelper;
import com.ljmu.andre.snaptools.ModulePack.Fragments.KotlinViews.MiscChangesViewProvider;
import com.ljmu.andre.snaptools.ModulePack.Notifications.SafeToastAdapter;
import com.ljmu.andre.snaptools.ModulePack.Utils.Result;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import timber.log.Timber;

import static com.ljmu.andre.GsonPreferences.Preferences.getCreateDir;
import static com.ljmu.andre.GsonPreferences.Preferences.getPref;
import static com.ljmu.andre.snaptools.ModulePack.Utils.ModulePreferenceDef.CURRENT_FONT;
import static com.ljmu.andre.snaptools.ModulePack.Utils.ModulePreferenceDef.FONTS_PATH;
import static com.ljmu.andre.snaptools.Utils.PreferenceHelpers.putAndKill;
import static com.ljmu.andre.snaptools.Utils.ResourceUtils.getDSLView;
import static com.ljmu.andre.snaptools.Utils.ResourceUtils.getIdFromString;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class MiscChangesFragment extends FragmentHelper {
	private MiscChangesViewProvider viewProvider;

	// ===========================================================================
	private ViewGroup mainContainer;
	private Spinner fontSpinner;
	// ===========================================================================

	@Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		viewProvider = new MiscChangesViewProvider(getActivity(), this::handleEvent);
		mainContainer = viewProvider.getMainContainer();
		fontSpinner = getDSLView(mainContainer, "font_selector_spinner");

		return mainContainer;
	}

	@Override public void onResume() {
		super.onResume();
		initFontSpinner();
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	private void initFontSpinner() {
		List<String> fontList = viewProvider.getFontList();
		fontList.clear();
		fontList.add("Default");

		List<String> items = new ArrayList<>();
		File fontFolder = getCreateDir(FONTS_PATH);

		if (fontFolder != null) {
			Timber.d("Searching for fonts in \"" + fontFolder.getPath() + "\"");

			File[] files = fontFolder.listFiles((file, s) -> s.endsWith(".ttf"));

			if (files != null) {
				for (File f : files) {
					//noinspection OctalInteger
					byte[] compare = {00, 01, 00, 00, 00};
					byte[] check = new byte[5];
					try {
						InputStream is = new FileInputStream(f);
						is.read(check);
						is.close();
					} catch (IOException e) {
						Timber.e(e);
					}

					Timber.d("Template: \"" + Arrays.toString(compare) + "\"|" + "Input: \"" + Arrays.toString(check) + "\"");
					if (Arrays.equals(compare, check)) {
						items.add(f.getName());
						Timber.d("Font is valid. Adding to list.");
					}
				}
			}
			fontList.addAll(items);
		}

		viewProvider.refreshFontAdapter();

		String currentFont = getPref(CURRENT_FONT);
		fontSpinner.setSelection(fontList.indexOf(currentFont), false);
	}

	private void handleEvent(@NotNull Result<MiscChangesEvent, Object> eventResult) {
		switch (eventResult.getKey()) {
			case FONT_SELECTED:
				String selectedFont = (String) eventResult.getValue();
				Timber.d("Selected Font: " + selectedFont);

				if (selectedFont.equalsIgnoreCase("Select font")) {
					break;
				}

				if (selectedFont.equalsIgnoreCase("No fonts found")) {
					SafeToastAdapter.showErrorToast(getActivity(), "No valid .ttf files found");
					break;
				}

				putAndKill(CURRENT_FONT, selectedFont, getActivity());

				/*// ===========================================================================
				Observable<Object> deleteFont1 = transformShellObservable(// <- Check function javadoc

						// Our usual ShellCommand with a specific error message
						ShellUtils.sendCommand("rm /data/data/com.snapchat.android/files/OnDemandResources/typeface-asset/helvetica/helvetica/HelveticaLTPro-Roman.ttf"),
						"Warning: Initial delete of font failed\nFont was not copied"

				);
				Observable<Object> deleteFont2 = transformShellObservable(// <- Check function javadoc

						// Our usual ShellCommand with a specific error message
						ShellUtils.sendCommand("rm /data/data/com.snapchat.android/files/OnDemandResources/typeface-asset/helvetica/helvetica/HelveticaLTPro-Bold.ttf"),
						"Warning: Initial delete of font failed\nFont was not copied"

				);
				// ===========================================================================
				Observable<Object> copyFont1 = transformShellObservable(// <- Check function javadoc

						// Our usual ShellCommand with a specific error message
						ShellUtils.sendCommand("cp \"" + getPref(ModulePreferenceDef.FONTS_PATH) + selectedFont + "\" /data/data/com.snapchat.android/files/OnDemandResources/typeface-asset/helvetica/helvetica/HelveticaLTPro-Roman.ttf"),
						"Error: Font failed to copy"

				);
				Observable<Object> copyFont2 = transformShellObservable(// <- Check function javadoc

						// Our usual ShellCommand with a specific error message
						ShellUtils.sendCommand("cp \"" + getPref(ModulePreferenceDef.FONTS_PATH) + selectedFont + "\" /data/data/com.snapchat.android/files/OnDemandResources/typeface-asset/helvetica/helvetica/HelveticaLTPro-Pro.ttf"),
						"Error: Font failed to copy"

				);
				// ===========================================================================

				// Concatenate our 4 observables into 1 so that they run one after another in order
				Observable.concat(
						deleteFont1, deleteFont2,
						copyFont1, copyFont2
				).observeOn(AndroidSchedulers.mainThread())
						.subscribe(new ErrorObserver<Object>() {
							// If an error is thrown at any point, all further execution stops and this is triggered
							@Override public void onError(Throwable e) {
								super.onError(e);

								String message = "Error: Unknown error while changing font";

								if(e instanceof Exception)
									message = e.getMessage();

								SafeToastAdapter.showErrorToast(
										getActivity(), message
								);
							}

							// If all observables have triggered successfully
							@Override public void onComplete() {
								super.onComplete();

								SafeToastAdapter.showDefaultToast(getActivity(), "Successfully overwrote font file");
							}
						});*/
				break;
			default:
				throw new IllegalStateException("Unknown MiscChanges Event: " + eventResult.toString());
		}
	}

	/**
	 * ===========================================================================
	 * Takes the result of shellObservable and maps the value to an exception
	 * - If when ran the observable returns FALSE, an exception will be thrown
	 * and caught in the Observable.concat(..).onError() function
	 * ===========================================================================
	 */
	private Observable<Object> transformShellObservable(Observable<Boolean> shellObservable, String errorMessage) {
		// Observable.map(...) takes the return value and runs it through a function to return something else
		return shellObservable.map(aBoolean -> {
			if (!aBoolean)
				throw new Exception(errorMessage);

			return new Object();
		});
	}

	public enum MiscChangesEvent {
		FONT_SELECTED
	}

	@Override public String getName() {
		return "Misc Changes";
	}

	@Override public Integer getMenuId() {
		return getIdFromString(getName());
	}


}
