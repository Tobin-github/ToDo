package top.tobin.shared.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class StateViewModel<S, A, E> : ViewModel() {
    protected abstract fun initState(): S
    open fun dispatch(action: A) {}

    private val _events = MutableSharedFlow<E>()
    val events = _events.asSharedFlow()

    private val _uiState by lazy {
        MutableStateFlow(initState())
    }

    val uiState by lazy {
        _uiState.asStateFlow()
    }

    protected fun updateState(block: S.() -> S) {
        _uiState.update(block)
    }

    protected fun sendEvent(event: E) {
        viewModelScope.launch {
            _events.emit(event)
        }
    }
}