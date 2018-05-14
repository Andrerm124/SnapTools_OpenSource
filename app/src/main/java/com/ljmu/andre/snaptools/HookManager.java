package com.ljmu.andre.snaptools;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Process;

import com.ljmu.andre.snaptools.RedactedClasses.Answers;
import com.ljmu.andre.snaptools.RedactedClasses.CustomEvent;
import com.github.anrwatchdog.ANRWatchDog;
import com.google.android.gms.common.annotation.KeepName;
import com.ljmu.andre.ErrorLogger.ErrorLogger;
import com.ljmu.andre.GsonPreferences.Preferences;
import com.ljmu.andre.snaptools.Databases.CacheDatabase;
import com.ljmu.andre.snaptools.Dialogs.Content.FrameworkLoadError;
import com.ljmu.andre.snaptools.Dialogs.DialogFactory;
import com.ljmu.andre.snaptools.Dialogs.ThemedDialog;
import com.ljmu.andre.snaptools.Dialogs.ThemedDialog.ThemedClickListener;
import com.ljmu.andre.snaptools.FCM.MessagingService;
import com.ljmu.andre.snaptools.Framework.FrameworkManager;
import com.ljmu.andre.snaptools.Framework.Utils.PackLoadState;
import com.ljmu.andre.snaptools.Networking.Helpers.AuthManager;
import com.ljmu.andre.snaptools.Networking.Packets.Packet;
import com.ljmu.andre.snaptools.Networking.VolleyHandler;
import com.ljmu.andre.snaptools.Networking.WebResponse.PacketResultListener;
import com.ljmu.andre.snaptools.Utils.BackgroundAuthVerifier;
import com.ljmu.andre.snaptools.Utils.ContextHelper;
import com.ljmu.andre.snaptools.Utils.ModuleChecker;
import com.ljmu.andre.snaptools.Utils.Security;
import com.ljmu.andre.snaptools.Utils.TimberUtils;
import com.ljmu.andre.snaptools.Utils.TrialUtils;
import com.ljmu.andre.snaptools.Utils.UnhookManager;
import com.ljmu.andre.snaptools.Utils.XposedUtils.ST_MethodHook;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import timber.log.Timber;

import static com.ljmu.andre.GsonPreferences.Preferences.getPref;
import static com.ljmu.andre.GsonPreferences.Preferences.putPref;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.ACCEPTED_TOS;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.CHECK_PACK_UPDATES_SC;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.ENABLE_ANR_WATCHDOG;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.NOTIFY_ON_LOAD;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.SYSTEM_ENABLED;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.WATCHDOG_HANG_TIMEOUT;
import static com.ljmu.andre.snaptools.Utils.NotificationUtils.showLoadedNotification;
import static com.ljmu.andre.snaptools.Utils.ResourceMapper.getResId;
import static com.ljmu.andre.snaptools.Utils.UnhookManager.abortSystem;
import static com.ljmu.andre.snaptools.Utils.UnhookManager.addUnhook;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

@KeepName
public class HookManager implements IXposedHookLoadPackage {

	// ===========================================================================

	private static AtomicBoolean hasHooked = new AtomicBoolean(false);
	private Context snapContext;
	private Activity snapActivity;


	// ===========================================================================

	@Override public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {


		/**
		 * ===========================================================================
		 * Self Hook -> forces isModuleActive() to return true
		 * ===========================================================================
		 */
		if (lpparam.packageName.contains(STApplication.PACKAGE)) {
			try {
				findAndHookMethod(
						ModuleChecker.class.getName(),
						lpparam.classLoader,
						"isModuleActive",
						XC_MethodReplacement.returnConstant(true)
				);
				findAndHookMethod(
						ModuleChecker.class.getName(),
						lpparam.classLoader,
						"getXposedVersion",
						XC_MethodReplacement.returnConstant(XposedBridge.getXposedVersion())
				);
			} catch (Throwable t) {
				XposedBridge.log(t);
			}

			return;
		}

		/**
		 * ===========================================================================
		 * Check if the current loading process is Snapchat or if we have already
		 * hooked the process
		 * ===========================================================================
		 */
		if (!lpparam.packageName.contains("com.snapchat.android")
				|| hasHooked.get()) {
			// Stops multiple hooks from being applied
			if (hasHooked.get())
				Timber.d("Tried to reapply hooks!");

			return;
		}

		hasHooked.set(true);

		TimberUtils.plantAppropriateTree();

		Timber.d("PID: "
				+ Process.myPid());

		ErrorLogger.init();

		Timber.d("Loading preferences");
		Preferences.init(
				Preferences.getExternalPath() + "/" + STApplication.MODULE_TAG + "/"
		);

		if (!(boolean) getPref(SYSTEM_ENABLED)) {
			Timber.w("System Disabled... Aborting initialisation");
			return;
		}

		Timber.d("Snapchat Is Loading!");

		Timber.d("Hooking Application Attach");
		addUnhook("System",
				findAndHookMethod(
						"android.app.Application",
						lpparam.classLoader,
						"attach",
						Context.class,
						new ST_MethodHook() {
							@Override
							protected void after(MethodHookParam param) throws Throwable {
								if (!(boolean) getPref(SYSTEM_ENABLED)) {
									Timber.w("System Disabled... Aborting initialisation");
									return;
								}

								initWatchdog();

								Timber.d("PID: "
										+ Process.myPid());

								Timber.d("Application Attach Called");
								snapContext = (Context) param.args[0];

								Application app = (Application) param.thisObject;
								Context moduleContext = app.createPackageContext(
										STApplication.PACKAGE, Context.CONTEXT_IGNORE_SECURITY);
								ContextHelper.set(moduleContext);
								MessagingService.launchService(app);

								addUnhook("System",
										findAndHookMethod(
												"com.snapchat.android.LandingPageActivity",
												lpparam.classLoader,
												"onCreate",
												Bundle.class,
												new ST_MethodHook() {
													@Override protected void before(MethodHookParam param) {
														snapActivity = (Activity) param.thisObject;
														UnhookManager.unhookAll("System");

														try {
															/**
															 * ===========================================================================
															 * Initialisation Stage
															 * ===========================================================================
															 */
															snapActivity = (Activity) param.thisObject;
															ContextHelper.setActivity(snapActivity);
															MessagingService.launchService(app);

															initFabrics(snapActivity, moduleContext);

															CacheDatabase.init(snapContext);
															VolleyHandler.init(snapActivity);

															if (!initTOS())
																return;

															// ===========================================================================

															/**
															 * ===========================================================================
															 * Authenticate the logged in user to ensure they are logged in and that they
															 * are who they say they are.
															 * ===========================================================================
															 */
															AuthManager.authUser(snapActivity, new PacketResultListener() {
																@Override public void success(String message, Packet packet) {
																	Answers.safeLogEvent(
																			new CustomEvent("UserAuth")
																					.putCustomAttribute(
																							"Success",
																							"TRUE"
																					)
																	);
																}

																// ===========================================================================

																@Override public void error(String message, Throwable t, int errorCode) {
																	Answers.safeLogEvent(
																			new CustomEvent("UserAuth")
																					.putCustomAttribute(
																							"Success",
																							"FALSE"
																					)
																	);

																	DialogFactory.createErrorDialog(
																			snapActivity,
																			"Authorisation Issue",
																			message + "\n\n" +
																					"You should go to the SnapTools application and sign in securely",
																			errorCode
																	).show();

																	abortSystem();
																}
															});

															TrialUtils.endTrialIfExpired(snapActivity);

															Timber.d("Loading FrameworkManager");

															// Load the certificate required to validate the ModulePack ==================
															Security.init(ContextHelper.getModuleResources(snapActivity));

															List<PackLoadState> packLoadStates;

															long packLoadStart = System.currentTimeMillis();
															packLoadStates = FrameworkManager.loadAllModulePacks(snapActivity);
															long packLoadEnd = System.currentTimeMillis();

															Timber.d("Starting Inject");
															FrameworkManager.injectAllHooks(packLoadStates, lpparam.classLoader, snapActivity);
															long packInjectEnd = System.currentTimeMillis();

															Timber.d("Load Times [Load: %s][Inject: %s]", (packLoadEnd - packLoadStart), (packInjectEnd - packLoadEnd));
															// Just a quick toggle to test the hang system =================
															//if(getPref(KILL_SC_ON_CHANGE))
															//Thread.sleep(30000);

															if (getPref(CHECK_PACK_UPDATES_SC))
																FrameworkManager.checkPacksForUpdate(snapActivity);

															boolean hasFailed = false;
															for (PackLoadState loadState : packLoadStates) {
																if (loadState.hasFailed())
																	hasFailed = true;
															}

															if (hasFailed) {
																new ThemedDialog(snapActivity)
																		.setTitle("Framework Load Error")
																		.setHeaderDrawable(
																				getResId(
																						moduleContext,
																						"error_header",
																						"drawable"
																				)
																		)
																		.setExtension(new FrameworkLoadError(packLoadStates))
																		.show();
																return;
															}

															BackgroundAuthVerifier.spoolVerifierThread();

															Timber.d("Framework loaded successfully");

															if ((boolean) getPref(NOTIFY_ON_LOAD) && packLoadStates.size() > 0)
																showLoadedNotification(snapActivity);
														} catch (NoSuchMethodError | NoClassDefFoundError e) {
															Timber.wtf(e, "Error inside LandingPage Hook");
															DialogFactory.createErrorDialog(
																	snapActivity,
																	"Error Loading SnapTools!",
																	"Fatal error loading system!"
																			+ "\n" + "Please ensure you have performed a full reboot before seeking help"
																			+ "\n\n" + "There appears to be an invalid Pack/Apk combination installed. Please make sure your Pack and APK are both updated"
															).show();
														} catch (Throwable t) {
															Timber.wtf(t, "Error inside LandingPage Hook");
															DialogFactory.createErrorDialog(
																	snapActivity,
																	"Error Loading SnapTools!",
																	"Fatal error loading system!"
																			+ "\n" + "Please ensure you have performed a full reboot before seeking help"
																			+ "\n\n" + t.getMessage()
															).show();
														}
													}
												}
										));
							}
						}
				));
	}

	private void initWatchdog() {
		Timber.d("Starting watchdog");

		try {
			if (getPref(ENABLE_ANR_WATCHDOG)) {
				int timeout = getPref(WATCHDOG_HANG_TIMEOUT);
				ANRWatchDog watchDog = new ANRWatchDog(timeout)
						.setANRListener(
								error -> {
									Timber.e(error, "Application Not Responding");

									boolean hangDisplayed =
											HangErrorActivity.start(
													snapContext,
													String.format(
															"The watchdog is reporting that Snapchat has been unresponsive for over %s seconds. This could be due to general lag or Snapchat is in a deadlock",
															timeout / 1000
													)
											);

									if (!hangDisplayed)
										throw error;
								}
						);

				UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
				ANRWatchDog.setDefaultUncaughtExceptionHandler((t, e) -> {
					defaultUncaughtExceptionHandler.uncaughtException(t, e);
					watchDog.interrupt();
				});

				watchDog.start();
			}
		} catch (Throwable t) {
			Timber.e(t, "Error initialising ANRWatchdog");
		}
	}

	private void initFabrics(Activity snapActivity, Context moduleContext) {
		Timber.e("FABRICS ANALYTICS WAS REDACTED FROM PUBLIC SOURCE!");

//		try {
//			Fabric.with(snapActivity, moduleContext, new Crashlytics());
//			String email = getPref(ST_EMAIL);
//			if (email != null)
//				Crashlytics.setUserEmail(email);
//
//			String displayName = getPref(ST_DISPLAY_NAME_OBFUS);
//			if (displayName != null)
//				Crashlytics.setUserName(displayName);
//
//			Set<String> selectedPacks = getPref(SELECTED_PACKS);
//			Crashlytics.setString("Selected Packs",
//					String.valueOf(selectedPacks));
//			Crashlytics.setString("User",
//					email);
//		} catch (Throwable e) {
//			Timber.e(e);
//
//			try {
//				SlackUtils.uploadToSlack(
//						"FabricsInitError",
//						Log.getStackTraceString(e)
//				);
//
//				if (snapActivity != null && !snapActivity.isFinishing()) {
//					SafeToast.show(
//							snapActivity,
//							"Failed to load error reporting... Please contact developers",
//							Toast.LENGTH_LONG,
//							true
//					);
//				}
//			} catch (Throwable ignored) {
//			}
//		}
	}

	private boolean initTOS() {
		if (!(boolean) getPref(ACCEPTED_TOS)) {
			Timber.w("Terms of service not accepted... Requesting acceptance");

			DialogFactory.createConfirmation(
					snapActivity,
					"Terms and Conditions",
					"By pressing YES, you agree to our Terms of Service and our Privacy Policy, both of which can be viewed under the Legal page in the SnapTools app.",
					new ThemedClickListener() {
						@Override public void clicked(ThemedDialog themedDialog) {
							putPref(ACCEPTED_TOS, true);
							themedDialog.dismiss();

							DialogFactory.createBasicMessage(
									snapActivity,
									"Restart Required",
									"You have accepted the ToS, a restart of Snapchat will be required!"
							).show();
						}
					},
					new ThemedClickListener() {
						@Override public void clicked(ThemedDialog themedDialog) {
							themedDialog.dismiss();

							DialogFactory.createErrorDialog(
									snapActivity,
									"Terms of Service Rejected",
									"You have NOT accepted the ToS therefore functionality is disabled"
											+ "\n\n"
											+ "It is advised that you uninstall this application to not see this message every launch"
							).show();

							UnhookManager.abortSystem();
						}
					}
			).setDismissable(false).show();

			return false;
		}

		return true;
	}
}