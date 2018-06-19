package com.ljmu.andre.snaptools.ModulePack.Fragments.KotlinViews

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import com.kekstudio.dachshundtablayout.DachshundTabLayout
import com.ljmu.andre.snaptools.Utils.ResourceUtils
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

class ThemedTabView: AnkoComponent<Context> {
    override fun createView(ui: AnkoContext<Context>): DachshundTabLayout = with(ui) {
        ankoView(::DachshundTabLayout, 0) {
            id = ResourceUtils.getIdFromString("tab_layout")

            backgroundResource = ResourceUtils.getDrawable(ui.ctx, "tab_border")
            setSelectedTabIndicatorColor(ContextCompat.getColor(
                    ui.ctx,
                    ResourceUtils.getColor(ui.ctx, "primaryLight")
            ))

            setTabTextColors(
                    ContextCompat.getColor(
                            ui.ctx,
                            ResourceUtils.getColor(ui.ctx, "textTertiary")
                    ),
                    ContextCompat.getColor(
                            ui.ctx,
                            ResourceUtils.getColor(ui.ctx, "primaryLight")
                    )
            )

            elevation = 4f
        }
    }

}