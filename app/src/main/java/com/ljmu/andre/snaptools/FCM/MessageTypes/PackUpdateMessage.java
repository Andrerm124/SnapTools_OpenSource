package com.ljmu.andre.snaptools.FCM.MessageTypes;

import com.ljmu.andre.snaptools.EventBus.EventBus;
import com.ljmu.andre.snaptools.Framework.FrameworkManager;
import com.ljmu.andre.snaptools.MainActivity;

import static com.ljmu.andre.GsonPreferences.Preferences.putPref;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.LAST_CHECK_PACKS;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class PackUpdateMessage extends Message {
	@Override void event(MainActivity activity, EventBus eventBus) {
		putPref(LAST_CHECK_PACKS, 0L);

		if (activity == null || activity.isFinishing())
			return;

		FrameworkManager.checkPacksForUpdate(activity);
	}
}
