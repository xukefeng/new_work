package com.mingzheng.common.utils.sp

import java.io.IOException
import java.io.Serializable

class DataSerializable<T : Serializable> : DataImpl<T>() {

    companion object {
        val LOGIN_INFO = "login_info"

    }

    @Synchronized
    fun setData(name: String, data: T) = try {
        putString(name, serialize(data))
    } catch (e: IOException) {
        e.printStackTrace()
    }

    @Synchronized
    fun getData(name: String): T {
        return try {
            deSerialization(getString(name)!!)
        } catch (e: Exception) {
            null!!
        }

    }
}