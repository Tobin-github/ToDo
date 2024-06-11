package top.tobin.common.utils

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("DiscouragedApi")
object ResourceUtil {
    fun getLayoutId(context: Context, paramString: String): Int {
        return context.resources.getIdentifier(paramString, "layout", context.packageName)
    }

    fun getStringId(context: Context, paramString: String): Int {
        return context.resources.getIdentifier(paramString, "string", context.packageName)
    }

    fun getDrawableId(context: Context, paramString: String): Int {
        return context.resources.getIdentifier(paramString, "drawable", context.packageName)
    }

    fun getStyleId(context: Context, paramString: String): Int {
        return context.resources.getIdentifier(paramString, "style", context.packageName)
    }

    fun getId(context: Context, paramString: String): Int {
        return context.resources.getIdentifier(paramString, "id", context.packageName)
    }

    fun getColorId(context: Context, paramString: String): Int {
        return context.resources.getIdentifier(paramString, "color", context.packageName)
    }

    fun getDimenId(context: Context, paramString: String): Int {
        return context.resources.getIdentifier(paramString, "dimen", context.packageName)
    }

    fun getAnimId(context: Context, paramString: String): Int {
        return context.resources.getIdentifier(paramString, "anim", context.packageName)
    }

    // 通过反射实现
    fun getStyleableIntArray(context: Context, name: String): IntArray? {
        try {
            val field =
                Class.forName(context.packageName + ".R\$styleable")
                    .getDeclaredField(name)
            return field[null] as IntArray
        } catch (t: Throwable) {
            t.printStackTrace()
        }
        return null
    }

    fun getStyleableIntArrayIndex(context: Context, name: String): Int {
        try {
            // use reflection to access the resource class
            val field = Class.forName(context.packageName + ".R\$styleable").getDeclaredField(name)
            return field[null] as Int
        } catch (t: Throwable) {
            t.printStackTrace()
        }
        return 0
    }
}