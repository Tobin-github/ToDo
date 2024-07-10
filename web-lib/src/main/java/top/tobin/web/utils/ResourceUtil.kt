package top.tobin.web.utils

import android.content.Context

/**
 * Created by Tobin on 2022/6/22.
 * Email: junbin.li@qq.com
 * Description: ResourceUtil.
 */
class ResourceUtil private constructor() {
    private var mContext: Context? = null

    companion object {
        val instance: ResourceUtil by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            ResourceUtil()
        }
    }

    fun init(context: Context) {
        mContext = context.applicationContext
    }

    fun getStringId(paramString: String): Int {
        return mContext?.resources?.getIdentifier(
            paramString, "string", mContext?.packageName
        ) ?: 0
    }

    fun getLayoutId(paramString: String): Int {
        return mContext?.resources?.getIdentifier(
            paramString, "layout", mContext?.packageName
        ) ?: 0
    }

    fun getDrawableId(paramString: String?): Int {
        return mContext?.resources?.getIdentifier(
            paramString, "drawable", mContext?.packageName
        ) ?: 0
    }

    fun getStyleId(paramString: String?): Int {
        return mContext?.resources?.getIdentifier(
            paramString, "style", mContext?.packageName
        ) ?: 0
    }

    fun getColorId(paramString: String?): Int {
        return mContext?.resources?.getIdentifier(
            paramString, "color", mContext?.packageName
        ) ?: 0
    }

    fun getDimenId(paramString: String?): Int {
        return mContext?.resources?.getIdentifier(
            paramString, "dimen", mContext?.packageName
        ) ?: 0
    }

    fun getAnimId(paramString: String?): Int {
        return mContext?.resources?.getIdentifier(
            paramString, "anim", mContext?.packageName
        ) ?: 0
    }

    fun getId(paramString: String?): Int {
        return mContext?.resources?.getIdentifier(
            paramString, "id", mContext?.packageName
        ) ?: 0
    }


}