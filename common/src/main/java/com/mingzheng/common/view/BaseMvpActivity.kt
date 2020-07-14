package com.mingzheng.common.view
import android.os.Bundle
import com.mingzheng.common.presenter.IBasePresenter

abstract class BaseMvpActivity<V : IBaseView, P : IBasePresenter<V>> : BaseRxActivity(), IBaseView {

    protected val mPresenter by lazy { createPresenter() }

    protected abstract fun createPresenter(): P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (mPresenter == null) {
            throw  NullPointerException("Presenter is null! Do you return null in createPresenter()?")
        }
        mPresenter.onMvpAttachView(this as V)
    }

    override fun onStart() {
        super.onStart()
        mPresenter.let {
            it.onMvpStart()
        }
    }

    override fun onResume() {
        super.onResume()
        mPresenter.let {
            it.onMvpResume()
        }
    }

    override fun onPause() {
        super.onPause()
        mPresenter.let {
            it.onMvpPause()
        }
    }

    override fun onStop() {
        super.onStop()
        mPresenter.let {
            it.onMvpStop()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mPresenter.let {
            it.onMvpSaveInstanceState(outState)
        }
    }

    override fun onDestroy() {
//        mLoadingDialog?.let {
//            it.destroy()
//            mLoadingDialog = null
//        }
        mPresenter.let {
            it.onMvpDetachView()
            it.onMvpDestroy()
        }
        super.onDestroy()

    }
}