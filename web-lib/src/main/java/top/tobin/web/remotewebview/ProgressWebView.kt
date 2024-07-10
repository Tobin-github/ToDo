package top.tobin.web.remotewebview

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.ViewGroup
import top.tobin.web.remotewebview.progressbar.WebProgressBar
import top.tobin.web.remotewebview.progressbar.IndicatorHandler
import top.tobin.web.remotewebview.webchromeclient.ProgressWebChromeClient

/**
 * Created by Tobin on 2022/5/31.
 * Email: junbin.li@qq.com
 * Description: ProgressWebView.
 */
class ProgressWebView : BaseWebView {
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

    private lateinit var indicatorHandler: IndicatorHandler

    private val handler = Looper.myLooper()?.let {
        Handler(it) { msg ->
            val progress = msg.obj as Int
            indicatorHandler.progress(progress)
            true
        }
    }

    override fun getHandler(): Handler? {
        return handler
    }

    private fun init(context: Context) {
        val progressBar = WebProgressBar(context)
        progressBar.layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        // progressBar.setVisibility(GONE);
        addView(progressBar)
        indicatorHandler = IndicatorHandler.instance.injectProgressView(progressBar)
        webChromeClient = ProgressWebChromeClient(handler)
    }
}