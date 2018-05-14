package com.ljmu.andre.snaptools.Fragments.Tutorials;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.ljmu.andre.snaptools.EventBus.EventBus;
import com.ljmu.andre.snaptools.EventBus.Events.ReqGoogleAuthEvent;
import com.ljmu.andre.snaptools.FCM.MessageTypes.Message;
import com.ljmu.andre.snaptools.Fragments.FragmentHelper;
import com.ljmu.andre.snaptools.R;
import com.ljmu.andre.snaptools.Utils.TutorialDetail;
import com.ljmu.andre.snaptools.Utils.TutorialDetail.InflationProcessor;
import com.ljmu.andre.snaptools.Utils.TutorialDetail.MessagePosition;

import me.toptas.fancyshowcase.FancyShowCaseView;

import static com.ljmu.andre.GsonPreferences.Preferences.getPref;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.STKN;
import static com.ljmu.andre.snaptools.Utils.ResourceUtils.getView;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class HomeTutorial {
	public static ImmutableList<TutorialDetail> getTutorials() {
		return new Builder<TutorialDetail>()
				/**
				 * ===========================================================================
				 * Display Intro Message
				 * ===========================================================================
				 */
				.add(
						new TutorialDetail()
								.setTitle("Welcome")
								.setMessage("Thanks for choosing to use SnapTools!\n\nWe're now going to walk you through how to use the app.")
								.setMessagePosition(MessagePosition.MIDDLE)
				)

				/**
				 * ===========================================================================
				 * Check Versioning
				 * ===========================================================================
				 */
				/*.add(
						new TutorialDetail()
								.setViewId(R.id.imageView)
								.setTitle("Version Check")
								.setMessageProcessor((activity, tutorialDetail) -> {
									String installVer = MiscUtils.getInstalledSCVer(activity);

									if (installVer == null)
										return "Uh... looks like Snapchat isn't installed on this device. You need to have Snapchat version " + PackUtils.getLatestSupportedVersion() + " to use SnapTools.\n\nPlease install the app and try again.";

									int offset = MiscUtils.versionCompare(installVer, PackUtils.getLatestSupportedVersion());
									switch (offset) {
										case -1:
											return "Uh oh, looks like your installed Snapchat version is out of date!\n\nPlease upgrade to version ";
										case 0:
											return "You're all good :) Your installed Snapchat version is compatible with the latest version of SnapTools!";
										case 1:
											return "Uh oh, looks like your installed Snapchat version isn't quite supported by us yet!\n\nPlease downgrade to version " + PackUtils.getLatestSupportedVersion() + " in order to use SnapTools's features.\n\nWe apologise for this inconvenience\nand will work to update the app ASAP.";
										default:
											return "Unknown issue determining versions";
									}
								})
				)*/

				/**
				 * ===========================================================================
				 * Perform login checks
				 * ===========================================================================
				 */
				.add(
						new TutorialDetail()
								.setViewId(R.id.auth_panel)
								.setTitle("Log In")
								.setMessagePosition(MessagePosition.MIDDLE)
								.setMessageProcessor((activity, tutorialDetail) -> {
									if (getPref(STKN) != null) {
										return "You're already logged in, fantastic! :)";
									}

									return "To use SnapTools, you need to connect your Google Account.\n\nTap \"Login with Google\", and on the next screen, please sign in.";
								})
								.setInflationProcessor(new InflationProcessor() {
									@Override public boolean beforeInflation(FragmentHelper fragment, TutorialDetail tutorialDetail, FancyShowCaseView showcaseView) {
										return false;
									}

									@Override public void afterInflation(FragmentHelper fragment, TutorialDetail tutorialDetail, FancyShowCaseView showcaseView) {
										if (getPref(STKN) == null) {
											getView(showcaseView, R.id.showcase_button_close)
													.setVisibility(View.GONE);

											Button nextButton = getView(showcaseView, R.id.showcase_button_next);
											nextButton.setText("Show Login");
											nextButton.setOnClickListener(new OnClickListener() {
												@Override public void onClick(View v) {
													EventBus.soleRegister(this);
													EventBus.getInstance().post(new ReqGoogleAuthEvent());
													EventBus.soleUnregister(this);
												}
											});
										}
									}
								})
				)

				.build();
		// ===========================================================================
	}
}
