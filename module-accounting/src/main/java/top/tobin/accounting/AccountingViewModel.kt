package top.tobin.accounting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import top.tobin.common.utils.LogUtil
import top.tobin.shared.data.repository.IAccountingRepository
import top.tobin.shared.data.repository.IScheduleRepository
import top.tobin.shared.model.AccountingModel
import top.tobin.shared.model.UserModel

import javax.inject.Inject

@HiltViewModel
class AccountingViewModel @Inject constructor(
    private val accountingRepository: IAccountingRepository
) : ViewModel() {
    private val _accountingList = MutableLiveData<List<AccountingModel>>()
    val accountingList = _accountingList

    init {
        val testData = mutableListOf<AccountingModel>()
        for (i in 0..30) {
            testData.add(AccountingModel(i.toLong(), "title$i"))
        }
        accountingList.postValue(testData)

        viewModelScope.launch {
            accountingRepository.fetchAccountList().collectLatest {
                LogUtil.e(TAG,"AccountingRepository fetchAccountList: $it")
            }
        }
    }

    companion object {
        const val TAG = "AccountingViewModel"
    }

}