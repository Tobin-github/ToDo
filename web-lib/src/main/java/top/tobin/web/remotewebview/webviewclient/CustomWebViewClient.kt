package top.tobin.web.remotewebview.webviewclient

import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.webkit.*
import top.tobin.common.utils.LogUtil
import top.tobin.web.R
import top.tobin.web.remotewebview.BaseWebView
import top.tobin.web.remotewebview.callback.WebViewCallBack

/**
 * Created by Tobin on 2022/5/31.
 * Email: junbin.li@qq.com
 * Description: CustomWebViewClient.
 */
class CustomWebViewClient(
    private val webView: WebView,
    private val webViewCallBack: WebViewCallBack,
    private val mHeaders: Map<String, String>,
    private val mWebViewTouch: WebviewTouch
) : WebViewClient() {
    var isReady = false
    private var isError = false

    interface WebviewTouch {
        val isTouchByUser: Boolean
    }

    /**
     * url重定向会执行此方法以及点击页面某些链接也会执行此方法.
     *
     * @return true: 表示当前url已经加载完成，即使url还会重定向都不会再进行加载,
     * false: 表示此url默认由系统处理，该重定向还是重定向，直到加载完成
     */
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        LogUtil.d(TAG, "shouldOverrideUrlLoading url: %s $url")
        // 当前链接的重定向, 通过是否发生过点击行为来判断
        if (!mWebViewTouch.isTouchByUser) {
            return super.shouldOverrideUrlLoading(view, url)
        }
        // 如果链接跟当前链接一样，表示刷新
        if (webView.url == url) {
            return super.shouldOverrideUrlLoading(view, url)
        }
        if (handleLinked(url)) {
            return true
        }
        if (webViewCallBack.overrideUrlLoading(view, url)) {
            return true
        }
        // 控制页面中点开新的链接在当前webView中打开
        if (mHeaders.isEmpty()) view.loadUrl(url)
        else view.loadUrl(url, mHeaders)
        return true
    }

    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        LogUtil.d(TAG, "shouldOverrideUrlLoading url: ${request.url}")
        // 当前链接的重定向
        if (!mWebViewTouch.isTouchByUser) {
            return super.shouldOverrideUrlLoading(view, request)
        }
        // 如果链接跟当前链接一样，表示刷新
        if (webView.url == request.url.toString()) {
            return super.shouldOverrideUrlLoading(view, request)
        }
        if (handleLinked(request.url.toString())) {
            return true
        }
        if (webViewCallBack.overrideUrlLoading(view, request.url.toString())) {
            return true
        }
        // 控制页面中点开新的链接在当前webView中打开
        view.loadUrl(request.url.toString(), mHeaders)
        return true
    }

    /**
     * 支持电话、短信、邮件、地图跳转，跳转的都是手机系统自带的应用
     */
    private fun handleLinked(url: String): Boolean {
        if (url.startsWith(WebView.SCHEME_TEL) || url.startsWith(SCHEME_SMS)
            || url.startsWith(WebView.SCHEME_MAILTO)
            || url.startsWith(WebView.SCHEME_GEO)
        ) {
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                webView.context.startActivity(intent)
            } catch (ignored: ActivityNotFoundException) {
                ignored.printStackTrace()
            }
            return true
        }
        return false
    }

    override fun onPageFinished(view: WebView, url: String?) {
        LogUtil.d(TAG, "onPageFinished url: $url")

        if (url?.startsWith(BaseWebView.CONTENT_SCHEME) == true) {
            isReady = true
        }
        LogUtil.d(TAG, "onPageFinished isError: $isError")
        if (!isError) {
            webViewCallBack.pageFinished(url)
        }
        isError = false
    }

    override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
        LogUtil.d(TAG, "onPageStarted url: $url")
        webViewCallBack.pageStarted(url)
    }

    override fun onScaleChanged(view: WebView, oldScale: Float, newScale: Float) {
        super.onScaleChanged(view, oldScale, newScale)
    }

    override fun shouldInterceptRequest(
        view: WebView,
        request: WebResourceRequest
    ): WebResourceResponse? {
        return shouldInterceptRequest(view, request.url.toString())
    }

    override fun onReceivedError(
        view: WebView,
        errorCode: Int,
        description: String,
        failingUrl: String
    ) {
        super.onReceivedError(view, errorCode, description, failingUrl)
        LogUtil.e(TAG, "webview error $errorCode + $description")
        isError = true
        webViewCallBack.onError(description)
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        if (request?.isForMainFrame == true) {
            LogUtil.e(TAG, "webview error ${error?.errorCode} + ${error?.description}")
            isError = true
            webViewCallBack.onError(error?.description as? String)
        }
    }

    override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
        val builder = AlertDialog.Builder(webView.context)
        var message = webView.context.getString(R.string.ssl_error)
        when (error.primaryError) {
            SslError.SSL_UNTRUSTED -> message =
                webView.context.getString(R.string.ssl_error_not_trust)
            SslError.SSL_EXPIRED -> message = webView.context.getString(R.string.ssl_error_expired)
            SslError.SSL_IDMISMATCH -> message =
                webView.context.getString(R.string.ssl_error_mismatch)
            SslError.SSL_NOTYETVALID -> message =
                webView.context.getString(R.string.ssl_error_not_valid)
        }
        message += webView.context.getString(R.string.ssl_error_continue_open)
        builder.setTitle(R.string.ssl_error)
        builder.setMessage(message)
        builder.setPositiveButton(R.string.continue_open) { dialog: DialogInterface?, which: Int -> handler.proceed() }
        builder.setNegativeButton(R.string.cancel) { dialog: DialogInterface?, which: Int -> handler.cancel() }
        val dialog = builder.create()
        dialog.show()
    }

    companion object {
        private const val TAG = "CustomWebViewCallBack"
        const val SCHEME_SMS = "sms:"
    }
}