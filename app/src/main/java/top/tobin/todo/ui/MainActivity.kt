package top.tobin.todo.ui

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import top.tobin.navigation.tab.NavigationController
import top.tobin.navigation.tab.item.BaseTabItem
import top.tobin.navigation.tab.item.NormalItemView
import top.tobin.common.viewbinding.binding
import top.tobin.common.base.BaseActivity
import top.tobin.common.eventbus.postEvent
import top.tobin.component.framework.BusinessCompManager
import top.tobin.todo.R
import top.tobin.todo.busmodulecfg.BusModuleConfigProxy
import top.tobin.todo.databinding.ActivityMainBinding
import top.tobin.common.utils.LogUtil
import top.tobin.my.event.TestEvent

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: MainActivity.
 */
@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val binding: ActivityMainBinding by binding()

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
        checkPermission()
        initJankStats()
    }

    override fun onResume() {
        super.onResume()
        postEvent(this, TestEvent("send by MainActivity to Dismiss xxx= with ActivityEvent"))
        postEvent(TestEvent("send by MainActivity to Dismiss xxx= with GlobalEvent"))
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
//        val listener = JankStats.OnFrameListener { frameData ->
//            // 一个真正的应用程序可以做一些更有趣的事情，比如将信息写入本地存储，然后再报告。
//            if (frameData.isJank) {
//                LogUtil.d("JankStatsSample", frameData.toString())
//            }
//        }
//        val jankStats = JankStats.createAndTrack(window, listener)
//        jankStats.isTrackingEnabled = true
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                LogUtil.d("Android VERSION R OR ABOVE，HAVE MANAGE_EXTERNAL_STORAGE GRANTED!")
            } else {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.data = Uri.parse("package:$packageName")
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                    LogUtil.d("resultCode: ${it.resultCode} data: ${it.data}")
                }.launch(intent)
            }
        } else {
            lifecycleScope.launch {
                val result = permission(WRITE_EXTERNAL_STORAGE)
                LogUtil.d("WRITE_EXTERNAL_STORAGE result: $result")
                if (result) {
                    if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {
                        permission(WRITE_EXTERNAL_STORAGE)
                    }
                    val rest =
                        multiplePermission(arrayOf(WRITE_EXTERNAL_STORAGE, ACCESS_COARSE_LOCATION))

                } else {
                    Toast.makeText(this@MainActivity, "请开启存储读写权限！", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }
    }

    private suspend fun ActivityResultCaller.permission(permission: String): Boolean {
        return callbackFlow {
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                this.trySend(isGranted).isSuccess
                close()
            }.launch(permission)
            awaitClose()
        }.first()
    }

    private suspend fun ActivityResultCaller.multiplePermission(permissions: Array<String>): Boolean {
        return callbackFlow {
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { resultCallback ->
                var isGrantAll = false
                resultCallback.entries.forEach {
                    isGrantAll = isGrantAll and it.value
                }
                this.trySend(true).isSuccess
                close()
            }.launch(permissions)
            awaitClose()
        }.first()
    }
}