package top.tobin.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import top.tobin.common.utils.LogUtil
import top.tobin.shared.data.remote.doFailure
import top.tobin.shared.data.remote.doSuccess
import top.tobin.shared.data.repository.IGalleryRepository
import top.tobin.shared.model.LocalPhoto
import javax.inject.Inject

sealed class GalleryUiState {
    data object Idle : GalleryUiState()
    data class Loading(val isLoading: Boolean) : GalleryUiState()
    data class Photos(val photos: List<LocalPhoto>) : GalleryUiState()
    data class Error(val message: String) : GalleryUiState()
}

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val galleryRepository: IGalleryRepository
) : ViewModel() {

    companion object {
        const val TAG = "GalleryViewModel"
    }

    private val _uiState = MutableStateFlow<GalleryUiState>(GalleryUiState.Idle)
    val uiState: StateFlow<GalleryUiState> = _uiState.asStateFlow()

    fun loadPhotos() {
        viewModelScope.launch {
            _uiState.value = GalleryUiState.Loading(true)
            LogUtil.i(TAG, "fetchLocalPhotos onStart")
            galleryRepository.fetchLocalPhotos()
                .collectLatest {
                    it.doSuccess { photos ->
                        _uiState.value = GalleryUiState.Photos(photos)
                    }
                    it.doFailure { throwable ->
                        _uiState.value = GalleryUiState.Error(throwable.message ?: "加载失败")
                    }
                }
        }
    }

}
