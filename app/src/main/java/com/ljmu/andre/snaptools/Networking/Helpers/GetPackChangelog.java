package com.ljmu.andre.snaptools.Networking.Helpers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ljmu.andre.snaptools.Networking.Packets.PackDataPacket;
import com.ljmu.andre.snaptools.Networking.WebRequest;
import com.ljmu.andre.snaptools.Networking.WebRequest.WebResponseListener;
import com.ljmu.andre.snaptools.Networking.WebResponse;
import com.ljmu.andre.snaptools.Networking.WebResponse.PacketResultListener;
import com.ljmu.andre.snaptools.Utils.DeviceIdManager;

import hugo.weaving.DebugLog;
import timber.log.Timber;

import static com.ljmu.andre.GsonPreferences.Preferences.getPref;
import static com.ljmu.andre.snaptools.Networking.WebRequest.assertParam;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.STKN;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.ST_EMAIL;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class GetPackChangelog {
	public static final String TAG = "get_changelog";
	private static final String GET_CHANGELOG_URL = "https://snaptools.org/SnapTools/Scripts/get_pack_changelog.php";

	@DebugLog public static void performCheck(
			@Nullable Activity activity,
			@NonNull String packType,
			@NonNull String snapVersion,
			@NonNull String packFlavour,
			PacketResultListener<PackDataPacket> packetResultListener) {
		Class cls = GetPackChangelog.class;
		String token;
		String email;
		String deviceId;

		try {
			token = assertParam(cls, "Invalid Token", getPref(STKN));
			email = assertParam(cls, "Invalid Email", getPref(ST_EMAIL));
			deviceId = assertParam(cls, "Invalid Device ID", DeviceIdManager.getDeviceId(activity));
		} catch (IllegalArgumentException e) {
			Timber.e(e);
			packetResultListener.error(
					"Missing Authentication Parameters",
					e,
					202
			);
			return;
		}

		new WebRequest.Builder()
				.setUrl(GET_CHANGELOG_URL)
				.setTag(TAG)
				.setPacketClass(PackDataPacket.class)
				.shouldClearCache(true)
				.setContext(activity)
				// ===========================================================================
				.addParam("token", token)
				.addParam("device_id", deviceId)
				.addParam("pack_type", packType)
				.addParam("sc_version", snapVersion)
				.addParam("email", email)
				.addParam("pack_flavour", packFlavour)
				// ===========================================================================
				.setCallback(new WebResponseListener() {
					@Override public void success(WebResponse webResponse) {
						PackDataPacket packDataPacket = webResponse.getResult();

						if (packDataPacket == null) {
							packetResultListener.error(
									"Received Empty Result!",
									null,
									203
							);

							return;
						}

						if (packDataPacket.banned) {
							packetResultListener.error(
									packDataPacket.getBanReason(),
									null,
									105
							);

							return;
						}

						packDataPacket.setPackType(packType);
						packDataPacket.setScVersion(snapVersion);

						packetResultListener.success(
								"Success",
								packDataPacket
						);
					}

					@Override public void error(WebResponse webResponse) {
						if (webResponse.getException() != null)
							Timber.e(webResponse.getException(), webResponse.getMessage());
						else
							Timber.w(webResponse.getMessage());

						packetResultListener.error(
								webResponse.getMessage(),
								webResponse.getException(),
								webResponse.getResponseCode()
						);
					}
				})
				.performRequest();
	}
}
