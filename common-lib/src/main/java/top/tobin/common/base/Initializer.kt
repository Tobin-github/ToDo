package top.tobin.common.base

import android.app.Application
import top.tobin.common.utils.LogUtil

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: Initializer.
 */
object Initializer {
    lateinit var application: Application

    fun init(application: Application) {
        LogUtil.i("CommonLibInitializer init")

        Initializer.application = application
    }

}