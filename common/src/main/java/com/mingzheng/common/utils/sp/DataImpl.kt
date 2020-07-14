package com.mingzheng.common.utils.sp

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

abstract class DataImpl<T> : SPManager(), DataSerializeInterface<T> {
    override fun serialize(`object`: T): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
        objectOutputStream.writeObject(`object`)
        var serStr = byteArrayOutputStream.toString("ISO-8859-1")
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8")
        objectOutputStream.close()
        byteArrayOutputStream.close()
        return serStr
    }

    override fun deSerialization(str: String): T {
        if (str == null) return null!!

        val redStr = java.net.URLDecoder.decode(str, "UTF-8")
        val byteArrayInputStream = ByteArrayInputStream(
            redStr.toByteArray(charset("ISO-8859-1"))
        )
        val objectInputStream = ObjectInputStream(
            byteArrayInputStream
        )
        val obj = objectInputStream.readObject() as T
        objectInputStream.close()
        byteArrayInputStream.close()

        return obj
    }
}