package top.tobin.media

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import kotlin.math.abs

class CustomGestureDetector(
    context: Context, private val listener: OnGestureListener
) : GestureDetector.SimpleOnGestureListener() {

    interface OnGestureListener {
        fun onSingleClick(): Boolean
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
                isLongPress = false
            }

            MotionEvent.ACTION_MOVE ->{
                if (isLongPress) {
                    return true
                }
            }

            MotionEvent.ACTION_UP -> {
                if (isLongPress) {
                    onLongPress(event)
                }
            }

        }
        return gestureDetector.onTouchEvent(event)
    }

    override fun onScroll(
        e1: MotionEvent?, e2: MotionEvent, distanceX: Float, distanceY: Float
    ): Boolean {
        Log.e(TAG, "onScroll isLongPress:$isLongPress")

        isScrolling = true
        if (isLongPress) {
            return true
        }
        return if (abs(distanceY) > abs(distanceX)) {
            listener.onVerticalScroll(distanceY)
        } else {
            listener.onHorizontalScroll(distanceX)
        }
    }

    override fun onLongPress(e: MotionEvent) {
        Log.e(TAG, "onLongPress ${e.actionMasked}")
        isLongPress = true
    }


    companion object {
        const val TAG = "CustomGestureDetector"
    }
}