package top.tobin.todo.utils

import android.text.format.Formatter
import android.util.Log
import top.tobin.common.base.Initializer
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: FileUtils.
 */
object FileUtils {
    private const val TAG = "FileUtils"

    /**
     * 文件扫描
     */
    fun scanFile(dirOrFile: File, callback: (file: File) -> Unit) {
        val fileList = dirOrFile.listFiles()
        fileList?.map {
            //如果是文件夹 回调 递归调用函数 再遍历判断
            if (it.isDirectory) {
                //再次调用自己
                scanFile(it, callback)
            } else { //是文件 回调
                //文件通过接口函数回调
                callback(it)
            }
        } ?: run {
            Log.d(TAG, "fileList is null")
        }
    }


    /**
     * 删除单个文件
     * @param filePath 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    fun deleteSingleFile(filePath: String): Boolean {
        val file = File(filePath)
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        return if (file.exists() && file.isFile) {
            if (file.delete()) {
                Log.d(TAG, "deleteSingleFile: $filePath success!")
                true
            } else {
                Log.e(TAG, "deleteSingleFile: $filePath failure!")
                false
            }
        } else {
            Log.e(TAG, "deleteSingleFile：$filePath does not exist!")
            false
        }
    }

    fun zipFile(file: File, zipFilePath: String) {
        val zipFile = createFile(zipFilePath)
        val buffer = ByteArray(1024)
        var zipOutputStream: ZipOutputStream? = null
        var inputStream: FileInputStream? = null
        try {
            zipOutputStream = ZipOutputStream(FileOutputStream(zipFile))
            if (!file.exists()) return
            zipOutputStream.putNextEntry(ZipEntry(file.name))
            inputStream = FileInputStream(file)
            var len: Int
            while (inputStream.read(buffer).also { len = it } > 0) {
                zipOutputStream.write(buffer, 0, len)
            }
            zipOutputStream.closeEntry()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
            zipOutputStream?.close()
        }
    }

    private fun createFile(filePath: String): File {
        val file = File(filePath)
        val parentFile = file.parentFile!!
        if (!parentFile.exists()) {
            parentFile.mkdirs()
        }
        if (!file.exists()) {
            file.createNewFile()
        }
        return file
    }

    fun formatSize(size: Long): String? {
        return Formatter.formatFileSize(Initializer.application, size)
    }

}