package de.niku.ttl.util

import android.os.Environment

/**
 * Checks if device has an external Storage
 */
fun hasExternalStorage(): Boolean {
    return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
}

/**
 * Checks if external Storage is writable
 */
fun isExternalStorageWriteable(): Boolean {
    return Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED_READ_ONLY
}