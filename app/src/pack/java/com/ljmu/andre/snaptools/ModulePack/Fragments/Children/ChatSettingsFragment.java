package com.ljmu.andre.snaptools.ModulePack.Fragments.Children;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.ljmu.andre.snaptools.Fragments.FragmentHelper;
import com.ljmu.andre.snaptools.ModulePack.Fragments.KotlinViews.ChatSettingsViewProvider;
import com.ljmu.andre.snaptools.ModulePack.Utils.ViewFactory;

import static com.ljmu.andre.GsonPreferences.Preferences.getPref;
import static com.ljmu.andre.snaptools.ModulePack.Utils.ModulePreferenceDef.SAVE_CHAT_IN_SC;
import static com.ljmu.andre.snaptools.ModulePack.Utils.ModulePreferenceDef.STORE_CHAT_MESSAGES;
import static com.ljmu.andre.snaptools.Utils.ResourceUtils.getIdFromString;
import static com.ljmu.andre.snaptools.Utils.PreferenceHelpers.putAndKill;
import static com.ljmu.andre.snaptools.Utils.StringEncryptor.decryptMsg;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class ChatSettingsFragment extends FragmentHelper {

	@Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return new ChatSettingsViewProvider().getMainContainer(getActivity());
	}

	@Override public String getName() {
		return /*Chat Settings*/ decryptMsg(new byte[]{72, -125, 37, -36, 49, -23, 79, 9, -78, -126, 1, -75, -36, -108, -11, -62});
	}

	@Override public Integer getMenuId() {
		return getIdFromString(getName());
	}
}
