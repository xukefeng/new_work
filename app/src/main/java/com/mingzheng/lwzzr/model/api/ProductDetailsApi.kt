package com.mingzheng.lwzzr.model.api

import com.mingzheng.lwzzr.model.HttpResult
import com.mingzheng.lwzzr.model.bean.ProductDetailsBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductDetailsApi {
    @GET("product/detail/{productId}")
    fun getProductInfo(@Path("productId") productId: String): Observable<HttpResult<String>>
}