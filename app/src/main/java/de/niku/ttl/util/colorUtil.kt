package de.niku.ttl.util

import android.content.Context

fun getResColorInt(context: Context, colorRes: Int): Int {
    return context.theme.resources.getColor(colorRes, null)
}