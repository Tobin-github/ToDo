package top.tobin.common.eventbus

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import top.tobin.common.base.AppScopeViewModelProvider

inline fun <reified T> getEventObserverCount(event: Class<T>): Int {
    return AppScopeViewModelProvider.getApplicationScopeViewModel(EventBusCore::class.java)
        .getEventObserverCount(event.name)
}

inline fun <reified T> getEventObserverCount(scope: ViewModelStoreOwner, event: Class<T>): Int {
    return ViewModelProvider(scope)[EventBusCore::class.java]
        .getEventObserverCount(event.name)
}

/**
 * 移除事件
 */
inline fun <reified T> removeStickyEvent(event: Class<T>) {
    AppScopeViewModelProvider.getApplicationScopeViewModel(EventBusCore::class.java)
        .removeStickEvent(event.name)
}

inline fun <reified T> removeStickyEvent(scope: ViewModelStoreOwner, event: Class<T>) {
    ViewModelProvider(scope)[EventBusCore::class.java]
        .removeStickEvent(event.name)
}

/**
 * 清除事件缓存
 */
@OptIn(ExperimentalCoroutinesApi::class)
inline fun <reified T> clearStickyEvent(event: Class<T>) {
    AppScopeViewModelProvider.getApplicationScopeViewModel(EventBusCore::class.java)
        .clearStickEvent(event.name)
}

@OptIn(ExperimentalCoroutinesApi::class)
inline fun <reified T> clearStickyEvent(scope: ViewModelStoreOwner, event: Class<T>) {
    ViewModelProvider(scope)[EventBusCore::class.java]
        .clearStickEvent(event.name)
}