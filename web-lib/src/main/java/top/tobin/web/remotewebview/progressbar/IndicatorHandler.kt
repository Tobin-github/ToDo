package top.tobin.web.remotewebview.progressbar

/**
 * Created by Tobin on 2022/5/31.
 * Email: junbin.li@qq.com
 * Description: 进度条指示.
 */
class IndicatorHandler {
    private var baseProgressSpec: BaseProgressSpec? = null
    fun progress(newProgress: Int) {
        when (newProgress) {
            0 -> reset()
            in 1..10 -> {
                showProgressBar()
            }
            in 11..94 -> {
                setProgressBar(newProgress)
            }
            else -> {
                setProgressBar(newProgress)
                finish()
            }
        }
    }

    fun offerIndicator(): BaseProgressSpec? {
        return baseProgressSpec
    }

    private fun reset() {
        baseProgressSpec?.reset()
    }

    fun finish() {
        baseProgressSpec?.hide()
    }

    fun setProgressBar(n: Int) {
        baseProgressSpec?.setProgress(n)
    }

    fun showProgressBar() {
        baseProgressSpec?.show()
    }

    fun injectProgressView(baseProgressSpec: BaseProgressSpec): IndicatorHandler {
        this.baseProgressSpec = baseProgressSpec
        return this
    }

    companion object {
        val instance: IndicatorHandler
            get() = IndicatorHandler()
    }
}