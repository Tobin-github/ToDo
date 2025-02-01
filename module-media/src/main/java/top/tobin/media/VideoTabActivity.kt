package top.tobin.media

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gyf.immersionbar.ktx.immersionBar
import top.tobin.media.databinding.ActivityVideoTabBinding

class VideoTabActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoTabBinding
    private val mVideoPagerAdapter by lazy {
        VideoPagerAdapter(this, VideoConstant.getVideoList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoTabBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vpVideoTab.adapter = mVideoPagerAdapter
        immersionBar {
            statusBarDarkFont(false)
            statusBarColor(android.R.color.black)
        }
    }

    companion object {

    }
}