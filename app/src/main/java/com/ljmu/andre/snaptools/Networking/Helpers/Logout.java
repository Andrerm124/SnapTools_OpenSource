package com.ljmu.andre.snaptools.Networking.Helpers;

import android.app.Activity;

import com.ljmu.andre.snaptools.RedactedClasses.Answers;
import com.ljmu.andre.snaptools.RedactedClasses.CustomEvent;
import com.ljmu.andre.snaptools.Networking.Packets.LogoutPacket;
import com.ljmu.andre.snaptools.Networking.WebRequest.Builder;
import com.ljmu.andre.snaptools.Networking.WebRequest.WebResponseListener;
import com.ljmu.andre.snaptools.Networking.WebResponse;
import com.ljmu.andre.snaptools.Networking.WebResponse.PacketResultListener;
import com.ljmu.andre.snaptools.STApplication;
import com.ljmu.andre.snaptools.Utils.DeviceIdManager;

import timber.log.Timber;

import static com.ljmu.andre.snaptools.Networking.WebRequest.assertParam;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class Logout {
	private static final String LOGOUT_URL = "https://snaptools.org/SnapTools/Scripts/logout.php";

	public static void logout(Activity activity, String email, String token, String logoutDeviceId,
	                          PacketResultListener<LogoutPacket> resultListener) {
		if (STApplication.DEBUG) {
			if (token == null)
				token = "DEBUG";
		}


		Class cls = Logout.class;
		String deviceId;

		try {
			assertParam(cls, "Invalid Token",
					token);
			assertParam(cls, "Invalid Email",
					email);
			deviceId = assertParam(cls, "Invalid Device ID",
					DeviceIdManager.getDeviceId(activity));
		} catch (IllegalArgumentException e) {
			Timber.e(e);
			resultListener.error(
					"Missing Authentication Parameters",
					e,
					202
			);
			return;
		}

		new Builder()
				.setUrl(LOGOUT_URL)
				.setPacketClass(LogoutPacket.class)
				.shouldClearCache(true)
				// ===========================================================================
				.addParam("email", email)
				.addParam("device_id", deviceId)
				.addParam("logout_device_id", logoutDeviceId)
				.addParam("token", token)
				.addParam("debug", Boolean.toString(STApplication.DEBUG))
				// ===========================================================================
				.setContext(activity)
				.setCallback(new WebResponseListener() {
					@Override public void success(WebResponse webResponse) {
						resultListener.success("Success", webResponse.getResult());

						Answers.safeLogEvent(
								new CustomEvent("Logout")
										.putCustomAttribute("Success", "TRUE")
						);
					}

					@Override public void error(WebResponse webResponse) {
						if (webResponse.getException() != null)
							Timber.e(webResponse.getException(), webResponse.getMessage());
						else
							Timber.w(webResponse.getMessage());

						Answers.safeLogEvent(
								new CustomEvent("Logout")
										.putCustomAttribute("Success", "FALSE")
						);

						resultListener.error(
								webResponse.getMessage(),
								webResponse.getException(),
								webResponse.getResponseCode()
						);
					}
				})
				.performRequest();
	}
}
