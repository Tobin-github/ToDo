package top.tobin.shared.data.repository

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import top.tobin.shared.data.remote.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import top.tobin.shared.model.LocalPhoto

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: GalleryRepository. 读取手机本地图片.
 */
class GalleryRepository(
    private val context: Context,
) : IGalleryRepository {

    companion object {
        private const val TAG = "GalleryRepository"
    }

    override suspend fun fetchLocalPhotos(): Flow<DataResult<List<LocalPhoto>>> {
        return flow<DataResult<List<LocalPhoto>>> {
            val photos = queryLocalPhotos()
            emit(DataResult.Success(photos))
        }.catch {
            emit(DataResult.Failure(Exception("读取本地图片失败: ${it.message}")))
        }.flowOn(Dispatchers.IO)
    }

    private fun queryLocalPhotos(): List<LocalPhoto> {
        val photos = mutableListOf<LocalPhoto>()
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_TAKEN,
            MediaStore.Images.Media.SIZE
        )
        val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"

        context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn) ?: ""
                val date = cursor.getLong(dateColumn)
                val size = cursor.getLong(sizeColumn)
                val uri = Uri.withAppendedPath(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id.toString()
                )
                photos.add(LocalPhoto(id, uri, name, date, size))
            }
        }
        return photos
    }

}
