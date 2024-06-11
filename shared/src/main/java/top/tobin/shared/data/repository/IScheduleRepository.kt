package top.tobin.shared.data.repository

import kotlinx.coroutines.flow.Flow
import top.tobin.shared.data.entity.ScheduleEntity
import top.tobin.shared.data.remote.DataResult
import java.util.*

interface IScheduleRepository {
    suspend fun fetchSchedule(scheduleAt: Date?): Flow<DataResult<List<ScheduleEntity>>>
    suspend fun addSchedule(scheduleEntity: ScheduleEntity): Flow<DataResult<Boolean>>
    suspend fun updateSchedule(scheduleEntity: ScheduleEntity): Flow<DataResult<Boolean>>
}