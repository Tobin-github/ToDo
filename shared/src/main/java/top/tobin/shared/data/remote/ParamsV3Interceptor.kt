package top.tobin.shared.data.remote

import okhttp3.*

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: 添加头部和body公共参数 和请求body数据加密处理 Interceptor.
 */
class ParamsV3Interceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }

}