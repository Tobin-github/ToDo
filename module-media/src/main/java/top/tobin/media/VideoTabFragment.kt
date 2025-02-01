package top.tobin.media

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.analytics.AnalyticsListener
import androidx.media3.exoplayer.source.LoadEventInfo
import androidx.media3.exoplayer.source.MediaLoadData
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import androidx.annotation.OptIn

class VideoTabFragment : Fragment(),CustomGestureDetector.OnGestureListener {
    companion object {
        private const val TAG = "VideoTabFragment"
    }
    private lateinit var playerView: PlayerView

    private val viewModel: VideoTabViewModel by viewModels({ requireActivity() })

    @OptIn(UnstableApi::class)
    private val player: ExoPlayer by lazy {
        val cache = VideoCacheManager.getCache(requireContext())

        // 构建 CacheDataSource
        val cacheDataSourceFactory = CacheDataSource.Factory()
            .setCache(cache)
            .setUpstreamDataSourceFactory(DefaultHttpDataSource.Factory())
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)

        ExoPlayer.Builder(requireContext()).setMediaSourceFactory(
            ProgressiveMediaSource.Factory(cacheDataSourceFactory)
        ).build()
    }

    private val gestureDetector by lazy {
        CustomGestureDetector(requireContext(), this)
    }

    private fun togglePlayPause() {
        if (player.isPlaying) {
            player.pause()
//            iv_pause.visibility = View.VISIBLE
        } else {
            player.play()
//            iv_pause.visibility = View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        test()
    }

    @OptIn(UnstableApi::class)
    private fun test() {
        player.addAnalyticsListener(object : AnalyticsListener {
            override fun onLoadCompleted(
                eventTime: AnalyticsListener.EventTime,
                loadEventInfo: LoadEventInfo,
                mediaLoadData: MediaLoadData
            ) {
                Log.d(
                    "VideoTabFragment",
                    "Data loaded from cache: ${loadEventInfo.responseHeaders}"
                )
            }
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_video_tab, container, false)
        playerView = view.findViewById(R.id.player_view)
        playerView.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val videoUri = arguments?.getString("video_url") ?: return
        playerView.player = player

        val mediaItem = MediaItem.fromUri(videoUri)
        player.setMediaItem(mediaItem)
        player.prepare()
    }

    @OptIn(UnstableApi::class)
    override fun onDestroyView() {
        super.onDestroyView()
        player.release()
        VideoCacheManager.release()
    }

    override fun onResume() {
        super.onResume()
        player.play()
    }

    override fun onPause() {
        super.onPause()
        player.pause()
    }

    override fun onLongPress(e: Boolean) {
        Log.e(TAG, "onLongPress: $e")
    }

    override fun onSingleClick(): Boolean {
        togglePlayPause()
        return true
    }

    override fun onDoubleClick(): Boolean {
        return false
    }

    override fun onVerticalScroll(distanceY: Float): Boolean {
        return false
    }

    override fun onHorizontalScroll(distanceX: Float): Boolean {
        return false
    }
}