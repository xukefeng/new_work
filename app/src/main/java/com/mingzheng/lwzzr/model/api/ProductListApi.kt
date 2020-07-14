package com.mingzheng.lwzzr.model.api

import com.mingzheng.lwzzr.model.HttpResult
import com.mingzheng.lwzzr.model.bean.HomeBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductListApi {

@GET("home/content")
 fun testRequest(@Query("channelId") channelId:String): Observable<HttpResult<HomeBean>>
}