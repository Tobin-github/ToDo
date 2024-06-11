package top.tobin.common.base

import androidx.fragment.app.Fragment
import com.gyf.immersionbar.ktx.immersionBar

open class BaseFragment : Fragment() {

    override fun onResume() {
        super.onResume()
    }

    protected fun setStatusBarDarkFont(isDarkFont: Boolean) {
        immersionBar {
            statusBarDarkFont(isDarkFont)
        }
    }
}