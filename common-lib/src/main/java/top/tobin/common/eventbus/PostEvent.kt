package top.tobin.common.eventbus

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import top.tobin.common.base.AppScopeViewModelProvider

//Application范围的事件
inline fun <reified T> postEvent(event: T, timeMillis: Long = 0L) {
    AppScopeViewModelProvider.getApplicationScopeViewModel(EventBusCore::class.java)
        .postEvent(T::class.java.name, event!!, timeMillis)
}

//限定范围的事件
inline fun <reified T> postEvent(scope: ViewModelStoreOwner, event: T, timeMillis: Long = 0L) {
    ViewModelProvider(scope)[EventBusCore::class.java]
        .postEvent(T::class.java.name, event!!, timeMillis)
}