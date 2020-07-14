package com.mingzheng.lwzzr.model.details

import com.mingzheng.lwzzr.model.AppRetrofitManager
import com.mingzheng.lwzzr.model.BaseModel
import com.mingzheng.lwzzr.model.HttpResult
import com.mingzheng.lwzzr.model.api.ProductDetailsApi
import com.mingzheng.lwzzr.model.api.ProductListApi
import com.mingzheng.lwzzr.model.bean.ProductDetailsBean
import com.mingzheng.lwzzr.model.callback.ResultCallback

class ProductDetailsModel :BaseModel(),ProductDetailsContract.IProductDetailsModel {
    override fun productInfoRequest(
        productId: String,
        callback: ResultCallback<HttpResult<String>>
    ) {
        subscribe(AppRetrofitManager().create(ProductDetailsApi::class.java).getProductInfo(productId), callback)
    }
}