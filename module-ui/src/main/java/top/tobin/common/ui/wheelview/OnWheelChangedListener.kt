package top.tobin.common.ui.wheelview

/**
 * Created by Tobin on 2022/9/27.
 * Email: junbin.li@qq.com
 * Description: .
 */
interface OnWheelChangedListener {
    fun onChanged(view: WheelView, oldIndex: Int, newIndex: Int)
}
