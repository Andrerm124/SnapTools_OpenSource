package com.ljmu.andre.snaptools.Networking.Helpers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ljmu.andre.CBIDatabase.CBITable;
import com.ljmu.andre.snaptools.Databases.CacheDatabase;
import com.ljmu.andre.snaptools.Databases.Tables.ServerPackObject;
import com.ljmu.andre.snaptools.Framework.MetaData.PackUpdateMetaData;
import com.ljmu.andre.snaptools.Networking.Packets.PackDataPacket;
import com.ljmu.andre.snaptools.Networking.WebRequest;
import com.ljmu.andre.snaptools.Networking.WebRequest.WebResponseListener;
import com.ljmu.andre.snaptools.Networking.WebResponse;
import com.ljmu.andre.snaptools.STApplication;
import com.ljmu.andre.snaptools.Utils.Assert;
import com.ljmu.andre.snaptools.Utils.DeviceIdManager;
import com.ljmu.andre.snaptools.Utils.MiscUtils;
import com.ljmu.andre.snaptools.Utils.PackUtils;

import hugo.weaving.DebugLog;
import timber.log.Timber;

import static com.ljmu.andre.GsonPreferences.Preferences.getPref;
import static com.ljmu.andre.snaptools.Utils.Constants.PACK_CHECK_COOLDOWN;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.STKN;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class CheckForUpdates {
	public static final String CHECK_UPDATE_URL = "https://snaptools.org/SnapTools/Scripts/check_version.php";
	public static final String TAG = "check_updates";

	public static boolean shouldUseCache() {
		return PackUtils.timeSinceLastPackCheck() < PACK_CHECK_COOLDOWN;
	}

	// TODO Setup JSON Error=True detection
	@DebugLog public static void performCheck(
			@Nullable Activity activity,
			@NonNull String packType,
			@NonNull String snapVersion,
			@NonNull String moduleVersion,
			@NonNull PackUpdateMetaData updateMetaData) {

		String stToken = getPref(STKN);

		if (stToken == null) {
			updateMetaData.setHasFailed(true);
			updateMetaData.result();
			return;
		}

		String device_id = DeviceIdManager.getDeviceId(activity);
		Assert.notNull("Null DeviceId", device_id);

		new WebRequest.Builder()
				.setUrl(CHECK_UPDATE_URL)
				.setTag(TAG)
				.setPacketClass(PackDataPacket.class)
				.shouldClearCache(true)
				.setContext(activity)
				// ===========================================================================
				.addParam("token", stToken)
				.addParam("device_id", device_id)
				.addParam("pack_type", packType)
				.addParam("sc_version", snapVersion)
				.addParam("allow_dev", String.valueOf(STApplication.DEBUG))
				// ===========================================================================
				.setCallback(new WebResponseListener() {
					@Override public void success(WebResponse webResponse) {
						PackDataPacket packDataPacket = webResponse.getResult();

						if (packDataPacket.error) {
							Timber.d("Failed to GetServerPacks: "
									+ packDataPacket.getErrorMessage());
							updateMetaData.setHasFailed(true);
							updateMetaData.result();
							return;
						}

						int versionOffset = MiscUtils.versionCompare(packDataPacket.getModVersion(), moduleVersion);

						boolean hasUpdate = versionOffset > 0;
						updateMetaData.setHasUpdate(hasUpdate);
						updateMetaData.setDevelopment(packDataPacket.development);
						updateMetaData.setPackVersion(packDataPacket.getModVersion());
						updateMetaData.setScVersion(snapVersion);
						updateMetaData.setType(packType);

						ServerPackObject serverPackObject = ServerPackObject.fromPackMetaData(updateMetaData);

						CBITable<ServerPackObject> serverPackTable = CacheDatabase.getTable(ServerPackObject.class);
						serverPackTable.insert(serverPackObject);

						updateMetaData.result();
					}

					@Override public void error(WebResponse webResponse) {
						if (webResponse.getException() != null)
							Timber.e(webResponse.getException(), webResponse.getMessage());
						else
							Timber.w(webResponse.getMessage());

						updateMetaData.setHasFailed(true);
						updateMetaData.result();
					}
				})
				.performRequest();
	}
}