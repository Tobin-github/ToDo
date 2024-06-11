package top.tobin.shared.data.repository

import top.tobin.shared.data.local.AppDataBase
import top.tobin.shared.data.remote.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import top.tobin.common.utils.LogUtil
import top.tobin.shared.data.remote.BingInterface
import top.tobin.shared.model.BingModel

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: GalleryRepository.
 */
class GalleryRepository(
    private val api: BingInterface,
    private val db: AppDataBase,
) : IGalleryRepository {

    companion object {
        private const val TAG = "GalleryRepository"
    }

    override suspend fun fetchRandPicture(): Flow<DataResult<BingModel>> {
        return flow {
            val netData = api.postBingRand()
            LogUtil.d("fetchRandPicture: $netData")
            if (netData.success) {
                emit(DataResult.Success(netData.data))
            } else {
                emit(DataResult.Failure(Exception("failure: ${netData.data}")))
            }
        }.catch {
            emit(DataResult.Failure(Exception("failure: ${it.message}")))
        }.flowOn(Dispatchers.IO)
    }

}