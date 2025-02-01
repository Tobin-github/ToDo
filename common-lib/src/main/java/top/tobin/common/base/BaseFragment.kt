package top.tobin.common.base

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.gyf.immersionbar.ktx.immersionBar

open class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarDarkFont(true)
    }

    protected fun setOnApplyWindowInsets(rootView : View) {
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    protected fun setStatusBarDarkFont(isDarkFont: Boolean) {
        immersionBar {
            statusBarDarkFont(isDarkFont)
        }
    }
}