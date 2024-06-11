package top.tobin.common.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import top.tobin.common.viewbinding.BindingViewHolder

inline fun <T, reified VB : ViewBinding> listAdapter(
    diffCallback: DiffUtil.ItemCallback<T>,
    crossinline onBindViewHolder: BindingViewHolder<VB>.(T) -> Unit
) = object : ListAdapter<T, BindingViewHolder<VB>>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BindingViewHolder<VB>(parent)

    override fun onBindViewHolder(holder: BindingViewHolder<VB>, position: Int) {
        onBindViewHolder(holder, currentList[position])
    }
}

inline fun <T, reified VB : ViewBinding> listAdapter(
    diffCallback: DiffUtil.ItemCallback<T>,
    noinline inflate: (LayoutInflater, ViewGroup, Boolean) -> VB,
    crossinline onBindViewHolder: BindingViewHolder<VB>.(T) -> Unit
) = object : ListAdapter<T, BindingViewHolder<VB>>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BindingViewHolder(parent, inflate)

    override fun onBindViewHolder(holder: BindingViewHolder<VB>, position: Int) {
        onBindViewHolder(holder, currentList[position])
    }
}

inline fun <T : Any, reified VB : ViewBinding> simpleListAdapter(
    diffCallback: DiffUtil.ItemCallback<T> = DiffCallback(),
    crossinline onBindViewHolder: VB.(T) -> Unit
) = object : SimpleListAdapter<T, BindingViewHolder<VB>>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BindingViewHolder<VB>(parent).apply {
            itemView.setOnClickListener {
                onItemClickListener?.onItemClick(
                    getItem(bindingAdapterPosition),
                    bindingAdapterPosition
                )
            }
            itemView.setOnLongClickListener {
                onItemLongClickListener?.onItemClick(
                    getItem(bindingAdapterPosition),
                    bindingAdapterPosition
                )
                onItemLongClickListener != null
            }
        }

    override fun onBindViewHolder(holder: BindingViewHolder<VB>, position: Int) {
        onBindViewHolder(holder.binding, currentList[position])
    }
}

abstract class SimpleListAdapter<T, VH : RecyclerView.ViewHolder>(diffCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, VH>(diffCallback) {
    var onItemClickListener: OnItemClickListener<T>? = null
    var onItemLongClickListener: OnItemClickListener<T>? = null

    fun doOnItemClick(block: OnItemClickListener<T>) {
        onItemClickListener = block
    }

    fun doOnItemLongClick(block: OnItemClickListener<T>) {
        onItemLongClickListener = block
    }

    fun interface OnItemClickListener<T> {
        fun onItemClick(item: T, position: Int)
    }
}

class DiffCallback<T : Any> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem == newItem
    override fun areContentsTheSame(oldItem: T, newItem: T) = false
}



