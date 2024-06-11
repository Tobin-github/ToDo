package top.tobin.shared.data.remote

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.*
import top.tobin.common.utils.LogUtil
import java.io.File
import java.io.FileInputStream
import java.io.IOException

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: UploadRequestBody.
 */
class UploadRequestBody(mFile: File) : RequestBody() {
    private val mFile: File
    private val mSumLength: Long
    private var uploaded = 0L
    private var percentTemp = 0L

    private var mUploadListener: ((percent: Int) -> Unit)? = null

    fun setUploadListener(uploadListener: (percent: Int) -> Unit) {
        mUploadListener = uploadListener
    }

    override fun contentType(): MediaType? {
        // return "text/plain".toMediaTypeOrNull()
        return "multipart/form-data".toMediaTypeOrNull()
    }

    @Throws(IOException::class)
    override fun contentLength(): Long {
        return mFile.length()
    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {

        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        val fileInputStream = FileInputStream(mFile)
        fileInputStream.use { mIn ->
            var read: Int
            while (mIn.read(buffer).also { read = it } != -1) {
                uploaded += read
                val percent = 100 * uploaded / mSumLength
                LogUtil.d("UploadRequestBody percent: $percent")
                if (percentTemp != percent) {
                    percentTemp = percent
                    if (percent <= 100) mUploadListener?.invoke(percent.toInt())
                }
                sink.write(buffer, 0, read)
            }
        }
    }

    companion object {
        private const val DEFAULT_BUFFER_SIZE = 2048
    }

    init {
        this.mFile = mFile
        mSumLength = mFile.length()
    }


}