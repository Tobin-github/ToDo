package top.tobin.shared.model

import androidx.recyclerview.widget.DiffUtil
import java.util.Date

data class AccountingModel(
    val id: Long,
    val title: String,
    val accountingTime: Date? = null,
    val createTime: Date? = null,
    val price: Double = 0.00,
    val priceUnit: String = "¥",
    val remark: String? = null,
    val accountingUser: String? = null,
    val accountingType: Int = 0

) {
    class DiffCallback : DiffUtil.ItemCallback<AccountingModel>() {
        override fun areItemsTheSame(oldItem: AccountingModel, newItem: AccountingModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AccountingModel,
            newItem: AccountingModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}
