package top.tobin.shared.data.repository

import kotlinx.coroutines.flow.*
import top.tobin.shared.data.remote.DataResult
import top.tobin.shared.data.entity.ScheduleEntity
import top.tobin.shared.data.local.AppDataBase
import top.tobin.shared.data.remote.ScheduleInterface
import java.util.*

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: ScheduleRepositoryImpl.
 */
class ScheduleRepository(
    private val api: ScheduleInterface,
    private val db: AppDataBase
) : IScheduleRepository {

    override suspend fun fetchSchedule(scheduleAt: Date?): Flow<DataResult<List<ScheduleEntity>>> {
        return flow {
            if (scheduleAt != null) {
                db.scheduleDao().getScheduleList(scheduleAt).collectLatest {
                    emit(DataResult.Success(it))
                }
            } else {
                db.scheduleDao().getScheduleList().collectLatest {
                    emit(DataResult.Success(it))
                }
            }
        }
    }

    override suspend fun addSchedule(scheduleEntity: ScheduleEntity): Flow<DataResult<Boolean>> {
        return flow {
            db.runInTransaction {
                db.scheduleDao().insertSchedule(scheduleEntity)

            }
        }
    }

    override suspend fun updateSchedule(scheduleEntity: ScheduleEntity): Flow<DataResult<Boolean>> {
        return flow {
            db.scheduleDao().updateSchedule(scheduleEntity)
        }
    }
}