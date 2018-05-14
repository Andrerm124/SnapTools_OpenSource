package com.ljmu.andre.snaptools.Dialogs

import android.app.Activity
import android.view.View

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */
interface DialogComponent {
    fun bind(activity: Activity, dialog: ModularDialog): View
}