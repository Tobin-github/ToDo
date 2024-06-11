package top.tobin.my

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import top.tobin.common.viewbinding.binding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import top.tobin.common.base.BaseFragment
import top.tobin.common.utils.DataStoreUtils
import top.tobin.common.utils.LogUtil
import top.tobin.my.databinding.FragmentMyBinding
import top.tobin.my.user.LoginActivity

@AndroidEntryPoint
class MyFragment : BaseFragment() {

    private val binding: FragmentMyBinding by binding()
    private val viewModel: MyViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance() = MyFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.rlItemUser.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }
        lifecycleScope.launch {
            viewModel.loading.collectLatest {

            }
        }

        binding.btTest.setOnClickListener {
            lifecycleScope.launch {
                DataStoreUtils.TEST_DATA.set("test_data")
            }
        }

        viewModel.randPicture.observe(viewLifecycleOwner) {

        }

    }

    override fun onResume() {
        super.onResume()
        setStatusBarDarkFont(true)
    }
}