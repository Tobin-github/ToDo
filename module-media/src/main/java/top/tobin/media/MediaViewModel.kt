package top.tobin.media

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import top.tobin.shared.data.repository.IScheduleRepository

import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val scheduleRepository: IScheduleRepository
) : ViewModel() {


}