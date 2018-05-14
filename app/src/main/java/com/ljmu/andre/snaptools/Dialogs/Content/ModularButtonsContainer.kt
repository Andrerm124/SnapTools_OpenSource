package com.ljmu.andre.snaptools.Dialogs.Content

import android.app.Activity
import android.view.Gravity
import android.view.View
import com.ljmu.andre.snaptools.Dialogs.DialogComponent
import com.ljmu.andre.snaptools.Dialogs.ModularDialog
import com.ljmu.andre.snaptools.Utils.ResourceUtils.getIdFromString
import org.jetbrains.anko.*

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */
class ModularButtonsContainer : DialogComponent {
    private val buttons = ArrayList<ModularButtonPrimary>()

    fun addButton(button: ModularButtonPrimary): ModularButtonsContainer {
        buttons.add(button)
        return this
    }

    override fun bind(activity: Activity, dialog: ModularDialog): View =
            activity.UI {
                tableRow {
                    lparams(width = matchParent, height = wrapContent)
                    gravity = Gravity.CENTER_HORIZONTAL
                    id = getIdFromString("ModularDialogContainer")

                    val buttonIterator: Iterator<ModularButtonPrimary> = buttons.iterator()
                    while (buttonIterator.hasNext()) {
                        val buttonData = buttonIterator.next()

                        val buttonView = buttonData.bind(activity, dialog)
                        buttonView.lparams(width = matchParent, height = wrapContent, initWeight = 1f)

                        addView(buttonView)

                        if (buttonIterator.hasNext()) {
                            space().lparams(width = dip(20), height = 0)
                        }
                    }
                }
            }.view
}