package com.ljmu.andre.snaptools.FCM.MessageTypes;

import com.ljmu.andre.snaptools.EventBus.EventBus;
import com.ljmu.andre.snaptools.MainActivity;
import com.ljmu.andre.snaptools.Networking.Helpers.Logout;
import com.ljmu.andre.snaptools.Networking.Packets.LogoutPacket;
import com.ljmu.andre.snaptools.Networking.WebResponse.PacketResultListener;
import com.ljmu.andre.snaptools.Utils.DeviceIdManager;

import static com.ljmu.andre.GsonPreferences.Preferences.getPref;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.STKN;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.ST_EMAIL;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class LogoutMessage extends Message {
	@Override void event(MainActivity activity, EventBus eventBus) {
		Logout.logout(
				activity,
				getPref(ST_EMAIL),
				getPref(STKN),
				DeviceIdManager.getDeviceId(activity),
				new PacketResultListener<LogoutPacket>() {
					@Override public void success(String message, LogoutPacket packet) {
					}

					@Override public void error(String message, Throwable t, int errorCode) {
					}
				}
		);

		activity.logoutPref();
	}
}
