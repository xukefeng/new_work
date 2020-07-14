package com.mingzheng.common.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.CheckResult
import com.trello.rxlifecycle3.LifecycleProvider
import com.trello.rxlifecycle3.LifecycleTransformer
import com.trello.rxlifecycle3.RxLifecycle
import com.trello.rxlifecycle3.android.FragmentEvent
import com.trello.rxlifecycle3.android.RxLifecycleAndroid
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

abstract class BaseRxFragment:BaseFragment(), LifecycleProvider<FragmentEvent> {
    private val lifecycleSubject: BehaviorSubject<FragmentEvent> = BehaviorSubject.create()

    override fun lifecycle(): Observable<FragmentEvent> {
        return lifecycleSubject.hide()
    }

    @CheckResult
    override fun <T> bindUntilEvent(event: FragmentEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
    }

    @CheckResult
    override fun <T> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject)
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleSubject.onNext(FragmentEvent.CREATE)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW)
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    @CallSuper
    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(FragmentEvent.START)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(FragmentEvent.RESUME)
    }

    @CallSuper
    override fun onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE)
        super.onPause()
    }

    @CallSuper
    override fun onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP)
        super.onStop()
    }

    override fun onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW)
        super.onDestroyView()
    }

    @CallSuper
    override fun onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY)
        super.onDestroy()
    }
}