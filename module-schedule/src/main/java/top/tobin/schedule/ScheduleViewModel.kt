package top.tobin.schedule

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import top.tobin.shared.data.entity.ScheduleEntity
import top.tobin.shared.data.remote.doFailure
import top.tobin.shared.data.remote.doSuccess
import top.tobin.shared.data.repository.IScheduleRepository

import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val scheduleRepository: IScheduleRepository
) : ViewModel() {

    fun addSchedule(scheduleEntity: ScheduleEntity) = liveData {
        scheduleRepository.addSchedule(scheduleEntity)
            .catch { ex ->
                Log.d("ScheduleViewModel", "ex: $ex")
                emit(false)
            }.onCompletion {
                // 请求完成
                Log.d("ScheduleViewModel", "onCompletion")
            }.collectLatest { result ->
                Log.d("ScheduleViewModel", "collectLatest result: $result")
                result.doFailure { throwable ->
                    Log.d("ScheduleViewModel", "doFailure throwable: $throwable")
                    emit(false)
                }

                result.doSuccess { value ->
                    emit(value)
                }
            }
    }
}