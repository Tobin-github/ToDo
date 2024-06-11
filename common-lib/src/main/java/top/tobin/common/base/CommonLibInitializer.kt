package top.tobin.common.base

import android.content.Context
import com.tencent.mmkv.MMKV
import androidx.startup.Initializer
import top.tobin.common.utils.LogUtil


class CommonLibInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        LogUtil.i("CommonLibInitializer create")
        MMKV.initialize(context)
    }


    override fun dependencies() = emptyList<Class<Initializer<*>>>()
}