package com.ljmu.andre.snaptools.Networking.Helpers;

import android.app.Activity;

import com.ljmu.andre.snaptools.Networking.Packets.AuthResultPacket;
import com.ljmu.andre.snaptools.Networking.WebRequest.Builder;
import com.ljmu.andre.snaptools.Networking.WebRequest.RequestType;
import com.ljmu.andre.snaptools.Networking.WebRequest.WebResponseListener;
import com.ljmu.andre.snaptools.Networking.WebResponse;
import com.ljmu.andre.snaptools.Networking.WebResponse.PacketResultListener;
import com.ljmu.andre.snaptools.Utils.CustomObservers.ErrorObserver;
import com.ljmu.andre.snaptools.Utils.DeviceIdManager;
import com.ljmu.andre.snaptools.Utils.PackUtils;

import hugo.weaving.DebugLog;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.ljmu.andre.GsonPreferences.Preferences.getPref;
import static com.ljmu.andre.GsonPreferences.Preferences.putPref;
import static com.ljmu.andre.snaptools.Networking.WebRequest.assertParam;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.FTKN;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.PACK_CHECKSUM;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.STKN;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.ST_EMAIL;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class AuthManager {
	private static final String AUTH_CHECK_URL2 = "https://snaptools.org/SnapTools/Scripts/usra.php";

	public static void authUser(Activity activity, PacketResultListener resultListener) {
		Class cls = AuthManager.class;
		String token;
		String email;
		String deviceId;

		try {
			token = assertParam(cls, "Invalid Token",
					getPref(STKN));
			email = assertParam(cls, "Invalid Email",
					getPref(ST_EMAIL));
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

		String fireToken = getPref(FTKN);

		if (fireToken == null)
			fireToken = "null";

		new Builder()
				.setUrl(AUTH_CHECK_URL2)
				.setType(RequestType.PACKET)
				.setPacketClass(AuthResultPacket.class)
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
						AuthResultPacket resultPacket = webResponse.getResult();

						if (resultPacket.banned) {
							resultListener.error(
									resultPacket.getBanReason(),
									null,
									resultPacket.getErrorCode()
							);
							return;
						}

						if (resultPacket.auth_status) {
							Observable.create(
									e -> {
										String generatedCheckSum = PackUtils.generatePacksChecksum();

										Timber.d("Putting checksum: "
												+ generatedCheckSum);

										if (generatedCheckSum != null)
											putPref(PACK_CHECKSUM, generatedCheckSum);
									})
									.subscribeOn(Schedulers.io())
									.subscribe(new ErrorObserver<>());
						} else {
							resultListener.error(
									resultPacket.getAuthDescription(),
									null,
									resultPacket.getErrorCode()
							);
						}
					}

					@Override public void error(WebResponse webResponse) {
						if (webResponse.getException() != null)
							Timber.e(webResponse.getException(), webResponse.getMessage());
						else
							Timber.w(webResponse.getMessage());

						performPackComparison(resultListener, webResponse);
					}
				})
				.performRequest();
	}

	@DebugLog private static void performPackComparison(PacketResultListener resultListener, WebResponse webResponse) {
		if (resultListener == null) {
			Timber.e("Passed a null AuthListener to an inherently useless function");
			return;
		}

		String storedCheckSum = getPref(PACK_CHECKSUM);

		if (storedCheckSum == null) {
			resultListener.error(
					"Failed to authenticate user",
					null,
					205
			);
			return;
		}

		String generatedCheckSum = PackUtils.generatePacksChecksum();

		if (generatedCheckSum == null || !storedCheckSum.equals(generatedCheckSum)) {
			resultListener.error(
					"Failed to authenticate user",
					null,
					206
			);
		}
	}
}