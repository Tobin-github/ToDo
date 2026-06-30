package top.tobin.gallery

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import top.tobin.common.base.BaseFragment
import top.tobin.common.viewbinding.binding
import top.tobin.gallery.databinding.FragmentGalleryBinding

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: 相册 Fragment，展示手机本地图片.
 */
@AndroidEntryPoint
class GalleryFragment : BaseFragment() {

    private val binding: FragmentGalleryBinding by binding()
    private val viewModel: GalleryViewModel by viewModels()

    private val galleryAdapter = GalleryAdapter()

    companion object {
        @JvmStatic
        fun newInstance() = GalleryFragment()
        val TAG: String = GalleryFragment::class.java.simpleName
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            viewModel.loadPhotos()
        } else {
            binding.tvEmpty.text = "需要存储权限才能读取图片"
            binding.tvEmpty.visibility = View.VISIBLE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeUiState()
        checkPermissionAndLoad()
    }

    private fun initRecyclerView() {
        binding.rvGallery.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = galleryAdapter
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                private val spacing = 2.dpToPx()
                override fun getItemOffsets(
                    outRect: Rect, view: View,
                    parent: RecyclerView, state: RecyclerView.State
                ) {
                    outRect.set(spacing, spacing, spacing, spacing)
                }
            })
        }
        galleryAdapter.setOnItemClickListener { _, position ->
            val photos =
                (viewModel.uiState.value as? GalleryUiState.Photos)?.photos
                    ?: return@setOnItemClickListener
            val intent = Intent(requireContext(), PhotoViewerActivity::class.java).apply {
                putParcelableArrayListExtra(PhotoViewerActivity.EXTRA_PHOTOS, ArrayList(photos))
                putExtra(PhotoViewerActivity.EXTRA_POSITION, position)
            }
            startActivity(intent)
        }
    }

    private fun observeUiState() {
        lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                when (state) {
                    is GalleryUiState.Idle -> {
                        binding.pbLoading.visibility = View.GONE
                    }

                    is GalleryUiState.Loading -> {
                        binding.pbLoading.visibility =
                            if (state.isLoading) View.VISIBLE else View.GONE
                    }

                    is GalleryUiState.Photos -> {
                        binding.pbLoading.visibility = View.GONE
                        if (state.photos.isEmpty()) {
                            binding.tvEmpty.text = "暂无图片"
                            binding.tvEmpty.visibility = View.VISIBLE
                        } else {
                            binding.tvEmpty.visibility = View.GONE
                            galleryAdapter.submitList(state.photos)
                        }
                    }

                    is GalleryUiState.Error -> {
                        binding.pbLoading.visibility = View.GONE
                        binding.tvEmpty.text = state.message
                        binding.tvEmpty.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun Int.dpToPx(): Int =
        (this * requireContext().resources.displayMetrics.density).toInt()

    private fun checkPermissionAndLoad() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if (ContextCompat.checkSelfPermission(
                requireContext(), permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.loadPhotos()
        } else {
            permissionLauncher.launch(permission)
        }
    }
}
