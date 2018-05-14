package com.ljmu.andre.snaptools.Networking.Helpers;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.ljmu.andre.snaptools.RedactedClasses.Answers;
import com.ljmu.andre.snaptools.RedactedClasses.CustomEvent;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.ljmu.andre.snaptools.Dialogs.Content.DeviceOverrideSelector;
import com.ljmu.andre.snaptools.Dialogs.DialogFactory;
import com.ljmu.andre.snaptools.Dialogs.ThemedDialog;
import com.ljmu.andre.snaptools.FCM.InstanceIDService;
import com.ljmu.andre.snaptools.Networking.Packets.LoginPacket;
import com.ljmu.andre.snaptools.Networking.Packets.LogoutPacket;
import com.ljmu.andre.snaptools.Networking.WebRequest.Builder;
import com.ljmu.andre.snaptools.Networking.WebRequest.WebResponseListener;
import com.ljmu.andre.snaptools.Networking.WebResponse;
import com.ljmu.andre.snaptools.Networking.WebResponse.PacketResultListener;
import com.ljmu.andre.snaptools.R;
import com.ljmu.andre.snaptools.STApplication;
import com.ljmu.andre.snaptools.Utils.DeviceIdManager;
import com.ljmu.andre.snaptools.Utils.SafeToast;

import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

import static com.ljmu.andre.snaptools.Networking.WebRequest.assertParam;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class LoginSync {
	private static final String LOGIN_SYNC_URL = "https://snaptools.org/SnapTools/Scripts/usrl.php";

	public static void loginSync2(Activity activity, PacketResultListener<LoginPacket> resultListener,
	                              GoogleSignInAccount account) {
		loginSync2(
				activity,
				resultListener,
				account.getIdToken(), account.getEmail(), account.getDisplayName(),
				null
		);
	}

	public static void loginSync2(Activity activity, PacketResultListener<LoginPacket> resultListener,
	                              String token, String email, String displayName,
	                              @Nullable String overwriteDevice) {
		Map<String, String> defaultParams = new HashMap<>();

		if (overwriteDevice != null) {
			defaultParams.put("overwrite_device",
					overwriteDevice);
		}

		if (STApplication.DEBUG) {
			if (token == null)
				token = "DEBUG";
		}

		String device_name = Build.MANUFACTURER + " " + Build.MODEL;

		Class cls = GetPackHistory.class;
		String deviceId;

		try {
			assertParam(cls, "Invalid Token", token);
			assertParam(cls, "Invalid Email", email);
			deviceId = assertParam(cls, "Invalid Device ID", DeviceIdManager.getDeviceId(activity));
		} catch (IllegalArgumentException e) {
			Timber.e(e);
			resultListener.error(
					"Missing Authentication Parameters",
					e,
					202
			);
			return;
		}

		String finalToken = token;
		new Builder()
				.setContext(activity)
				.setUrl(LOGIN_SYNC_URL)
				.setPacketClass(LoginPacket.class)
				.setParams(defaultParams)
				.shouldClearCache(true)
				// ===========================================================================
				.addParam("email", email)
				.addParam("device_id", deviceId)
				.addParam("device_name", device_name)
				.addParam("token", token)
				.addParam("debug", Boolean.toString(STApplication.DEBUG))
				.addParam("fire_token", InstanceIDService.getNonNullFireToken())
				// ===========================================================================
				.setCallback(
						new WebResponseListener() {
							@Override public void success(WebResponse webResponse) {
								LoginPacket loginPacket = webResponse.getResult();

								loginPacket.setDisplayName(displayName);
								loginPacket.setEmail(email);

								if (loginPacket.banned) {
									resultListener.error(
											loginPacket.getBanReason(),
											null,
											loginPacket.getErrorCode()
									);
									return;
								}

								loginPacket.googleToken = finalToken;

								if (loginPacket.device_cap) {
									ThemedDialog logoutDialog = setupLogoutDialog(
											activity,
											email,
											finalToken,
											loginPacket
									);

									logoutDialog.setOnDismissListener(dialog ->
											loginSync2(activity, resultListener, finalToken, email, displayName, null)
									);

									logoutDialog.show();
									return;
								}

								if (!loginPacket.auth_status) {
									resultListener.error(
											loginPacket.getAuthDescription(),
											null,
											loginPacket.getErrorCode()
									);
								}

								resultListener.success(null, loginPacket);
							}

							@Override public void error(WebResponse webResponse) {
								if (webResponse.getException() != null)
									Timber.e(webResponse.getException(), webResponse.getMessage());
								else
									Timber.w(webResponse.getMessage());

								resultListener.error(
										webResponse.getMessage(),
										webResponse.getException(),
										webResponse.getResponseCode()
								);
							}
						}
				)
				.performRequest();
	}

	private static ThemedDialog setupLogoutDialog(Activity activity, String email, String token,
	                                              LoginPacket loginPacket) {
		DeviceOverrideSelector extension = new DeviceOverrideSelector()
				.setDevices(loginPacket.getDevices());

		extension.setCallable(
				devicePacket ->
						Logout.logout(activity, email, token, devicePacket.device_id,
								new PacketResultListener<LogoutPacket>() {
									@Override public void success(String message, LogoutPacket packet) {
										Timber.d(message);

										if (packet.logoutState) {
											extension.removeDevice(devicePacket.device_id);

											SafeToast.show(
													activity,
													"Successfully logged out device",
													Toast.LENGTH_LONG
											);

											Answers.safeLogEvent(
													new CustomEvent("Logout")
															.putCustomAttribute("Success", String.valueOf(true))
											);
										} else {
											DialogFactory.createErrorDialog(
													activity,
													"Device Logout Error",
													message,
													packet.getErrorCode()
											).show();

											Answers.safeLogEvent(
													new CustomEvent("Logout")
															.putCustomAttribute("Success", String.valueOf(false))
											);
										}
									}

									@Override public void error(String message, Throwable t, int errorCode) {
										if (t != null)
											Timber.e(t, message);
										else
											Timber.w(message);

										DialogFactory.createErrorDialog(
												activity,
												"Device Logout Error",
												message,
												errorCode
										).show();

										Answers.safeLogEvent(
												new CustomEvent("Logout")
														.putCustomAttribute("Success", String.valueOf(false))
										);
									}
								})
		);

		return new ThemedDialog(activity)
				.setTitle("Device Cap Reached")
				.setHeaderDrawable(R.drawable.error_header)
				.setExtension(extension)
				.setDismissable(false);
	}
}
