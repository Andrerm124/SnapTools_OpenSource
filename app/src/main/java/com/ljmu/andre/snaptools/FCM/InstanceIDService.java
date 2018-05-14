package com.ljmu.andre.snaptools.FCM;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.ljmu.andre.snaptools.EventBus.EventBus;
import com.ljmu.andre.snaptools.EventBus.Events.FirebaseTokenRefreshEvent;

import timber.log.Timber;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class InstanceIDService extends FirebaseInstanceIdService {
	@Override public void onCreate() {
		super.onCreate();
		EventBus.soleRegister(this);
	}

	@Override public void onDestroy() {
		super.onDestroy();
		EventBus.soleUnregister(this);
	}

	@Override public void onTokenRefresh() {
		Timber.d("FireToken Refreshed");
		String refreshedToken = FirebaseInstanceId.getInstance().getToken();
		EventBus.getInstance().post(new FirebaseTokenRefreshEvent(refreshedToken));
	}

	public static String getNonNullFireToken() {
		String fireToken = FirebaseInstanceId.getInstance().getToken();
		return fireToken == null ? "null" : fireToken;
	}
}
