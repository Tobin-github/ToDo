package top.tobin.common.ui.wheelview

import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.util.AttributeSet
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

class Wheel3DView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    WheelView(context, attrs) {
    private val mCamera = Camera()
    private val mMatrix = Matrix()

    override val prefHeight: Int
        get() {
            val padding = paddingTop + paddingBottom
            val innerHeight = (mItemHeight * mItemCount * 2 / Math.PI).toInt()
            return innerHeight + padding
        }

    override fun drawItem(canvas: Canvas, index: Int, offset: Int) {
        val text = getCharSequence(index) ?: return
        // 滚轮的半径
        val r = (height - paddingTop - paddingBottom) / 2
        // 和中间选项的距离
        val range = (index - mScroller.itemIndex) * mItemHeight - offset
        // 当滑动的角度和y轴垂直时（此时文字已经显示为一条线），不绘制文字
        if (abs(range.toDouble()) > r * Math.PI / 2) return

        val centerX = mClipRectMiddle.centerX()
        val centerY = mClipRectMiddle.centerY()

        val angle = range.toDouble() / r
        // 绕x轴滚动的角度
        val rotate = Math.toDegrees(-angle).toFloat()
        // 滚动的距离映射到x轴的长度
//        val translateX = (cos(angle) * sin(Math.PI / 36) * r * mToward).toFloat()
        // 滚动的距离映射到y轴的长度
        val translateY = (sin(angle) * r).toFloat()
        // 滚动的距离映射到z轴的长度
        val translateZ = ((1 - cos(angle)) * r).toFloat()
        // 折射偏移量x
        val refractX = textSize * .05f
        // 透明度
        val alpha = (cos(angle) * 255).toInt()

        // 绘制与下分界线相交的文字
        if (range in 1..<mItemHeight) {
            canvas.save()
            canvas.translate(refractX, 0f)
            canvas.clipRect(mClipRectMiddle)
            drawText(
                canvas, text, centerX.toFloat(), centerY.toFloat(), 0f,
                translateY, translateZ, rotate, mSelectedTextPaint
            )
            canvas.restore()

            mTextPaint.alpha = alpha
            canvas.save()
            canvas.clipRect(mClipRectBottom)
            drawText(
                canvas, text, centerX.toFloat(), centerY.toFloat(), 0f,
                translateY, translateZ, rotate, mTextPaint
            )
            canvas.restore()
        } else if (range >= mItemHeight) {
            mTextPaint.alpha = alpha
            canvas.save()
            canvas.clipRect(mClipRectBottom)
            drawText(
                canvas, text, centerX.toFloat(), centerY.toFloat(), 0f,
                translateY, translateZ, rotate, mTextPaint
            )
            canvas.restore()
        } else if (range < 0 && range > -mItemHeight) {
            canvas.save()
            canvas.translate(refractX, 0f)
            canvas.clipRect(mClipRectMiddle)
            drawText(
                canvas, text, centerX.toFloat(), centerY.toFloat(), 0f,
                translateY, translateZ, rotate, mSelectedTextPaint
            )
            canvas.restore()

            mTextPaint.alpha = alpha
            canvas.save()
            canvas.clipRect(mClipRectTop)
            drawText(
                canvas, text, centerX.toFloat(), centerY.toFloat(), 0f,
                translateY, translateZ, rotate, mTextPaint
            )
            canvas.restore()
        } else if (range <= -mItemHeight) {
            mTextPaint.alpha = alpha
            canvas.save()
            canvas.clipRect(mClipRectTop)
            drawText(
                canvas, text, centerX.toFloat(), centerY.toFloat(), 0f,
                translateY, translateZ, rotate, mTextPaint
            )
            canvas.restore()
        } else {
            canvas.save()
            canvas.translate(refractX, 0f)
            canvas.clipRect(mClipRectMiddle)
            drawText(
                canvas, text, centerX.toFloat(), centerY.toFloat(), 0f,
                translateY, translateZ, rotate, mSelectedTextPaint
            )
            canvas.restore()
        }
    }

    private fun drawText(
        canvas: Canvas, text: CharSequence, centerX: Float, centerY: Float, translateX: Float,
        translateY: Float, translateZ: Float, rotateX: Float, paint: Paint
    ) {
        mCamera.save()
        mCamera.translate(translateX, 0f, translateZ)
        mCamera.rotateX(rotateX)
        mCamera.getMatrix(mMatrix)
        mCamera.restore()

        val x = centerX
        val y = centerY + translateY

        // 设置绕x轴旋转的中心点位置
        mMatrix.preTranslate(-x, -y)
        mMatrix.postTranslate(x, y)

        val fontMetrics = paint.fontMetrics
        val baseline = ((fontMetrics.top + fontMetrics.bottom) / 2).toInt()

        canvas.concat(mMatrix)
        canvas.drawText(text, 0, text.length, x, y - baseline, paint)
    }
}
