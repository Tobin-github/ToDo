package top.tobin.shared.ext

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: TimeExt.
 */
const val DATE_FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss"
const val DATE_FORMAT_YMD = "yyyy-MM-dd"

/**
 * 将Long转换成日期字符串
 *
 * @param pattern 时间样式
 * @return 时间字符串
 */
@SuppressLint("SimpleDateFormat")
fun Long.toDateString(pattern: String = DATE_FORMAT_YMDHMS): String =
    SimpleDateFormat(pattern).format(this)