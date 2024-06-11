package top.tobin.my

import android.util.Log
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun test() {
        val timeString = "2023-04-01 12:00:00"; // 假设这是UTC时间
        val originalFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val targetFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

        try {
            val date = originalFormat.parse(timeString)

            // 设置目标时区，比如纽约时区
            targetFormat.timeZone = TimeZone.getTimeZone("Europe/Berlin")

            // 转换时区后的时间
            val timeInNewYork = targetFormat.format(date)

            Log.e("eeeee", "Time in New York: $timeInNewYork")
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        // 创建一个日历实例
        val calendar = Calendar.getInstance()

        // 假设我们要从UTC转换到东京时区
        val fromTimeZoneId = "UTC"
        val toTimeZoneId = "Asia/Tokyo"

        // 转换时区
        val convertedCalendar = convertTimeZone(calendar, fromTimeZoneId, toTimeZoneId)

        // 输出转换后的时间
        println("Original time: " + calendar.time)
        println("Converted time: " + convertedCalendar.time)
        assertEquals(calendar.time, convertedCalendar.time)
    }

    private fun convertTimeZone(date: Calendar, fromTimeZoneId: String, toTimeZoneId: String): Calendar {
        // 设置日历的时区为UTC
        date.timeZone = TimeZone.getTimeZone(fromTimeZoneId)

        // 转换到目标时区
        date.timeZone = TimeZone.getTimeZone(toTimeZoneId)

        return date
    }
}