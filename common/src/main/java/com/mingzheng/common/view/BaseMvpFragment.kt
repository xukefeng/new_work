package com.mingzheng.common.view

import android.os.Bundle
import android.view.View
import com.mingzheng.common.presenter.IBasePresenter

abstract class BaseMvpFragment<V : IBaseView, P : IBasePresenter<V>>:BaseRxFragment(), IBaseView {

    protected var mPresenter: P? = null
    protected abstract fun createPresenter(): P

    override fun initView(contentView: View,savedInstanceState: Bundle?) {
        super.initView(contentView,savedInstanceState)
        this.mPresenter = this.createPresenter()
        if (this.mPresenter != null) {
            this.mPresenter!!.onMvpAttachView(this as V)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mPresenter!!.onMvpSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        mPresenter!!.onMvpDetachView()
        mPresenter!!.onMvpDestroy()
        super.onDestroyView()

    }
}