package com.ljmu.andre.snaptools.ModulePack.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljmu.andre.snaptools.Fragments.FragmentHelper;
import com.ljmu.andre.snaptools.ModulePack.Fragments.KotlinViews.StealthViewProvider;

import static com.ljmu.andre.snaptools.Utils.ResourceUtils.getIdFromString;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class StealthViewingFragment extends FragmentHelper {
	@Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return new StealthViewProvider().getMainContainer(getActivity());
	}

	@Override public String getName() {
		return "Stealth Viewing";
	}

	@Override public Integer getMenuId() {
		return getIdFromString(getName());
	}
}
