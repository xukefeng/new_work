package com.mingzheng.common.presenter

import android.os.Bundle
import com.mingzheng.common.view.IBaseView

interface IBasePresenter<V:IBaseView> {

    fun onMvpAttachView(view: V)

    fun onMvpStart()

    fun onMvpResume()

    fun onMvpPause()

    fun onMvpStop()

    fun onMvpSaveInstanceState(savedInstanceState: Bundle?)

    fun onMvpDetachView()

    fun onMvpDestroy()

    fun init()

    fun isViewAttached():Boolean
}