package top.tobin.shared.data.local

import androidx.room.TypeConverter
import java.util.*

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: DateConverters.
 */
class DateConverters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

}

