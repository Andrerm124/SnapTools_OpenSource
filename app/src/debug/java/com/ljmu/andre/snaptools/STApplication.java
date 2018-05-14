package com.ljmu.andre.snaptools;

import android.app.Application;

import com.github.anrwatchdog.ANRWatchDog;
import com.ljmu.andre.ErrorLogger.ErrorLogger;
import com.ljmu.andre.snaptools.Networking.VolleyHandler;
import com.ljmu.andre.snaptools.Utils.ContextHelper;
import com.ljmu.andre.snaptools.Utils.TimberUtils;

import hugo.weaving.DebugLog;
import timber.log.Timber;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class STApplication extends Application {
	public static final boolean DEBUG = true;
	public static final String MODULE_TAG = "SnapTools";
	public static final String PACKAGE = STApplication.class.getPackage().getName();

	private static STApplication mInstance;

	@Override public void onCreate() {
		TimberUtils.plantAppropriateTree();

		Timber.d("Starting Application [BuildVariant: DEBUG]");
		ContextHelper.set(getApplicationContext());

		VolleyHandler.init(getApplicationContext());
		ErrorLogger.init();

		Timber.d("Initialising Activities");
		super.onCreate();
		mInstance = this;

		try {
			ANRWatchDog.setDefaultUncaughtExceptionHandler(Thread.getDefaultUncaughtExceptionHandler());
			new ANRWatchDog(10000)
					.setANRListener(
							error -> Timber.e(error, "Application Not Responding")
					)
					.start();
		} catch (Throwable t) {
			Timber.e(t, "Error initialising ANRWatchdog");
		}
	}

	@DebugLog public static synchronized STApplication getInstance() {
		Timber.d("Instance: " + mInstance);
		return mInstance;
	}
}
