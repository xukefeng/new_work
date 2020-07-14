package com.mingzheng.lwzzr.model.home

import com.mingzheng.lwzzr.model.AppRetrofitManager
import com.mingzheng.lwzzr.model.BaseModel
import com.mingzheng.lwzzr.model.HttpResult
import com.mingzheng.lwzzr.model.api.ProductListApi
import com.mingzheng.lwzzr.model.bean.HomeBean
import com.mingzheng.lwzzr.model.callback.ResultCallback

class ProductListModel:BaseModel(), HomeContract.IHomeModel {

    override fun request(
        channelId: String,
        callback: ResultCallback<HttpResult<HomeBean>>
    ) {
        subscribe(AppRetrofitManager().create(ProductListApi::class.java).testRequest(channelId), callback)
    }
}