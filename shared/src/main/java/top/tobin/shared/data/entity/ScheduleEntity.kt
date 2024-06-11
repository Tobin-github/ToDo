package top.tobin.shared.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class ScheduleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val content: String = "", // 日程任务内容
    val repeat : Boolean = false, // 是否重复
    val isImportant : Boolean = false, // 是否重要
    val createAt: Date = Date(System.currentTimeMillis()), // 日程创建时间
    val endAt: Date?, // 日程截止日期
    val remindAt: Date?, // 日程提醒时间
    val scheduleAt: Date, // 日程时间
    val status: Int = 0, // 日程状态 0 未完成 1已完成
    val remark: String? //备注
)
