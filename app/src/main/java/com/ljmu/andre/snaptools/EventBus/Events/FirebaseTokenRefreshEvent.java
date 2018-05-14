package com.ljmu.andre.snaptools.EventBus.Events;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class FirebaseTokenRefreshEvent {
	private String fireToken;

	public FirebaseTokenRefreshEvent(String fireToken) {
		this.fireToken = fireToken;
	}

	public String getFireToken() {
		return fireToken;
	}
}
