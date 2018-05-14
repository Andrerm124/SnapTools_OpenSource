package com.ljmu.andre.snaptools.Utils;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.ljmu.andre.snaptools.BuildConfig;
import com.ljmu.andre.snaptools.STApplication;

import java.util.concurrent.TimeUnit;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

@SuppressWarnings("ConstantConditions")
public class Constants {
	public static final long LAST_FETCH_FORCE_NEW_VALUES = STApplication.DEBUG ? 30000 : TimeUnit.DAYS.toMillis(14);

	public static long SHOP_CHECK_COOLDOWN = STApplication.DEBUG ? 30000 : TimeUnit.HOURS.toMillis(1);
	public static long APK_CHECK_COOLDOWN = STApplication.DEBUG ? 30000 : TimeUnit.MINUTES.toMillis(15);
	public static long PACK_CHECK_COOLDOWN = STApplication.DEBUG ? 30000 : TimeUnit.MINUTES.toMillis(15);
	public static long FAQ_CHECK_COOLDOWN = STApplication.DEBUG ? 30000 : TimeUnit.HOURS.toMillis(12);
	public static long FEATURES_CHECK_COOLDOWN = STApplication.DEBUG ? 30000 : TimeUnit.HOURS.toMillis(12);
	public static long TRANSLATIONS_CHECK_COOLDOWN = STApplication.DEBUG ? 30000 : TimeUnit.HOURS.toMillis(4);
	public static long REMOTE_CONFIG_COOLDOWN = STApplication.DEBUG ? 30000 : TimeUnit.HOURS.toSeconds(4);
	public static long REMIND_TUTORIAL_COOLDOWN = TimeUnit.DAYS.toMillis(14);

	@RequiresFramework(73)
	public static void initConstants() {
		FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
		SHOP_CHECK_COOLDOWN = remoteConfig.getLong("shop_check_cooldown_hours");
		APK_CHECK_COOLDOWN = remoteConfig.getLong("apk_check_cooldown_minutes");
		PACK_CHECK_COOLDOWN = remoteConfig.getLong("pack_check_cooldown_minutes");
		FAQ_CHECK_COOLDOWN = remoteConfig.getLong("faq_check_cooldown_hours");
		FEATURES_CHECK_COOLDOWN = remoteConfig.getLong("features_check_cooldown_hours");
		TRANSLATIONS_CHECK_COOLDOWN = remoteConfig.getLong("translations_check_cooldown_hours");
		REMOTE_CONFIG_COOLDOWN = remoteConfig.getLong("remote_config_check_cooldown_hours");
		REMIND_TUTORIAL_COOLDOWN = remoteConfig.getLong("remind_tutorial_cooldown_days");
	}

	@Deprecated
	public static int getBuildConfig() {
		return BuildConfig.VERSION_CODE;
	}

	public static int getApkVersionCode() {
		return BuildConfig.VERSION_CODE;
	}

	public static String getApkVersionName() {
		return BuildConfig.VERSION_NAME;
	}

	public static String getApkFlavor() {
		return BuildConfig.FLAVOR;
	}

	@RequiresFramework(73)
	public static boolean isApkDebug() {
		return BuildConfig.DEBUG;
	}
}
