package com.ljmu.andre.snaptools.FCM.MessageTypes;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.ljmu.andre.snaptools.EventBus.EventBus;
import com.ljmu.andre.snaptools.Exceptions.MessageNotBuiltException;
import com.ljmu.andre.snaptools.MainActivity;
import com.ljmu.andre.snaptools.R;
import com.ljmu.andre.snaptools.SplashActivity;

import java.util.Map;
import java.util.Map.Entry;

import timber.log.Timber;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class NotificationMessage extends Message {
	@Nullable private Map<String, String> parentMessageData;

	protected NotificationMessage(@Nullable Map<String, String> parentMessageData) {
		this.parentMessageData = parentMessageData;
	}

	@Override void event(MainActivity activity, EventBus eventBus) {
	}

	@Override protected void buildFromData(Context context, Map<String, String> data) throws MessageNotBuiltException {
		id = data.get("id");
		title = data.get("notification_title");
		message = data.get("notification_message");

		if (parentMessageData != null) {
			if (title == null)
				title = parentMessageData.get("title");

			if (message == null)
				message = parentMessageData.get("message");
		}

		if (title == null || message == null)
			throw new MessageNotBuiltException("Notification not provided a title or message");
	}

	public void sendNotification(Context context) {
		Timber.d("Sending notification to foreground");
		NotificationManager notificationManager = (NotificationManager)
				context.getSystemService(NOTIFICATION_SERVICE);

		Intent intent = new Intent(context, SplashActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

// use System.currentTimeMillis() to have a unique ID for the pending intent
		PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_CANCEL_CURRENT);

		Notification n = new Notification.Builder(context)
				.setContentTitle(getTitle())
				.setContentText(getMessage())
				.setSmallIcon(R.drawable.snaptools_notification)
				.setColor(ContextCompat.getColor(context, R.color.primary))
				.setContentIntent(pIntent)
				.setAutoCancel(true)
				.build();

		notificationManager.notify(0, n);
		Timber.d("Notification sent");
	}
}
