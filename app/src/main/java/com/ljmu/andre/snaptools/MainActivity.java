package com.ljmu.andre.snaptools;

import android.Manifest.permission;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.Subscribe;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.ljmu.andre.GsonPreferences.Preferences;
import com.ljmu.andre.Translation.Translator;
import com.ljmu.andre.snaptools.RedactedClasses.Answers;
import com.ljmu.andre.snaptools.RedactedClasses.CustomEvent;
import com.ljmu.andre.snaptools.RedactedClasses.LoginEvent;
import com.ljmu.andre.snaptools.Databases.CacheDatabase;
import com.ljmu.andre.snaptools.Dialogs.Content.DeviceOverrideSelector;
import com.ljmu.andre.snaptools.Dialogs.Content.FrameworkLoadError;
import com.ljmu.andre.snaptools.Dialogs.Content.ModularButtonPrimary;
import com.ljmu.andre.snaptools.Dialogs.Content.ModularButtonsContainer;
import com.ljmu.andre.snaptools.Dialogs.Content.ModularHeader;
import com.ljmu.andre.snaptools.Dialogs.Content.ModularTextView;
import com.ljmu.andre.snaptools.Dialogs.Content.TextInputBasic;
import com.ljmu.andre.snaptools.Dialogs.DialogFactory;
import com.ljmu.andre.snaptools.Dialogs.ModularDialog;
import com.ljmu.andre.snaptools.Dialogs.ThemedDialog;
import com.ljmu.andre.snaptools.Dialogs.ThemedDialog.ThemedClickListener;
import com.ljmu.andre.snaptools.EventBus.EventBus;
import com.ljmu.andre.snaptools.EventBus.Events.BannerUpdateEvent;
import com.ljmu.andre.snaptools.EventBus.Events.FirebaseTokenRefreshEvent;
import com.ljmu.andre.snaptools.EventBus.Events.GoogleAuthEvent;
import com.ljmu.andre.snaptools.EventBus.Events.LoadPackSettingsEvent;
import com.ljmu.andre.snaptools.EventBus.Events.LogoutEvent;
import com.ljmu.andre.snaptools.EventBus.Events.MasterSwitchEvent;
import com.ljmu.andre.snaptools.EventBus.Events.ModuleEventRequest;
import com.ljmu.andre.snaptools.EventBus.Events.PackLoadEvent;
import com.ljmu.andre.snaptools.EventBus.Events.PackUnloadEvent;
import com.ljmu.andre.snaptools.EventBus.Events.ReqCheckApkUpdateEvent;
import com.ljmu.andre.snaptools.EventBus.Events.ReqGoogleAuthEvent;
import com.ljmu.andre.snaptools.EventBus.Events.ReqGoogleDisconnectEvent;
import com.ljmu.andre.snaptools.EventBus.Events.ReqLoadFragmentEvent;
import com.ljmu.andre.snaptools.EventBus.Events.ReqLogoutEvent;
import com.ljmu.andre.snaptools.EventBus.Events.ServerMessageEvent;
import com.ljmu.andre.snaptools.EventBus.Events.ShopPurchaseEvent;
import com.ljmu.andre.snaptools.EventBus.Events.TutorialFinishedEvent;
import com.ljmu.andre.snaptools.FCM.InstanceIDService;
import com.ljmu.andre.snaptools.FCM.MessageTypes.Message;
import com.ljmu.andre.snaptools.FCM.MessagingService;
import com.ljmu.andre.snaptools.Fragments.FragmentHelper;
import com.ljmu.andre.snaptools.Fragments.HomeFragment;
import com.ljmu.andre.snaptools.Framework.FrameworkManager;
import com.ljmu.andre.snaptools.Framework.MetaData.LocalPackMetaData;
import com.ljmu.andre.snaptools.Framework.Module;
import com.ljmu.andre.snaptools.Framework.ModulePack;
import com.ljmu.andre.snaptools.Framework.Utils.PackLoadState;
import com.ljmu.andre.snaptools.Networking.Helpers.AuthManager;
import com.ljmu.andre.snaptools.Networking.Helpers.CheckAPKUpdate;
import com.ljmu.andre.snaptools.Networking.Helpers.FirebaseTokenRefresh;
import com.ljmu.andre.snaptools.Networking.Helpers.LoginSync;
import com.ljmu.andre.snaptools.Networking.Helpers.Logout;
import com.ljmu.andre.snaptools.Networking.Packets.LoginPacket;
import com.ljmu.andre.snaptools.Networking.Packets.LogoutPacket;
import com.ljmu.andre.snaptools.Networking.Packets.Packet;
import com.ljmu.andre.snaptools.Networking.WebResponse.PacketResultListener;
import com.ljmu.andre.snaptools.UIComponents.CustomNavigation;
import com.ljmu.andre.snaptools.UIComponents.CustomNavigation.NavigationFragmentListener;
import com.ljmu.andre.snaptools.UIComponents.UITheme;
import com.ljmu.andre.snaptools.Utils.Constants;
import com.ljmu.andre.snaptools.Utils.ContextHelper;
import com.ljmu.andre.snaptools.Utils.CustomObservers.ErrorObserver;
import com.ljmu.andre.snaptools.Utils.DeviceIdManager;
import com.ljmu.andre.snaptools.Utils.MiscUtils;
import com.ljmu.andre.snaptools.Utils.ModuleChecker;
import com.ljmu.andre.snaptools.Utils.SafeToast;
import com.ljmu.andre.snaptools.Utils.Security;
import com.ljmu.andre.snaptools.Utils.ShowcaseFactory;
import com.ljmu.andre.snaptools.Utils.ThemeUtils;
import com.ljmu.andre.snaptools.Utils.TranslationDef;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.Unbinder;
import hugo.weaving.DebugLog;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;
import timber.log.Timber;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.ljmu.andre.GsonPreferences.Preferences.getPref;
import static com.ljmu.andre.GsonPreferences.Preferences.putPref;
import static com.ljmu.andre.GsonPreferences.Preferences.removePref;
import static com.ljmu.andre.Translation.Translator.translate;
import static com.ljmu.andre.snaptools.Utils.Constants.APK_CHECK_COOLDOWN;
import static com.ljmu.andre.snaptools.Utils.Constants.REMIND_TUTORIAL_COOLDOWN;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.BACK_OPENS_MENU;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.CHECK_APK_UPDATES;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.CURRENT_THEME;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.FTKN;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.LAST_APK_UPDATE_CHECK;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.LAST_BUILD_FLAVOUR;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.LAST_CHECK_SHOP;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.LAST_OPEN_APP;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.LATEST_APK_VERSION_CODE;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.SHOW_TUTORIAL;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.STKN;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.STORED_MESSAGE_METADATA_CACHE;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.ST_DEV;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.ST_DISPLAY_NAME;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.ST_DISPLAY_NAME_OBFUS;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.ST_EMAIL;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.SYSTEM_ENABLED;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.TRANSLATION_LOCALE;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.TRIAL_MODE;
import static com.ljmu.andre.snaptools.Utils.FrameworkViewFactory.addRelativeParamRule;
import static com.ljmu.andre.snaptools.Utils.ResourceUtils.getIdFromString;
import static com.ljmu.andre.snaptools.Utils.ResourceUtils.getView;
import static com.ljmu.andre.snaptools.Utils.StringUtils.htmlHighlight;
import static com.ljmu.andre.snaptools.Utils.StringUtils.obfus;
import static com.ljmu.andre.snaptools.Utils.TranslationDef.AUTOMATIC_INITIALISATION_SUCCESS;
import static com.ljmu.andre.snaptools.Utils.TranslationDef.DEFAULT_TRANSLATION_FOUND_MESSAGE;
import static com.ljmu.andre.snaptools.Utils.TranslationDef.DEFAULT_TRANSLATION_FOUND_TITLE;
import static com.ljmu.andre.snaptools.Utils.TranslationDef.FRAMEWORK_LOAD_ERROR_TITLE;
import static com.ljmu.andre.snaptools.Utils.TranslationDef.PACK_LOAD_FATAL_ERROR_MSG;
import static com.ljmu.andre.snaptools.Utils.TranslationDef.PACK_LOAD_FATAL_ERROR_TITLE;

public class MainActivity
		extends AppCompatActivity
		implements NavigationFragmentListener, OnConnectionFailedListener {

	// ===========================================================================

	public static final int GOOGLE_AUTH_RESPONSE = 100;
	private static final String[] PERMISSION_REQUESTS = {
			permission.READ_EXTERNAL_STORAGE,
			permission.WRITE_EXTERNAL_STORAGE,
			permission.KILL_BACKGROUND_PROCESSES,
			permission.INTERNET,
			permission.VIBRATE,
			permission.RECEIVE_BOOT_COMPLETED
	};

	// ===========================================================================

	private static final List<Integer> TUTORIAL_FRAGMENTS = new ImmutableList.Builder<Integer>()
			.add(R.id.nav_home)
			.add(R.id.nav_pack_manager)
			.add(R.id.nav_settings)
			.build();

	// ===========================================================================

	@BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
	@BindView(R.id.my_toolbar) Toolbar toolbar;
	@BindView(R.id.drawer_navigation_view) CustomNavigation navigationView;
	@BindView(R.id.txt_mod_inactive) TextView txtModInactive;
	@BindView(R.id.txt_banner_apk_update) TextView txtBannerApkUpdate;
	@BindView(R.id.txt_banner_pack_update) TextView txtBannerPackUpdate;
	@BindView(R.id.btn_tutorial) ImageButton btnTutorial;
	@BindView(R.id.txt_master_switch_disabled) TextView txtMasterSwitchDisabled;
	@BindView(R.id.konfetti) KonfettiView konfettiView;

	// ===========================================================================

	private Unbinder unbinder;
	private GoogleApiClient mGoogleApiClient;
	private FirebaseRemoteConfig remoteConfig;
	private boolean isInitialised;
	private UITheme currentTheme;

	// ===========================================================================

	/**
	 * ===========================================================================
	 * Set the view and check for permissions before initialising
	 * ===========================================================================
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (BuildConfig.DEBUG) {
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
					.detectLeakedClosableObjects()
					.detectLeakedRegistrationObjects()
					.detectLeakedSqlLiteObjects()
					.penaltyLog()
					.penaltyDeath()
					.build());
		}

		super.onCreate(savedInstanceState);

		ThemeUtils.onActivityCreateSetTheme(this);
		setContentView(R.layout.activity_main);
		unbinder = ButterKnife.bind(this);
		remoteConfig = FirebaseRemoteConfig.getInstance();

		ContextHelper.set(getApplicationContext());
		ContextHelper.setActivity(this);

		getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		if (handlePermissions())
			initialiseApplication();
	}

	@Override protected void onDestroy() {
		super.onDestroy();
		Timber.d("Destroyed");
		unbinder.unbind();
		EventBus.soleUnregister(this);
//		Crashlytics.dispose();
	}

	@Override protected void onSaveInstanceState(Bundle outState) {
	}

	/**
	 * ===========================================================================
	 * Check for necessary Permissions -> Perform request on missing perms
	 * ===========================================================================
	 */
	private boolean handlePermissions() {
		for (String permission : PERMISSION_REQUESTS) {
			if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {

				// Ask the user if they wish to resolve missing permissions
				DialogFactory.createBasicMessage(
						this,
						getString(R.string.permission_request_title),
						getString(R.string.permission_request_msg),
						new ThemedClickListener() {
							@Override public void clicked(ThemedDialog themedDialog) {
								ActivityCompat.requestPermissions(
										MainActivity.this,
										PERMISSION_REQUESTS,
										1
								);

								themedDialog.dismiss();
							}
						}
				).setDismissable(false).show();

				return false;
			}
		}

		return true;
	}

	/**
	 * ===========================================================================
	 * After permissions are granted -> Initialise the App
	 * ===========================================================================
	 */
	private void initialiseApplication() {
		if (isInitialised)
			return;

		isInitialised = true;


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

			DialogFactory.createErrorDialog(
					this,
					"Error Initialising Preferences",
					"Preference system not loaded. The reason is likely to be permission issues. The application will terminate to preserve data integrity"
							+ "\n\n"
							+ "Reason: " + e.getMessage(),
					new ThemedClickListener() {
						@Override public void clicked(ThemedDialog themedDialog) {
							themedDialog.dismiss();
							finish();
						}
					}
			).setDismissable(false).show();

			return;
		}

		/**
		 * ===========================================================================
		 * Translation System Initialisation
		 * ===========================================================================
		 */
		if (remoteConfig.getBoolean("enable_translation_api_"
				+ BuildConfig.FLAVOR)) {
			try {
				String savedLocaleString = getPref(TRANSLATION_LOCALE);
				boolean hasSavedLocale = savedLocaleString != null;

				Timber.d("Saved language locale: "
						+ savedLocaleString);

				if (!hasSavedLocale) {
					savedLocaleString = Locale.getDefault().getDisplayLanguage();
					Timber.d("Saved language not found... Defaulting to "
							+ savedLocaleString);
				}

				/**
				 * ===========================================================================
				 * If saved translation mode is not english, load the translations
				 * ===========================================================================
				 */
				if (!savedLocaleString.equals("English")) {
					Translator.initTranslationDefinitions(this, new TranslationDef(), true);
					String finalSavedLocaleString = savedLocaleString;
					Translator.init(
							this,
							savedLocaleString + ".xml",
							success -> {
								if (!success) {
									SafeToast.show(
											this,
											"Failed to fetch translations",
											true
									);

									if (!hasSavedLocale) {
										putPref(TRANSLATION_LOCALE, Locale.ENGLISH.getDisplayLanguage());
									}

									return;
								}

								if (!hasSavedLocale) {
									DialogFactory.createConfirmation(
											this,
											translate(DEFAULT_TRANSLATION_FOUND_TITLE),
											String.format(translate(DEFAULT_TRANSLATION_FOUND_MESSAGE),
													finalSavedLocaleString)
													+ "\n\n"
													+ String.format(DEFAULT_TRANSLATION_FOUND_MESSAGE.getText(),
													finalSavedLocaleString),
											new ThemedClickListener() {
												@Override public void clicked(ThemedDialog themedDialog) {
													themedDialog.dismiss();
													putPref(TRANSLATION_LOCALE, finalSavedLocaleString);
												}
											},
											new ThemedClickListener() {
												@Override public void clicked(ThemedDialog themedDialog) {
													themedDialog.dismiss();
													putPref(TRANSLATION_LOCALE, Locale.ENGLISH.getDisplayLanguage());
												}
											}
									).show();
								}
							}
					);
				}
			} catch (Throwable t) {
				Timber.e(t, "Couldn't load translation system");

				DialogFactory.createErrorDialog(
						this,
						"Error Loading Translations",
						"An unknown error occurred while loading the translation system"
				).show();
			}
		}

		// ===========================================================================

		/**
		 * ===========================================================================
		 * Fabric Setup
		 * ===========================================================================
		 */
//		Fabric.with(this, this, new Crashlytics());
//		String email = getPref(ST_EMAIL);
//		if (email != null)
//			Crashlytics.setUserEmail(email);
//
//		String displayName = getPref(ST_DISPLAY_NAME_OBFUS);
//		if (displayName != null)
//			Crashlytics.setUserName(displayName);
//
//		Set<String> selectedPacks = getPref(SELECTED_PACKS);
//		Crashlytics.setString("Selected Packs",
//				String.valueOf(selectedPacks));
//		Crashlytics.setString("User", email);

		/**
		 * ===========================================================================
		 * Device ID Initialisation
		 * ===========================================================================
		 */
		if (!DeviceIdManager.isSystemIdAssigned()) {
			if (!DeviceIdManager.assignSystemId(this)) {
				return;
			} else {
				SafeToast.show(
						this,
						translate(AUTOMATIC_INITIALISATION_SUCCESS)
				);
			}
		}

		/**
		 * ===========================================================================
		 *  App Theming Preferences Check
		 * ===========================================================================
		 */
		currentTheme = UITheme.getMatchingThemeFromName(getPref(CURRENT_THEME));
		ThemeUtils.cTheme = currentTheme.getTheme();
		ThemeUtils.onActivityCreateSetTheme(this);
		findViewById(R.id.main_activity).setBackgroundResource(currentTheme.getColorBackgroundID());


		/**
		 * ===========================================================================
		 * Bindings
		 * ===========================================================================
		 */
		ButterKnife.bind(this);
		CacheDatabase.init(this);
		EventBus.getInstance().register(this);

		// ===========================================================================

		/**
		 * ===========================================================================
		 * Toolbar Initialisation
		 * ===========================================================================
		 */
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
				this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
		drawerLayout.addDrawerListener(actionBarDrawerToggle);
		navigationView.init(this);
		actionBarDrawerToggle.syncState();

		// ===========================================================================

		/**
		 * ===========================================================================
		 * Initiate the Banner Headers
		 * ===========================================================================
		 */
		initBanners();

		// ===========================================================================


		/**
		 * ===========================================================================
		 * Initialise Security and Load Module Packs
		 * ===========================================================================
		 */
		try {
			Security.init(getResources());

			// ===========================================================================
			List<PackLoadState> packLoadStates = FrameworkManager.loadAllModulePacks(this);
			// ===========================================================================

			boolean hasFailed = false;
			for (PackLoadState loadState : packLoadStates) {
				if (loadState.hasFailed())
					hasFailed = true;
			}

			if (hasFailed) {
				new ThemedDialog(this)
						.setTitle(translate(FRAMEWORK_LOAD_ERROR_TITLE))
						.setHeaderDrawable(R.drawable.error_header)
						.setExtension(new FrameworkLoadError(packLoadStates))
						.show();
			}
		} catch (Throwable t) {
			Timber.wtf(t, "Fatal error loading packs");
			DialogFactory.createErrorDialog(
					this,
					translate(PACK_LOAD_FATAL_ERROR_TITLE),
					translate(PACK_LOAD_FATAL_ERROR_MSG)
							+ "\n\n" + t.getMessage()
			).show();
		}

		FrameworkManager.checkPacksForUpdate(this);

		/**
		 * ===========================================================================
		 * Google Sign In Config
		 * ===========================================================================
		 */
		GoogleSignInOptions gso = new Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestIdToken("REDACTED")
				.requestEmail()
				.build();

		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.enableAutoManage(this, this)
				.addApi(Auth.GOOGLE_SIGN_IN_API, gso)
				.build();

		/**
		 * ===========================================================================
		 * User Authentication Checking
		 * ===========================================================================
		 */
		// If login token missing, ask to log in =====================================
		if (getPref(STKN) == null) {

			// If tutorial hasn't been shown, show it and login user =====================
			if (getPref(SHOW_TUTORIAL)) {
				DialogFactory.createConfirmation(
						this,
						"Tutorial",
						"Would you like to view a full application tutorial?",
						new ThemedClickListener() {
							@Override public void clicked(ThemedDialog themedDialog) {
								themedDialog.dismiss();
								FragmentHelper activeFragment = navigationView.getActiveFragment();

								if (activeFragment == null) {
									Timber.w("Null active fragment!");
									return;
								}

								activeFragment.triggerTutorial(true);
							}
						},
						new ThemedClickListener() {
							@Override public void clicked(ThemedDialog themedDialog) {
								displayLoginRequest();
								themedDialog.dismiss();
								putPref(SHOW_TUTORIAL, false);
							}
						}
				).show();
			} else { // Otherwise show login request ==============================================
				FragmentHelper activeFragment = navigationView.getActiveFragment();
				activeFragment.triggerOnVisible((fragmentHelper, v) -> displayLoginRequest());
			}
		} else {

			// Otherwise authenticate logged in user =====================================
			AuthManager.authUser(this, new PacketResultListener() {

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
							MainActivity.this,
							"System Issue",
							message,
							new ThemedClickListener() {
								@Override
								public void clicked(ThemedDialog themedDialog) {
									handleReqGoogleAuthEvent(new ReqGoogleAuthEvent());
									themedDialog.dismiss();
								}
							},
							errorCode
					).show();
				}
			});

		}

		// ===========================================================================

		/**
		 * ===========================================================================
		 * Less important functions such as setting applications
		 * ===========================================================================
		 */
		initApkUpdateChecker();
		initFirebaseSubscriptions();
		checkForSnapchatBeta();
		initReminders();
		Translator.translateActivity(this);
	}

	/**
	 * ===========================================================================
	 * Display a reminder about viewing page tutorials if they have been
	 * inactive for longer than {@link Constants#REMIND_TUTORIAL_COOLDOWN}
	 * ===========================================================================
	 */
	private void initReminders() {
		long lastOpenedApp = getPref(LAST_OPEN_APP);

		// Already showing tutorial, don't remind about tutorial =====================
		if (getPref(SHOW_TUTORIAL)) {
			return;
		}

		if (MiscUtils.calcTimeDiff(lastOpenedApp) > REMIND_TUTORIAL_COOLDOWN) {
			ShowcaseFactory.detatchCurrentShowcase(this);

			ShowcaseFactory.getDefaultShowcase(this, (showCaseView, messageContainer, txtTitle, txtMessage, btnClose, btnNext) -> {
				addRelativeParamRule(messageContainer, RelativeLayout.CENTER_VERTICAL);

				txtTitle.setText("Tutorial Reminder");
				txtMessage.setText(
						"Remember, you can tap the ? button on the top right of certain menus to get a tutorial for that screen including some useful information about the features it has."
								+ "\n\n" +
								"Or you can long press it on the Home page to get a full basic app tutorial!"
				);

				btnClose.setVisibility(GONE);

				btnNext.setText("Okay");
				btnNext.setOnClickListener(v -> showCaseView.hide());
			}).focusOn(getView(this, R.id.btn_tutorial)).build().show();
		}

		putPref(LAST_OPEN_APP, System.currentTimeMillis());
	}

	/**
	 * ===========================================================================
	 * Set the initial state of the page banners
	 * ===========================================================================
	 */
	private void initBanners() {
		//noinspection ConstantConditions=
		txtModInactive.setVisibility(ModuleChecker.isModuleActive() ? GONE : View.VISIBLE);

		// Trigger the master switch visibility check ================================
		handleMasterSwitchEvent(new MasterSwitchEvent());
		handleBannerEvent(new BannerUpdateEvent(BannerUpdateEvent.MASTER_SWITCH));
		handleBannerEvent(new BannerUpdateEvent(BannerUpdateEvent.APK_UPDATE));
		handleBannerEvent(new BannerUpdateEvent(BannerUpdateEvent.PACK_UPDATE));
	}

	/**
	 * ===========================================================================
	 * Display the Login Request showcase view
	 * ===========================================================================
	 */
	private void displayLoginRequest() {
		ShowcaseFactory.detatchCurrentShowcase(this);

		ShowcaseFactory.getDefaultShowcase(this, (showCaseView, messageContainer, txtTitle, txtMessage, btnClose, btnNext) -> {
			addRelativeParamRule(messageContainer, RelativeLayout.CENTER_VERTICAL);

			txtTitle.setText("Login Request");
			txtMessage.setText(
					"In order to use this software correctly you will be required to log in."
							+ "\n\n"
							+ "You will now be asked which Google account you would like to be signed in with"
			);

			btnClose.setVisibility(GONE);

			btnNext.setText("Okay");
			btnNext.setOnClickListener(v -> {
				showCaseView.hide();
				handleReqGoogleAuthEvent(new ReqGoogleAuthEvent());
			});
		}).build().show();
	}

	/**
	 * ===========================================================================
	 * Request Google Sign In
	 * ===========================================================================
	 */
	@Subscribe public void handleReqGoogleAuthEvent(ReqGoogleAuthEvent authEvent) {
		// Check if it's a developers build ==========================================
		if (!STApplication.DEBUG) {
			/**
			 * ===========================================================================
			 * Display the Google Signin intent and wait for the result
			 * ===========================================================================
			 */
			Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
			startActivityForResult(signInIntent, GOOGLE_AUTH_RESPONSE);
		} else {

			// Display the uglier developer login panel ==================================
			DialogFactory.createBasicTextInputDialog(
					this,
					"Debug Login",
					"You must provide your email",
					"Account Email",
					getPref(ST_EMAIL),
					null,
					new ThemedClickListener() {
						@Override public void clicked(ThemedDialog themedDialog) {
							TextInputBasic input = themedDialog.getExtension();
							String email = input.getInputMessage();
							themedDialog.dismiss();

							DialogFactory.createBasicTextInputDialog(
									MainActivity.this,
									"Debug Login",
									"Please enter the current Server Password",
									"Server Password",
									null,
									InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD,
									new ThemedClickListener() {
										@Override public void clicked(ThemedDialog themedDialog) {
											TextInputBasic input = themedDialog.getExtension();
											String password = input.getInputMessage();

											LoginSync.loginSync2(
													MainActivity.this,
													new PacketResultListener<LoginPacket>() {
														@Override public void success(String message, LoginPacket packet) {
															loginResult(packet);
														}

														@Override public void error(String message, Throwable t, int errorCode) {
															loginError(message, t, errorCode);
														}
													},
													password,
													email,
													"Developer",
													null
											);

											themedDialog.dismiss();
										}
									}
							).show();
						}
					}
			).show();
		}
	}

	/**
	 * ===========================================================================
	 * Begin checking if an APK update has been released
	 * ===========================================================================
	 */
	private void initApkUpdateChecker() {
		if (!(boolean) getPref(CHECK_APK_UPDATES))
			return;

		if (MiscUtils.calcTimeDiff(getPref(LAST_APK_UPDATE_CHECK)) > APK_CHECK_COOLDOWN)
			CheckAPKUpdate.checkApkUpdate(this, false);
	}

	/**
	 * ===========================================================================
	 * Handle the firebase topics that should be subscribed to and generate
	 * a firebase token if needed
	 * ===========================================================================
	 */
	private void initFirebaseSubscriptions() {
		String storedFireToken = getPref(FTKN);

		if (!InstanceIDService.getNonNullFireToken().equals(storedFireToken))
			FirebaseTokenRefresh.refreshToken(this);

		//noinspection ConstantConditions
		if (BuildConfig.FLAVOR.equals("prod"))
			FirebaseMessaging.getInstance().unsubscribeFromTopic("beta");

		FirebaseMessaging.getInstance().unsubscribeFromTopic(getPref(LAST_BUILD_FLAVOUR));
		FirebaseMessaging.getInstance().subscribeToTopic(BuildConfig.FLAVOR);
		FirebaseMessaging.getInstance().subscribeToTopic("all");
		putPref(LAST_BUILD_FLAVOUR, BuildConfig.FLAVOR);
	}

	/**
	 * ===========================================================================
	 * Snapchat Beta versions are not currently supported. As a result, when
	 * launching SnapTools with a Snapchat Beta version installed, it will
	 * display an appropriate message.
	 * <p>
	 * This function can be controlled via the Firebase Remote Config.
	 * ===========================================================================
	 */
	private void checkForSnapchatBeta() {
		// Set `check_sc_beta` remote config to FALSE to disable checks ==============
		if (!remoteConfig.getBoolean("check_sc_beta"))
			return;

		String installedSCVersion = MiscUtils.getInstalledSCVer(this);

		if (installedSCVersion == null)
			return;

		if (installedSCVersion.toLowerCase().contains("beta")) {
			DialogFactory.createErrorDialog(
					this,
					"Snapchat Beta Detected",
					"Snapchat Beta version detected ("
							+ installedSCVersion + ")"
							+ "\nSnapTools will " + htmlHighlight("NEVER") + " support a Beta version of Snapchat as we require a stable base to work from"
			).show();
		}
	}

	/**
	 * ===========================================================================
	 * EventBus subscription to listen for master switch changes
	 * ===========================================================================
	 */
	@Subscribe public void handleMasterSwitchEvent(MasterSwitchEvent switchEvent) {
		if (txtModInactive.getVisibility() == View.VISIBLE) {
			txtMasterSwitchDisabled.setVisibility(GONE);
			return;
		}

		txtMasterSwitchDisabled.setVisibility(
				getPref(SYSTEM_ENABLED) ? GONE : VISIBLE
		);
	}

	/**
	 * ===========================================================================
	 * EventBus subscription to listen for Banner events.
	 * E.g. Apk Update, Master Switch toggle, etc
	 * ===========================================================================
	 */
	@Subscribe public void handleBannerEvent(BannerUpdateEvent bannerEvent) {
		switch (bannerEvent.eventType) {
			case BannerUpdateEvent.MASTER_SWITCH:
				if (txtModInactive.getVisibility() == View.VISIBLE) {
					txtMasterSwitchDisabled.setVisibility(GONE);
					return;
				}

				txtMasterSwitchDisabled.setVisibility(
						getPref(SYSTEM_ENABLED) ? GONE : VISIBLE
				);

				break;
			case BannerUpdateEvent.APK_UPDATE:
				txtBannerApkUpdate.setVisibility(
						(int) getPref(LATEST_APK_VERSION_CODE) <= BuildConfig.VERSION_CODE ? GONE : VISIBLE
				);

				txtBannerApkUpdate.setOnClickListener(v -> CheckAPKUpdate.checkApkUpdate(this, true, true));

				break;
//			case BannerEvent.PACK_UPDATE:
//				txtBannerPackUpdate.setVisibility(
//						(int) getPref(LATEST_PACK_VERSION_NAME) <=  ? GONE : VISIBLE
//				);
//
//				break;
		}
	}

	@Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Timber.d("Result: [Request: %s][Result: %s][Data: %s]", requestCode, resultCode, data);

		if (requestCode == GOOGLE_AUTH_RESPONSE) {
			GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

			Timber.d("Sign in acc: %s",
					result.getSignInAccount());
			if (result.isSuccess()) {
				GoogleSignInAccount account = result.getSignInAccount();

				if (account != null) {
					if (account.getIdToken() != null) {
						LoginSync.loginSync2(
								this,
								new PacketResultListener<LoginPacket>() {
									@Override public void success(String message, LoginPacket packet) {
										loginResult(packet);
									}

									@Override public void error(String message, Throwable t, int errorCode) {
										loginError(message, t, errorCode);
									}
								},
								account
						);
					} else {
						displayGoogleAuthError("Reason: No Account Id Token");
					}
				} else
					displayGoogleAuthError("Reason: No Account Data Received");
			} else {
				String message =
						result.getStatus().isCanceled() ?
								"Sign in was cancelled" :
								result.getStatus().isInterrupted() ?
										"Sign in was interrupted" :
										result.getStatus().getStatusMessage() != null ?
												result.getStatus().getStatusMessage() :
												"Unknown Reason";

				displayGoogleAuthError("Reason: " + message);
			}
		}
	}

	public void loginResult(LoginPacket loginPacket) {
		if (loginPacket.device_cap) {
			DialogFactory.createErrorDialog(
					this,
					"Device Cap Reached",
					"You have logged in on too many devices"
			);

			new ThemedDialog(this)
					.setTitle("Device Cap Reached")
					.setHeaderDrawable(R.drawable.error_header)
					.setExtension(
							new DeviceOverrideSelector()
									.setDevices(loginPacket.getDevices())
									.setCallable(
											devicePacket -> Timber.d("CALLABLE")
									)
					).show();

			return;
		}

		if (!loginPacket.auth_status) {
			loginError(loginPacket.auth_description, null, loginPacket.getErrorCode());
			return;
		}

		putPref(ST_DISPLAY_NAME, loginPacket.getDisplayName());
		putPref(ST_DISPLAY_NAME_OBFUS, obfus(loginPacket.getDisplayName()));
		putPref(ST_EMAIL, loginPacket.getEmail());
		putPref(STKN, loginPacket.getToken());
		putPref(FTKN, InstanceIDService.getNonNullFireToken());
		putPref(ST_DEV, loginPacket.developer);


		ShowcaseFactory.detatchCurrentShowcase(this);

		ShowcaseFactory.getDefaultShowcase(this, (showCaseView, messageContainer, txtTitle, txtMessage, btnClose, btnNext) -> {
			addRelativeParamRule(messageContainer, RelativeLayout.CENTER_VERTICAL);

			txtTitle.setText("Login Successful");
			txtMessage.setText(
					"Successfully logged into SnapTools as "
							+ loginPacket.getDisplayName()
			);

			btnClose.setVisibility(GONE);
			btnNext.setOnClickListener(v -> {
				showCaseView.hide();
				EventBus.getInstance().post(new GoogleAuthEvent(loginPacket));
			});
			btnNext.setText("Done");
		}).build().show();

		Answers.safeLogEvent(
				new LoginEvent()
						.putMethod("Google Auth")
						.putSuccess(true)
		);
	}

	public void loginError(String reason, @Nullable Throwable throwable, int errorCode) {
		ShowcaseFactory.detatchCurrentShowcase(this);

		ShowcaseFactory.getDefaultShowcase(this, (showCaseView, messageContainer, txtTitle, txtMessage, btnClose, btnNext) -> {
			addRelativeParamRule(messageContainer, RelativeLayout.CENTER_VERTICAL);

			txtTitle.setText("Login Failed");
			txtMessage.setText(
					"Reason: " + reason + "\n\n"
							+ "As a result, network communications to the SnapTools server have been disabled"
							+ "\n\n"
							+ "Error Code: " + errorCode
			);

			btnClose.setVisibility(GONE);
			btnNext.setText("Okay");
			btnNext.setOnClickListener((view) -> {
				showCaseView.hide();
				EventBus.getInstance().post(new GoogleAuthEvent(reason));
			});
		}).build().show();

		if (throwable != null)
			Timber.e(throwable, reason);
		else {
			Timber.d("LoginError: " + reason);
		}

		Auth.GoogleSignInApi.signOut(mGoogleApiClient);

		Answers.safeLogEvent(
				new LoginEvent()
						.putMethod("Google Auth")
						.putSuccess(false)
		);
	}

	private void displayGoogleAuthError(String message) {
		ShowcaseFactory.detatchCurrentShowcase(this);

		ShowcaseFactory.getDefaultShowcase(this, (showCaseView, messageContainer, txtTitle, txtMessage, btnClose, btnNext) -> {
			addRelativeParamRule(messageContainer, RelativeLayout.CENTER_VERTICAL);

			txtTitle.setText("Error Signing In");
			txtMessage.setText(
					"Google sign in was unsuccessful"
							+ "\n\n"
							+ message
			);

			btnClose.setVisibility(GONE);
			btnNext.setText("Okay");
			btnNext.setOnClickListener((view) -> showCaseView.hide());
		}).build().show();
	}

	@Override public void onBackPressed() {
		try {
			if (navigationView != null) {
				FragmentHelper activeFragment = navigationView.getActiveFragment();

				if (activeFragment != null && activeFragment.onBackPressed()) {
					return;
				}
			}
			if ((boolean) getPref(BACK_OPENS_MENU) && !drawerLayout.isDrawerOpen(Gravity.START)) {
				drawerLayout.openDrawer(Gravity.START);
				return;
			}

			super.onBackPressed();
		} catch (IllegalArgumentException e) {
			Timber.w(e);
		}
	}

	@Override protected void onResume() {
		super.onResume();
		GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
		int statusCode = apiAvailability.isGooglePlayServicesAvailable(this);
		apiAvailability.getErrorDialog(this, statusCode, GOOGLE_AUTH_RESPONSE);

		if (isInitialised) {
			displayCachedMessages();
		}
	}

	/**
	 * ===========================================================================
	 * Process Firebase messages that were cached due to the app not being in
	 * the foreground.
	 * ===========================================================================
	 */
	private void displayCachedMessages() {
		List<Map<String, String>> messageMetaDataSet = getPref(STORED_MESSAGE_METADATA_CACHE);

		Timber.d("Found %s cached messages", messageMetaDataSet.size());

		for (Map<String, String> data : messageMetaDataSet) {
			Timber.d("Working on metadata: " + data);

			MessagingService.handleMessageData(
					this,
					data,
					EventBus.getInstance()
			);
		}

		// Reset the cache ===========================================================
		putPref(STORED_MESSAGE_METADATA_CACHE, new ArrayList<>());
	}

	@Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		for (int grantResult : grantResults) {
			if (grantResult != PackageManager.PERMISSION_GRANTED) {
				DialogFactory.createErrorDialog(
						this,
						"Permission Request Denied",
						"As you've declined a vital permission request the application will terminate",
						new ThemedClickListener() {
							@Override public void clicked(ThemedDialog themedDialog) {
								themedDialog.dismiss();
								finish();
							}
						}
				).setDismissable(false).show();

				return;
			}
		}

		initialiseApplication();
	}

	@Override public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU && drawerLayout != null && !(boolean) getPref(BACK_OPENS_MENU)) {
			drawerLayout.openDrawer(Gravity.START);
			return true;
		}

		return super.onKeyUp(keyCode, event);
	}

	public void moveToMenu(@IdRes int menuId) {
		if (navigationView == null) {
			Timber.w("Can't move to menu %s. Navigation View is null",
					menuId);
			return;
		}

		navigationView.onNavigationItemSelected(menuId);
	}

	@Override public void fragmentSelected(FragmentHelper fragment) {
		drawerLayout.closeDrawers();
		replaceFragmentContainer(fragment);
		ActionBar actionBar = getSupportActionBar();

		if (actionBar != null)
			actionBar.setSubtitle(fragment.getName());

		btnTutorial.setVisibility(fragment.hasTutorial() ? View.VISIBLE : GONE);
	}

	@DebugLog private void replaceFragmentContainer(FragmentHelper newFragment) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		//transaction.setCustomAnimations(R.anim.enter, R.anim.exit);
		transaction.replace(R.id.fragment_container, newFragment);
		transaction.commit();
	}

	@SuppressWarnings("unused")
	@Subscribe public void handleFirebaseMessage(Message message) {
		Timber.d("Got firebase message posted: "
				+ message);

		message.triggerEvent(this, EventBus.getInstance());
	}

	@SuppressWarnings("unused")
	@Subscribe public void handleFirebaseTokenRefreshEvent(FirebaseTokenRefreshEvent tokenRefreshEvent) {
		if (getPref(STKN) != null) {
			FirebaseTokenRefresh.refreshToken(
					this
			);
		}
	}

	@SuppressWarnings("unused")
	@Subscribe public void handleReqCheckApkUpdateEvent(ReqCheckApkUpdateEvent updateEvent) {
		CheckAPKUpdate.checkApkUpdate(this, false);
	}

	@SuppressWarnings("unused")
	@Subscribe public void handleServerNotification(ServerMessageEvent serverMessageEvent) {
		Observable.create(
				e -> {
					new ModularDialog(this)
							.addComponent(new ModularHeader("Server Message"))
							.addComponent(new ModularTextView(serverMessageEvent.getMessage()))
							.addComponent(new ModularButtonsContainer()
									.addButton(
											new ModularButtonPrimary(
													"Understood",
													Dialog::dismiss
											)
									)
							)
							.show();

					e.onComplete();
				}
		).subscribeOn(AndroidSchedulers.mainThread())
				.subscribe();
	}

	@SuppressWarnings("unused")
	@Subscribe public void handleShopPurchaseEvent(ShopPurchaseEvent shopPurchaseEvent) {
		Observable.create(
				e -> {
					Timber.d("NEW ITEM PURCHASE");
					putPref(LAST_CHECK_SHOP, 0L);
					navigationView.onNavigationItemSelected(R.id.nav_shop);

					if (shopPurchaseEvent.getState()) {
						DialogFactory.createBasicMessage(
								this,
								shopPurchaseEvent.getTitle(),
								shopPurchaseEvent.getMessage()
						).show();
					} else {
						DialogFactory.createErrorDialog(
								this,
								shopPurchaseEvent.getTitle(),
								shopPurchaseEvent.getMessage()
						).show();
					}

					e.onComplete();
				}
		).subscribeOn(AndroidSchedulers.mainThread())
				.subscribe(new ErrorObserver<>());
	}

	@SuppressWarnings("unused")
	@Subscribe public void handleReqLogoutEvent(ReqLogoutEvent logoutEvent) {
		Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
				status -> {
					if (status.isSuccess()) {
						Logout.logout(
								MainActivity.this,
								getPref(ST_EMAIL),
								getPref(STKN),
								DeviceIdManager.getDeviceId(MainActivity.this),
								new PacketResultListener<LogoutPacket>() {
									@Override public void success(String message, LogoutPacket packet) {
									}

									@Override public void error(String message, Throwable t, int errorCode) {
									}
								}
						);

						logoutPref();

						ShowcaseFactory.getDefaultShowcase(this, (showCaseView, messageContainer, txtTitle, txtMessage, btnClose, btnNext) -> {
							addRelativeParamRule(messageContainer, RelativeLayout.CENTER_VERTICAL);

							txtTitle.setText("Logout Successful");
							txtMessage.setText("You have successfully logged out of SnapTools");

							btnClose.setVisibility(GONE);
							btnNext.setText("Okay");
							btnNext.setOnClickListener((view) -> showCaseView.hide());
						}).build().show();

					} else {

						ShowcaseFactory.getDefaultShowcase(this, (showCaseView, messageContainer, txtTitle, txtMessage, btnClose, btnNext) -> {
							addRelativeParamRule(messageContainer, RelativeLayout.CENTER_VERTICAL);

							txtTitle.setText("Logout Failure");
							txtMessage.setText(
									"Couldn't log out from SnapTools"
											+ "\n\nReason: " + status.getStatusMessage()
							);

							btnClose.setVisibility(GONE);
							btnNext.setText("Okay");
							btnNext.setOnClickListener((view) -> showCaseView.hide());
						}).build().show();
					}
				});
	}

	public void logoutPref() {
		removePref(STKN);
		removePref(ST_DISPLAY_NAME);
		removePref(TRIAL_MODE);

		EventBus.getInstance().post(new LogoutEvent());
	}

	@SuppressWarnings("unused")
	@Subscribe public void handleReqGoogleDisconnectEvent(ReqGoogleDisconnectEvent disconnectEvent) {
		Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
				status -> {
					if (status.isSuccess()) {
						logoutPref();

						ShowcaseFactory.getDefaultShowcase(this, (showCaseView, messageContainer, txtTitle, txtMessage, btnClose, btnNext) -> {
							txtTitle.setText("Disconnect Successful");
							txtMessage.setText("Your google account has been disconnected from SnapTools and you have been logged out");

							btnClose.setVisibility(GONE);
							btnNext.setText("Okay");
							btnNext.setOnClickListener((view) -> showCaseView.hide());
						}).build().show();

					} else {

						ShowcaseFactory.getDefaultShowcase(this, (showCaseView, messageContainer, txtTitle, txtMessage, btnClose, btnNext) -> {
							addRelativeParamRule(messageContainer, RelativeLayout.CENTER_VERTICAL);

							txtTitle.setText("Disconnect Failure");
							txtMessage.setText(
									"Couldn't disconnect your account from SnapTools"
											+ "\n\nReason: " + status.getStatusMessage()
							);

							btnClose.setVisibility(GONE);
							btnNext.setText("Okay");
							btnNext.setOnClickListener((view) -> showCaseView.hide());
						}).build().show();

					}
				}
		);
	}

	@SuppressWarnings("unused")
	@DebugLog @Subscribe public void handlePackLoadEvent(PackLoadEvent loadEvent) {
		try {
			ModulePack modulePack = loadEvent.getModulePack();

			if (navigationView == null) {
				Timber.w("Navigation view doesn't seem to exist");
				return;
			}

			Menu navMenu = navigationView.getMenu();

			Observable.create((ObservableOnSubscribe<Void>) e -> {
				if (modulePack == null) {
					e.onComplete();
					return;
				}

				Timber.d("Creating menu for %s",
						modulePack.getPackName());

				String menuName = "Pack: "
						+ modulePack.getPackDisplayName();
				int menuId = getIdFromString(menuName);

				if (navMenu.findItem(menuId) != null)
					navMenu.removeGroup(menuId);

				SubMenu packSubMenu = navMenu.addSubMenu(
						menuId,
						menuId,
						Menu.NONE,
						menuName
				);

				FragmentHelper[] staticFragments = modulePack.getStaticFragments();

				if (staticFragments != null) {
					for (FragmentHelper staticFragment : staticFragments) {
						packSubMenu.add(menuId, staticFragment.getMenuId(), Menu.NONE, staticFragment.getName());
						navigationView.addFragment(staticFragment);
					}
				}

				for (Module module : modulePack.getModules()) {
					FragmentHelper[] moduleFragments;

					try {
						moduleFragments = module.getUIFragments();
					} catch (Throwable t) {
						Timber.w(t, "Incompatible modulepack");
						SafeToast.show(this, "Current ModulePack not supported",
								Toast.LENGTH_LONG, true);
						e.onComplete();
						return;
					}

					if (moduleFragments == null)
						continue;

					for (FragmentHelper fragment : moduleFragments) {
						if (fragment == null)
							continue;

						packSubMenu.add(
								Menu.NONE,
								fragment.getMenuId(),
								Menu.NONE,
								fragment.getName()
						);

						navigationView.addFragment(fragment);
					}

				}
			}).subscribeOn(AndroidSchedulers.mainThread())
					.subscribe(new ErrorObserver<>("Error adding pack to menu"));
		} catch (Throwable t) {
			Timber.e(t, "Issue handling pack load event");
		}
	}

	@SuppressWarnings("unused")
	@DebugLog @Subscribe public void handlePackUnloadEvent(PackUnloadEvent packUnloadEvent) {
		LocalPackMetaData packMetaData = packUnloadEvent.getPackMetaData();

		Menu menu = navigationView.getMenu();

		for (int i = 0; i < menu.size(); i++) {
			MenuItem item = menu.getItem(i);

			if (item.getTitle().equals("Pack: "
					+ packMetaData.getDisplayName())) {
				item.getSubMenu().clear();
				navigationView.removeFragment(item.getItemId());
			}
		}
	}

	@SuppressWarnings("unused")
	@Subscribe public void handleModuleEventRequest(ModuleEventRequest eventRequest) {
		Menu navMenu = navigationView.getMenu();

		if (navMenu == null) {
			Timber.w("Null NavMenu on module event");
			return;
		}

		ModulePack pack = FrameworkManager.getModulePack(eventRequest.getPackName());
		if (pack == null) {
			Timber.w("Pack not found... Not scanning fragments");
			return;
		}

		Module module = pack.getModule(eventRequest.getModuleName());
		if (module == null) {
			Timber.w("Module not found... Not scanning fragments");
			return;
		}

		String menuName = "Pack: "
				+ pack.getPackDisplayName();
		int menuId = getIdFromString(menuName);

		SubMenu packSubMenu = navMenu.findItem(menuId).getSubMenu();

		FragmentHelper[] moduleFragments;

		try {
			moduleFragments = module.getUIFragments();
		} catch (Throwable t) {
			Timber.w(t, "Incompatible modulepack");
			return;
		}

		if (moduleFragments == null)
			return;

		switch (eventRequest.getEventRequest()) {
			case UNLOAD:
				for (FragmentHelper fragment : moduleFragments) {
					if (fragment == null)
						continue;

					packSubMenu.removeItem(fragment.getMenuId());
					navigationView.removeFragment(fragment.getMenuId());
				}
				break;
			case LOAD:

				for (FragmentHelper fragment : moduleFragments) {
					if (fragment == null)
						continue;

					packSubMenu.add(
							Menu.NONE,
							fragment.getMenuId(),
							Menu.NONE,
							fragment.getName()
					);

					navigationView.addFragment(fragment);
				}
				break;
			default:
				Timber.d("Ignoring Unhandled Request");
		}
	}

	@SuppressWarnings("unused")
	@DebugLog @Subscribe public void handleLoadPackSettingsEvent(LoadPackSettingsEvent loadEvent) {
		String packName = loadEvent.getPackName();
		ModulePack pack = FrameworkManager.getModulePack(packName);

		if (pack == null) {
			Timber.e("No pack found");
			SafeToast.show(this, "Couldn't find pack settings page",
					Toast.LENGTH_LONG, true);
			return;
		}

		FragmentHelper[] staticFragments = pack.getStaticFragments();

		if (staticFragments == null || staticFragments.length <= 0) {
			Timber.w("No settings page found");
			SafeToast.show(this, "Couldn't find pack settings page",
					Toast.LENGTH_LONG, true);
			return;
		}

		Integer menuId = staticFragments[0].getMenuId();

		if (menuId == null || !navigationView.onNavigationItemSelected(menuId)) {
			Timber.e("No menu found with id: " + menuId);
			SafeToast.show(this, "Couldn't find pack settings page",
					Toast.LENGTH_LONG, true);
		}
	}

	@SuppressWarnings("unused")
	@Subscribe public void handleTutorialFinishedEvent(TutorialFinishedEvent tutorialFinishedEvent) {
		if (tutorialFinishedEvent.isFullTutorial()) {
			int index = -1;
			for (Integer menuId : TUTORIAL_FRAGMENTS) {
				index++;

				if (menuId.equals(tutorialFinishedEvent.getMenuId()))
					break;
			}

			Timber.d("Index: " + index);

			if (index == -1)
				throw new IllegalStateException("Couldn't figure out menu item: "
						+ tutorialFinishedEvent.getMenuId());

			if (index + 1 < TUTORIAL_FRAGMENTS.size()) {
				int nextMenuId = TUTORIAL_FRAGMENTS.get(index + 1);

				if (navigationView.onNavigationItemSelected(nextMenuId)) {
					navigationView.getActiveFragment().triggerOnVisible(
							(fragmentHelper, v) -> fragmentHelper.triggerTutorial(true)
					);
				}
			} else {
				navigationView.onNavigationItemSelected(R.id.nav_home);

				putPref(SHOW_TUTORIAL, false);

				ShowcaseFactory.getDefaultShowcase(this, (showCaseView, messageContainer, txtTitle, txtMessage, btnClose, btnNext) -> {
					addRelativeParamRule(messageContainer, RelativeLayout.CENTER_VERTICAL);

					txtTitle.setText("End of Tutorial");
					txtMessage.setText("Thanks for going through the Tutorial!"
							+ "\n" + "Enjoy SnapTools!");
					btnClose.setVisibility(GONE);

					btnNext.setText("Done");
					btnNext.setOnClickListener(v -> navigationView.getActiveFragment().triggerOnVisible(
							(fragmentHelper, v1) -> {
								showCaseView.hide();
								triggerKonfetti();
							}, 250
					));
				}).build().show();

				Answers.safeLogEvent(
						new CustomEvent("Finished Full Tutorial")
				);
			}
		}
	}

	public void triggerKonfetti() {
		konfettiView.build()
				.addColors(Color.YELLOW, Color.rgb(255, 175, 0))
				.setDirection(0.0, 359.0)
				.setSpeed(1f, 5f)
				.setFadeOutEnabled(true)
				.setTimeToLive(2000L)
				.addShapes(Shape.RECT, Shape.CIRCLE)
				.addSizes(new Size(12, 5))
				.setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
				.stream(300, 5000L);
	}

	@SuppressWarnings("unused")
	@Subscribe public void handleReqLoadFragmentEvent(ReqLoadFragmentEvent loadFragmentEvent) {
		if (!navigationView.onNavigationItemSelected(loadFragmentEvent.getFragmentId())) {
			Timber.e("Couldn't load fragment for id: "
					+ loadFragmentEvent);
			SafeToast.show(
					this,
					"Couldn't load fragment "
							+ loadFragmentEvent.getName(),
					Toast.LENGTH_LONG,
					true
			);
		}
	}

	@Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
		Timber.d("Failed google connection");
	}

	@OnClick(R.id.btn_tutorial) public void tutorialClicked() {
		FragmentHelper activeFragment = navigationView.getActiveFragment();

		if (activeFragment == null) {
			Timber.w("Null active fragment!");
			return;
		}

		if (drawerLayout != null)
			drawerLayout.closeDrawers();

		activeFragment.triggerTutorial(false);

		Answers.safeLogEvent(
				new CustomEvent("Manual Tutorial")
						.putCustomAttribute("Active Fragment",
								activeFragment.getName())
		);
	}

	@OnLongClick(R.id.btn_tutorial) public boolean tutorialLongClick() {
		FragmentHelper activeFragment = navigationView.getActiveFragment();

		if (activeFragment != null && activeFragment instanceof HomeFragment) {
			activeFragment.triggerTutorial(true);
		}

		return true;
	}
}
