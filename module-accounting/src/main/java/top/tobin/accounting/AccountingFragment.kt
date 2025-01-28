package top.tobin.accounting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import top.tobin.accounting.databinding.FragmentAccountingBinding
import top.tobin.accounting.databinding.ItemAccountingListBinding
import top.tobin.common.base.BaseFragment
import top.tobin.common.ui.listAdapter
import top.tobin.common.utils.LogUtil
import top.tobin.shared.model.AccountingModel
import top.tobin.common.viewbinding.binding

@AndroidEntryPoint
class AccountingFragment : BaseFragment() {

    private val binding: FragmentAccountingBinding by binding()
    private val viewModel: AccountingViewModel by viewModels()

    private val adapter = listAdapter<AccountingModel, ItemAccountingListBinding>(
        AccountingModel.DiffCallback()
    ) {
        this.binding.tvTitle.text = it.title
        this.binding.tvPrice.text = it.priceUnit.plus(it.price)
        this.binding.tvUser.text = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnApplyWindowInsets(view)
        binding.rvAccounting.adapter = adapter
        viewModel.accountingList.observe(viewLifecycleOwner) {
            LogUtil.e("observe  accountingList size:${it.size} content: $it")
            adapter.submitList(it)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AccountingFragment()
    }
}