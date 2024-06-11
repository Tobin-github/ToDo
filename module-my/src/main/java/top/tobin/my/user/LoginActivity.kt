package top.tobin.my.user

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.gyf.immersionbar.ktx.immersionBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import top.tobin.common.base.BaseActivity
import top.tobin.common.eventbus.observeEvent
import top.tobin.common.eventbus.postEvent
import top.tobin.common.ui.LoadingDialog
import top.tobin.common.ui.simpleListAdapter
import top.tobin.common.utils.ClickUtils
import top.tobin.common.utils.ResourceUtil
import top.tobin.my.databinding.ActivityLoginBinding
import top.tobin.my.databinding.ItemListUserBinding
import top.tobin.my.databinding.LayoutAccountPopupBinding
import top.tobin.shared.model.UserModel
import top.tobin.common.utils.LogUtil
import top.tobin.common.viewbinding.binding
import top.tobin.common.viewbinding.popupWindow
import top.tobin.my.event.TestEvent

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: LoginFragment.
 */
@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    private val binding: ActivityLoginBinding by binding(false)
    private val viewModel: LoginViewModel by viewModels()
    private val userListWindow by popupWindow<LayoutAccountPopupBinding> {
        rvUser.adapter = adapter
    }

    private val adapter = simpleListAdapter<UserModel, ItemListUserBinding>(
        UserModel.DiffCallback(),
    ) { userModel ->
        this.tvUserName.text = userModel.username
        this.ivDelete.setOnClickListener {
            viewModel.deleteUser(userModel)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickListener()

        lifecycleScope.launch {
            viewModel.loading.collect {
                if (it) LoadingDialog.instance.showLoading(this@LoginActivity, "登录中...")
                else LoadingDialog.instance.dismissLoading()
            }
        }

        viewModel.historyLoginUser.observe(this) {
            if (it.isNullOrEmpty()) {
                binding.ivUserList.visibility = View.GONE
                userListWindow.dismiss()
            } else {
                binding.ivUserList.visibility = View.VISIBLE
            }
            LogUtil.d("UserModel: $it")
            adapter.submitList(it.ifEmpty { null })

            binding.tvInfo.text = it?.toString()
        }

        adapter.doOnItemClick { item, _ ->
            userListWindow.dismiss()
            binding.etUsername.setText(item.username)
            binding.etPassword.setText(item.password)

            postDismissPopEvent()
        }

        userListWindow.setOnDismissListener {
            binding.ivUserList.setImageResource(
                ResourceUtil.getDrawableId(this, "arrow_expansion")
            )
        }

        observeActivityScopeEvents()
        observeGlobalEvents()

        binding.cbVisiblePassword.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.etPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance();
            } else {
                binding.etPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance();
            }
        }
    }

    private fun setClickListener() {
        ClickUtils.applySingleDebouncing(binding.tvLogin) {
            val username = binding.etUsername.text?.toString()
            val password = binding.etPassword.text?.toString()
            if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
                Toast.makeText(this, "用户名和密码不能为空！", Toast.LENGTH_SHORT).show()
                return@applySingleDebouncing
            }
            viewModel.postLogin(username, password).observe(this) {
                LogUtil.d("LoginActivity", "login info: $it")
                finish()
            }
        }

        ClickUtils.applySingleDebouncing(binding.ivUserList) {
            userListWindow.isOutsideTouchable = true
            userListWindow.isFocusable = true
            if (!userListWindow.isShowing) {

                binding.ivUserList.setImageResource(
                    ResourceUtil.getDrawableId(this, "arrow_collapse")
                )
                userListWindow.showAsDropDown(binding.etUsername)
            }
        }

        ClickUtils.applySingleDebouncing(binding.tvRegister) {
            val username = binding.etUsername.text?.toString()
            val password = binding.etPassword.text?.toString()
            if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
                Toast.makeText(this, "用户名和密码不能为空！", Toast.LENGTH_SHORT).show()
                return@applySingleDebouncing
            }
            viewModel.postRegister(username, password).observe(this) {
                if (it) {
                    Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "注册失败！", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }


    private fun postDismissPopEvent() {
        postEvent(this, TestEvent("send by LoginActivity to Dismiss PopWindow with ActivityEvent"))

        postEvent(TestEvent("send by LoginActivity to Dismiss PopWindow with GlobalEvent"))

    }

    //跨页面
    private fun observeGlobalEvents() {
        //全局事件
        observeEvent<TestEvent>(minActiveState = Lifecycle.State.RESUMED) { value ->
            LogUtil.d("MainActivity received GlobalEvent  :${value.name}")
        }
    }


    //本页面
    private fun observeActivityScopeEvents() {
        //Activity 级别的 事件
        //自定义事件
        observeEvent<TestEvent>(this) {
            LogUtil.d("MainActivity received ActivityEvent: ${it.name}")
        }

        //自定义事件 切换线程
        observeEvent<TestEvent>(this, Dispatchers.IO) {
            LogUtil.d("received ActivityEvent:${it.name} " + Thread.currentThread().name)
        }

        //自定义事件 指定最小生命周期
        observeEvent<TestEvent>(this, minActiveState = Lifecycle.State.CREATED) {
            LogUtil.d("=========received ActivityEvent:${it.name}   >  DESTROYED")
        }

        //自定义事件 切换线程 + 指定最小生命周期
        observeEvent<TestEvent>(this, Dispatchers.IO, Lifecycle.State.RESUMED) {
            LogUtil.d(
                "received ActivityEvent:${it.name} >  DESTROYED    " + Thread.currentThread().name
            )
        }

    }
}
