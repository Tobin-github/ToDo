package top.tobin.shared.data.repository

import top.tobin.shared.data.remote.DataResult
import kotlinx.coroutines.flow.Flow
import top.tobin.shared.model.UserModel

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: IUserRepository.
 */
interface IUserRepository {

    suspend fun fetchLogin(username: String, password: String): Flow<DataResult<UserModel>>

    suspend fun register(userModel: UserModel): Flow<DataResult<Boolean>>

    suspend fun getHistoryLoginUser(): Flow<List<UserModel>>

    suspend fun removeLocalUser(userModel: UserModel)


}