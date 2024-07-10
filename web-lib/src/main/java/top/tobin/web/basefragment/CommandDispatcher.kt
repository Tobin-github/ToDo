package top.tobin.web.basefragment

import android.content.Context
import android.os.RemoteException
import android.text.TextUtils
import android.webkit.WebView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import top.tobin.web.command.base.ResultBack

import top.tobin.web.command.webviewprocess.WebViewProcessCommandsManager
import top.tobin.web.utils.MainLooper

import top.tobin.web.mainprocess.RemoteWebBinderPool
import top.tobin.web.utils.WebConstants
import top.tobin.web.IWebAidlInterface
import top.tobin.web.IWebAidlCallback

import top.tobin.web.remotewebview.BaseWebView
import top.tobin.common.utils.LogUtil

import java.lang.Exception

/**
 * Created by Tobin on 2022/5/31.
 * Email: junbin.li@qq.com
 * Description: WebView所有请求分发.
 * 规则：
 * 1、先处理UI依赖
 * 2、再判断是否是跨进程通信，跨进程通信需要通过AIDL方式分发数据
 */
open class CommandDispatcher {
    private val gson = Gson()

    // 实现跨进程通信的接口
    private var webAidlInterface: IWebAidlInterface? = null
    fun initAidlConnect(context: Context) {
        if (webAidlInterface != null) {
            return
        }
        Thread {
            LogUtil.d(TAG, "AIDL Begin to connect with main process")
            val binderPool: RemoteWebBinderPool = RemoteWebBinderPool.getInstance(context)
            val iBinder = binderPool.queryBinder(RemoteWebBinderPool.BINDER_WEB_AIDL)
            webAidlInterface = IWebAidlInterface.Stub.asInterface(iBinder)
        }.start()
    }

    fun exec(
        context: Context, cmd: String, params: String, webView: WebView,
        dispatcherCallBack: DispatcherCallBack?
    ) {
        LogUtil.d(TAG, "command: $cmd params: $params")
        try {
            if (WebViewProcessCommandsManager.instance.checkHitLocalCommand(cmd)) {
                execLocalCommand(context, cmd, params, webView, dispatcherCallBack)
            } else {
                execRemoteCommand(cmd, params, webView, dispatcherCallBack)
            }
        } catch (e: Exception) {
            LogUtil.d(TAG, "Command exec error: ${e.message}")
        }
    }

    @Throws(Exception::class)
    private fun execLocalCommand(
        context: Context,cmd: String, params: String,
        webView: WebView, dispatcherCallBack: DispatcherCallBack?
    ) {
        LogUtil.d(TAG, "execLocalCommand params: $params")
        val mapParams = gson.fromJson<MutableMap<String, Any>>(params, MutableMap::class.java)
        WebViewProcessCommandsManager.instance.findAndExecLocalCommand(
            context, cmd, mapParams,
            object : ResultBack {
                override fun onResult(status: Int, action: String?, result: Any?) {
                    try {
                        if (status == WebConstants.CONTINUE) {
                            LogUtil.d(TAG, "execRemoteCommand WebConstants.CONTINUE")
                            if (action != null) {
                                execRemoteCommand(
                                    action,
                                    gson.toJson(result),
                                    webView,
                                    dispatcherCallBack
                                )
                            }
                        } else {
                            if (action != null) {
                                handleCallback(
                                    status,
                                    action,
                                    gson.toJson(result),
                                    webView,
                                    dispatcherCallBack
                                )
                            }
                        }
                    } catch (e: Exception) {
                        LogUtil.d(TAG, "Command exec error ${e.message}")
                    }
                }
            }
        )
    }

    @Throws(Exception::class)
    private fun execRemoteCommand(
        cmd: String, params: String, webView: WebView,
        dispatcherCallBack: DispatcherCallBack?
    ) {
        webAidlInterface?.handleWebAction(
            cmd,
            params,
            object : IWebAidlCallback.Stub() {
                @Throws(RemoteException::class)
                override fun onResult(responseCode: Int, actionName: String, response: String) {
                    handleCallback(
                        responseCode,
                        actionName,
                        response,
                        webView,
                        dispatcherCallBack
                    )
                }
            })

    }

    private fun handleCallback(
        responseCode: Int, actionName: String, response: String,
        webView: WebView, dispatcherCallBack: DispatcherCallBack?
    ) {
        MainLooper.runOnUiThread {
            dispatcherCallBack?.preHandleBeforeCallback(responseCode, actionName, response)
            val params = Gson().fromJson<Map<String?, Any?>>(
                response,
                object : TypeToken<Map<String?, Any?>?>() {}.type
            )

            if (params[WebConstants.NATIVE2WEB_CALLBACK] != null && !TextUtils.isEmpty(
                    params[WebConstants.NATIVE2WEB_CALLBACK].toString()
                )
            ) {
                if (webView is BaseWebView) {
                    webView.handleCallback(response)
                }
            }
        }
    }

    /**
     * Dispatcher 过程中的回调介入
     */
    interface DispatcherCallBack {
        fun preHandleBeforeCallback(
            responseCode: Int,
            actionName: String?,
            response: String?
        ): Boolean
    }

    companion object {
        private const val TAG = "CommandDispatcher"
        var instance: CommandDispatcher? = null
            get() {
                if (field == null) {
                    synchronized(CommandDispatcher::class.java) {
                        if (field == null) {
                            field = CommandDispatcher()
                        }
                    }
                }
                return field
            }
            private set
    }
}