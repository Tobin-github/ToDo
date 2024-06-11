package top.tobin.shared.data.remote

import retrofit2.http.GET
import retrofit2.http.Headers
import top.tobin.shared.model.BingResult

interface AccountingInterface {

    /**
     * 随机图片
     */
    @Headers(
        "content-type:application/json"
    )
    @GET(("api/bing?type=json&rand=sj"))
    suspend fun postBingRand(): BingResult
}