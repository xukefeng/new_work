package com.mingzheng.lwzzr.presenter

import com.mingzheng.common.presenter.BasePresenter
import com.mingzheng.lwzzr.model.HttpResult
import com.mingzheng.lwzzr.model.bean.ProductDetailsBean
import com.mingzheng.lwzzr.model.callback.ResultCallback
import com.mingzheng.lwzzr.model.details.ProductDetailsContract
import com.mingzheng.lwzzr.model.details.ProductDetailsModel
import io.reactivex.disposables.Disposable

class ProductDetailsPresenter :
    BasePresenter<ProductDetailsContract.IProductDetailsModel, ProductDetailsContract.IProductDetailsView>(),
    ProductDetailsContract.IProductDetailsPresenter {
    override fun createModel(): ProductDetailsContract.IProductDetailsModel? {
        return ProductDetailsModel()
    }

    override fun init() {

    }

    override fun productInfoRequest(productId: String) {
        mModel!!.productInfoRequest(productId,
            object : ResultCallback<HttpResult<String>> {
                override fun onSuccess(result: HttpResult<String>) {
                 if (isViewAttached())
                 {
                    result.data?.let {  mView!!.productInfoRequestSuccess(it) }
                 }
                }

                override fun onFail(code:Int,msg: String) {
                 if (isViewAttached())
                 {
                     mView!!.requestFails(code,msg)
                 }
                }

                override fun onDisposable(d: Disposable) {

                }

            })
    }

}