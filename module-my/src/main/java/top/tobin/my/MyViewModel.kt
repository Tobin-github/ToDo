package top.tobin.my

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import top.tobin.shared.base.StateViewModel
import top.tobin.shared.data.repository.IScheduleRepository
import top.tobin.shared.model.BingModel

import javax.inject.Inject

data class MyUiState(
    val cardList: BingModel
)

sealed interface IMyAction {
    data class UpdateAddedList(val adapterData: List<BingModel>) : IMyAction
    data class ChangeMapSize(val type: IMyAction) : IMyAction
}

sealed interface IMyEvent {
    class MoveEndEvent
}

@HiltViewModel
class MyViewModel @Inject constructor(
    private val scheduleRepository: IScheduleRepository
) : StateViewModel<MyUiState, IMyAction, IMyEvent>() {

    private val _loading: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val loading = _loading.asSharedFlow()

    private val _randPicture = MutableLiveData<BingModel>()
    val randPicture = _randPicture as LiveData<BingModel>


    override fun initState(): MyUiState {
        return MyUiState(cardList = BingModel("", "", ""))
    }
}