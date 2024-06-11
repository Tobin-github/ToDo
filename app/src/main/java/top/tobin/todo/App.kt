package top.tobin.todo

import android.content.Context
import androidx.multidex.MultiDexApplication
import top.tobin.common.base.Initializer
import dagger.hilt.android.HiltAndroidApp
import top.tobin.component.framework.BusinessCompManager
import top.tobin.todo.busmodulecfg.BusModuleConfigProxy

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: App. handleAccEvent
 */
@HiltAndroidApp
class App : MultiDexApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Initializer.init(this)
    }

    override fun onCreate() {
        super.onCreate()
        loadAllBusComp()
    }

    /**
     * 加载所有的业务组件
     */
    private fun loadAllBusComp() {
        for (busModule in BusModuleConfigProxy.instance.getBusModules()) {
            BusinessCompManager.getInstance().registerComp(busModule.moduleName, busModule.moduleImp)
        }
    }


}