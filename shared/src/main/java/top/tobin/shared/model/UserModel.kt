package top.tobin.shared.model

import androidx.recyclerview.widget.DiffUtil

data class UserModel(
    val id: Long = 0,
    val username: String,
    val password: String
) {

    class DiffCallback : DiffUtil.ItemCallback<UserModel>() {
        override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem == newItem
        }
    }
}
