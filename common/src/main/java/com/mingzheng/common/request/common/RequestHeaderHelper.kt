package com.mingzheng.common.request.common

import okhttp3.Request

class RequestHeaderHelper {
companion object{
    fun getCommonHeaders(request: Request?, headMap: Map<String, String>?): Request.Builder? {
        if (request == null || headMap == null)
            return null
        val builder = request.newBuilder()
        for (key in headMap.keys) {
            headMap[key]?.let { builder.addHeader(key, it) }
        }
        builder.method(request.method, request.body)
        return builder
    }

    fun getCommonHeaders(
        request: Request?,
        headMap: Map<String, String>?,
        contentType: String
    ): Request.Builder? {
        if (request == null || headMap == null)
            return null
        val builder = request.newBuilder()
        for (key in headMap.keys) {
            headMap[key]?.let { builder.addHeader(key, it) }
        }
        builder.addHeader("Content-Type", contentType)
        builder.method(request.method, request.body)
        return builder
    }
}
}