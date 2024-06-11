package top.tobin.shared.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import top.tobin.shared.data.entity.AccountingEntity
import java.util.*

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: AccountingDao.
 */
@Dao
interface AccountingDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSchedule(vararg logEntity: AccountingEntity)

    @Query("SELECT * FROM AccountingEntity")
    fun getAccountingList(): Flow<List<AccountingEntity>>


}