package com.ljmu.andre.snaptools.FCM.MessageTypes;

import android.app.Dialog;
import android.content.Context;

import com.ljmu.andre.snaptools.Dialogs.Content.ModularButtonPrimary;
import com.ljmu.andre.snaptools.Dialogs.Content.ModularButtonsContainer;
import com.ljmu.andre.snaptools.Dialogs.Content.ModularHeader;
import com.ljmu.andre.snaptools.Dialogs.Content.ModularTextView;
import com.ljmu.andre.snaptools.Dialogs.ModularDialog;
import com.ljmu.andre.snaptools.EventBus.EventBus;
import com.ljmu.andre.snaptools.Exceptions.MessageLifespanException;
import com.ljmu.andre.snaptools.Exceptions.MessageNotBuiltException;
import com.ljmu.andre.snaptools.MainActivity;

import java.util.Map;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class ServerMessage extends Message {
	private boolean showKonfetti;

	@Override void event(MainActivity activity, EventBus eventBus) {
		if (activity == null || activity.isFinishing())
			return;

		//noinspection ConstantConditions
		new ModularDialog(activity)
				.addComponent(new ModularHeader(getTitle()))
				.addComponent(new ModularTextView(getMessage()))
				.addComponent(
						new ModularButtonsContainer()
								.addButton(
										new ModularButtonPrimary(
												"Understood",
												Dialog::dismiss
										)
								)
				)
				.show();

		if (showKonfetti)
			activity.triggerKonfetti();
	}

	@Override protected void buildFromData(Context context, Map<String, String> data) throws MessageNotBuiltException, MessageLifespanException {
		super.buildFromData(context, data);

		if (getTitle() == null)
			title = "Server Message";

		if (getMessage() == null)
			throw new MessageNotBuiltException("Server message requires requires message text");

		String showKonfettiString = data.get("show_konfetti");
		if (showKonfettiString != null)
			showKonfetti = Boolean.valueOf(showKonfettiString);
	}
}
