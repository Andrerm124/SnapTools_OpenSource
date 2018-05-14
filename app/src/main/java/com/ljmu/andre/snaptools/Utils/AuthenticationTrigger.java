package com.ljmu.andre.snaptools.Utils;

import android.app.Activity;

import com.ljmu.andre.snaptools.RedactedClasses.Answers;
import com.ljmu.andre.snaptools.RedactedClasses.CustomEvent;
import com.ljmu.andre.CBIDatabase.Utils.QueryBuilder;
import com.ljmu.andre.snaptools.Databases.CacheDatabase;
import com.ljmu.andre.snaptools.Databases.Tables.PurchaseTable;
import com.ljmu.andre.snaptools.Dialogs.DialogFactory;
import com.ljmu.andre.snaptools.Framework.FrameworkManager;
import com.ljmu.andre.snaptools.Networking.Helpers.AuthPack;

import timber.log.Timber;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class AuthenticationTrigger {
	public static void triggerPackAuthCheck(Activity activity, String packName, String scVersion, String packVersion) {
		AuthPack.authPack(
				activity,
				packName,
				scVersion,
				packVersion,
				(state, message, purchaseToken, responseCode) -> {
					if (state) {
						CacheDatabase.insert(
								new PurchaseTable(
										purchaseToken,
										"pack",
										scVersion
								)
						);

						Answers.safeLogEvent(
								new CustomEvent("PackAuth")
										.putCustomAttribute("Success", "TRUE")
										.putCustomAttribute("PackName", packName)
										.putCustomAttribute("Version", packVersion)
						);
					} else {
						Timber.d("Authentication failed: "
								+ message);

						DialogFactory.createErrorDialog(
								activity,
								"Failed Pack Authentication",
								message,
								responseCode
						).show();

						try {
							CacheDatabase.getTable(PurchaseTable.class).delete(
									new QueryBuilder()
											.addSelection("identifier", scVersion)
							);
						} catch (Throwable t) {
							Timber.e(t);
						}

						try {
							FrameworkManager.deleteModPack(
									packName, null
							);
						} catch (Throwable t) {
							Timber.e(t);
						}

						UnhookManager.abortSystem();

						Answers.safeLogEvent(
								new CustomEvent("PackAuth")
										.putCustomAttribute("Success", "FALSE")
										.putCustomAttribute("PackName", packName)
										.putCustomAttribute("Version", packVersion)
						);
					}
				}
		);
	}
}
