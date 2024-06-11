package top.tobin.accounting

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gyf.immersionbar.ktx.immersionBar
import dagger.hilt.android.AndroidEntryPoint
import top.tobin.accounting.databinding.FragmentAccountingBinding
import top.tobin.accounting.databinding.ItemAccountingListBinding
import top.tobin.common.ui.listAdapter
import top.tobin.common.utils.LogUtil
import top.tobin.shared.model.AccountingModel
import top.tobin.common.viewbinding.bindingView

@AndroidEntryPoint
class AccountingFragment : Fragment(R.layout.fragment_accounting) {

    private val binding: FragmentAccountingBinding by bindingView()
    private val viewModel: AccountingViewModel by viewModels()

    private val adapter = listAdapter<AccountingModel, ItemAccountingListBinding>(
        AccountingModel.DiffCallback()
    ) {
        this.binding.tvTitle.text = it.title
        this.binding.tvPrice.text = it.priceUnit.plus(it.price)
        this.binding.tvUser.text = ""
    }

    override fun onResume() {
        super.onResume()
        immersionBar {
            statusBarDarkFont(true)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvAccounting.adapter = adapter

        viewModel.accountingList.observe(viewLifecycleOwner) {
            LogUtil.e("observe  accountingList size:${it.size} content: $it")
            adapter.submitList(it)
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AccountingFragment()
    }
}