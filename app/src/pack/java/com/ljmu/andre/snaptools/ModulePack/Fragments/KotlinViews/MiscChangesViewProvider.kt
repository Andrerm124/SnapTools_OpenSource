package com.ljmu.andre.snaptools.ModulePack.Fragments.KotlinViews

import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.ljmu.andre.GsonPreferences.Preferences.getPref
import com.ljmu.andre.snaptools.ModulePack.Fragments.KotlinViews.CustomViews.Companion.header
import com.ljmu.andre.snaptools.ModulePack.Fragments.KotlinViews.CustomViews.Companion.label
import com.ljmu.andre.snaptools.ModulePack.Fragments.MiscChangesFragment
import com.ljmu.andre.snaptools.ModulePack.Utils.KotlinUtils.Companion.toDp
import com.ljmu.andre.snaptools.ModulePack.Utils.KotlinUtils.Companion.toId
import com.ljmu.andre.snaptools.ModulePack.Utils.ModulePreferenceDef.*
import com.ljmu.andre.snaptools.ModulePack.Utils.Result
import com.ljmu.andre.snaptools.ModulePack.Utils.ViewFactory
import com.ljmu.andre.snaptools.Utils.Callable
import com.ljmu.andre.snaptools.Utils.PreferenceHelpers.putAndKill
import com.ljmu.andre.snaptools.Utils.ResourceUtils
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.themedSwitchCompat

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */
@Suppress("DEPRECATION", "UNCHECKED_CAST")
class MiscChangesViewProvider(var activity: Activity, private var eventCallable: Callable<Result<MiscChangesFragment.MiscChangesEvent, Any>>) {
    val fontList: List<String> = ArrayList()
    private val fontSpinnerAdapter = ArrayAdapter<String>(activity,
            R.layout.simple_spinner_dropdown_item, fontList)

    @SuppressLint("ResourceType")
    fun <T : ViewGroup> getMainContainer(): T =
            activity.UI {
                scrollView {
                    lparams(matchParent, matchParent)
                    id = "misc_changes_scrollview".toId()

                    verticalLayout {
                        header("Caption Settings")

                        linearLayout {
                            label("Snapchat Font: ").lparams(width = matchParent, height = wrapContent) {
                                weight = 1f
                                gravity = Gravity.CENTER_VERTICAL
                            }

                            themedSpinner {
                                id = "font_selector_spinner".toId()
                                adapter = fontSpinnerAdapter

                                ViewFactory.assignItemChangedProvider(
                                        this,
                                        ViewFactory.OnItemChangedProvider<String>(
                                                { newItem, _, _ ->
                                                    eventCallable.call(
                                                            Result(MiscChangesFragment.MiscChangesEvent.FONT_SELECTED, newItem)
                                                    )
                                                },
                                                { getPref(CURRENT_FONT) }
                                        )
                                )
                            }.lparams(matchParent) {
                                gravity = Gravity.CENTER_VERTICAL
                                weight = 1f
                            }
                        }

                        themedSwitchCompat(ResourceUtils.getStyle(activity, "DefaultSwitch")) {
                            text = "Force Caption Multi-Line"
                            verticalPadding = dip(10)
                            id = ResourceUtils.getIdFromString("switch_misc_force_multiline")
                            isChecked = getPref(FORCE_MULTILINE)
                            setOnCheckedChangeListener({ _, isChecked -> putAndKill(FORCE_MULTILINE, isChecked, activity) })
                        }.lparams(matchParent)

                        header("Caption Menu Settings")

                        themedSwitchCompat(ResourceUtils.getStyle(activity, "DefaultSwitch")) {
                            text = "Cut Button"
                            verticalPadding = dip(10)
                            id = ResourceUtils.getIdFromString("switch_misc_context_cut")
                            isChecked = getPref(CUT_BUTTON)
                            setOnCheckedChangeListener({ _, isChecked -> putAndKill(CUT_BUTTON, isChecked, activity) })
                        }.lparams(matchParent)

                        themedSwitchCompat(ResourceUtils.getStyle(activity, "DefaultSwitch")) {
                            text = "Copy Option"
                            verticalPadding = dip(10)
                            id = ResourceUtils.getIdFromString("switch_misc_context_copy")
                            isChecked = getPref(COPY_BUTTON)
                            setOnCheckedChangeListener({ _, isChecked -> putAndKill(COPY_BUTTON, isChecked, activity) })
                        }.lparams(matchParent)

                        themedSwitchCompat(ResourceUtils.getStyle(activity, "DefaultSwitch")) {
                            text = "Paste Button"
                            verticalPadding = dip(10)
                            id = ResourceUtils.getIdFromString("switch_misc_context_paste")
                            isChecked = getPref(PASTE_BUTTON)
                            setOnCheckedChangeListener({ _, isChecked -> putAndKill(PASTE_BUTTON, isChecked, activity) })
                        }.lparams(matchParent)

                        /*header("Unlimited Viewing")

                        themedSwitchCompat(ResourceUtils.getStyle(activity, "DefaultSwitch")) {
                            text = "Looping Videos"
                            verticalPadding = dip(10)
                            id = ResourceUtils.getIdFromString("switch_viewing_videos")
                            isChecked = getPref(UNLIMITED_VIEWING_VIDEOS)
                            setOnCheckedChangeListener({ _, isChecked -> putAndKill(UNLIMITED_VIEWING_VIDEOS, isChecked, activity) })
                        }.lparams(matchParent)

                        themedSwitchCompat(ResourceUtils.getStyle(activity, "DefaultSwitch")) {
                            text = "Timeless Images"
                            verticalPadding = dip(10)
                            id = ResourceUtils.getIdFromString("switch_viewing_videos")
                            isChecked = getPref(UNLIMITED_VIEWING_IMAGES)
                            setOnCheckedChangeListener({ _, isChecked -> putAndKill(UNLIMITED_VIEWING_IMAGES, isChecked, activity) })
                        }.lparams(matchParent)*/

                    }.lparams(matchParent, wrapContent) {
                        margin = 16.toDp()
                    }
                }
            }.view as T

    fun refreshFontAdapter() {
        fontSpinnerAdapter.notifyDataSetChanged()
    }
}