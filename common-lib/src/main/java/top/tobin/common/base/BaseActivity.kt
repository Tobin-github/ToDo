package top.tobin.common.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.gyf.immersionbar.ktx.immersionBar

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        immersionBar {
            statusBarColor(android.R.color.transparent)
            navigationBarColor(android.R.color.transparent)
            statusBarDarkFont(true)
        }
    }

    private fun setStatusBarsAndNavigationBar() {
        // 状态栏透明
        window.statusBarColor = Color.TRANSPARENT
        // 导航栏背景透明
        window.navigationBarColor = Color.TRANSPARENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = true
            window.isStatusBarContrastEnforced = true
        }

        // 导航栏横线的颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.navigationBarDividerColor = Color.TRANSPARENT
        }
        // 打开沉浸式
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val controller = WindowInsetsControllerCompat(window, window.decorView)
        // 状态栏显示
        controller.show(WindowInsetsCompat.Type.statusBars())
        // 导航栏显示
//        controller.show(WindowInsetsCompat.Type.navigationBars())
        //导航栏的前景颜色更改为浅色
//        controller.isAppearanceLightNavigationBars = true
        //状态栏的前景颜色更改为浅色
//        controller.isAppearanceLightStatusBars = false
    }
}