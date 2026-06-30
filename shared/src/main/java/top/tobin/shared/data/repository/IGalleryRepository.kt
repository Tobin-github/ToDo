package top.tobin.shared.data.repository

import top.tobin.shared.data.remote.DataResult
import kotlinx.coroutines.flow.Flow
import top.tobin.shared.model.LocalPhoto

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: IGalleryRepository.
 */
interface IGalleryRepository {

    suspend fun fetchLocalPhotos(): Flow<DataResult<List<LocalPhoto>>>

}
