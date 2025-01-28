package top.tobin.media

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import top.tobin.common.viewbinding.binding
import dagger.hilt.android.AndroidEntryPoint
import top.tobin.common.base.BaseFragment
import top.tobin.common.cpp.TestCpp
import top.tobin.media.databinding.FragmentMediaBinding

@AndroidEntryPoint
class MediaFragment : BaseFragment() {

    private val binding: FragmentMediaBinding by binding()
    private val viewModel: MediaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val testStr = TestCpp.stringFromJNI()
        "testStr: $testStr".also { binding.tvTest.text = it }

        binding.btShortVideo.setOnClickListener {
            openShortVideo()
        }
    }

    private fun openShortVideo() {
        startActivity(Intent(context, VideoTabActivity::class.java))
    }

    companion object {

        @JvmStatic
        fun newInstance() = MediaFragment()
    }
}


