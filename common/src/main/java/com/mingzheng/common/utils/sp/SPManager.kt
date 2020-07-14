package com.mingzheng.common.utils.sp

import android.content.Context
import android.content.SharedPreferences
import com.mingzheng.common.BaseApp

abstract class SPManager {
    companion object {
        var CURRENT_PREFERENCE: String = "lwzzr_preference"
    }

    var sharedPreferences: SharedPreferences? = null
    var context: Context? = null

    constructor(){
        this.context = BaseApp.getContext()
        if (this.context != null) {
            sharedPreferences =
                this.context!!.getSharedPreferences(CURRENT_PREFERENCE, Context.MODE_PRIVATE)
        } else {
            throw NullPointerException("SPManager=========== context为空 你的Application必须继承自BaseApplication")
        }
    }

    protected fun putString(key: String, value: String) {
        sharedPreferences!!.edit().putString(key, value).commit()
    }

    protected fun getString(key: String): String? {
        return sharedPreferences!!.getString(key, "")
    }

    protected fun remove(key: String): Boolean {
        return sharedPreferences!!.edit().remove(key).commit()
    }

    protected fun putInt(key: String, value: Int) {
        sharedPreferences!!.edit().putInt(key, value).commit()
    }

    protected fun getInt(key: String): Int {
        return sharedPreferences!!.getInt(key, 0)
    }

    protected fun putBoolean(key: String, value: Boolean) {
        sharedPreferences!!.edit().putBoolean(key, value).commit()
    }

    protected fun getBoolean(key: String): Boolean {
        return sharedPreferences!!.getBoolean(key, false)
    }
}