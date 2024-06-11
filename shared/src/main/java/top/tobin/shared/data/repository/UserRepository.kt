package top.tobin.shared.data.repository

import android.util.Log
import top.tobin.shared.data.entity.UserEntity
import top.tobin.shared.data.local.AppDataBase
import top.tobin.shared.data.remote.UserInterface
import top.tobin.shared.data.remote.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import top.tobin.shared.data.mapper.Mapper
import top.tobin.shared.model.UserModel
import top.tobin.common.utils.LogUtil

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: UserRepository.
 */
class UserRepository(
    private val api: UserInterface,
    private val db: AppDataBase,
    private val mapper2UserModel: Mapper<UserEntity, UserModel>,
    private val mapper2UserEntity: Mapper<UserModel, UserEntity>
) : IUserRepository {

    override suspend fun fetchLogin(
        username: String,
        password: String
    ): Flow<DataResult<UserModel>> {
        return flow {
            val user: UserEntity? = db.userDao().getUser(username, password)
            Log.d(TAG, "fetchLogin $user")
            user?.let {
                emit(DataResult.Success(mapper2UserModel.map(it)))
            } ?: emit(DataResult.Failure(Exception("用户名或密码错误")))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun register(userModel: UserModel): Flow<DataResult<Boolean>> {
        return flow {
            val netData = api.postJson()
//            LogUtil.d(TAG, "register netData: $netData")
            kotlinx.coroutines.delay(1000)
            val entity = db.userDao().getUser(userModel.username)
            if (entity != null) {
                emit(DataResult.Failure(Exception("该用户已经存在")))
            } else {
                val result = db.userDao().insertUser(mapper2UserEntity.map(userModel))
                LogUtil.d("register result $result")
                emit(DataResult.Success(result > 0))
            }
        }.catch {
            emit(DataResult.Failure(Exception(it.message)))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getHistoryLoginUser(): Flow<List<UserModel>> {
        return flow {
            db.userDao().getHistoryLoginUser().collect {
                val userModelList: MutableList<UserModel> = ArrayList()
                for (entity in it) {
                    userModelList.add(mapper2UserModel.map(entity))
                }
                emit(userModelList)
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun removeLocalUser(userModel: UserModel) {
        db.userDao().deleteUser(mapper2UserEntity.map(userModel))
    }

    companion object {
        private const val TAG = "UserRepositoryImpl"
    }

}