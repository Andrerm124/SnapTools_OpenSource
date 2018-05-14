package com.ljmu.andre.snaptools.Fragments.Children;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ljmu.andre.Translation.Translator;
import com.ljmu.andre.snaptools.Dialogs.DialogFactory;
import com.ljmu.andre.snaptools.Dialogs.ThemedDialog;
import com.ljmu.andre.snaptools.Dialogs.ThemedDialog.ThemedClickListener;
import com.ljmu.andre.snaptools.EventBus.EventBus;
import com.ljmu.andre.snaptools.EventBus.Events.ReqLogoutEvent;
import com.ljmu.andre.snaptools.Networking.Helpers.CheckTrialMode;
import com.ljmu.andre.snaptools.Networking.Packets.TrialPacket;
import com.ljmu.andre.snaptools.Networking.WebResponse.PacketResultListener;
import com.ljmu.andre.snaptools.R;
import com.ljmu.andre.snaptools.Utils.TrialUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static com.ljmu.andre.GsonPreferences.Preferences.getPref;
import static com.ljmu.andre.GsonPreferences.Preferences.putPref;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.ST_DISPLAY_NAME;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.TRIAL_ACTIVE_TIME;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.TRIAL_MODE;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class LoggedInFragment extends Fragment {
	@BindView(R.id.txt_display_name) TextView txtDisplayName;
	@BindView(R.id.txt_trial_state) TextView txtTrialState;
	@BindView(R.id.btn_trial) Button btnTrial;
	Handler timerHandler = new Handler();
	private Long endTime;
	Runnable timerRunnable = new Runnable() {
		@SuppressLint("DefaultLocale")
		@Override
		public void run() {
			if (endTime == null) {
				txtTrialState.setText("Trial Mode");
				return;
			}

			long millis = endTime - System.currentTimeMillis();
			int seconds = (int) (millis / 1000);
			int minutes = seconds / 60;
			seconds = seconds % 60;

			if (millis <= 0) {
				txtTrialState.setText("Trial Expired");
				TrialUtils.endTrialIfExpired(getActivity());
				return;
			}

			txtTrialState.setText("Trial Mode: " + String.format("%dm:%02ds", minutes, seconds));
			timerHandler.postDelayed(this, 500);
		}
	};

	@Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View layoutContainer = inflater.inflate(R.layout.child_frag_logged_in, container, false);
		EventBus.soleRegister(this);
		ButterKnife.bind(this, layoutContainer);

		String displayName = getPref(ST_DISPLAY_NAME);

		if (displayName == null)
			displayName = "???";

		txtDisplayName.setText(displayName);

		return layoutContainer;
	}

	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		Translator.translateFragment(this);
	}

	@Override public void onResume() {
		super.onResume();
		updateTrialState();
	}

	@Override
	public void onPause() {
		super.onPause();

		if (timerHandler != null)
			timerHandler.removeCallbacks(timerRunnable);
	}

	@Override public void onDestroy() {
		super.onDestroy();
		EventBus.soleUnregister(this);
	}

	public void updateTrialState() {
		TrialUtils.confirmTrialMode(
				getActivity(),
				trialPacket -> {
					Runnable runnable = () -> {
						if (getActivity() == null || getActivity().isDestroyed() || isRemoving())
							return;

						int trialMode = getPref(TRIAL_MODE);
						Long trialActiveTime = getPref(TRIAL_ACTIVE_TIME);

						if (trialMode == TrialUtils.TRIAL_AVAILABLE) {
							Timber.d("Trial is available");
							btnTrial.setVisibility(View.VISIBLE);
						} else if (trialMode == TrialUtils.TRIAL_ACTIVE) {
							btnTrial.setVisibility(View.GONE);
							txtTrialState.setVisibility(View.VISIBLE);

							if (trialActiveTime != null) {
								endTime = trialActiveTime + TimeUnit.HOURS.toMillis(1);
								timerHandler.postDelayed(timerRunnable, 0);
							}
						}
					};

					if (Looper.myLooper() != Looper.getMainLooper())
						getActivity().runOnUiThread(runnable);
					else
						runnable.run();
				}
		);
	}

	@OnClick(R.id.btn_logout) public void reqLogout() {
		EventBus.getInstance().post(new ReqLogoutEvent());
	}

	@OnClick(R.id.btn_trial) public void reqTrial() {
		Timber.w("Asking for trial");

		DialogFactory.createConfirmation(
				getActivity(),
				"Activate Trial?",
				"You are about to enable Trial Mode."
						+ "\n\nThis function is a ONE TIME USE per user and will allow you to trial any premium item as though it was purchased"
						+ "\nThis feature is provided with the intent to assist in the decision of whether or not to purchase a premium pack and should not be abused",
				new ThemedClickListener() {
					@Override public void clicked(ThemedDialog themedDialog) {
						activateTrial();
						themedDialog.dismiss();
					}
				}
		).show();
	}

	private void activateTrial() {
		CheckTrialMode.activateTrialMode(
				getActivity(),
				new PacketResultListener<TrialPacket>() {
					@Override public void success(String message, TrialPacket packet) {
						Timber.d("Trial Mode: " + packet.trialMode);

						if (packet.trialMode == TrialUtils.TRIAL_ACTIVE) {
							putPref(TRIAL_MODE, TrialUtils.TRIAL_ACTIVE);
							putPref(TRIAL_ACTIVE_TIME, packet.getActiveTimestamp());

							DialogFactory.createBasicMessage(
									getActivity(),
									"Trial Mode Enabled",
									"Successfully enabled Trial Mode, you can now use any premium pack for up to 1 hour"
							).show();

							updateTrialState();
						} else {
							error(
									"Unable to enable Trial Mode",
									null,
									-1
							);
						}
					}

					@Override public void error(String message, Throwable t, int errorCode) {
						DialogFactory.createErrorDialog(
								getActivity(),
								"Error Enabling Trial Mode",
								message,
								errorCode
						).show();

						if (errorCode == 160)
							putPref(TRIAL_MODE, TrialUtils.TRIAL_EXPIRED);
					}
				}
		);
	}

	/*@OnClick(R.id.btn_logout_disconnect) public void reqDisconnect() {
		EventBus.getInstance().post(new ReqGoogleDisconnectEvent());
	}*/
}
