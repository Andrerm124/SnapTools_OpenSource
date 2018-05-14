package com.ljmu.andre.snaptools.FCM.MessageTypes;

import android.content.Context;

import com.ljmu.andre.snaptools.Dialogs.DialogFactory;
import com.ljmu.andre.snaptools.EventBus.EventBus;
import com.ljmu.andre.snaptools.Exceptions.MessageLifespanException;
import com.ljmu.andre.snaptools.Exceptions.MessageNotBuiltException;
import com.ljmu.andre.snaptools.MainActivity;
import com.ljmu.andre.snaptools.Utils.ResourceUtils;

import java.util.Map;

import static com.ljmu.andre.GsonPreferences.Preferences.putPref;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.LAST_CHECK_PACKS;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.LAST_CHECK_SHOP;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class ItemPurchaseMessage extends Message {
	private boolean state;

	@Override void event(MainActivity activity, EventBus eventBus) {
		putPref(LAST_CHECK_SHOP, 0L);
		putPref(LAST_CHECK_PACKS, 0L);

		if (activity == null || activity.isFinishing())
			return;

		if (state) {
			DialogFactory.createBasicMessage(
					activity,
					title,
					message
			).show();
		} else {
			DialogFactory.createErrorDialog(
					activity,
					title,
					message
			).show();
		}
	}

	@Override protected void buildFromData(Context context, Map<String, String> data) throws MessageNotBuiltException, MessageLifespanException {
		super.buildFromData(context, data);

		state = Boolean.valueOf(data.get("state"));

		if (targetMenu == null)
			targetMenu = ResourceUtils.getId(context, "nav_shop");
	}
}
