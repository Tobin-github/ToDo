package top.tobin.shared.data.local

import androidx.room.*
import top.tobin.shared.data.entity.ScheduleEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: ScheduleDao.
 */
@Dao
interface ScheduleDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSchedule(logEntityList: List<ScheduleEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSchedule(vararg logEntity: ScheduleEntity)

    @Query("SELECT * FROM ScheduleEntity")
    fun getScheduleList(): Flow<List<ScheduleEntity>>

    //这里不需要挂起（返回flow或livedata都不需要）
    @Query("SELECT * FROM ScheduleEntity WHERE date(scheduleAt / 1000, 'unixepoch') = date(:scheduleAt / 1000, 'unixepoch')")
    fun getScheduleList(scheduleAt: Date): Flow<List<ScheduleEntity>>

    @Query("SELECT * FROM ScheduleEntity where content LIKE '%' || :content || '%'")
    fun getScheduleList(content: String): Flow<List<ScheduleEntity>>

    @Query("DELETE FROM ScheduleEntity where id = :id")
    suspend fun deleteSchedule(id: Long)

    @Update
    fun updateSchedule(vararg status: ScheduleEntity)

    @Delete
    fun deleteSchedule(vararg status: ScheduleEntity)

}