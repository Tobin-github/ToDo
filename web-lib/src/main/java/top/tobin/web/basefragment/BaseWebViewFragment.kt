package top.tobin.web.basefragment

import android.content.Context
import android.os.Bundle
import android.os.Looper
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.annotation.LayoutRes
import top.tobin.common.utils.LogUtil
import top.tobin.web.R
import top.tobin.web.basefragment.CommandDispatcher.DispatcherCallBack
import top.tobin.web.remotewebview.BaseWebView
import top.tobin.web.remotewebview.callback.WebViewCallBack
import top.tobin.web.utils.WebConstants
import kotlin.collections.HashMap

/**
 * Created by Tobin on 2022/5/31.
 * Email: junbin.li@qq.com
 * Description: BaseWebViewFragment.
 */
abstract class BaseWebViewFragment : BaseFragment(), WebViewCallBack {
    private lateinit var webView: BaseWebView
    private var accountInfoHeaders: HashMap<String, String> = HashMap()
    private lateinit var webUrl: String

    @get:LayoutRes
    protected abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webUrl = arguments?.getString(WebConstants.INTENT_TAG_URL) ?: ""
        LogUtil.d(TAG, "arguments webUrl: $webUrl")
        if (arguments?.containsKey(ACCOUNT_INFO_HEADERS) == true) {
            (arguments?.getSerializable(ACCOUNT_INFO_HEADERS) as HashMap<String, String>).let {
                accountInfoHeaders = it
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layoutRes, container, false)
        webView = view.findViewById(R.id.web_view)
        webView.setHeaders(accountInfoHeaders)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView.registerWebViewCallBack(this)
        CommandDispatcher.instance?.initAidlConnect(requireContext())
        loadUrl()
    }

    private fun loadUrl() {
        webView.loadUrl(webUrl)
    }

    override fun onResume() {
        super.onResume()
        webView.dispatchEvent("pageResume")
        webView.onResume()
    }

    override fun onPause() {
        super.onPause()
        webView.dispatchEvent("pagePause")
        webView.onPause()
    }

    override fun onStop() {
        super.onStop()
        webView.dispatchEvent("pageStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        webView.dispatchEvent("pageDestroy")
        clearWebView(webView)
    }

    override fun pageStarted(url: String?) {}
    override fun pageFinished(url: String?) {}
    override fun overrideUrlLoading(view: WebView, url: String): Boolean {
        return false
    }

    override fun onError(description: String?) {
        LogUtil.e(TAG, "onError: $description")
    }

    override fun exec(context: Context, cmd: String, params: String, webView: WebView) {
        CommandDispatcher.instance?.exec(context, cmd, params, webView, dispatcherCallBack)
    }

    private val dispatcherCallBack: DispatcherCallBack? get() = null

    fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            onBackHandle()
        } else false
    }

    fun onBackPressed(): Boolean {
        return onBackHandle()
    }

    private fun onBackHandle(): Boolean {
        return if (webView.canGoBack()) {
            webView.goBack()
            true
        } else {
            false
        }
    }

    private fun clearWebView(webView: WebView) {
        if (Looper.myLooper() != Looper.getMainLooper()) return
        LogUtil.d(TAG, "clearWebView: ${Looper.myLooper()}")
        webView.stopLoading()
        webView.handler?.removeCallbacksAndMessages(null)
        webView.removeAllViews()
        (webView.parent as ViewGroup).removeView(webView)
        webView.webChromeClient = null
        webView.tag = null
        webView.clearHistory()
        webView.destroy()
    }

    companion object {
        const val ACCOUNT_INFO_HEADERS = "account_header"
        private const val TAG = "BaseWebViewFragment"
    }
}