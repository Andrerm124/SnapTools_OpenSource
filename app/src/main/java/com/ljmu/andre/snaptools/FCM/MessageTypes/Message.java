package com.ljmu.andre.snaptools.FCM.MessageTypes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.ljmu.andre.snaptools.EventBus.EventBus;
import com.ljmu.andre.snaptools.Exceptions.MessageLifespanException;
import com.ljmu.andre.snaptools.Exceptions.MessageNotBuiltException;
import com.ljmu.andre.snaptools.MainActivity;
import com.ljmu.andre.snaptools.Utils.CustomObservers.ErrorObserver;
import com.ljmu.andre.snaptools.Utils.ResourceUtils;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.STORED_MESSAGE_METADATA_CACHE;
import static com.ljmu.andre.snaptools.Utils.MiscUtils.isInForeground;
import static com.ljmu.andre.snaptools.Utils.PreferenceHelpers.addToCollection;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public abstract class Message {
	public static final long DEFAULT_LIFESPAN = TimeUnit.HOURS.toMillis(24);
	@NotNull protected String id;
	@Nullable protected String title;
	@Nullable protected String message;
	protected boolean allowNotification = true;
	protected Long receivedTime;
	protected Long backgroundLife;
	@Nullable Integer targetMenu;

	public void triggerEvent(MainActivity activity, EventBus eventBus) {
		Observable.create(
				e -> {
					try {
						event(activity, eventBus);

						if (targetMenu != null)
							activity.moveToMenu(targetMenu);
					} catch (Throwable t) {
						e.onError(new Exception(t));
						return;
					}

					e.onComplete();
				}
		).subscribeOn(AndroidSchedulers.mainThread())
				.subscribe(new ErrorObserver<>("Error while triggering message event"));
	}

	abstract void event(MainActivity activity, EventBus eventBus);

	@Override public int hashCode() {
		return Objects.hashCode(getTitle(), getMessage(), targetMenu);
	}

	@Override public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Message)) return false;
		Message message1 = (Message) o;
		return Objects.equal(getTitle(), message1.getTitle()) &&
				Objects.equal(getMessage(), message1.getMessage()) &&
				Objects.equal(targetMenu, message1.targetMenu);
	}

	@Nullable public String getTitle() {
		return title;
	}

	@Nullable public String getMessage() {
		return message;
	}

	@NonNull public String getId() {
		return id;
	}

	protected <T> T assertData(String message, T data) throws MessageNotBuiltException {
		if (data == null)
			throw new MessageNotBuiltException(message);

		return data;
	}

	@Nullable public static <T extends Message> Message build(Context context, Class<T> messageClass, Map<String, String> data) {
		try {
			Timber.d("Building new %s message", messageClass.getSimpleName());
			Timber.d("Data: " + data);

			T message = messageClass.newInstance();
			Timber.d("Building message from data");
			message.buildFromData(context, data);

			if (!isInForeground(context)) {
				Timber.d("Application in background");

				Timber.d("Adding message to stored metacache");
				//MessageMetaData messageMetaData = new MessageMetaData(data);
				addToCollection(STORED_MESSAGE_METADATA_CACHE, data);

				if (message.allowNotification) {
					Timber.d("Diverting to notification");
					NotificationMessage notificationMessage = new NotificationMessage(data);
					notificationMessage.buildFromData(context, data);
					return notificationMessage;
				}

				return null;
			}

			return message;
		} catch (InstantiationException e) {
			Timber.e(e, "Failed to instantiate Message: " + messageClass.getSimpleName());
		} catch (IllegalAccessException e) {
			Timber.e(e, messageClass.getSimpleName() + " has incorrect access level");
		} catch (MessageNotBuiltException e) {
			Timber.e(e, "Message not built correctly");
		} catch (MessageLifespanException e) {
			Timber.w("Message lifespan expired: " + data);
		} catch (Throwable e) {
			Timber.e(e, "Unknown error handling message");
		}

		return null;
	}

	protected void buildFromData(Context context, Map<String, String> data) throws MessageNotBuiltException, MessageLifespanException {
		buildVitals(context, data);

		title = data.get("title");
		message = data.get("message");

		targetMenu = ResourceUtils.getId(context, data.get("target_menu"));

		if (targetMenu == 0)
			targetMenu = null;
	}

	protected void buildVitals(Context context, Map<String, String> data) throws MessageNotBuiltException, MessageLifespanException {
		id = data.get("id");

		String backgroundLifeString = data.get("background_life");
		if (backgroundLifeString != null)
			backgroundLife = Long.parseLong(backgroundLifeString);
		else
			backgroundLife = DEFAULT_LIFESPAN;

		String receivedTimeString = data.get("received_time");
		if (receivedTimeString != null) {
			receivedTime = Long.parseLong(receivedTimeString);

			long expireTime = receivedTime + backgroundLife;

			if (System.currentTimeMillis() > expireTime)
				throw new MessageLifespanException();
		}

		String allowNotificationString = data.get("allow_notification");

		if (allowNotificationString != null && !allowNotificationString.equalsIgnoreCase("true"))
			allowNotification = false;
	}
}
