package com.ljmu.andre.snaptools.Fragments.Children;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljmu.andre.Translation.Translator;
import com.ljmu.andre.snaptools.EventBus.EventBus;
import com.ljmu.andre.snaptools.EventBus.Events.ReqGoogleAuthEvent;
import com.ljmu.andre.snaptools.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class LoginFragment extends Fragment {
	@Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View layoutContainer = inflater.inflate(R.layout.child_frag_login, container, false);
		ButterKnife.bind(this, layoutContainer);

		EventBus.soleRegister(this);
		return layoutContainer;
	}

	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Translator.translateFragment(this);
	}

	@Override public void onDestroy() {
		super.onDestroy();
		EventBus.soleUnregister(this);
	}

	@OnClick(R.id.btn_login) public void onViewClicked() {
		Timber.d("Login");
		EventBus.getInstance().post(new ReqGoogleAuthEvent());
	}
/*
	public void handleResizing() {
		((TextView)getActivity().findViewById(R.id.btn_login)).setTextSize(10);
		((TextView)getActivity().findViewById(R.id.textView2)).setTextSize(10);
		((TextView)getActivity().findViewById(R.id.textView)).setTextSize(14);

	}*/
}
