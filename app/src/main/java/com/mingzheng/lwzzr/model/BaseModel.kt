package com.mingzheng.lwzzr.model

import com.mingzheng.lwzzr.model.callback.ResultCallback
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class BaseModel {
    /**
     * 发起订阅
     *
     * @param observable
     * @param callback
     * @param <T>
    </T> */
    protected fun <T : HttpResult<*>> subscribe(
        observable: Observable<T>,
        callback: ResultCallback<T>
    ) {
        observable.subscribeOn(Schedulers.io())//发送事件在子线程
            .unsubscribeOn(Schedulers.io())//解除订阅子线程
            .observeOn(AndroidSchedulers.mainThread())//接收事件主线程（获取到网络的数据进行UI的更新显示故在主线程）
            .subscribe(object : Observer<T> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                    callback.onDisposable(d)
                }

                override fun onNext(t: T) {
                    if (t.code == 200) {
                        callback.onSuccess(t)
                    } else {
                        t.code?.let { t.message?.let { it1 -> callback.onFail(it, it1) } }
                    }
                }

                override fun onError(e: Throwable) {
                    callback.onFail(e.hashCode(), e.message.toString())
                }

            })
    }
}