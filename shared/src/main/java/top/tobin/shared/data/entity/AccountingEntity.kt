package top.tobin.shared.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class AccountingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String = "",
    val accountingTime: Date? = null,
    val createTime: Date = Date(System.currentTimeMillis()),
    val price: Double = 0.00,
    val priceUnit: String = "¥",
    val remark: String? = null,
    val accountingUser: String? = null,
    val accountingType: Int = 0
)
