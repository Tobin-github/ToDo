package top.tobin.gallery

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import top.tobin.common.base.Initializer
import top.tobin.common.utils.LogUtil
import top.tobin.shared.data.remote.doFailure
import top.tobin.shared.data.remote.doSuccess
import top.tobin.shared.data.repository.IGalleryRepository
import top.tobin.shared.model.BingModel
import javax.inject.Inject

sealed class GalleryUiState {
    data object Idle : GalleryUiState()
    data class Loading(val isLoading: Boolean) : GalleryUiState()
    data class BingModels(val bingModel: BingModel) : GalleryUiState()
    data class ImmersionBar(val statusBarDarkFont: Boolean) : GalleryUiState()
}

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val galleryRepository: IGalleryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<GalleryUiState>(GalleryUiState.Idle)
    val uiState: StateFlow<GalleryUiState> = _uiState.asStateFlow()

    init {
        updatePicture()
    }

    fun updateImmersionBar(statusBarDarkFont: Boolean) {
        _uiState.value = GalleryUiState.ImmersionBar(statusBarDarkFont)
    }

    fun updatePicture() {
        viewModelScope.launch {
            galleryRepository.fetchRandPicture().onStart {
                LogUtil.e("=======  fetchRandPicture onStart")
                _uiState.value = GalleryUiState.Loading(true)
            }.onCompletion {
                _uiState.value = GalleryUiState.Loading(false)
            }.collectLatest {
                it.doSuccess { bingModel ->
                    _uiState.value = GalleryUiState.BingModels(bingModel)
                }

                it.doFailure { throwable ->
                    Toast.makeText(
                        Initializer.application,
                        "${throwable.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}