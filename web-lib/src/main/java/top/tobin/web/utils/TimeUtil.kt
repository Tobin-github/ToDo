package top.tobin.web.utils

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import kotlin.collections.HashMap

/**
 * Created by Tobin on 2022/6/28.
 * Email: junbin.li@qq.com
 * Description: TimeUtil.
 */
object TimeUtil {
    private val SDF_THREAD_LOCAL: ThreadLocal<Map<String, SimpleDateFormat>> =
        object : ThreadLocal<Map<String, SimpleDateFormat>>() {
            override fun initialValue(): Map<String, SimpleDateFormat> {
                return HashMap()
            }
        }

    private fun getDefaultFormat(): SimpleDateFormat {
        return getSafeDateFormat("yyyy-MM-dd HH:mm:ss")
    }

    @SuppressLint("SimpleDateFormat")
    private fun getSafeDateFormat(pattern: String): SimpleDateFormat {
        val sdfMap = SDF_THREAD_LOCAL.get() as MutableMap<String, SimpleDateFormat>
        var simpleDateFormat = sdfMap[pattern]
        if (simpleDateFormat == null) {
            simpleDateFormat = SimpleDateFormat(pattern)
            sdfMap[pattern] = simpleDateFormat
        }
        return simpleDateFormat
    }

    /**
     * 检查设备是否正在使用网络提供时间。
     * 在您想要验证设备是否设置了正确的时间、避免欺诈或想要防止用户弄乱时间并滥用您的“一次性”和“过期”功能的情况下很有用。
     * @return `true`：是   `false`：否
     */
    fun isUsingNetworkProvidedTime(context: Context): Boolean {
        return Settings.Global.getInt(context.contentResolver, Settings.Global.AUTO_TIME, 0) == 1
    }

    /**
     * Formatted time string to the milliseconds.
     *
     * @param time    The formatted time string.
     * @param pattern The pattern of date format, such as yyyy/MM/dd HH:mm
     * @return the milliseconds
     */
    fun string2Millis(time: String, pattern: String): Long {
        return string2Millis(time, getSafeDateFormat(pattern))
    }

    /**
     * 以毫秒为单位的格式化时间字符串
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the milliseconds
     */
    private fun string2Millis(time: String, format: DateFormat): Long {
        try {
            return format.parse(time).time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return -1
    }

}