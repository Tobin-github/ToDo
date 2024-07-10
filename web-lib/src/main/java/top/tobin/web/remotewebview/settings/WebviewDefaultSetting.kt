package top.tobin.web.remotewebview.settings

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.webkit.WebSettings
import android.webkit.WebView

/**
 * Created by Tobin on 2022/5/31.
 * Email: junbin.li@qq.com
 * Description: webview的一些配置.
 */
open class WebviewDefaultSetting protected constructor() {
    fun toSetting(webView: WebView) {
        settings(webView)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun settings(webView: WebView) {
        WebView.enableSlowWholeDocumentDraw()
        val mWebSettings = webView.settings
        mWebSettings.javaScriptEnabled = true
        mWebSettings.setSupportZoom(true)
        mWebSettings.builtInZoomControls = false
        if (isNetworkConnected(webView.context)) {
            mWebSettings.cacheMode = WebSettings.LOAD_DEFAULT
        } else {
            mWebSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        }
        mWebSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        mWebSettings.textZoom = 100

        mWebSettings.loadsImagesAutomatically = true
        mWebSettings.setSupportMultipleWindows(false)
        mWebSettings.blockNetworkImage = false //是否阻塞加载网络图片  协议http or https
        mWebSettings.allowFileAccess = true //允许加载本地文件html  file协议
        //通过 file url 加载的 Javascript 读取其他的本地文件 .建议关闭
        mWebSettings.allowFileAccessFromFileURLs = false
        //允许通过 file url 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源
        mWebSettings.allowUniversalAccessFromFileURLs = false
        mWebSettings.javaScriptCanOpenWindowsAutomatically = true
        mWebSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        mWebSettings.savePassword = false
        mWebSettings.saveFormData = false
        mWebSettings.loadWithOverviewMode = true
        mWebSettings.useWideViewPort = true
        mWebSettings.domStorageEnabled = true
        mWebSettings.setNeedInitialFocus(true)
        mWebSettings.defaultTextEncodingName = "utf-8" //设置编码格式
        mWebSettings.setGeolocationEnabled(true)
        mWebSettings.defaultTextEncodingName = "utf-8"
        mWebSettings.databaseEnabled = true

        //mWebSettings.setAppCacheEnabled(true)
        mWebSettings.cacheMode = WebSettings.LOAD_DEFAULT
        //mWebSettings.setAppCachePath(appCacheDir)
        //mWebSettings.setAppCacheMaxSize((1024 * 1024 * 80).toLong())

        // 用户可以自己设置useragent;最好不要随便修改UserAgent, 有可能导致客户端网页无法通过UserAgent显示正确的页面；
        //mWebSettings.setUserAgentString("Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Mobile Safari/537.36");
        //WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)
    }

    companion object {
        val instance: WebviewDefaultSetting
            get() = WebviewDefaultSetting()

        private fun isNetworkConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo
            return networkInfo?.isConnected ?: false
        }
    }
}