package com.mingzheng.common.utils

import android.content.Context
import android.os.Handler
import android.widget.Toast
import com.mingzheng.common.BaseApp

class ToastUtils {

    companion object {
        private var mToast: Toast? = null

        /**
         * 显示一个toast提示
         *
         * @param resouceId toast字符串资源id
         */
        fun showToast(resouceId: Int) {
            showToast(BaseApp.getContext()!!.resources.getString(resouceId))
        }

        fun showToast(text: String) {
            showToast(text, Toast.LENGTH_SHORT)

        }

        /**
         * 显示一个toast提示
         *
         * @param text     toast字符串
         * @param duration toast显示时间
         */
        fun showToast(text: String, duration: Int) {
            BaseApp.getContext()?.let { showToast(it, text, duration) }
        }

        /**
         * 显示一个toast提示
         *
         * @param context  context 上下文对象
         * @param text     toast字符串
         * @param duration toast显示时间
         */
        fun showToast(context: Context, text: String, duration: Int) {
            /**
             * 保证运行在主线程
             */
            runOnUIThread(Runnable {
                if (mToast == null) {
                    mToast = Toast.makeText(context, text, duration)
                } else {
                    mToast!!.setText(text)
                    mToast!!.setDuration(duration)
                }
                mToast!!.show()
            })
        }

        fun isRunOnUIThread(): Boolean {
            // 获取当前线程id, 如果当前线程id和主线程id相同, 那么当前就是主线程
            val myTid = android.os.Process.myTid()
            return myTid == getMainThreadId()
        }

        /**
         * 获取主线程id
         *
         * @return 主线程id
         */
        fun getMainThreadId(): Int {
            return BaseApp.getMainThreadId()!!
        }

        /**
         * 运行在主线程
         *
         * @param r 运行的Runnable对象
         */
        fun runOnUIThread(r: Runnable) {
            if (isRunOnUIThread()) {
                // 已经是主线程, 直接运行
                r.run()
            } else {
                // 如果是子线程, 借助handler让其运行在主线程
                getHandler().post(r)
            }
        }

        /**
         * 获取全局handler
         *
         * @return 全局handler
         */
        fun getHandler(): Handler {
            return BaseApp.getHandler()!!
        }

    }
}