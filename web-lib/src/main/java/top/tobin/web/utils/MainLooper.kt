package top.tobin.web.utils

import android.os.Handler
import android.os.Looper

/**
 * Created by Tobin on 2022/5/31.
 * Email: junbin.li@qq.com
 * Description: 切换UI线程.
 */
open class MainLooper protected constructor(looper: Looper) : Handler(looper) {
    companion object {
        private val instance = MainLooper(Looper.getMainLooper())
        fun runOnUiThread(runnable: Runnable) {
            if (Looper.getMainLooper() == Looper.myLooper()) {
                runnable.run()
            } else {
                instance.post(runnable)
            }
        }
    }
}