package top.tobin.common.utils

import android.util.Log
import java.lang.Exception
import java.lang.StringBuilder

object LogUtil {

    /**
     * 默认 TAG
     */
    private const val PUB_TAG = "TODO"

    /**
     * 是否打印调试日志标志位
     */
    private const val DEBUG_FLAG = true


    private fun getAllStackInfo() {
        val stackArray = Thread.currentThread().stackTrace
        if (stackArray.isNotEmpty()) {
            for ((index, stack) in stackArray.withIndex()) {
                Log.d(PUB_TAG, "*****************")
                Log.d(PUB_TAG, "index:$index")
                Log.d(PUB_TAG, "stack.className:" + stack.className)
                Log.d(PUB_TAG, "stack.methodName:" + stack.methodName)
                Log.d(PUB_TAG, "stack.fileName:" + stack.fileName)
                Log.d(PUB_TAG, "stack.lineNumber:" + stack.lineNumber)
                Log.d(PUB_TAG, "stack.isNativeMethod:" + stack.isNativeMethod)
                Log.d(PUB_TAG, "*****************")
            }
        }
    }

    fun v(msg: String) {
        if (getLogPre(PUB_TAG, msg, "v")) return
        Log.v(PUB_TAG, msg)
    }

    fun d(msg: String) {
        if (!DEBUG_FLAG) return
        if (getLogPre(PUB_TAG, msg, "d")) return
        Log.d(PUB_TAG, msg)
    }


    fun i(msg: String) {
        if (getLogPre(PUB_TAG, msg, "i")) return
        Log.i(PUB_TAG, msg)
    }

    fun w(msg: String) {
        if (getLogPre(PUB_TAG, msg, "w")) return
        Log.w(PUB_TAG, msg)
    }

    fun e(msg: String) {
        if (getLogPre(PUB_TAG, msg, "e")) return
        Log.e(PUB_TAG, msg)
    }

    fun v(tag: String, msg: String) {
        if (getLogPre(tag, msg, "v")) return
        Log.v(tag, msg)
    }

    fun d(tag: String, msg: String) {
        if (!DEBUG_FLAG) return
        if (getLogPre(tag, msg, "d")) return
        Log.d(tag, msg)
    }

    fun i(tag: String, msg: String) {
        if (getLogPre(tag, msg, "i")) return
        Log.i(tag, msg)
    }

    fun w(tag: String, msg: String) {
        if (getLogPre(tag, msg, "w")) return
        Log.w(tag, msg)
    }

    fun e(tag: String, msg: String) {
        if (getLogPre(tag, msg, "e")) return
        Log.e(tag, msg)
    }

    /**
     * 组合特定格式的日志前缀，示例： (MainActivity.kt:26)【onClickEvent】
     */
    private fun getLogPre(tag: String, msg: String, grade: String): Boolean {
        try {
            val stackArray = Thread.currentThread().stackTrace
            //这个值是经过测试得出的；运行 test() 方法可以看出调用 LogUtil 的类 的 index = 4
            val index = 4
            if (stackArray.size > (index + 1)) {
                val logTraceElement = stackArray[index]
                val logPre = StringBuilder()
                logPre.append("(")
                logPre.append(logTraceElement.fileName)
                logPre.append(":")
                logPre.append(logTraceElement.lineNumber)
                logPre.append(")")
                logPre.append("【")
                logPre.append(logTraceElement.methodName)
                logPre.append("】")

                when (grade) {
                    "v" -> {
                        Log.v(tag, logPre.toString() + msg)
                    }
                    "i" -> {
                        Log.i(tag, logPre.toString() + msg)
                    }
                    "d" -> {
                        Log.d(tag, logPre.toString() + msg)
                    }
                    "w" -> {
                        Log.w(tag, logPre.toString() + msg)
                    }
                    "e" -> {
                        Log.e(tag, logPre.toString() + msg)
                    }
                }
                return true
            }
        } catch (e: Exception) {
            e.message?.let { Log.e(tag, it) }
            e.printStackTrace()
        }
        return false
    }

}
