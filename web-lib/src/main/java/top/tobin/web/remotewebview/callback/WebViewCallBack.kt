package top.tobin.web.remotewebview.callback

import android.content.Context
import android.webkit.WebView

/**
 * WebView回调统一处理
 * 所有涉及到WebView交互的都必须实现这个callback
 */
interface WebViewCallBack {
    fun pageStarted(url: String?)
    fun pageFinished(url: String?)
    fun overrideUrlLoading(view: WebView, url: String): Boolean
    fun onError(description: String?)
    fun exec(context: Context, cmd: String, params: String, webView: WebView)
}