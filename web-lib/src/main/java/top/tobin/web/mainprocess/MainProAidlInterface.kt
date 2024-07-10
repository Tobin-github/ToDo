package top.tobin.web.mainprocess

import android.content.Context
import android.os.Process
import android.os.RemoteException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import top.tobin.common.utils.LogUtil
import top.tobin.web.command.base.ResultBack
import top.tobin.web.command.mainprocess.MainProcessCommandsManager
import top.tobin.web.IWebAidlInterface
import top.tobin.web.IWebAidlCallback
import java.lang.Exception
import java.util.HashMap

/**
 * Created by Tobin on 2022/5/31.
 * Email: junbin.li@qq.com
 * Description: MainProAidlInterface.
 */
class MainProAidlInterface(context: Context) : IWebAidlInterface.Stub() {
    private val context: Context

    @Throws(RemoteException::class)
    override fun handleWebAction(
        actionName: String,
        jsonParams: String,
        callback: IWebAidlCallback
    ) {
        val pid = Process.myPid()
        LogUtil.d("MainProAidlInterface", "pid: $pid")
        try {
            val params = Gson().fromJson<HashMap<String, Any>>(
                jsonParams,
                object : TypeToken<HashMap<String?, Any?>?>() {}.type
            )
            handleRemoteAction(actionName, params, callback)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(Exception::class)
    private fun handleRemoteAction(
        actionName: String,
        paramMap: MutableMap<String, Any>,
        callback: IWebAidlCallback?
    ) {
        MainProcessCommandsManager.instance.findAndExecRemoteCommand(
            context, actionName, paramMap, object : ResultBack {
                override fun onResult(status: Int, action: String?, result: Any?) {
                    try {
                        callback?.onResult(status, actionName, Gson().toJson(result))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        )
    }

    init {
        this.context = context.applicationContext
    }
}