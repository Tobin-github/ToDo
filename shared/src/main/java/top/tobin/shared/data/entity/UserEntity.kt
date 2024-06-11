package top.tobin.shared.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: UserEntity.
 */
@Entity(indices = [Index(value = ["username"], unique = true)])
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val username: String,
    val password: String
)
