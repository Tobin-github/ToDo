package top.tobin.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: AppScopeViewModelProvider.
 */
object AppScopeViewModelProvider : ViewModelStoreOwner {

    private val eventViewModelStore: ViewModelStore = ViewModelStore()

    override val viewModelStore: ViewModelStore get() = eventViewModelStore

    private val mApplicationProvider: ViewModelProvider by lazy {
        ViewModelProvider(
            AppScopeViewModelProvider,
            ViewModelProvider.AndroidViewModelFactory.getInstance(Initializer.application)
        )
    }

    fun <T : ViewModel> getApplicationScopeViewModel(modelClass: Class<T>): T {
        return mApplicationProvider[modelClass]
    }

}