package com.mingzheng.common.presenter

import android.os.Bundle
import com.mingzheng.common.model.IBaseModel
import com.mingzheng.common.view.IBaseView

abstract class BasePresenter<M : IBaseModel, V : IBaseView> :IBasePresenter<V> {
    protected val mModel: M? by lazy { createModel() }
    protected var mView: V? = null

    abstract fun createModel(): M?

    protected fun getView(): V? {
        return mView
    }

    private fun attach(view: V) {
        mView = view
    }

    override fun onMvpAttachView(view: V) {
        attach(view)
        init()
    }

    override  fun onMvpStart() {
    }

    override fun onMvpResume() {
    }

    override  fun onMvpPause() {
    }

    override  fun onMvpStop() {
    }

    override  fun onMvpSaveInstanceState(savedInstanceState: Bundle?) {
    }

    override fun onMvpDetachView() {
        detach()
    }

    override fun onMvpDestroy() {
        detach()
    }

    private fun detach() {
        mView?.let {
            mView = null
        }
    }

    override fun isViewAttached(): Boolean {
        return mView != null
    }

}