package top.tobin.common.ui.wheelview

import android.content.Context
import android.view.MotionEvent
import android.view.VelocityTracker
import android.widget.Scroller
import kotlin.math.abs

/**
 * Created by Tobin on 2022/9/27.
 * Email: junbin.li@qq.com
 * Description: .
 */
class WheelScroller(context: Context, private val mWheelView: WheelView) : Scroller(context) {
    private var mScrollOffset = 0
    private var lastTouchY = 0f
    private var isScrolling = false

    private val mVelocityTracker: VelocityTracker by lazy {
        VelocityTracker.obtain()
    }
    var onWheelChangedListener: OnWheelChangedListener? = null

    fun computeScroll() {
        if (isScrolling) {
            isScrolling = computeScrollOffset()
            doScroll(currY - mScrollOffset)
            if (isScrolling) {
                mWheelView.postInvalidate()
            } else {
                // 滚动结束后，重新调整位置
                justify()
            }
        }
    }

    var mCurrentIndex = -1

    private fun doScroll(distance: Int) {
        mScrollOffset += distance
        if (!mWheelView.isCyclic) {
            // 限制滚动边界
            val maxOffset = (mWheelView.itemSize - 1) * mWheelView.mItemHeight
            if (mScrollOffset < 0) {
                mScrollOffset = 0
            } else if (mScrollOffset > maxOffset) {
                mScrollOffset = maxOffset
            }
        }
        // 滚动的时候不回调， 回调结束后回调 @See justify()
//        notifyWheelChangedListener()
    }

    private fun notifyWheelChangedListener() {
        val oldValue = mCurrentIndex
        val newValue = getCurrentIndex()
        if (oldValue != newValue) {
            mCurrentIndex = newValue
            if (onWheelChangedListener != null) {
                onWheelChangedListener!!.onChanged(mWheelView, oldValue, newValue)
            }
        }
    }

    private fun getCurrentIndex(): Int {
        val itemHeight = mWheelView.mItemHeight
        val itemSize = mWheelView.itemSize
        if (itemSize == 0) return -1
        val itemIndex = if (mScrollOffset < 0) {
            (mScrollOffset - itemHeight / 2) / itemHeight
        } else {
            (mScrollOffset + itemHeight / 2) / itemHeight
        }
        var currentIndex = itemIndex % itemSize
        if (currentIndex < 0) {
            currentIndex += itemSize
        }
        return currentIndex
    }

    fun setCurrentIndex(index: Int, animated: Boolean) {
        val position = index * mWheelView.mItemHeight
        val distance = position - mScrollOffset
        if (distance == 0) {
            return
        }
        if (animated) {
            isScrolling = true
            startScroll(0, mScrollOffset, 0, distance, JUSTIFY_DURATION)
            mWheelView.invalidate()
        } else {
            doScroll(distance)
            mWheelView.invalidate()
        }
    }

    val itemIndex: Int
        get() = if (mWheelView.mItemHeight == 0) 0 else mScrollOffset / mWheelView.mItemHeight

    val itemOffset: Int
        get() = if (mWheelView.mItemHeight == 0) 0 else mScrollOffset % mWheelView.mItemHeight

    fun reset() {
        isScrolling = false
        mScrollOffset = 0
        mCurrentIndex = -1
        notifyWheelChangedListener()
        forceFinished(true)
    }

    /**
     * 当滚轮结束滑行后，调整滚轮的位置，需要调用该方法
     */
    private fun justify() {
        val itemHeight = mWheelView.mItemHeight
        val offset = mScrollOffset % itemHeight
        if (offset > 0 && offset < itemHeight / 2) {
            isScrolling = true
            startScroll(0, mScrollOffset, 0, -offset, JUSTIFY_DURATION)
            mWheelView.invalidate()
        } else if (offset >= itemHeight / 2) {
            isScrolling = true
            startScroll(0, mScrollOffset, 0, itemHeight - offset, JUSTIFY_DURATION)
            mWheelView.invalidate()
        } else if (offset < 0 && offset > -itemHeight / 2) {
            isScrolling = true
            startScroll(0, mScrollOffset, 0, -offset, JUSTIFY_DURATION)
            mWheelView.invalidate()
        } else if (offset <= -itemHeight / 2) {
            isScrolling = true
            startScroll(0, mScrollOffset, 0, -itemHeight - offset, JUSTIFY_DURATION)
            mWheelView.invalidate()
        }
        notifyWheelChangedListener()
    }

    fun onTouchEvent(event: MotionEvent): Boolean {

        mVelocityTracker.addMovement(event)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastTouchY = event.y
                forceFinished(true)
            }

            MotionEvent.ACTION_MOVE -> {
                val touchY = event.y
                val deltaY = (touchY - lastTouchY).toInt()
                if (deltaY != 0) {
                    doScroll(-deltaY)
                    mWheelView.invalidate()
                }
                lastTouchY = touchY
            }

            MotionEvent.ACTION_UP -> {
                mVelocityTracker.computeCurrentVelocity(1000)
                val velocityY = mVelocityTracker.yVelocity

                if (abs(velocityY.toDouble()) > 0) {
                    isScrolling = true
                    fling(
                        0,
                        mScrollOffset,
                        0,
                        -velocityY.toInt(),
                        0,
                        0,
                        Int.MIN_VALUE,
                        Int.MAX_VALUE
                    )
                    mWheelView.invalidate()
                } else {
                    justify()
                }
                // 当触发抬起、取消事件后，回收VelocityTracker
                mVelocityTracker.recycle()
            }

            MotionEvent.ACTION_CANCEL -> {
                mVelocityTracker.recycle()
            }
        }
        return true
    }

    companion object {
        const val JUSTIFY_DURATION: Int = 400
    }
}
