package com.ljmu.andre.snaptools.Networking.Helpers;

import android.app.Activity;

import com.android.volley.DefaultRetryPolicy;
import com.ljmu.andre.snaptools.Networking.Packets.AuthPaymentPacket;
import com.ljmu.andre.snaptools.Networking.VolleyHandler;
import com.ljmu.andre.snaptools.Networking.WebRequest;
import com.ljmu.andre.snaptools.Networking.WebRequest.RequestType;
import com.ljmu.andre.snaptools.Networking.WebRequest.WebResponseListener;
import com.ljmu.andre.snaptools.Networking.WebResponse;
import com.ljmu.andre.snaptools.STApplication;
import com.ljmu.andre.snaptools.Utils.DeviceIdManager;

import java.util.concurrent.TimeUnit;

import hugo.weaving.DebugLog;
import timber.log.Timber;

import static com.ljmu.andre.GsonPreferences.Preferences.getPref;
import static com.ljmu.andre.snaptools.Networking.WebRequest.assertParam;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.STKN;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.ST_EMAIL;

@Deprecated
public class AuthPurchase {
	private static final String REQUEST_TAG = "order_claim";
	private static final int ORDER_TIMEOUT = (int) TimeUnit.SECONDS.toMillis(20);
	private static final String PURCHASE_AUTH_URL = "https://snaptools.org/SnapTools/Scripts/apymt.php";
	private static final String VERIFY_AUTH_URL = "https://snaptools.org/SnapTools/Scripts/verify_payment.php";

	@DebugLog public static void authPurchase(Activity activity, String orderId, String type, String identifier,
	                                          AuthPurchaseListener authListener) {
		Class cls = AuthPurchase.class;
		String token;
		String email;
		String deviceId;

		try {
			token = assertParam(cls, "Invalid Token",
					getPref(STKN));
			email = assertParam(cls, "Invalid Email",
					getPref(ST_EMAIL));
			deviceId = assertParam(cls, "Invalid Device ID",
					DeviceIdManager.getDeviceId(activity));
		} catch (IllegalArgumentException e) {
			Timber.e(e);
			authListener.authComplete(
					false,
					null,
					"Missing Authentication Parameters",
					e
			);
			return;
		}

		new WebRequest.Builder()
				.setUrl(PURCHASE_AUTH_URL)
				.setType(RequestType.PACKET)
				.setPacketClass(AuthPaymentPacket.class)
				.setContext(activity)
				.setTag(REQUEST_TAG)
				.setRetryPolicy(new DefaultRetryPolicy(ORDER_TIMEOUT, 0, 1))
				// ===========================================================================
				.addParam("order_id", orderId)
				.addParam("type", type)
				.addParam("identifier", identifier)
				.addParam("device_id", deviceId)
				.addParam("token", token)
				.addParam("email", email)
				.addParam("debug", Boolean.toString(STApplication.DEBUG))
				// ===========================================================================
				.setCallback(
						new WebResponseListener() {
							@Override public void success(WebResponse webResponse) {
								AuthPaymentPacket resultPacket = webResponse.getResult();

								Timber.d("Packet: "
										+ resultPacket.toString());

								if (resultPacket.banned) {
									authListener.authComplete(
											false,
											null,
											resultPacket.getBanReason(),
											null
									);
									return;
								}

								if (!resultPacket.auth_status) {
									authListener.authComplete(
											false,
											null,
											resultPacket.getAuthDescription(),
											null
									);
									return;
								}

								if (resultPacket.paymentToken == null) {
									String message = resultPacket.paymentMessage;

									if (message == null)
										message = "Unknown error retrieving payment token";

									authListener.authComplete(
											false,
											null,
											message,
											null
									);
									return;
								}

								authListener.authComplete(
										resultPacket.paymentState,
										resultPacket.paymentToken,
										resultPacket.paymentMessage,
										null
								);
							}

							@Override public void error(WebResponse webResponse) {
								VolleyHandler.getInstance().cancelPendingRequests(REQUEST_TAG);

								if (webResponse.getException() != null)
									Timber.e(webResponse.getException(), webResponse.getMessage());
								else
									Timber.w(webResponse.getMessage());

								authListener.authComplete(
										false,
										null,
										webResponse.getMessage(),
										webResponse.getException()
								);
							}
						}
				)
				.performRequest();
	}

	public static void verifyPayment(Activity activity, String type, String identifier,
	                                 VerifyPurchaseListener verifyListener) {
		String token = getPref(STKN);
		String email = getPref(ST_EMAIL);
		String deviceId = DeviceIdManager.getDeviceId(activity);

		if (token == null || token.isEmpty() || deviceId == null || deviceId.isEmpty()
				|| email == null || email.isEmpty()) {
			Timber.e("Invalid token or device id");
			return;
		}

		new WebRequest.Builder()
				.setUrl(VERIFY_AUTH_URL)
				.setType(RequestType.STRING)
				.setContext(activity)
				.setTag(REQUEST_TAG)
				.setRetryPolicy(new DefaultRetryPolicy(ORDER_TIMEOUT, 0, 1))
				// ===========================================================================
				.addParam("email", email)
				.addParam("device_id", deviceId)
				.addParam("type", type)
				.addParam("identifier", identifier)
				// ===========================================================================
				.setCallback(
						new WebResponseListener() {
							@Override public void success(WebResponse webResponse) {
								String result = webResponse.getResult();

								if (result.equals("NONE")) {
									verifyListener.verificationComplete(
											false,
											null
									);
								} else {
									verifyListener.verificationComplete(
											true,
											result
									);
								}
							}

							@Override public void error(WebResponse webResponse) {
								Timber.e(webResponse.getException(), webResponse.getMessage());
								verifyListener.verificationComplete(false, null);
							}
						}
				)
				.performRequest();

	}

	public interface AuthPurchaseListener {
		void authComplete(boolean purchaseState, String purchaseToken, String message, Throwable error);
	}

	public interface VerifyPurchaseListener {
		void verificationComplete(boolean verificationState, String purchaseToken);
	}
}
