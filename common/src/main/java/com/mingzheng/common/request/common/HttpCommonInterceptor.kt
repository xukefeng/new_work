package com.mingzheng.common.request.common

import com.mingzheng.common.utils.LogUtils
import com.mingzheng.common.utils.sp.DataSerializable
import okhttp3.*
import org.json.JSONObject

class HttpCommonInterceptor :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var mediaType: MediaType? = null
        var content = ""
        var map = HashMap<String, String>()
        map["Bearer"] = "2088612911077196"
        map["Accept"]="application/json"
        var newBuilder: Request.Builder =
            RequestHeaderHelper.getCommonHeaders(chain.request(), map,"application/json")!!
        var newRequest = newBuilder.build()
        var response: Response = chain.proceed(newRequest)
        if (response != null) {
            val responseBody = response.body
            if (responseBody != null) {
                content = responseBody.string()
                mediaType = response.body!!.contentType()
                LogUtils.e(TAG, newRequest.url.toString() + "=============请求成功==============")
                try {
                    val jsonObject = JSONObject(content)
                    LogUtils.e(
                        TAG,
                        ">>>>>>>>>>>>>>>>" + jsonObject.toString() + ">>>>>>>>>>>>>>>>>>>>>>>"
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            } else {
                LogUtils.e(
                    TAG,
                    newRequest.url.toString() + "===============================responseBody为空"
                )
            }
        } else {
            LogUtils.e(
                TAG,
                newRequest.url.toString() + "===============================response为空"
            )
        }


        return response.newBuilder()
            .body(ResponseBody.create(mediaType, content))
            .build()
    }

    private val TAG = "request"

}