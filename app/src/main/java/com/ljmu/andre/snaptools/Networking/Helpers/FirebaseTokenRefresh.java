package com.ljmu.andre.snaptools.Networking.Helpers;

import android.app.Activity;

import com.ljmu.andre.snaptools.FCM.InstanceIDService;
import com.ljmu.andre.snaptools.Networking.Packets.Packet;
import com.ljmu.andre.snaptools.Networking.WebRequest;
import com.ljmu.andre.snaptools.Networking.WebRequest.WebResponseListener;
import com.ljmu.andre.snaptools.Networking.WebResponse;
import com.ljmu.andre.snaptools.Utils.DeviceIdManager;

import timber.log.Timber;

import static com.ljmu.andre.GsonPreferences.Preferences.getPref;
import static com.ljmu.andre.GsonPreferences.Preferences.putPref;
import static com.ljmu.andre.snaptools.Networking.WebRequest.assertParam;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.FTKN;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.ST_EMAIL;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class FirebaseTokenRefresh {
	private static final String FIRE_TOKEN_REFRESH_URL = "https://snaptools.org/SnapTools/Scripts/refresh_firebase_token.php";

	public static void refreshToken(Activity activity) {
		Class cls = FirebaseTokenRefresh.class;
		String email;
		String deviceId;

		try {
			email = assertParam(cls, "Invalid Email",
					getPref(ST_EMAIL));
			deviceId = assertParam(cls, "Invalid Device ID",
					DeviceIdManager.getDeviceId(activity));
		} catch (IllegalArgumentException e) {
			Timber.e(e);
			return;
		}

		new WebRequest.Builder()
				.setUrl(FIRE_TOKEN_REFRESH_URL)
				.setPacketClass(Packet.class)
				// ===========================================================================
				.addParam("email", email)
				.addParam("device_id", deviceId)
				.addParam("fire_token", InstanceIDService.getNonNullFireToken())
				// ===========================================================================
				.setCallback(
						new WebResponseListener() {
							@Override public void success(WebResponse webResponse) {
								Packet refreshPacket = webResponse.getResult();

								if (refreshPacket != null && !refreshPacket.error)
									putPref(FTKN, InstanceIDService.getNonNullFireToken());
							}

							@Override public void error(WebResponse webResponse) {
								if (webResponse.getException() != null)
									Timber.e(webResponse.getException(), webResponse.getMessage());
								else
									Timber.w(webResponse.getMessage());
							}
						}
				)
				.performRequest();
	}
}
