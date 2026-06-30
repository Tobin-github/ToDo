package top.tobin.shared.data.remote

import retrofit2.http.GET

interface AccountingInterface {

    // TODO: 替换为实际记账接口
    @GET("posts")
    suspend fun fetchAccountList(): Any
}
