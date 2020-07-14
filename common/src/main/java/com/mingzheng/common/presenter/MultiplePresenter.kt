package com.mingzheng.common.presenter

import android.os.Bundle
import com.mingzheng.common.view.IBaseView
import java.util.ArrayList

class MultiplePresenter<V : IBaseView>:IBasePresenter<V>{
    override fun onMvpAttachView(view: V) {

    }

    override fun onMvpStart() {

    }

    override fun onMvpResume() {

    }

    override fun onMvpPause() {

    }

    override fun onMvpStop() {

    }

    override fun onMvpSaveInstanceState(savedInstanceState: Bundle?) {

    }

    override fun onMvpDetachView() {

    }

    override fun onMvpDestroy() {

    }

    override fun init() {

    }

    override fun isViewAttached(): Boolean {
        return true
    }

    var v: V? = null
    private val presenters = ArrayList<IBasePresenter<V>>()

    constructor(v: V) {
        this.v = v
    }

    fun <K:IBasePresenter<V>> addPresenter(vararg addPresenter: K) {
        val var3 = addPresenter.size

        for (var4 in 0 until var3) {
            val ap = addPresenter[var4]
            ap.onMvpAttachView(this.v!!)
            this.presenters.add(ap)
        }

    }

    fun detachView() {
        val var1 = this.presenters.iterator()

        while (var1.hasNext()) {
            var1.next().onMvpDetachView()
        }

        this.v = null
    }
}