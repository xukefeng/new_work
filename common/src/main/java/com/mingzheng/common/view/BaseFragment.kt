package com.mingzheng.common.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mingzheng.common.utils.LogUtils
import com.mingzheng.common.utils.ToastUtils

abstract class BaseFragment:Fragment() {
    protected var mActivityContext:Context?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mActivityContext=context;
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutId(), null)
        initView(view,savedInstanceState)
        initListener(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData(savedInstanceState)
    }

   protected abstract fun getLayoutId():Int


    protected open fun initView(contentView: View,savedInstanceState: Bundle?){}

    protected open fun initListener(contentView: View){}

    protected open fun initData(savedInstanceState: Bundle?){}


    /**
     * 在主线程弹出Toast 提示
     * @param msg 需要弹出的提示信息
     */
    protected open fun showToast(msg: String) {
      ToastUtils.showToast(msg)
    }

    /**
     * 在主线程弹出Toast 提示
     * @param stringRes 需要弹出的提示信息的string资源ID
     */
    protected open fun showToast(stringRes: Int) {
       ToastUtils.showToast(stringRes)
    }


}