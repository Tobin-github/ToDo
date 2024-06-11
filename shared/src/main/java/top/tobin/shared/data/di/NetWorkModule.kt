package top.tobin.shared.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import top.tobin.common.utils.LogUtil
import top.tobin.shared.data.remote.AccountingInterface
import top.tobin.shared.data.remote.BingInterface
import top.tobin.shared.data.remote.ParamsV3Interceptor
import top.tobin.shared.data.remote.ScheduleInterface
import top.tobin.shared.data.remote.UserInterface
import top.tobin.shared.data.remote.converter.JsonConverterFactory
import java.io.File
import javax.inject.Singleton


/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: NetWorkModule.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {

    /**
     * @Provides 常用于被 @Module 注解标记类的内部的方法，并提供依赖项对象。
     * @Singleton 提供单例
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val cacheDir = File(context.cacheDir, "HttpResponseCache")
        //缓存可用大小为10M
        val cache = Cache(cacheDir, (10 * 1024 * 1024).toLong())

//        val cacheControlInterceptor = Interceptor { chain: Interceptor.Chain ->
//            val request: Request = chain.request().newBuilder()
//                .cacheControl(CacheControl.FORCE_CACHE)
//                .build()
//            val originalResponse: Response = chain.proceed(request)
//            val maxStale = 60 * 60 * 24 * 4 * 7 //离线缓存4周
//            return@Interceptor originalResponse.newBuilder()
//                .removeHeader("Pragma")
//                .removeHeader("Cache-Control")
//                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
//                .build()
//        }

        return OkHttpClient.Builder()
//            .addNetworkInterceptor(cacheControlInterceptor)
//            .addInterceptor(cacheControlInterceptor)
            .cache(cache)
            //日志拦截器会影响文件上传进度监听
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(JsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserInterface {
        return retrofit.create(UserInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideScheduleService(retrofit: Retrofit): ScheduleInterface {
        return retrofit.create(ScheduleInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideBingService(okHttpClient: OkHttpClient): BingInterface {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.vvhan.com/")
            .addConverterFactory(JsonConverterFactory.create())
            .build()
            .create(BingInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideAccountingService(retrofit: Retrofit): AccountingInterface {
        return retrofit.create(AccountingInterface::class.java)
    }

}
