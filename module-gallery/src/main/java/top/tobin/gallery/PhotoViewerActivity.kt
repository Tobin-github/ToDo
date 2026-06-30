package top.tobin.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import top.tobin.gallery.databinding.ActivityPhotoViewerBinding
import top.tobin.gallery.databinding.ItemPhotoViewerBinding
import top.tobin.shared.model.LocalPhoto

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: 图片全屏查看，支持左右滑动.
 */
class PhotoViewerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhotoViewerBinding
    private val photos = mutableListOf<LocalPhoto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        intent.getParcelableArrayListExtra<LocalPhoto>(EXTRA_PHOTOS)?.let {
            photos.addAll(it)
        }
        val startPosition = intent.getIntExtra(EXTRA_POSITION, 0)

        if (photos.isEmpty()) {
            finish()
            return
        }

        binding.vpPhoto.adapter = PhotoPagerAdapter(photos)
        binding.vpPhoto.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tvIndex.text = "${position + 1} / ${photos.size}"
            }
        })
        binding.vpPhoto.setCurrentItem(startPosition, false)
    }

    companion object {
        const val EXTRA_PHOTOS = "extra_photos"
        const val EXTRA_POSITION = "extra_position"
    }
}

private class PhotoPagerAdapter(
    private val photos: List<LocalPhoto>
) : RecyclerView.Adapter<PhotoPagerAdapter.PhotoViewHolder>() {

    class PhotoViewHolder(val binding: ItemPhotoViewerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemPhotoViewerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(photos[position].uri)
            .into(holder.binding.ivPhotoFull)
    }

    override fun getItemCount() = photos.size
}
