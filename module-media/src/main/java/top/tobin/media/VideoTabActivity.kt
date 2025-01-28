package top.tobin.media

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import top.tobin.media.databinding.ActivityVideoTabBinding

class VideoTabActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoTabBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoTabBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    companion object {

    }
}