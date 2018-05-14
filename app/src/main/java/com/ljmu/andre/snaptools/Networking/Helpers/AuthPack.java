package com.ljmu.andre.snaptools.Networking.Helpers;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

import android.app.Activity;
import android.support.annotation.Nullable;
import android.util.Pair;

import com.google.common.base.Optional;
import com.google.common.hash.Funnels;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import com.ljmu.andre.CBIDatabase.CBITable;
import com.ljmu.andre.CBIDatabase.Utils.QueryBuilder;
import com.ljmu.andre.snaptools.Databases.CacheDatabase;
import com.ljmu.andre.snaptools.Databases.Tables.PurchaseTable;
import com.ljmu.andre.snaptools.Networking.Packets.AuthPackPacket;
import com.ljmu.andre.snaptools.Networking.WebRequest;
import com.ljmu.andre.snaptools.Networking.WebRequest.RequestType;
import com.ljmu.andre.snaptools.Networking.WebRequest.WebResponseListener;
import com.ljmu.andre.snaptools.Networking.WebResponse;
import com.ljmu.andre.snaptools.Utils.AnimationUtils;
import com.ljmu.andre.snaptools.Utils.CustomObservers.SimpleObserver;
import com.ljmu.andre.snaptools.Utils.DeviceIdManager;
import com.ljmu.andre.snaptools.Utils.PreferenceHelpers;
import com.ljmu.andre.snaptools.Utils.Security.PackCertification;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Calendar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.ljmu.andre.GsonPreferences.Preferences.getPref;
import static com.ljmu.andre.snaptools.Networking.WebRequest.assertParam;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.MODULES_PATH;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.P_TKNS;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.STKN;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.ST_EMAIL;
import static com.ljmu.andre.snaptools.Utils.PreferenceHelpers.getFromMap;

/**
 * Separate class to the {@link AuthManager} to make it harder to find
 */
public class AuthPack {
	private static final String PACK_AUTH_URL = "https://snaptools.org/SnapTools/Scripts/chkpck.php";

	public static void authPack(Activity activity, String packName, String identifier, String packVersion,
	                            PackAuthListener authListener) {

		Class cls = AuthPack.class;
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
			AnimationUtils.hiddenAuthTriggeredBool = true;

			Timber.e(e);
			authListener.authenticationComplete(
					false,
					"Missing Authentication Parameters",
					null,
					202
			);
			return;
		}

		new WebRequest.Builder()
				.setUrl(PACK_AUTH_URL)
				.setType(RequestType.PACKET)
				.setPacketClass(AuthPackPacket.class)
				.setContext(activity)
				.hideAuthTest()
				.useDefaultRetryPolicy()
				// ===========================================================================
				.addParam("pf", PackCertification.getFingerprint())
				.addParam("device_id", deviceId)
				.addParam("token", token)
				.addParam("identifier", identifier)
				.addParam("email", email)
				.addParam("version", packVersion)
				// ===========================================================================
				.setCallback(
						new WebResponseListener() {
							@Override public void success(WebResponse webResponse) {
								AnimationUtils.hiddenAuthTriggeredBool = true;

								AuthPackPacket resultPacket = webResponse.getResult();

								if (resultPacket.banned) {
									authListener.authenticationComplete(
											false,
											resultPacket.getBanReason(),
											null,
											resultPacket.getErrorCode()
									);
									return;
								}

								if (resultPacket.token == null && resultPacket.auth_status) {
									resultPacket.auth_status = false;
									resultPacket.auth_description = "Empty purchase token for pack";
								}

								if (resultPacket.auth_status)
									storePaymentToken(resultPacket.token, packName, deviceId);

								authListener.authenticationComplete(
										resultPacket.auth_status,
										resultPacket.getAuthDescription(),
										resultPacket.token,
										resultPacket.getErrorCode()
								);
							}

							@Override public void error(WebResponse webResponse) {
								AnimationUtils.hiddenAuthTriggeredBool = true;

								if (webResponse.getException() != null)
									Timber.e(webResponse.getException(), webResponse.getMessage());
								else
									Timber.w(webResponse.getMessage());

								if (webResponse.getResponseCode() == 102) {
									authListener.authenticationComplete(
											false,
											"Trial expired or purchase not authorised",
											null,
											webResponse.getResponseCode()
									);

									return;
								}

								performPackCheckFallback(authListener, packName, identifier, deviceId);
							}
						}
				)
				.performRequest();
	}

	private static void storePaymentToken(String purchaseToken, String packName, String deviceId) {
		Observable.fromCallable(
				() -> {
					String purchaseHash = generatePaymentHash(purchaseToken, packName, deviceId);
					return Optional.fromNullable(purchaseHash);
				}
		).subscribeOn(Schedulers.computation())
				.subscribe(
						new SimpleObserver<Optional<String>>() {
							@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
							@Override public void onNext(@NonNull Optional<String> stringOptional) {
								if (stringOptional.isPresent())
									PreferenceHelpers.addToMap(P_TKNS, packName, stringOptional.get());
							}
						}
				);
	}

	private static void performPackCheckFallback(PackAuthListener authListener, String packName, String identifier, String deviceId) {
		String existingPurchaseHash = getFromMap(P_TKNS, packName);
		String failMessage = "Failed to verify pack integrity";

		if (existingPurchaseHash == null) {
			authListener.authenticationComplete(false, failMessage, null, 205);
			Timber.d("No existing purchase hash");
			return;
		}

		Observable.fromCallable(
				() -> {
					CBITable<PurchaseTable> purchaseTable = CacheDatabase.getTable(PurchaseTable.class);
					try {
						PurchaseTable purchaseData = purchaseTable.getFirst(
								new QueryBuilder()
										.addSelection(
												"identifier",
												identifier
										)
										.addSelection(
												"pending",
												"0"
										)
						);


						if (purchaseData == null || purchaseData.isPending)
							return new Pair<String, String>(null, null);

						String purchaseHash = generatePaymentHash(purchaseData.purchaseToken, packName, deviceId);
						return new Pair<>(purchaseData.purchaseToken, purchaseHash);
					} catch (IllegalStateException e) {
						Timber.e(e);
						purchaseTable.dropTable();
					}

					return new Pair<String, String>(null, null);
				}
		).subscribeOn(Schedulers.computation())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
						new SimpleObserver<Pair<String, String>>() {
							@Override public void onNext(@NonNull Pair<String, String> tokenPair) {
								String storedPurchaseToken = tokenPair.first;
								String purchaseHash = tokenPair.second;

								if (purchaseHash == null) {
									authListener.authenticationComplete(false, failMessage, null, 205);
									return;
								}

								if (purchaseHash.equals(existingPurchaseHash)) {
									authListener.authenticationComplete(
											true,
											"Successfully authenticated pack using fallback",
											storedPurchaseToken,
											0
									);
								} else {
									authListener.authenticationComplete(false, failMessage, null, 206);
								}
							}
						}
				);
	}

	private static String generatePaymentHash(String purchaseToken, String packName, String deviceId) throws IOException {

		File modulePackFile = new File((String) getPref(MODULES_PATH), packName + ".jar");

		// Set up a new hasher with a given seed ===========================================================================
		Hasher hasher = Hashing.murmur3_128(6141251).newHasher();

		// Dump the contents of the file into the hasher =============================
		ByteSource source = Files.asByteSource(modulePackFile);
		source.copyTo(Funnels.asOutputStream(hasher));

		// Put the rest of the data into the hasher ==================================
		hasher.putString(purchaseToken, Charset.defaultCharset());
		hasher.putInt(
				(int) Math.ceil(
						((double) Calendar.getInstance().get(Calendar.DAY_OF_YEAR)) / 2
				)
		);
		hasher.putString(deviceId, Charset.defaultCharset());

		// Generate the final hashcode ===============================================
		HashCode hashCode = hasher.hash();
		return hashCode.toString();
	}

	public interface PackAuthListener {
		void authenticationComplete(boolean state, String message, @Nullable String purchaseToken, int responseCode);
	}
}
