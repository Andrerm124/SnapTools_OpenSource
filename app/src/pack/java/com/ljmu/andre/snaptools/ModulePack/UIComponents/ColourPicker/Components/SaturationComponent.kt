package com.ljmu.andre.snaptools.ModulePack.UIComponents.ColourPicker.Components

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

import com.ljmu.andre.snaptools.ModulePack.UIComponents.ColourPicker.Metrics
import com.ljmu.andre.snaptools.ModulePack.UIComponents.ColourPicker.Paints

internal class SaturationComponent(metrics: Metrics, paints: Paints, override val arcLength: Float, override val arcStartAngle: Float) : ArcComponent(metrics, paints) {
    override val range: Float = 1f
    override val hslIndex: Int = 1
    override val NO_OF_COLORS = 11
    override val colors = IntArray(NO_OF_COLORS)
    override val colorPosition = FloatArray(NO_OF_COLORS)

    init {
        angle = (arcStartAngle + arcLength / 2f).toDouble()
    }

}