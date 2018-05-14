package com.ljmu.andre.snaptools.Networking.Helpers;

import android.app.Activity;

import com.ljmu.andre.snaptools.Networking.Packets.TrialPacket;
import com.ljmu.andre.snaptools.Networking.WebRequest.Builder;
import com.ljmu.andre.snaptools.Networking.WebRequest.RequestType;
import com.ljmu.andre.snaptools.Networking.WebRequest.WebResponseListener;
import com.ljmu.andre.snaptools.Networking.WebResponse;
import com.ljmu.andre.snaptools.Networking.WebResponse.PacketResultListener;
import com.ljmu.andre.snaptools.Utils.DeviceIdManager;

import timber.log.Timber;

import static com.ljmu.andre.GsonPreferences.Preferences.getPref;
import static com.ljmu.andre.snaptools.Networking.WebRequest.assertParam;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.FTKN;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.STKN;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.ST_EMAIL;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class CheckTrialMode {
	private static final String CHECK_TRIAL_URL = "https://snaptools.org/SnapTools/Scripts/check_trial_mode.php";
	private static final String ACTIVATE_TRIAL_URL = "https://snaptools.org/SnapTools/Scripts/activate_trial.php";

	public static void checkTrialMode(Activity activity, PacketResultListener<TrialPacket> resultListener) {
		Class cls = CheckTrialMode.class;
		String token;
		String email;
		String deviceId;

		try {
			token = assertParam(cls, "Invalid Token", getPref(STKN));
			email = assertParam(cls, "Invalid Email", getPref(ST_EMAIL));
			deviceId = assertParam(cls, "Invalid Device ID", DeviceIdManager.getDeviceId(activity));
		} catch (IllegalArgumentException e) {
			Timber.e(e);
			resultListener.error(
					"Missing Authentication Parameters",
					e,
					202
			);
			return;
		}

		String fireToken = getPref(FTKN);

		if (fireToken == null)
			fireToken = "null";

		new Builder()
				.setUrl(CHECK_TRIAL_URL)
				.setType(RequestType.PACKET)
				.setPacketClass(TrialPacket.class)
				.setContext(activity)
				// ===========================================================================
				.addParam("token", token)
				.addParam("device_id", deviceId)
				.addParam("email", email)
				.addParam("fire_token", fireToken)
				// ===========================================================================
				.useDefaultRetryPolicy()
				.setCallback(new WebResponseListener() {
					@Override public void success(WebResponse webResponse) {
						TrialPacket resultPacket = webResponse.getResult();

						if (resultPacket.banned) {
							resultListener.error(
									resultPacket.getBanReason(),
									null,
									resultPacket.getErrorCode()
							);
							return;
						}

						if (!resultPacket.auth_status) {
							resultListener.error(
									resultPacket.getAuthDescription(),
									null,
									resultPacket.getErrorCode()
							);
							return;
						}

						resultListener.success(
								resultPacket.auth_description,
								resultPacket
						);
					}

					@Override public void error(WebResponse webResponse) {
						if (webResponse.getException() != null)
							Timber.e(webResponse.getException(), webResponse.getMessage());
						else
							Timber.w(webResponse.getMessage());
					}
				})
				.performRequest();
	}

	public static void activateTrialMode(Activity activity, PacketResultListener<TrialPacket> resultListener) {
		String token = getPref(STKN);
		String fireToken = getPref(FTKN);
		String email = getPref(ST_EMAIL);
		String deviceId = DeviceIdManager.getDeviceId(activity);

		if (token == null || token.isEmpty() || deviceId == null || deviceId.isEmpty()
				|| email == null || email.isEmpty()) {
			resultListener.error(
					"Invalid token or device id",
					null,
					202
			);
			return;
		}

		new Builder()
				.setUrl(ACTIVATE_TRIAL_URL)
				.setType(RequestType.PACKET)
				.setPacketClass(TrialPacket.class)
				.setContext(activity)
				.addParam("token", token)
				.addParam("device_id", deviceId)
				.addParam("email", email)
				.addParam("fire_token", fireToken)
				.useDefaultRetryPolicy()
				.setCallback(new WebResponseListener() {
					@Override public void success(WebResponse webResponse) {
						TrialPacket resultPacket = webResponse.getResult();

						if (resultPacket.banned) {
							resultListener.error(
									resultPacket.getBanReason(),
									null,
									resultPacket.getErrorCode()
							);
							return;
						}

						if (!resultPacket.auth_status) {
							resultListener.error(
									resultPacket.getAuthDescription(),
									null,
									resultPacket.getErrorCode()
							);
							return;
						}

						resultListener.success(
								resultPacket.auth_description,
								resultPacket
						);
					}

					@Override public void error(WebResponse webResponse) {
						if (webResponse.getException() != null)
							Timber.e(webResponse.getException(), webResponse.getMessage());
						else
							Timber.w(webResponse.getMessage());

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
