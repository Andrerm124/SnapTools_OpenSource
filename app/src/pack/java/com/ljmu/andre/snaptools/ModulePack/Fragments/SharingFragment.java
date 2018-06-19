package com.ljmu.andre.snaptools.ModulePack.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ljmu.andre.snaptools.BuildConfig;
import com.ljmu.andre.snaptools.Dialogs.DialogFactory;
import com.ljmu.andre.snaptools.Fragments.FragmentHelper;
import com.ljmu.andre.snaptools.ModulePack.Fragments.KotlinViews.SharingView;

import timber.log.Timber;

import static com.ljmu.andre.GsonPreferences.Preferences.getPref;
import static com.ljmu.andre.snaptools.ModulePack.Utils.ModulePreferenceDef.BATCHED_MEDIA_CAP;
import static com.ljmu.andre.snaptools.ModulePack.Utils.ModulePreferenceDef.SHOW_SHARING_TUTORIAL;
import static com.ljmu.andre.snaptools.ModulePack.Utils.ViewFactory.getDefaultTextView;
import static com.ljmu.andre.snaptools.ModulePack.Utils.ViewFactory.getLabelledSeekbar;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.COMPRESSION_QUALITY;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.SHARING_COMPRESS_VIDEOS;
import static com.ljmu.andre.snaptools.Utils.PreferenceHelpers.putAndKill;
import static com.ljmu.andre.snaptools.Utils.ResourceUtils.getDSLView;
import static com.ljmu.andre.snaptools.Utils.ResourceUtils.getIdFromString;
import static com.ljmu.andre.snaptools.Utils.ResourceUtils.getStyle;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class SharingFragment extends FragmentHelper {
	@Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		LinearLayout sharingContainer = new SharingView().getContainer(getActivity());

		/*SwitchCompat switchShowTutorial = getDSLView(sharingContainer, "switch_sharing_show_tutorial");
		switchShowTutorial.setChecked(getPref(SHOW_SHARING_TUTORIAL));
		switchShowTutorial.setOnCheckedChangeListener(
				(buttonView, isChecked) -> putAndKill(SHOW_SHARING_TUTORIAL, isChecked, getActivity())
		);*/

		return sharingContainer;
	}

	@Override public String getName() {
		return "Sharing";
	}

	@Override public Integer getMenuId() {
		return getIdFromString(getName());
	}


}
