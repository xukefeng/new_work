package com.mingzheng.lwzzr.model.home

import com.mingzheng.common.model.IBaseModel
import com.mingzheng.common.presenter.BasePresenter
import com.mingzheng.common.view.IBaseView
import com.mingzheng.lwzzr.model.HttpResult
import com.mingzheng.lwzzr.model.bean.HomeBean
import com.mingzheng.lwzzr.model.callback.ResultCallback

interface HomeContract {
    interface IHomeView : IBaseView {
        fun requestSuccess(result: HomeBean)

        fun onFail(msg:String)
    }

    abstract class HomePresenter:BasePresenter<IHomeModel,IHomeView>(){
       abstract fun  request(channelId: String)
    }

    interface IHomeModel:IBaseModel{
    fun request(channelId: String,callback: ResultCallback<HttpResult<HomeBean>>);
    }
}