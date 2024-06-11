package top.tobin.shared.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import top.tobin.shared.data.entity.UserEntity

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: UserDao.
 */
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntityList: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(userEntity: UserEntity): Long

    @Delete
    fun deleteUser(userEntity: UserEntity)

    @Query("DELETE FROM UserEntity WHERE id = :id")
    fun deleteUser(id: Long)

    //这里不需要挂起（返回flow或livedata都不需要）
    @Query("SELECT * FROM UserEntity WHERE username = :username AND password = :password")
    fun getUser(username: String, password: String): UserEntity?

    @Query("SELECT * FROM UserEntity WHERE username = :username")
    fun getUser(username: String): UserEntity?

    @Query("SELECT * FROM UserEntity")
    fun getHistoryLoginUser(): Flow<List<UserEntity>>
}