package top.tobin.web.remotewebview.javascriptinterface

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.webkit.JavascriptInterface
import top.tobin.common.utils.LogUtil

/**
 * Created by Tobin on 2022/5/31.
 * Email: junbin.li@qq.com
 * Description: 保留command的注册. 不支持command通过远程aidl方式调用
 */
class WebViewJavascriptInterface(private val mContext: Context) {

    private val mHandler = Handler(Looper.myLooper()!!)
    private var javascriptCommand: JavascriptCommand? = null

    @JavascriptInterface
    fun post(cmd: String, param: String) {
        mHandler.post {
            try {
                LogUtil.d("WebViewJavascriptInterface", "cmd: $cmd param: $param")
                javascriptCommand?.exec(mContext, cmd, param)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setJavascriptCommand(javascriptCommand: JavascriptCommand) {
        this.javascriptCommand = javascriptCommand
    }

    interface JavascriptCommand {
        fun exec(context: Context, cmd: String, params: String)
    }
}