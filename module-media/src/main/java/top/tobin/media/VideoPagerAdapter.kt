package top.tobin.media

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class VideoPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val videoUrls: MutableList<String>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = videoUrls.size

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