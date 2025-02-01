package top.tobin.media

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

@UnstableApi
object VideoCacheManager {

    private var cache: Cache? = null

    fun getCache(context: Context): Cache {
        return cache ?: synchronized(this) {
            cache ?: buildCache(context).also { cache = it }
        }
    }

    @OptIn(UnstableApi::class)
    private fun buildCache(context: Context): Cache {
        val cacheDir = File(context.externalCacheDir, "media3_cache")
        val evictor = LeastRecentlyUsedCacheEvictor(200 * 1024 * 1024)
        val databaseProvider = StandaloneDatabaseProvider(context)
        return SimpleCache(cacheDir, evictor, databaseProvider)
    }

    @kotlin.OptIn(DelicateCoroutinesApi::class)
    fun release() {
        GlobalScope.launch {
            cache?.release()
        }
        cache = null
    }
}