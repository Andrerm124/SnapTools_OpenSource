package com.ljmu.andre.snaptools;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.ljmu.andre.snaptools.Utils.Constants;
import com.ljmu.andre.snaptools.Utils.MiscUtils;
import com.ljmu.andre.snaptools.Utils.RemoteConfigDefaults;
import com.ljmu.andre.snaptools.Utils.ResourceUtils;

import timber.log.Timber;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class SplashActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Timber.d("SPLASH");

		if (!initRemoteConfig()) {
			Timber.d("Awaiting remote config results first");

			setContentView(R.layout.activity_splash);
			ResourceUtils.<TextView>getView(this, R.id.txt_message)
					.setText("Loading...");

			return;
		}

		openMainActivity();
	}

	/**
	 * ===========================================================================
	 * Init the remote config
	 * Returns FALSE if config is requires load completion first
	 * ===========================================================================
	 */
	private boolean initRemoteConfig() {
		FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();

		// Create a Remote Config Setting to enable developer mode, which you can use to increase
		// the number of fetches available per hour during development. See Best Practices in the
		// README for more information.
		// [START enable_dev_mode]
		FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
				.setDeveloperModeEnabled(BuildConfig.DEBUG)
				.build();

		remoteConfig.setConfigSettings(configSettings);
		// [END enable_dev_mode]

		// Set default Remote Config parameter values. An app uses the in-app default values, and
		// when you need to adjust those defaults, you set an updated value for only the values you
		// want to change in the Firebase console. See Best Practices in the README for more
		// information.
		// [START set_default_values]
		remoteConfig.setDefaults(RemoteConfigDefaults.get());

		boolean shouldWaitForUpdate = false;

		long lastFetch = remoteConfig.getInfo().getFetchTimeMillis();
		Timber.d("LastFetched: [LastStatus: %s][Time: %s][Offset: %s]", remoteConfig.getInfo().getLastFetchStatus(), lastFetch, MiscUtils.calcTimeDiff(lastFetch));

		if (MiscUtils.calcTimeDiff(remoteConfig.getInfo().getFetchTimeMillis()) > Constants.LAST_FETCH_FORCE_NEW_VALUES) {
			shouldWaitForUpdate = true;
		}

		// [END set_default_values]
		remoteConfig.activateFetched();

		boolean finalShouldWaitForUpdate = shouldWaitForUpdate;
		remoteConfig.fetch(Constants.REMOTE_CONFIG_COOLDOWN)
				.addOnCompleteListener(task -> {
					if (task.isSuccessful()) {
						// After config data is successfully fetched, it must be activated before newly fetched
						// values are returned.
						remoteConfig.activateFetched();
					} else {
						Timber.e("Failed to fetch remote config values");
					}

					if (finalShouldWaitForUpdate) {
						openMainActivity();
					}
				});

		return !shouldWaitForUpdate;
	}

	private void openMainActivity() {
		if (!STApplication.DEBUG)
			Constants.initConstants();

		Intent intent;

		if (getIntent().getExtras() != null) {
			intent = new Intent(getIntent());
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		} else
			intent = new Intent();

		intent.setComponent(new ComponentName(this, MainActivity.class));
		startActivity(intent);
		finish();
	}
}
