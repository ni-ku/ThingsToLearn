package de.niku.ttl.data.db

import androidx.room.TypeConverter
import java.util.*

object DbTypeConverters {

    @TypeConverter
    @JvmStatic
    fun fromTimestamp(value: Long?): Date? {
        return value?.let {
            Date(value)
        }
    }

    @TypeConverter
    @JvmStatic
    fun dateToTimestamp(value: Date?): Long? {
        return value?.let {
            value.time
        }
    }
}