package top.tobin.gallery

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.gyf.immersionbar.ktx.immersionBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import top.tobin.common.base.BaseFragment
import top.tobin.common.ui.LoadingDialog
import top.tobin.common.utils.LogUtil
import top.tobin.gallery.databinding.FragmentGalleryBinding
import top.tobin.common.viewbinding.binding

@AndroidEntryPoint
class GalleryFragment : BaseFragment() {

    private val binding: FragmentGalleryBinding by binding()
    private val viewModel: GalleryViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance() = GalleryFragment()
        val TAG: String = GalleryFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListener()
        observeUiState()
        immersionBar {
            statusBarDarkFont(true)
        }
    }

    private fun initClickListener() {
        binding.btUpdatePicture.setOnClickListener {
            LogUtil.e("=========== initClickListener")
            viewModel.updatePicture()
        }
    }

    private fun observeUiState() {
        lifecycleScope.launch {
            viewModel.uiState.collectLatest {
                when (it) {
                    GalleryUiState.Idle -> {

                    }

                    is GalleryUiState.BingModels -> {
                        LogUtil.d(TAG, "randPicture: ${it.bingModel}")
                        binding.tvTestContent.text = it.bingModel.title
                        loadPicture(it.bingModel.url, binding.ivRandPicture)
                    }

                    is GalleryUiState.ImmersionBar -> {
                        setStatusBarDarkFont(it.statusBarDarkFont)
                    }

                    is GalleryUiState.Loading -> {
                        activity?.let { ac ->
                            if (it.isLoading) {
                                LoadingDialog.instance.showLoading(ac, "图片加载中...")
                            } else {
                                LoadingDialog.instance.dismissLoading()
                            }
                        } ?: kotlin.run {
                            LogUtil.e("SwitchLoading activity is null")
                        }
                    }
                }
            }
        }

    }

    private fun loadPicture(url: String, imageView: ImageView) {
        Glide.with(this).asBitmap().load(url).listener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(
                e: GlideException?, model: Any?, target: Target<Bitmap>, isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Bitmap, model: Any, target: Target<Bitmap>?, dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                paletteStatusBar(resource)
                return false
            }
        }).into(binding.ivRandPicture)
    }

    private fun paletteStatusBar(bitmap: Bitmap) {
        Palette.from(bitmap).generate {
            if (it == null) {
                return@generate
            }
            var mostPopularSwatch: Palette.Swatch? = null
            for (swatch in it.swatches) {
                if (mostPopularSwatch == null || swatch.population > mostPopularSwatch.population) {
                    mostPopularSwatch = swatch
                }
            }
            mostPopularSwatch?.let { swatch ->
                val luminance = ColorUtils.calculateLuminance(swatch.rgb)
                // 当luminance小于0.5时，我们认为这是一个深色值.
                if (luminance < 0.5) {
                    viewModel.updateImmersionBar(false)
                } else {
                    viewModel.updateImmersionBar(true)
                }
            }
            it.lightVibrantSwatch?.rgb?.let { rgb ->
                binding.btUpdatePicture.setBackgroundColor(rgb)
            }
        }
    }

}