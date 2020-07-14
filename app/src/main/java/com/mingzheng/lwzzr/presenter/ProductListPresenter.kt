package com.mingzheng.lwzzr.presenter

import android.util.Log
import com.mingzheng.lwzzr.model.HttpResult
import com.mingzheng.lwzzr.model.home.HomeContract
import com.mingzheng.lwzzr.model.bean.HomeBean
import com.mingzheng.lwzzr.model.callback.ResultCallback
import com.mingzheng.lwzzr.model.home.ProductListModel
import io.reactivex.disposables.Disposable

class ProductListPresenter :HomeContract.HomePresenter() {
    override fun createModel(): HomeContract.IHomeModel? {
        return ProductListModel()
    }

    override fun init() {

    }

    override fun request(channelId: String) {
        mModel!!.request(channelId,object :ResultCallback<HttpResult<HomeBean>>{
            override fun onSuccess(result: HttpResult<HomeBean>) {
                Log.e("xkff","========================")
                if (isViewAttached())
                {
                    result.data?.let { mView!!.requestSuccess(it) }
                }
            }

            override fun onFail(code:Int,msg: String) {
                mView!!.requestFails(code,msg)
            }

            override fun onDisposable(d: Disposable) {

            }
        })
    }
}