package top.tobin.shared.data.remote

import top.tobin.shared.data.entity.UserEntity
import com.google.gson.JsonObject
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: 日程相关后台接口.
 */
interface ScheduleInterface {
    @Headers(
        "content-type:application/json"
    )
    @POST(("v3/api"))
    suspend fun addSchedule(@Body body: RequestBody): UserEntity


//    @Multipart
//    @Headers("api:x.upload")
//    @POST(("gw/api"))
//    suspend fun uploadFile(
//        @Part file: MultipartBody.Part,
//        @PartMap params: Map<String, RequestBody>
//    ): UploadResponse
//
//    /**
//     * 网关2.0文件上传
//     *
//     * @param url 接口请求路径，拼接在域名后面，比如：iov_gw/api
//     * @param headerMap Map<String, @JvmSuppressWildcards Any>
//     * @param file RequestBody
//     * @return Deferred<JsonObject>
//     *             @HeaderMap headerMap: Map<String, @JvmSuppressWildcards Any>,
//
//     */
//    @Headers("api:x.daUpload")
//    @POST("v3/api")
//    suspend fun uploadFileV3(
//        @Body file: MultipartBody
//    ): UploadResponse

}