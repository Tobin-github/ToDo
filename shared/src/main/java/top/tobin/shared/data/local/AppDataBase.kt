package top.tobin.shared.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import top.tobin.shared.data.entity.AccountingEntity
import top.tobin.shared.data.entity.ScheduleEntity
import top.tobin.shared.data.entity.UserEntity


/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: AppDataBase.
 */
@Database(
    entities = [UserEntity::class, ScheduleEntity::class, AccountingEntity::class],
    version = 1, exportSchema = false
)
@TypeConverters(value = [DateConverters::class])
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun scheduleDao(): ScheduleDao
    abstract fun accountingDao(): AccountingDao
}