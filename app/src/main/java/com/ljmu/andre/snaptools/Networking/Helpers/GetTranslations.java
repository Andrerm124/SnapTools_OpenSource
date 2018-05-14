package com.ljmu.andre.snaptools.Networking.Helpers;

import android.content.Context;

import java.io.File;

import hugo.weaving.DebugLog;

import static com.ljmu.andre.GsonPreferences.Preferences.getCreateDir;
import static com.ljmu.andre.GsonPreferences.Preferences.getPref;
import static com.ljmu.andre.GsonPreferences.Preferences.putPref;
import static com.ljmu.andre.snaptools.Utils.Constants.TRANSLATIONS_CHECK_COOLDOWN;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.LAST_CHECK_TRANSLATIONS;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.TRANSLATIONS_PATH;
import static com.ljmu.andre.snaptools.Utils.MiscUtils.calcTimeDiff;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class GetTranslations extends CachedFileDownloader {
	private final String translationUrlRoot;
	private final String translationFilename;

	public GetTranslations(String translationUrlRoot, String translationFilename) {
		this.translationUrlRoot = translationUrlRoot;
		this.translationFilename = translationFilename;
	}

	public boolean shouldUseCacheExposed() {
		return shouldUseCache();
	}

	@Override protected boolean shouldUseCache() {
		long lastChecked = getPref(LAST_CHECK_TRANSLATIONS);

		return lastChecked == 0 || calcTimeDiff(lastChecked) > TRANSLATIONS_CHECK_COOLDOWN;
	}

	@Override protected File getCacheDir(Context context) {
		return getCreateDir(TRANSLATIONS_PATH);
	}

	@Override protected String getCachedFilename() {
		return translationFilename;
	}

	@Override protected String getURL() {
		return translationUrlRoot + translationFilename;
	}

	@Override protected void updateCacheTime() {
		putPref(LAST_CHECK_TRANSLATIONS, System.currentTimeMillis());
	}
}
