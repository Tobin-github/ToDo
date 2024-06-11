package top.tobin.common.eventbus

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.withStateAtLeast
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import top.tobin.common.utils.LogUtil
import kotlin.collections.set

class EventBusCore : ViewModel() {

    //正常事件
    private val eventFlows: HashMap<String, MutableSharedFlow<Any>> = HashMap()

    //粘性事件
    private val stickyEventFlows: HashMap<String, MutableSharedFlow<Any>> = HashMap()

    private fun getEventFlow(eventName: String, isSticky: Boolean): MutableSharedFlow<Any> {
        return if (isSticky) {
            stickyEventFlows[eventName]
        } else {
            eventFlows[eventName]
        } ?: MutableSharedFlow<Any>(
            replay = if (isSticky) 1 else 0,
            extraBufferCapacity = Int.MAX_VALUE
        ).also {
            if (isSticky) {
                stickyEventFlows[eventName] = it
            } else {
                eventFlows[eventName] = it
            }
        }
    }

    fun <T : Any> observeEvent(
        lifecycleOwner: LifecycleOwner,
        eventName: String,
        minState: Lifecycle.State,
        dispatcher: CoroutineDispatcher,
        isSticky: Boolean,
        onReceived: (T) -> Unit
    ): Job {
        return lifecycleOwner.lifecycleScope.launch {
            getEventFlow(eventName, isSticky).collect { value ->
                lifecycleOwner.withStateAtLeast(minState) {
                    this.launch(dispatcher) {
                        invokeReceived(value, onReceived)
                    }
                }
            }
        }
    }

    suspend fun <T : Any> observeWithoutLifecycle(
        eventName: String,
        isSticky: Boolean,
        onReceived: (T) -> Unit
    ) {
        getEventFlow(eventName, isSticky).collect { value ->
            invokeReceived(value, onReceived)
        }
    }

    fun postEvent(eventName: String, value: Any, timeMillis: Long) {
        listOfNotNull(
            getEventFlow(eventName, false),
            getEventFlow(eventName, true)
        ).forEach { flow ->
            viewModelScope.launch {
                delay(timeMillis)
                flow.emit(value)
            }
        }
    }

    fun removeStickEvent(eventName: String) {
        stickyEventFlows.remove(eventName)
    }

    @ExperimentalCoroutinesApi
    fun clearStickEvent(eventName: String) {
        stickyEventFlows[eventName]?.resetReplayCache()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> invokeReceived(value: Any, onReceived: (T) -> Unit) {
        try {
            onReceived.invoke(value as T)
        } catch (e: ClassCastException) {
            LogUtil.e("EventBusCore", "ClassCastException: $e")
        } catch (e: Exception) {
            LogUtil.e("EventBusCore", "Exception: $e")
        }
    }

    fun getEventObserverCount(eventName: String): Int {
        val stickyObserverCount = stickyEventFlows[eventName]?.subscriptionCount?.value ?: 0
        val normalObserverCount = eventFlows[eventName]?.subscriptionCount?.value ?: 0
        return stickyObserverCount + normalObserverCount
    }

}