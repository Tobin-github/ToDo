package top.tobin.web.remotewebview.progressbar

import android.animation.*
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout

/**
 * Created by Tobin on 2022/5/31.
 * Email: junbin.li@qq.com
 * Description: 进度条.
 */
class WebProgressBar constructor(context: Context) : FrameLayout(context), BaseProgressSpec {
    /**
     * 进度条颜色
     */
    private var mColor = Color.parseColor("#3DDFE6F2")

    /**
     * 进度条的画笔
     */
    private var mPaint: Paint = Paint()

    /**
     * 进度条动画
     */
    private var mAnimator: Animator? = null

    /**
     * 控件的宽度
     */
    private var mTargetWidth = 0

    /**
     * 标志当前进度条的状态
     */
    private var _tag = 0
    private fun init(context: Context) {
        mPaint.isAntiAlias = true
        mPaint.color = mColor
        mPaint.isDither = true
        mPaint.strokeCap = Paint.Cap.SQUARE
        mTargetWidth = context.resources.displayMetrics.widthPixels
    }

    fun setColor(color: Int) {
        mColor = color
        mPaint.color = color
    }

    fun setColor(color: String?) {
        this.setColor(Color.parseColor(color))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val wMode = MeasureSpec.getMode(widthMeasureSpec)
        var w = MeasureSpec.getSize(widthMeasureSpec)
        val hMode = MeasureSpec.getMode(heightMeasureSpec)
        var h = MeasureSpec.getSize(heightMeasureSpec)
        if (wMode == MeasureSpec.AT_MOST) {
            w = w.coerceAtMost(context.resources.displayMetrics.widthPixels)
        }
        if (hMode == MeasureSpec.AT_MOST) {
            h = WEB_PROGRESS_DEFAULT_HEIGHT
        }
        setMeasuredDimension(w, h)
    }

    private var currentProgress = 0f
    override fun onDraw(canvas: Canvas) {}
    override fun dispatchDraw(canvas: Canvas) {
        canvas.drawRect(
            0f, 0f,
            currentProgress / 100 * this.width.toFloat(),
            this.height.toFloat(),
            mPaint
        )
    }

    override fun show() {
        if (visibility == GONE) {
            this.visibility = VISIBLE
            currentProgress = 0f
            startAnim(false)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mTargetWidth = measuredWidth
        val screenWidth = context.resources.displayMetrics.widthPixels
        if (mTargetWidth >= screenWidth) {
            CURRENT_MAX_DECELERATE_SPEED_DURATION = MAX_DECELERATE_SPEED_DURATION
            CURRENT_MAX_UNIFORM_SPEED_DURATION = MAX_UNIFORM_SPEED_DURATION
        } else {
            //取比值
            val rate = mTargetWidth / screenWidth.toFloat()
            CURRENT_MAX_UNIFORM_SPEED_DURATION = (MAX_UNIFORM_SPEED_DURATION * rate).toInt()
            CURRENT_MAX_DECELERATE_SPEED_DURATION = (MAX_DECELERATE_SPEED_DURATION * rate).toInt()
        }
    }

    fun setProgress(progress: Float) {
        if (visibility == GONE) {
            visibility = VISIBLE
        }
        if (progress < 95f) return
        if (_tag != FINISH) {
            startAnim(true)
        }
    }

    override fun hide() {
        _tag = FINISH
    }

    private var target = 0f
    private fun startAnim(isFinished: Boolean) {
        val v: Float = if (isFinished) 100F else 95.toFloat()
        if (mAnimator?.isStarted == true) mAnimator!!.cancel()
        currentProgress = if (currentProgress == 0f) 0.00000001f else currentProgress
        if (!isFinished) {
            val mAnimator = ValueAnimator.ofFloat(currentProgress, v)
            val residue = 1f - currentProgress / 100 - 0.05f
            mAnimator.interpolator = LinearInterpolator()
            mAnimator.duration = (residue * CURRENT_MAX_UNIFORM_SPEED_DURATION).toLong()
            mAnimator.addUpdateListener(mAnimatorUpdateListener)
            mAnimator.start()
            this.mAnimator = mAnimator
        } else {
            var segment95Animator: ValueAnimator? = null
            if (currentProgress < 95f) {
                segment95Animator = ValueAnimator.ofFloat(currentProgress, 95f)
                val residue = 1f - currentProgress / 100f - 0.05f
                segment95Animator.interpolator = LinearInterpolator()
                segment95Animator.duration =
                    (residue * CURRENT_MAX_DECELERATE_SPEED_DURATION).toLong()
                segment95Animator.interpolator = DecelerateInterpolator()
                segment95Animator.addUpdateListener(mAnimatorUpdateListener)
            }
            val mObjectAnimator = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f)
            mObjectAnimator.duration = DO_END_ANIMATION_DURATION.toLong()
            val mValueAnimatorEnd = ValueAnimator.ofFloat(95f, 100f)
            mValueAnimatorEnd.duration = DO_END_ANIMATION_DURATION.toLong()
            mValueAnimatorEnd.addUpdateListener(mAnimatorUpdateListener)
            var mAnimatorSet = AnimatorSet()
            mAnimatorSet.playTogether(mObjectAnimator, mValueAnimatorEnd)
            if (segment95Animator != null) {
                val mAnimatorSet1 = AnimatorSet()
                mAnimatorSet1.play(mAnimatorSet).after(segment95Animator)
                mAnimatorSet = mAnimatorSet1
            }
            mAnimatorSet.addListener(mAnimatorListenerAdapter)
            mAnimatorSet.start()
            mAnimator = mAnimatorSet
        }
        _tag = STARTED
        target = v
    }

    private val mAnimatorUpdateListener =
        ValueAnimator.AnimatorUpdateListener { animation: ValueAnimator ->
            currentProgress = animation.animatedValue as Float
            this@WebProgressBar.invalidate()
        }
    private val mAnimatorListenerAdapter: AnimatorListenerAdapter =
        object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                doEnd()
            }
        }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        /*
         * animator cause leak , if not cancel;
         */
        if (mAnimator != null && mAnimator!!.isStarted) {
            mAnimator!!.cancel()
            mAnimator = null
        }
    }

    private fun doEnd() {
        if (_tag == FINISH && currentProgress == 100f) {
            visibility = GONE
            currentProgress = 0f
            this.alpha = 1f
        }
        _tag = UN_START
    }

    override fun reset() {
        currentProgress = 0f
        if (mAnimator != null && mAnimator!!.isStarted) {
            mAnimator!!.cancel()
        }
    }

    override fun setProgress(newProgress: Int) {
        setProgress(java.lang.Float.valueOf(newProgress.toFloat()))
    }

    companion object {
        /**
         * 默认匀速动画最大的时长
         */
        const val MAX_UNIFORM_SPEED_DURATION = 5 * 1000

        /**
         * 默认加速后减速动画最大时长
         */
        const val MAX_DECELERATE_SPEED_DURATION = 600

        /**
         * 结束动画时长 ， Fade out 。
         */
        const val DO_END_ANIMATION_DURATION = 300

        /**
         * 当前匀速动画最大的时长
         */
        private var CURRENT_MAX_UNIFORM_SPEED_DURATION = MAX_UNIFORM_SPEED_DURATION

        /**
         * 当前加速后减速动画最大时长
         */
        private var CURRENT_MAX_DECELERATE_SPEED_DURATION = MAX_DECELERATE_SPEED_DURATION
        const val UN_START = 0
        const val STARTED = 1
        const val FINISH = 2

        /**
         * 默认的高度
         */
        var WEB_PROGRESS_DEFAULT_HEIGHT = 2
    }

    init {
        init(context)
    }
}