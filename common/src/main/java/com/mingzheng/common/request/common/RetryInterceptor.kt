package com.mingzheng.common.request.common

import okhttp3.Interceptor
import okhttp3.Response

class RetryInterceptor(var maxRetry: Int/*最大重试次数*/) : Interceptor {

    var retryNum = 0//假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        println("retryNum=$retryNum")
        var response = chain.proceed(request)
        while (!response.isSuccessful && retryNum < maxRetry) {
            retryNum++
            println("retryNum=$retryNum")
            response = chain.proceed(request)
        }
        return response

    }

}