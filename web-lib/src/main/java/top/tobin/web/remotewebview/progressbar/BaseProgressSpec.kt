package top.tobin.web.remotewebview.progressbar

/**
 * Created by Tobin on 2022/5/31.
 * Email: junbin.li@qq.com
 * Description: 定义进度条接口函数.
 */
interface BaseProgressSpec {
    fun show()
    fun hide()
    fun reset()
    fun setProgress(newProgress: Int)
}