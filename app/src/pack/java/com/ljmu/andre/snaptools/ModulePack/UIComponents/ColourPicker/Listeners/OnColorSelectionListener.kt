package com.ljmu.andre.snaptools.ModulePack.UIComponents.ColourPicker.Listeners

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

/**
 * Listener to listen to color change events
 */
interface OnColorSelectionListener {

    /**
     * Invoked every time the color changes
     *
     * @param color the selected color
     */
    fun onColorSelected(color: Int)

    /**
     * Invoked when the color selection started
     *
     * @param color the color before the selection started
     */
    fun onColorSelectionStart(color: Int)

    /**
     * Invoked when the color selection is over
     *
     * @param color the selected color
     */
    fun onColorSelectionEnd(color: Int)
}