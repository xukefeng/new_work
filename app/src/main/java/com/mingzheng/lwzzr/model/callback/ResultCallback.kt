package com.mingzheng.lwzzr.model.callback

import io.reactivex.disposables.Disposable

interface ResultCallback<T>{
     fun onSuccess(result: T)

     fun onFail(code:Int,msg: String)

     fun onDisposable(d: Disposable)
}