package com.mingzheng.common.utils.sp

import java.io.IOException

interface DataSerializeInterface<T> {
    /**
     * 序列化对象
     *
     * @param object
     * @return
     */
    @Throws(IOException::class)
    abstract fun serialize(`object`: T): String

    /**
     * 反序列化
     *
     * @param str
     * @return
     */
    @Throws(IOException::class, ClassNotFoundException::class)
    abstract fun deSerialization(str: String): T
}