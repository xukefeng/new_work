package com.mingzheng.common.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper

open class BaseDelegateAdapter(
    mContext: Context,
    mCount: Int,
    layoutHelper: LayoutHelper,
    layoutId: Int
) : DelegateAdapter.Adapter<BaseViewHolder>() {
    var mContext: Context? = null
    var mCount: Int = -1
    var layoutHelper: LayoutHelper? = null
    var layoutId: Int = -1

    init {
        this.mContext = mContext
        this.mCount = mCount
        this.layoutHelper = layoutHelper
        this.layoutId = layoutId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(LayoutInflater.from(mContext).inflate(layoutId, parent, false))
    }

    override fun getItemCount(): Int {
        return mCount
    }

    override fun onCreateLayoutHelper(): LayoutHelper? {
        return this.layoutHelper
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {

    }
}