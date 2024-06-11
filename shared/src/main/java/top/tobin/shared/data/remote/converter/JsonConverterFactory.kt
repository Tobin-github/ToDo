package top.tobin.shared.data.remote.converter

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: JsonConverterFactory.
 */
class JsonConverterFactory constructor(private val gson: Gson) : Converter.Factory() {
    companion object {
        fun create(): JsonConverterFactory {
            return JsonConverterFactory(Gson())
        }
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val adapter = gson.getAdapter(TypeToken.get(type)) as? TypeAdapter<*>
        return adapter?.let {
            JsonResponseBodyConverter(gson, it)
        } //响应
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        val adapter = gson.getAdapter(TypeToken.get(type)) as? TypeAdapter<*>
        return adapter?.let {
            JsonRequestBodyConverter(gson, it)
        } //请求
    }
}