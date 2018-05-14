package com.ljmu.andre.snaptools.FCM.MessageTypes;

import android.content.Context;

import com.ljmu.andre.snaptools.EventBus.EventBus;
import com.ljmu.andre.snaptools.Exceptions.MessageLifespanException;
import com.ljmu.andre.snaptools.Exceptions.MessageNotBuiltException;
import com.ljmu.andre.snaptools.MainActivity;

import java.util.Map;

import static com.ljmu.andre.GsonPreferences.Preferences.removePref;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class SetPreferenceMessage extends Message {
	@Override void event(MainActivity activity, EventBus eventBus) {
	}


	@Override protected void buildFromData(Context context, Map<String, String> data) throws MessageNotBuiltException, MessageLifespanException {
		super.buildFromData(context, data);
		allowNotification = false;

		String function = assertData("No function provided", data.get("function"));
		String preference = assertData("No preference provided", data.get("preference"));

		switch (function) {
			case "remove":
				removePref(preference);
				break;
			default:
				throw new MessageNotBuiltException("Unknown function: " + function);
		}
	}
}
