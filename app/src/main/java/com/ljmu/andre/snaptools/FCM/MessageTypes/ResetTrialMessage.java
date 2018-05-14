package com.ljmu.andre.snaptools.FCM.MessageTypes;

import com.ljmu.andre.snaptools.EventBus.EventBus;
import com.ljmu.andre.snaptools.MainActivity;
import com.ljmu.andre.snaptools.Utils.TrialUtils;

import static com.ljmu.andre.GsonPreferences.Preferences.putPref;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.TRIAL_MODE;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class ResetTrialMessage extends Message {
	@Override void event(MainActivity activity, EventBus eventBus) {
		putPref(TRIAL_MODE, TrialUtils.TRIAL_UNKNOWN);
	}
}
