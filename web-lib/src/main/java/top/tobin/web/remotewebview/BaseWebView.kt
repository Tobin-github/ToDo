package top.tobin.web.remotewebview

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.view.*
import android.webkit.WebView
import com.google.gson.Gson
import top.tobin.common.utils.LogUtil
import top.tobin.web.remotewebview.callback.WebViewCallBack
import top.tobin.web.remotewebview.settings.WebviewDefaultSetting
import top.tobin.web.remotewebview.webviewclient.CustomWebViewClient.WebviewTouch
import top.tobin.web.remotewebview.webviewclient.CustomWebViewClient
import top.tobin.web.remotewebview.javascriptinterface.WebViewJavascriptInterface
import java.lang.Exception
import kotlin.collections.HashMap

/**
 * Created by Tobin on 2022/5/31.
 * Email: junbin.li@qq.com
 * Description: BaseWebView.
 */
open class BaseWebView : WebView, WebviewTouch {
    private var mCustomCallback: ActionMode.Callback? = null
    var webViewCallBack: WebViewCallBack? = null
    private var mHeaders: Map<String, String> = HashMap()
    private lateinit var remoteInterface: WebViewJavascriptInterface

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        init(context)
    }

    constructor(
        context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context)
    }

    fun registerWebViewCallBack(webViewCallBack: WebViewCallBack) {
        this.webViewCallBack = webViewCallBack
        webViewClient = CustomWebViewClient(this, webViewCallBack, mHeaders, this)
    }

    fun setHeaders(mHeaders: Map<String, String>) {
        this.mHeaders = mHeaders
    }

    private fun init(ctx: Context) {
        WebviewDefaultSetting.instance.toSetting(this)
        /*
         * Web Native交互触发
         */
        remoteInterface = WebViewJavascriptInterface(ctx)
        remoteInterface.setJavascriptCommand(object : WebViewJavascriptInterface.JavascriptCommand{
            override fun exec(context: Context, cmd: String, params: String) {
                webViewCallBack?.exec(context, cmd, params, this@BaseWebView)
            }
        })

        addJavascriptInterface(remoteInterface, "android")
    }

    override fun startActionMode(callback: ActionMode.Callback): ActionMode? {
        return parent?.startActionModeForChild(this, wrapCallback(callback))
    }

    override fun startActionMode(callback: ActionMode.Callback, type: Int): ActionMode? {
        return parent?.startActionModeForChild(this, wrapCallback(callback), type)
    }

    private fun wrapCallback(callback: ActionMode.Callback): ActionMode.Callback {
        return mCustomCallback?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                CallbackWrapperM(mCustomCallback!!, callback)
            } else {
                CallbackWrapperBase(mCustomCallback!!, callback)
            }
        } ?: run() {
            callback
        }
    }

    fun setCustomActionCallback(callback: ActionMode.Callback?) {
        this.mCustomCallback = callback
    }

    private class CallbackWrapperBase(
        private val mWrappedCustomCallback: ActionMode.Callback,
        private val mWrappedSystemCallback: ActionMode.Callback
    ) : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            return mWrappedCustomCallback.onCreateActionMode(mode, menu)
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return mWrappedCustomCallback.onPrepareActionMode(mode, menu)
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return mWrappedCustomCallback.onActionItemClicked(mode, item)
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            try {
                mWrappedCustomCallback.onDestroyActionMode(mode)
                mWrappedSystemCallback.onDestroyActionMode(mode)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private class CallbackWrapperM(
        private val mWrappedCustomCallback: ActionMode.Callback,
        private val mWrappedSystemCallback: ActionMode.Callback
    ) : ActionMode.Callback2() {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            return mWrappedCustomCallback.onCreateActionMode(mode, menu)
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return mWrappedCustomCallback.onPrepareActionMode(mode, menu)
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return mWrappedCustomCallback.onActionItemClicked(mode, item)
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            mWrappedCustomCallback.onDestroyActionMode(mode)
            mWrappedSystemCallback.onDestroyActionMode(mode)
        }

        override fun onGetContentRect(mode: ActionMode, view: View, outRect: Rect) {
            if (mWrappedCustomCallback is ActionMode.Callback2) {
                mWrappedCustomCallback.onGetContentRect(mode, view, outRect)
            } else if (mWrappedSystemCallback is ActionMode.Callback2) {
                mWrappedSystemCallback.onGetContentRect(mode, view, outRect)
            } else {
                super.onGetContentRect(mode, view, outRect)
            }
        }
    }

    fun setContent(htmlContent: String) {
        try {
            loadDataWithBaseURL(CONTENT_SCHEME, htmlContent, "text/html", "UTF-8", null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun loadUrl(url: String) {
        if (mHeaders.isEmpty()) {
            super.loadUrl(url)
        } else {
            super.loadUrl(url, mHeaders)
        }
        LogUtil.d(TAG, "WebView load url: $url")
        resetAllStateInternal(url)
    }

    override fun loadUrl(url: String, additionalHttpHeaders: Map<String, String>) {
        super.loadUrl(url, additionalHttpHeaders)
        LogUtil.d(TAG, "WebView load url: $url")
        resetAllStateInternal(url)
    }

    fun handleCallback(response: String) {
        if (!TextUtils.isEmpty(response)) {
            LogUtil.d(TAG, "handleCallback response: $response")
            val trigger = "javascript:dj.callback($response)"
            evaluateJavascript(trigger, null)
        }
    }

    private fun loadJS(cmd: String, param: Any?) {
        val trigger = "javascript:" + cmd + "(" + Gson().toJson(param) + ")"
        evaluateJavascript(trigger, null)
    }

    fun dispatchEvent(name: String) {
        val param: MutableMap<String, String> = HashMap(1)
        param["name"] = name
        LogUtil.d(TAG, "dispatchEvent param: $param")
        loadJS("dj.dispatchEvent", param)
    }

    final override var isTouchByUser = false
        private set

    private fun resetAllStateInternal(url: String) {
        if (!TextUtils.isEmpty(url) && url.startsWith("javascript:")) {
            return
        }
        resetAllState()
    }

    // 加载url时重置touch状态
    protected fun resetAllState() {
        isTouchByUser = false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            isTouchByUser = true
        }
        return super.onTouchEvent(event)
    }

    companion object {
        private const val TAG = "BaseWebView"
        const val CONTENT_SCHEME = "file:///android_asset/"
    }
}