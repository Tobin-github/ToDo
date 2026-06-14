package top.tobin.media

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import top.tobin.media.databinding.ActivityVideoTabBinding

class VideoTabActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoTabBinding

    private val mVideoPagerAdapter by lazy {
        VideoPagerAdapter(this, VideoConstant.getVideoList())
    }

    var pos = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoTabBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.vpVideoTab.adapter = mVideoPagerAdapter

        binding.vpVideoTab.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                pos = position
            }
        })

        binding.btRemove.setOnClickListener {
            mVideoPagerAdapter.removeItem(pos, null)
            Log.e(TAG, "btRemove pos: $pos")
//            mVideoPagerAdapter.addItem(pos + 1)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy")
    }

    companion object {
        const val TAG = "VideoTabActivity"
    }
}