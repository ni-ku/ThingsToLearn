package de.niku.ttl.util

import android.view.Menu
import androidx.core.view.iterator

fun colorMenuItem(menu: Menu, color: Int) {
    for (item in menu.iterator()) {
        if (item.icon != null) {
            item.icon.setTint(color)
        }
    }
}