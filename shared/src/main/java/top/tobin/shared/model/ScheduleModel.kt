package top.tobin.shared.model

import androidx.recyclerview.widget.DiffUtil
import top.tobin.shared.ext.toDateString
import java.util.*

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: LogInfoModel.
 */
data class ScheduleModel(
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
) {

    fun getTimeString(date: Date?): String? {
        return date?.time?.toDateString()
    }

    class DiffCallback : DiffUtil.ItemCallback<ScheduleModel>() {
        override fun areItemsTheSame(oldItem: ScheduleModel, newItem: ScheduleModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ScheduleModel, newItem: ScheduleModel): Boolean {
            return oldItem == newItem
        }
    }

}
