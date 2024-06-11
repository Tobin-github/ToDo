package top.tobin.shared.data.remote.converter

import com.google.gson.*
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: 自定义响应ResponseBody.
 */
class JsonResponseBodyConverter<T> constructor(
    private val gson: Gson,
    private val adapter: TypeAdapter<T>
) : Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T? {
        try {
            val originalBody = value.string()

            return adapter.fromJson(originalBody)
//            val encryptJson: JsonObject = GsonBuilder().create().fromJson(originalBody)

        } catch (e: Exception) {
            throw Exception(e.message)
        } finally {
            value.close()
        }
    }
}