package com.ljmu.andre.snaptools.FCM;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ljmu.andre.GsonPreferences.Preferences;
import com.ljmu.andre.snaptools.BuildConfig;
import com.ljmu.andre.snaptools.EventBus.EventBus;
import com.ljmu.andre.snaptools.FCM.MessageTypes.Message;
import com.ljmu.andre.snaptools.FCM.MessageTypes.NotificationMessage;
import com.ljmu.andre.snaptools.STApplication;

import java.util.Map;
import java.util.UUID;

import timber.log.Timber;

import static com.ljmu.andre.snaptools.BuildConfig.APPLICATION_ID;
import static com.ljmu.andre.snaptools.FCM.MessageTypes.Message.DEFAULT_LIFESPAN;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class MessagingService extends FirebaseMessagingService {
	@Override public void onCreate() {
		super.onCreate();
		EventBus.soleRegister(this);

		/**
		 * ===========================================================================
		 * Preference System Initialisation
		 * ===========================================================================
		 */
		try {
			Preferences.init(
					Preferences.getExternalPath() + "/" + STApplication.MODULE_TAG + "/"
			);
		} catch (Exception e) {
			Timber.e(e);
		}
	}

	@Override public void onDestroy() {
		super.onDestroy();
		EventBus.soleUnregister(this);
	}

	@Override public void onMessageReceived(RemoteMessage remoteMessage) {
		super.onMessageReceived(remoteMessage);

		try {
			Timber.d(
					"MessageId; " + remoteMessage.getMessageId()
							+ "\nMessageType: " + remoteMessage.getMessageType()
							+ "\nFrom: " + remoteMessage.getFrom()
							+ "\nReceived message: " + remoteMessage.getData()
			);

			Map<String, String> messageData = remoteMessage.getData();

			handleMessageData(this, messageData, EventBus.getInstance());
		} catch (Throwable t) {
			Timber.e(t, "Error inside Firebase Messaging Service");
		}
	}

	public static void handleMessageData(Context context, Map<String, String> messageData, EventBus eventBus) {
		try {
			String className = messageData.get("message_class");

			if (className == null) {
				if (messageData.size() == 1 && messageData.containsKey("profile"))
					return;

				Timber.e("Firebase Message doesn't have a class defined. Data: " + messageData);
				return;
			}

			className = Message.class.getPackage().getName() + "." + className;
			Timber.d("ClassPath: " + className);

			@SuppressWarnings("unchecked")
			Class<? extends Message> messageClass = (Class<? extends Message>) Class.forName(
					className,
					true,
					Message.class.getClassLoader()
			);

			assignMessageDefaults(messageData);

			Message message = Message.build(context, messageClass, messageData);

			if (message == null) {
				Timber.w("No message to post");
				return;
			}

			if (message instanceof NotificationMessage) {
				((NotificationMessage) message).sendNotification(context);
			}

			eventBus.post(message);
		} catch (Throwable t) {
			Timber.e(t, "Error inside Firebase Messaging Service");
		}
	}

	private static void assignMessageDefaults(Map<String, String> messageData) {
		if (!messageData.containsKey("id"))
			messageData.put("id", UUID.randomUUID().toString());

		if (!messageData.containsKey("received_time"))
			messageData.put("received_time", String.valueOf(System.currentTimeMillis()));

		if (!messageData.containsKey("background_life"))
			messageData.put("background_life", String.valueOf(DEFAULT_LIFESPAN));
	}

	public static void launchService(Context context) {
		Intent i = new Intent();
		i.setComponent(new ComponentName(APPLICATION_ID, APPLICATION_ID + ".FCM.MessagingService"));
		Timber.d("Launch Service Result: " + context.startService(i));
	}
}
