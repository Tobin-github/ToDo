package top.tobin.todo.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.metrics.performance.JankStats
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gyf.immersionbar.ktx.immersionBar
import dagger.hilt.android.AndroidEntryPoint
import top.tobin.navigation.tab.NavigationController
import top.tobin.navigation.tab.item.BaseTabItem
import top.tobin.navigation.tab.item.NormalItemView
import top.tobin.common.viewbinding.binding
import top.tobin.common.utils.LogUtil
import top.tobin.component.framework.BusinessCompManager
import top.tobin.todo.R
import top.tobin.todo.busmodulecfg.BusModuleConfigProxy
import top.tobin.todo.databinding.ActivityMainVerticalBinding

/**
 * 车机平板Activity， 左侧tab
 */
@AndroidEntryPoint
class MainTableActivity : AppCompatActivity() {
    private val binding: ActivityMainVerticalBinding by binding()

    private val busModules = BusModuleConfigProxy.instance.getBusModules()

    private fun getChildFragments(): MutableList<Fragment> {
        val fragments = mutableListOf<Fragment>()
        for (busModel in busModules) {
            BusinessCompManager.getInstance().getBusComp(busModel.moduleName)?.busEnterPage?.let {
                fragments.add(it)
            }
        }
        return fragments
    }

    private fun getTabItems(): MutableList<BaseTabItem> {
        val tabItems = mutableListOf<BaseTabItem>()
        for (busModel in busModules) {
            BusinessCompManager.getInstance().getBusComp(busModel.moduleName)?.getNavBean(false)
                ?.let {
                    tabItems.add(newItem(it.normalImg, it.selectImg, it.navName))
                }
        }
        return tabItems
    }

    private val navigationController: NavigationController by lazy {
        binding.pageTab.custom()
            .addItems(getTabItems())
            .enableVerticalLayout()
            .build()
    }

    private val fragmentAdapter: FragmentStateAdapter by lazy {
        object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return getChildFragments().size
            }

            override fun createFragment(position: Int): Fragment {
                return getChildFragments()[position]
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBottomNavigation()

        initJankStats()

        immersionBar {
            statusBarColor(android.R.color.transparent)
            navigationBarColor(android.R.color.transparent)
        }
    }

    /**
     * 初始化底部导航
     */
    private fun initBottomNavigation() {
        binding.mainViewPage2.adapter = fragmentAdapter
        binding.mainViewPage2.isUserInputEnabled = false
        //自动适配ViewPager页面切换
        navigationController.setupWithViewPager(binding.mainViewPage2)
    }

    //创建一个Item
    private fun newItem(drawable: Int, checkedDrawable: Int, text: String): BaseTabItem {
        val normalItemView = NormalItemView(this)
        normalItemView.initialize(drawable, checkedDrawable, text)
        normalItemView.setTextDefaultColor(getColor(R.color.text_second))
        normalItemView.setTextCheckedColor(getColor(R.color.text_main))
        return normalItemView
    }

    private fun initJankStats() {
        val listener = JankStats.OnFrameListener { frameData ->
            // 一个真正的应用程序可以做一些更有趣的事情，比如将信息写入本地存储，然后再报告。
            LogUtil.v("JankStatsSample", frameData.toString())
        }
        val jankStats = JankStats.createAndTrack(window, listener)
        jankStats.isTrackingEnabled = true
    }
}