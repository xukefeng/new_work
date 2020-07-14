package com.mingzheng.common.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.mingzheng.common.BaseApp

class AppInfoUtils {
    companion object{
        /**
         * 获取sdk版本号
         */
        fun getSdkInt(): Int {

            return Build.VERSION.SDK_INT
        }

        /**
         * 获取包名
         */
        fun getVersionName(): String {
            var versionName: String? = ""
            try {
                // ---get the package info---
                val pm = BaseApp.getContext()!!.getPackageManager()
                val pi = pm.getPackageInfo(BaseApp.getContext()!!.packageName, 0)
                versionName = pi.versionName
                if (versionName!!.isEmpty()) {
                    return ""
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return versionName!!
        }

        /**
         * 获取应用版本号
         */
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
        fun getVersionCode(): String {
            var versioncode = -1
            try {
                // ---get the package info---
                val pm = BaseApp.getContext()!!.packageManager
                val pi = pm.getPackageInfo(BaseApp.getContext()!!.packageName, 0)
                versioncode = pi.versionCode
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return versioncode.toString() + ""
        }

        /**
         * 获取手机型号
         */
        fun getDeviceName(): String {
            var devName = Build.MODEL
            devName = devName.trim { it <= ' ' }
            return devName
        }

        /**
         * 获取系统语言
         */
        fun getLanguage(): String {
            val locale =BaseApp.getContext()!!.resources.configuration.locale
            return locale.getLanguage() + "-" + locale.getCountry()
        }
    }
}