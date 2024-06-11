package top.tobin.common.ui

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import top.tobin.common.R

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: LoadingDialog.
 */
class LoadingDialog private constructor() {
    companion object {
        val instance = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = LoadingDialog()
    }

    private var dialog: Dialog? = null
    private var msg: String? = null

    fun showLoading(activity: Activity, msg: String) {
        this.msg = msg
        if (dialog == null) {
            dialog = BaseDialog(activity)
        }
        dialog!!.show()
    }

    /**
     * 关闭dialog.
     */
    fun dismissLoading() {
        dialog?.dismiss()
        dialog = null
    }

    inner class BaseDialog(activity: Activity) : Dialog(activity, R.style.LoadingDialogStyle),
        LifecycleEventObserver {

        init {
            if (activity is ComponentActivity) {
                activity.lifecycle.addObserver(this)
            }
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.dialog_loading)

            setCancelable(true)
            setCanceledOnTouchOutside(false)
            window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            window?.attributes?.gravity = Gravity.CENTER
            val lp = window?.attributes
            lp?.width = WindowManager.LayoutParams.WRAP_CONTENT
            lp?.height = WindowManager.LayoutParams.WRAP_CONTENT
            window?.attributes = lp

            findViewById<TextView>(R.id.tipTextView).text = msg

        }

        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (Lifecycle.Event.ON_DESTROY == event) {
                if (isShowing) {
                    dismissLoading()
                }
            }
        }
    }

}