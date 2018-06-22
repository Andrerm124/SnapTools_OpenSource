package com.ljmu.andre.snaptools.ModulePack.Fragments;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ljmu.andre.snaptools.Dialogs.ThemedDialog;
import com.ljmu.andre.snaptools.Dialogs.ThemedDialog.ThemedDialogExtension;
import com.ljmu.andre.snaptools.ModulePack.Fragments.KotlinViews.PickerViewProvider;
import com.ljmu.andre.snaptools.ModulePack.UIComponents.ColourPicker.HSLColorPicker;
import com.ljmu.andre.snaptools.ModulePack.UIComponents.ColourPicker.Listeners.SimpleColorSelectionListener;
import com.ljmu.andre.snaptools.R;
import com.ljmu.andre.snaptools.Utils.Callable;
import com.ljmu.andre.snaptools.Utils.ResourceMapper;
import com.ljmu.andre.snaptools.Utils.ResourceUtils;

import java.util.HashMap;
import java.util.HashSet;

import timber.log.Timber;

import static com.ljmu.andre.snaptools.Utils.ResourceUtils.getDSLView;

/**
 * Created by ethan on 1/11/2018.
 */

public class ColorPickerExtension implements ThemedDialogExtension {

	private Callable<Integer> returnColor = null;
	private Integer currentColor = -11418171;
	private Activity activity;
	public ColorPickerExtension(Activity snapActivity) {
		this.activity = snapActivity;
	}

	@Override public void onCreate(LayoutInflater inflater, View parent, ViewGroup content, ThemedDialog themedDialog) {
		//getLayout(inflater.getContext(), "colorPicker"),
		final HSLColorPicker colorPicker = new HSLColorPicker(inflater.getContext());
		colorPicker.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		ViewGroup mainContainer = new PickerViewProvider().getMainContainer(inflater.getContext());
		FrameLayout pickerContainer = getDSLView(mainContainer, "color_picker_container");
		Button cancelButton = getDSLView(mainContainer, "button_cancel");
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				returnColor.call(-5);
			}
		});
		Button okayButton = getDSLView(mainContainer, "button_okay");
		okayButton.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				returnColor.call(currentColor);
			}
		});

		//ImageView imageView = (ImageView) inflater.inflate(R.id.fontImageSelector, content);
		colorPicker.setColorSelectionListener(new SimpleColorSelectionListener() {
			@Override
			public void onColorSelected(int color) {
				// cant be done like this: text.setBackgroundColor(color);
				//colorPicker.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
				Timber.d("color = " + color);
				//if (returnColor != null) {
					currentColor = color;
				//}
			}
		});

		//colorPicker.setVisibility(View.VISIBLE);
		pickerContainer.addView(colorPicker);
		content.addView(mainContainer);
	}

	public ColorPickerExtension setCallback(Callable<Integer> returnColor) {
		this.returnColor = returnColor;
		return this;
	}
}
