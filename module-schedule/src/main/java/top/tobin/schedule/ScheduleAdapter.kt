package top.tobin.schedule

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import top.tobin.common.viewbinding.BindingViewHolder
import top.tobin.schedule.databinding.ItemListScheduleBinding
import top.tobin.shared.model.ScheduleModel

class ScheduleAdapter :
    ListAdapter<ScheduleModel, BindingViewHolder<ItemListScheduleBinding>>(ScheduleModel.DiffCallback()) {
    private var mOnItemClickListener: ((viewId: Int, item: ScheduleModel, position: Int) -> Unit)? =
        null

    fun setOnItemClickListener(mOnItemClickListener: (viewId: Int, item: ScheduleModel, position: Int) -> Unit) {
        this.mOnItemClickListener = mOnItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BindingViewHolder(parent, ItemListScheduleBinding::inflate)

    override fun onBindViewHolder(
        holder: BindingViewHolder<ItemListScheduleBinding>, position: Int
    ) {
        val logInfoModel = currentList[holder.bindingAdapterPosition]

//        holder.binding.tvOperationUpload.setOnClickListener {
//            mOnItemClickListener?.invoke(it.id, logInfoModel, holder.bindingAdapterPosition)
//        }
//        holder.binding.tvOperationDelete.setOnClickListener {
//            mOnItemClickListener?.invoke(it.id, logInfoModel, holder.bindingAdapterPosition)
//        }
    }


}