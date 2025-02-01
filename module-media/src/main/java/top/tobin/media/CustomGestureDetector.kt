package top.tobin.media

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import kotlin.math.abs

class CustomGestureDetector(
    context: Context, private val listener: OnGestureListener
) : GestureDetector.SimpleOnGestureListener() {

    interface OnGestureListener {
        fun onLongPress(isPressing: Boolean)
        fun onSingleClick(): Boolean
        fun onDoubleClick(): Boolean
        fun onVerticalScroll(distanceY: Float): Boolean
        fun onHorizontalScroll(distanceX: Float): Boolean
    }

    private val gestureDetector = GestureDetector(context, this)
    private var isScrolling = false
    private var isLongPress = false

    fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                isScrolling = false
            }

            MotionEvent.ACTION_UP -> {
                if (!isScrolling) {
                    listener.onSingleClick()
                }
                isLongPress = false
                listener.onLongPress(false)
            }

            MotionEvent.ACTION_CANCEL -> {
                isLongPress = false
                listener.onLongPress(false)
            }
        }
        return gestureDetector.onTouchEvent(event)
    }

    // 控制双击灵敏度（单位：ms）
    override fun onDoubleTap(e: MotionEvent): Boolean {
        return listener.onDoubleClick()
    }

    override fun onScroll(
        e1: MotionEvent?, e2: MotionEvent, distanceX: Float, distanceY: Float
    ): Boolean {
        isScrolling = true
        return if (abs(distanceY) > abs(distanceX)) {
            listener.onVerticalScroll(distanceY)
        } else {
            listener.onHorizontalScroll(distanceX)
        }
    }

    // 屏蔽长按事件避免冲突
    override fun onLongPress(e: MotionEvent) {
        isLongPress = true
        listener.onLongPress(true)
    }
}