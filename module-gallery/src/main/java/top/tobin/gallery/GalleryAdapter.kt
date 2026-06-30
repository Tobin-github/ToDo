package top.tobin.gallery

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import top.tobin.common.viewbinding.BindingViewHolder
import top.tobin.gallery.databinding.ItemGalleryBinding
import top.tobin.shared.model.LocalPhoto

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: 相册网格适配器.
 */
class GalleryAdapter :
    ListAdapter<LocalPhoto, BindingViewHolder<ItemGalleryBinding>>(DIFF_CALLBACK) {

    private var mOnItemClickListener: ((item: LocalPhoto, position: Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (LocalPhoto, Int) -> Unit) {
        mOnItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BindingViewHolder(parent, ItemGalleryBinding::inflate)

    override fun onBindViewHolder(holder: BindingViewHolder<ItemGalleryBinding>, position: Int) {
        val photo = currentList[holder.bindingAdapterPosition]
        Glide.with(holder.itemView)
            .load(photo.uri)
            .centerCrop()
            .into(holder.binding.ivPhoto)

        holder.itemView.setOnClickListener {
            mOnItemClickListener?.invoke(photo, holder.bindingAdapterPosition)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<LocalPhoto>() {
            override fun areItemsTheSame(oldItem: LocalPhoto, newItem: LocalPhoto) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: LocalPhoto, newItem: LocalPhoto) =
                oldItem == newItem
        }
    }
}
