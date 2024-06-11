package top.tobin.shared.data.repository

import top.tobin.shared.data.remote.DataResult
import kotlinx.coroutines.flow.Flow
import top.tobin.shared.model.BingModel
import top.tobin.shared.model.UserModel

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: IGalleryRepository.
 */
interface IGalleryRepository {

    suspend fun fetchRandPicture(): Flow<DataResult<BingModel>>


}