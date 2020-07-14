package com.mingzheng.lwzzr.model.details

import com.mingzheng.common.model.IBaseModel
import com.mingzheng.common.view.IBaseView
import com.mingzheng.lwzzr.model.HttpResult
import com.mingzheng.lwzzr.model.bean.ProductDetailsBean
import com.mingzheng.lwzzr.model.callback.ResultCallback

interface ProductDetailsContract {
    interface IProductDetailsView : IBaseView {
        fun productInfoRequestSuccess(result: String)
    }

    interface IProductDetailsModel : IBaseModel {
        fun productInfoRequest(
            productId: String,
            callback: ResultCallback<HttpResult<String>>
        )
    }

    interface IProductDetailsPresenter{
        fun productInfoRequest(productId: String)
    }
}