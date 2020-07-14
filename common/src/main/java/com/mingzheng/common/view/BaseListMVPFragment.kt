package com.mingzheng.common.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.mingzheng.common.R
import com.mingzheng.common.presenter.IBasePresenter
import com.mingzheng.common.utils.ActionBar
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.activity_base_list_mvp.*

abstract class BaseListMVPFragment<V : IBaseView, P : IBasePresenter<V>>:BaseMvpFragment<V,P>() {
    var virtualLayoutManager: VirtualLayoutManager? = null
    var delegateAdapter: DelegateAdapter? = null
    var refreshLayout: SmartRefreshLayout?=null
    var recyclerView:RecyclerView?=null
    var action_bar:View?=null
    var contentView:View?=null

    override fun initView(contentView: View, savedInstanceState: Bundle?) {
        super.initView(contentView, savedInstanceState)
        this.contentView=contentView
        recyclerView=contentView.findViewById(R.id.recyclerView)
        action_bar=contentView.findViewById(R.id.action_bar)
        refreshLayout=smartRefreshLayout
        virtualLayoutManager= VirtualLayoutManager(mActivityContext!!)
        delegateAdapter= DelegateAdapter(virtualLayoutManager,true)
        recyclerView!!.layoutManager=virtualLayoutManager!!
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_base_list_mvp
    }

    fun addAdapter(adapter:BaseDelegateAdapter){
        if (this.delegateAdapter!=null)
        {
            this.delegateAdapter!!.addAdapter(adapter)
        }
    }

    fun notifyAdapter(){
        recyclerView!!.adapter=this.delegateAdapter
    }

    fun setTitle(title:String){
        ActionBar.setTitle(this.contentView!!,title)
    }

    fun setBack(resId:Int){
        ActionBar.setLeftImg(this.contentView!!,resId, View.OnClickListener {
            (mActivityContext as BaseActivity).finish()
        })
    }

    fun setBack(resId:Int,onClickListener: View.OnClickListener){
        ActionBar.setLeftImg(this.contentView!!,resId,onClickListener)
    }
}