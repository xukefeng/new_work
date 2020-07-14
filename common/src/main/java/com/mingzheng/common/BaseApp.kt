package com.mingzheng.common

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Process
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.mingzheng.common.utils.LogUtils

abstract class BaseApp: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        mContext =applicationContext
        mainThreadId= Process.myTid();
        handler= Handler(Looper.getMainLooper())
        if (BuildConfig.DEBUG) {
            LogUtils.logSwitch(true)
//            ARouter.openLog()
//            ARouter.openDebug()
        } else {
            LogUtils.logSwitch(false)
        }
      initData()
    }

    abstract fun initData()

    companion object{
        private var mContext:Context?=null
        private var mainThreadId:Int?=null
        private var handler: Handler? = null
        fun getContext(): Context? {
            return mContext
        }

        fun getMainThreadId():Int?{
            return mainThreadId
        }

        fun getHandler():Handler?{
            return handler
        }
    }
}