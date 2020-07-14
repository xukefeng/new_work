package com.mingzheng.common.utils

import android.util.Log

class LogUtils {
    companion object{
        /**
         * 日志打印开关，开发与调试为true，在正式版发布时置为false
         */
        var showLog = true

        /**
         * 统一的标签
         */
        private val TAG = "myLog"

        /**
         * VERBOSE日志，显示为黑色
         *
         * @param tag     标签，可以为类名，可以为对象名，也可以为自己自定义的标签
         * @param content 日志内容
         */
        fun v(tag: String, content: String) {
            if (showLog)
                Log.v(tag, content)
        }


        /**
         * INFO日志，显示为绿色
         *
         * @param tag     标签，可以为类名，可以为对象名，也可以为自己自定义的标签
         * @param content 日志内容
         */
        fun i(tag: String, content: String) {
            if (showLog)
                Log.i(tag, content)
        }

        /**
         * DEBUG日志，显示为蓝色
         *
         * @param tag     标签，可以为类名，可以为对象名，也可以为自己自定义的标签
         * @param content 日志内容
         */
        fun d(tag: String, content: String) {
            if (showLog)
                Log.d(tag, content)
        }

        /**
         * WARNING日志，显示为橘色
         *
         * @param tag     标签，可以为类名，可以为对象名，也可以为自己自定义的标签
         * @param content 日志内容
         */
        fun w(tag: String, content: String) {
            if (showLog)
                Log.w(tag, content)
        }

        /**
         * ERROR日志，显示为红色
         *
         * @param tag     标签，可以为类名，可以为对象名，也可以为自己自定义的标签
         * @param content 日志内容
         */
        fun e(tag: String, content: String) {
            if (showLog)
                Log.e(tag, content)
        }

        /**
         * VERBOSE日志，显示为黑色
         *
         * @param content 日志内容
         */
        fun v(content: String) {
            v(TAG, content)
        }

        /**
         * INFO日志，显示为绿色
         *
         * @param content 日志内容
         */
        fun i(content: String) {
            i(TAG, content)
        }

        /**
         * DEBUG日志，显示为蓝色
         *
         * @param content 日志内容
         */
        fun d(content: String) {
            d(TAG, content)
        }

        /**
         * WARNING日志，显示为橘色
         *
         * @param content 日志内容
         */
        fun w(content: String) {
            w(TAG, content)
        }

        /**
         * ERROR日志，显示为红色
         *
         * @param content 日志内容
         */
        fun e(content: String) {
            e(TAG, content)
        }

        /**
         * 日志开关
         */
        fun logSwitch(b: Boolean) {
            showLog = b
        }
    }
}