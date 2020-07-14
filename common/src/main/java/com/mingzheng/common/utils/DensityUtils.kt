package com.mingzheng.common.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Point
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.*
import com.mingzheng.common.BaseApp

class DensityUtils {
   var displayMetrics: DisplayMetrics?=null
    private var sHasCamera: Boolean? = null

    private var sPortraitRealSizeCache: IntArray? = null
    private var sLandscapeRealSizeCache: IntArray? = null
    companion object{
        private val VIVO_NAVIGATION_GESTURE = "navigation_gesture_on"
        private val HUAWAI_DISPLAY_NOTCH_STATUS = "display_notch_status"
        private val XIAOMI_DISPLAY_NOTCH_STATUS = "force_black"
        private val XIAOMI_FULLSCREEN_GESTURE = "force_fsg_nav_bar"
     val instance:DensityUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
         DensityUtils()
     }
    }

    constructor(){
        displayMetrics=BaseApp.getContext()!!.resources.displayMetrics;
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    fun getScreenWidth(): Int {
        return displayMetrics!!.widthPixels
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    fun getScreenHeight(): Int {
        var screenHeight = displayMetrics!!.heightPixels
        if (ScreenDeviceUtils.instance.isXiaomi() && xiaomiNavigationGestureEnabled(BaseApp.getContext()!!)) {
            screenHeight += getResourceNavHeight(BaseApp.getContext()!!)
        }
        return screenHeight
    }

    /**
     * 单位转换: dp -> px
     *
     * @param dp
     * @return
     */
    fun dp2px(context: Context, dp: Int): Int {
        return (getDensity(context) * dp + 0.5).toInt()
    }

    /**
     * 单位转换: sp -> px
     *
     * @param sp
     * @return
     */
    fun sp2px(context: Context, sp: Int): Int {
        return (getFontDensity(context) * sp + 0.5).toInt()
    }

    /**
     * 单位转换:px -> dp
     *
     * @param px
     * @return
     */
    fun px2dp(context: Context, px: Int): Int {
        return (px / getDensity(context) + 0.5).toInt()
    }

    /**
     * 单位转换:px -> sp
     *
     * @param px
     * @return
     */
    fun px2sp(context: Context, px: Int): Int {
        return (px / getFontDensity(context) + 0.5).toInt()
    }

    fun getDensity(context: Context): Float {
        return context.resources.displayMetrics.density
    }

    fun getFontDensity(context: Context): Float {
        return context.resources.displayMetrics.scaledDensity
    }

    /**
     * 判断是否有状态栏
     *
     * @param context
     * @return
     */
    fun hasStatusBar(context: Context): Boolean {
        if (context is Activity) {
            val attrs = context.window.attributes
            return attrs.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN != WindowManager.LayoutParams.FLAG_FULLSCREEN
        }
        return true
    }

    @SuppressLint("NewApi")
    fun xiaomiNavigationGestureEnabled(context: Context): Boolean {
        val `val` = Settings.Global.getInt(context.contentResolver, XIAOMI_FULLSCREEN_GESTURE, 0)
        return `val` != 0
    }


    /**
     * 获取ActionBar高度
     *
     * @param context
     * @return
     */
    fun getActionBarHeight(context: Context): Int {
        var actionBarHeight = 0
        val tv = TypedValue()
        if (context.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(
                tv.data,
                context.resources.displayMetrics
            )
        }
        return actionBarHeight
    }

    private fun getResourceNavHeight(context: Context): Int {
        // 小米4没有nav bar, 而 navigation_bar_height 有值
        val resourceId =
            context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            context.resources.getDimensionPixelSize(resourceId)
        } else -1
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    fun getStatusBarHeight(context: Context): Int {
        if (ScreenDeviceUtils.instance.isXiaomi()) {
            val resourceId =
                context.resources.getIdentifier("status_bar_height", "dimen", "android")
            return if (resourceId > 0) {
                context.resources.getDimensionPixelSize(resourceId)
            } else 0
        }
        try {
            val c = Class.forName("com.android.internal.R\$dimen")
            val obj = c.newInstance()
            val field = c.getField("status_bar_height")
            val x = Integer.parseInt(field.get(obj).toString())
            if (x > 0) {
                return context.resources.getDimensionPixelSize(x)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return 0
    }

    /**
     * 获取虚拟菜单的高度,若无则返回0
     *
     * @param context
     * @return
     */
    fun getNavMenuHeight(context: Context): Int {
        if (!isNavMenuExist(context)) {
            return 0
        }
        val resourceNavHeight = getResourceNavHeight(context)
        return if (resourceNavHeight >= 0) {
            resourceNavHeight
        } else getRealScreenSize(context)[1] - getScreenHeight()

        // 小米 MIX 有nav bar, 而 getRealScreenSize(context)[1] - getScreenHeight(context) = 0
    }

    fun isNavMenuExist(context: Context): Boolean {
        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        val hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey()
        val hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)

        return !hasMenuKey && !hasBackKey
    }

    fun getRealScreenSize(context: Context): IntArray {
        if (ScreenDeviceUtils.instance.isEssentialPhone() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Essential Phone 8.0版本后，Display size 会根据挖孔屏的设置而得到不同的结果，不能信任 cache
            return doGetRealScreenSize(context)
        }
        val orientation = context.resources.configuration.orientation
        var result: IntArray?
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            result = sLandscapeRealSizeCache
            if (result == null) {
                result = doGetRealScreenSize(context)
                if (result!![0] > result[1]) {
                    // the result may be wrong sometimes, do not cache !!!!
                    sLandscapeRealSizeCache = result
                }
            }
            return result
        } else {
            result = sPortraitRealSizeCache
            if (result == null) {
                result = doGetRealScreenSize(context)
                if (result!![0] < result[1]) {
                    // the result may be wrong sometimes, do not cache !!!!
                    sPortraitRealSizeCache = result
                }
            }
            return result
        }
    }
    private fun doGetRealScreenSize(context: Context): IntArray {
        val size = IntArray(2)
        var widthPixels: Int
        var heightPixels: Int
        val w = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val d = w.defaultDisplay
        val metrics = DisplayMetrics()
        d.getMetrics(metrics)
        // since SDK_INT = 1;
        widthPixels = metrics.widthPixels
        heightPixels = metrics.heightPixels
        try {
            // used when 17 > SDK_INT >= 14; includes window decorations (statusbar bar/menu bar)
            widthPixels = Display::class.java.getMethod("getRawWidth").invoke(d) as Int
            heightPixels = Display::class.java.getMethod("getRawHeight").invoke(d) as Int
        } catch (ignored: Exception) {
        }

        if (Build.VERSION.SDK_INT >= 17) {
            try {
                // used when SDK_INT >= 17; includes window decorations (statusbar bar/menu bar)
                val realSize = Point()
                d.getRealSize(realSize)


                Display::class.java.getMethod("getRealSize", Point::class.java).invoke(d, realSize)
                widthPixels = realSize.x
                heightPixels = realSize.y
            } catch (ignored: Exception) {
            }

        }

        size[0] = widthPixels
        size[1] = heightPixels
        return size
    }

}