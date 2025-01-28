package top.tobin.common.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.CheckedTextView
import android.widget.GridLayout
import java.util.LinkedList

/**
 * Created by Tobin on 2022/10/8.
 * Email: junbin.li@qq.com
 */
class WeekGridLayout : GridLayout {
    constructor(context: Context?) : super(context) {
        initView(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        initView(context)
    }

    private val data = LinkedList<String>()

    private fun initView(context: Context?) {
        data.add("周日")
        data.add("周一")
        data.add("周二")
        data.add("周三")
        data.add("周四")
        data.add("周五")
        data.add("周六")

        for (text in data) {
            val tv = CheckedTextView(context)
//            tv.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
            tv.text = text
//            LayoutParams params = new LayoutParams();
//            tv.setLayoutParams(params);
            addView(tv)
        }
    }
}