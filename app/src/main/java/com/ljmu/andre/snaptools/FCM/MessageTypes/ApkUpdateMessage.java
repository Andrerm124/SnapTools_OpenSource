package com.ljmu.andre.snaptools.FCM.MessageTypes;

import com.ljmu.andre.snaptools.EventBus.EventBus;
import com.ljmu.andre.snaptools.MainActivity;
import com.ljmu.andre.snaptools.Networking.Helpers.CheckAPKUpdate;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class ApkUpdateMessage extends Message {
	@Override void event(MainActivity activity, EventBus eventBus) {
		if (activity == null || activity.isFinishing())
			return;

		CheckAPKUpdate.checkApkUpdate(activity, false);
	}
}
