package com.mingzheng.common.loader

import com.mingzheng.common.request.RetrofitServiceManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
/**
 * @name BaseLoader
 * @description 封装公共loader进行线程切换
 * @version 1.0.0
 */
abstract class BaseLoader<T> {
    protected val mServiceManager: RetrofitServiceManager by lazy { createServiceManager() }
    protected val mService: T by lazy { createService() }

    /**
     * 创建 ServiceManager 实例
     */
    abstract fun createServiceManager(): RetrofitServiceManager

    /**
     * 创建 Service 实例
     */
    abstract fun createService(): T

    /**
     * 设置Observable的工作线程
     * @param observable
     * @param <T>
     * @return
    </T> */
    fun <T> observe(observable: Observable<T>): Observable<T> {
        return observable.subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }



}