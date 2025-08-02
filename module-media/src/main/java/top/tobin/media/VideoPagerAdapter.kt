package top.tobin.media

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class VideoPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val videoUrls: MutableList<String>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = videoUrls.size

    fun removeItem(position : Int, viewPager2: ViewPager2?) {
        val idsOld = videoUrls.toMutableList()
        videoUrls.removeAt(position)
        val idsNew = videoUrls.toMutableList()
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = idsOld.size
            override fun getNewListSize(): Int = idsNew.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                idsOld[oldItemPosition] == idsNew[newItemPosition]

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                areItemsTheSame(oldItemPosition, newItemPosition)
        }, true).dispatchUpdatesTo(this)

    }

    fun addItem(position: Int) {
        val item = "http://baobab.kaiyanapp.com/api/v1/playUrl?vid=170935&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss&udid=$position"
        val idsOld = videoUrls.toMutableList()
        videoUrls.add(position, item)
        val idsNew = videoUrls.toMutableList()
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = idsOld.size
            override fun getNewListSize(): Int = idsNew.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                idsOld[oldItemPosition] == idsNew[newItemPosition]

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                areItemsTheSame(oldItemPosition, newItemPosition)
        }, true).dispatchUpdatesTo(this)
    }

    override fun getItemId(position: Int): Long {
        return videoUrls[position].hashCode().toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return videoUrls.any { it.hashCode().toLong() == itemId }
    }

    override fun createFragment(position: Int): Fragment {
        return VideoTabFragment().apply {
            arguments = bundleOf("video_url" to videoUrls[position])
        }
    }

    fun addVideos(newVideos: List<String>) {
        videoUrls.addAll(newVideos)
        notifyItemRangeInserted(videoUrls.size - newVideos.size, newVideos.size)
    }
}