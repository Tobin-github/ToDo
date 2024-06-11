package top.tobin.shared.data.remote

import top.tobin.shared.data.entity.UserEntity
import com.google.gson.JsonObject
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: 用户相关后台接口.
 */
interface UserInterface {
    @Headers(
        "content-type:application/json"
    )
    @POST(("v3/api"))
    suspend fun login(@Body body: RequestBody): UserEntity

    @Headers(
        "content-type:application/json"
    )
    @POST(("v3/api"))
    suspend fun register(@Body body: RequestBody): JsonObject

    @Headers(
        "content-type:application/json"
    )

    @GET(("posts"))
    suspend fun postJson(): Any

}