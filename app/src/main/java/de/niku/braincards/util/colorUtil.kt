package de.niku.braincards.util

import android.content.Context
import android.os.Build

fun getResColorInt(context: Context, colorRes: Int): Int {
    var color: Int = 0
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        color = context!!.theme.resources.getColor(colorRes, null)
    } else {
        color = context!!.theme.resources.getColor(colorRes)
    }
    return color
}