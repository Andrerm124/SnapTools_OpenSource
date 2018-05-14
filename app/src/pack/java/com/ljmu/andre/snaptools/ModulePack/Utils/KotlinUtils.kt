package com.ljmu.andre.snaptools.ModulePack.Utils

import android.content.res.Resources
import com.ljmu.andre.snaptools.Utils.ResourceUtils.getIdFromString

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */
class KotlinUtils {
    companion object {
        fun Int.toDp(): Int = Math.ceil((this * Resources.getSystem().displayMetrics.density).toDouble()).toInt()
        fun Int.toPx(): Int = Math.ceil((this / Resources.getSystem().displayMetrics.density).toDouble()).toInt()
        fun String.toId(): Int = getIdFromString(this)
    }
}