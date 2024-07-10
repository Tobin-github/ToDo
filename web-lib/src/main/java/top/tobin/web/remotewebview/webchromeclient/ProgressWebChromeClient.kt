package top.tobin.web.remotewebview.webchromeclient

import android.content.DialogInterface
import android.os.Handler
import android.os.Message
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import top.tobin.web.remotewebview.ProgressWebView
import top.tobin.web.command.base.Command
import java.util.HashMap

/**
 * Created by Tobin on 2022/5/31.
 * Email: junbin.li@qq.com
 * Description: ProgressWebChromeClient.
 */
class ProgressWebChromeClient(private val progressHandler: Handler?) : WebChromeClient() {
    override fun onReceivedTitle(view: WebView, title: String) {
        super.onReceivedTitle(view, title)
        if (view is ProgressWebView) {
            if (title.isNotEmpty()) {
                val params = HashMap<String, String>()
                params[Command.COMMAND_UPDATE_TITLE_PARAMS] = title
                view.webViewCallBack?.exec(
                    view.getContext(),
                    Command.COMMAND_UPDATE_TITLE,
                    Gson().toJson(params),
                    view
                )
            }
        }
    }

    override fun onProgressChanged(view: WebView, newProgress: Int) {
        var mProgress = newProgress
        val message = Message()
        if (mProgress == 100) {
            message.obj = mProgress
            progressHandler?.sendMessageDelayed(message, 200)
        } else {
            if (mProgress < 10) {
                mProgress = 10
            }
            message.obj = mProgress
            progressHandler?.sendMessage(message)
        }
        super.onProgressChanged(view, mProgress)
    }

    override fun onJsAlert(view: WebView, url: String, message: String, result: JsResult): Boolean {
        AlertDialog.Builder(view.context)
            .setTitle(android.R.string.dialog_alert_title)
            .setMessage(message)
            .setPositiveButton(
                android.R.string.ok
            ) { _: DialogInterface?, _: Int ->
                //按钮事件
                Toast.makeText(
                    view.context,
                    view.context.getString(android.R.string.ok) + " clicked.",
                    Toast.LENGTH_LONG
                ).show()
            }
            .show()
        // 不加这行代码，会造成Alert劫持：Alert只会弹出一次，并且WebView会卡死
        result.confirm()
        return true
    }
}