package top.tobin.common.utils

import android.view.View
import androidx.annotation.IntRange
import java.lang.UnsupportedOperationException

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: 对视图的点击去抖动.
 */
class ClickUtils private constructor() {
    /**
     * 防抖点击监听器.
     */
    abstract class OnDebouncingClickListener(
        private val mIsGlobal: Boolean, private val mDuration: Long
    ) : View.OnClickListener {
        abstract fun onDebouncingClick(v: View?)

        override fun onClick(v: View) {
            if (mIsGlobal) {
                if (mEnabled) {
                    mEnabled = false
                    v.postDelayed(ENABLE_AGAIN, mDuration)
                    onDebouncingClick(v)
                }
            } else {
                if (isValid(v, mDuration)) {
                    onDebouncingClick(v)
                }
            }
        }

        companion object {
            private var mEnabled = true
            private val ENABLE_AGAIN = Runnable { mEnabled = true }

            private fun isValid(view: View, duration: Long): Boolean {
                val curTime = System.currentTimeMillis()
                val tag = view.getTag(DEBOUNCING_TAG)
                if (tag !is Long) {
                    view.setTag(DEBOUNCING_TAG, curTime)
                    return true
                }
                if (curTime - tag <= duration) {
                    return false
                }
                view.setTag(DEBOUNCING_TAG, curTime)
                return true
            }
        }
    }

    companion object {
        private const val DEBOUNCING_TAG = -7
        private const val DEBOUNCING_DEFAULT_VALUE: Long = 500

        /**
         * 对单视图应用防抖点击.
         *
         * @param view     The view.
         * @param listener The listener.
         */
        fun applySingleDebouncing(view: View, listener: View.OnClickListener?) {
            applySingleDebouncing(arrayOf(view), listener)
        }

        /**
         * 对单视图应用防抖点击.
         *
         * @param views    The views.
         * @param listener The listener.
         */
        fun applySingleDebouncing(views: Array<View>?, listener: View.OnClickListener?) {
            applySingleDebouncing(views, DEBOUNCING_DEFAULT_VALUE, listener)
        }

        /**
         * 对单视图应用防抖点击.
         *
         * @param views    The views.
         * @param duration The duration of debouncing.
         * @param listener The listener.
         */
        fun applySingleDebouncing(
            views: Array<View>?, @IntRange(from = 0) duration: Long, listener: View.OnClickListener?
        ) {
            applyDebouncing(views, false, duration, listener)
        }

        fun applyGlobalDebouncing(view: View, listener: View.OnClickListener?) {
            applyGlobalDebouncing(arrayOf(view), listener)
        }

        fun applyGlobalDebouncing(
            view: View, @IntRange(from = 0) duration: Long, listener: View.OnClickListener?
        ) {
            applyGlobalDebouncing(arrayOf(view), duration, listener)
        }

        /**
         * 对所有设置 GlobalDebouncing 的视图应用防抖点击.
         *
         * @param views    The views.
         * @param listener The listener.
         */
        fun applyGlobalDebouncing(views: Array<View>?, listener: View.OnClickListener?) {
            applyGlobalDebouncing(views, DEBOUNCING_DEFAULT_VALUE, listener)
        }

        /**
         * 对所有设置 GlobalDebouncing 的视图应用防抖点击.
         *
         * @param views    The views.
         * @param duration The duration of debouncing.
         * @param listener The listener.
         */
        fun applyGlobalDebouncing(
            views: Array<View>?, @IntRange(from = 0) duration: Long, listener: View.OnClickListener?
        ) {
            applyDebouncing(views, true, duration, listener)
        }

        private fun applyDebouncing(
            views: Array<View>?,
            isGlobal: Boolean,
            @IntRange(from = 0) duration: Long,
            listener: View.OnClickListener?
        ) {
            if (views.isNullOrEmpty() || listener == null) return

            for (view in views) {
                view.setOnClickListener(object : OnDebouncingClickListener(isGlobal, duration) {
                    override fun onDebouncingClick(v: View?) {
                        listener.onClick(v)
                    }
                })
            }
        }
    }

    init {
        throw UnsupportedOperationException("instantiate error!")
    }
}