package com.mingzheng.common.view

import android.os.Bundle
import android.view.View
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.mingzheng.common.R
import com.mingzheng.common.presenter.IBasePresenter
import com.mingzheng.common.utils.ActionBar
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.activity_base_list_mvp.*

abstract class BaseListMVPActivity<V : IBaseView, P : IBasePresenter<V>> :BaseMvpActivity<V,P>() {
    var virtualLayoutManager: VirtualLayoutManager? = null
    var delegateAdapter: DelegateAdapter? = null
    var refreshLayout:SmartRefreshLayout?=null
    var action_bar: View?=null
    var pageSize:Int=20
    var pageNum:Int=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.action_bar=action_bar
        refreshLayout=smartRefreshLayout
        virtualLayoutManager= VirtualLayoutManager(this)
        delegateAdapter= DelegateAdapter(virtualLayoutManager,true)
        recyclerView.layoutManager=virtualLayoutManager

        this.refreshLayout!!.setOnRefreshLoadMoreListener(object :OnRefreshLoadMoreListener{
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                //上拉加载
                loadMoreData()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                //下拉刷新
                loadData()
            }
        })
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
        recyclerView.adapter=this.delegateAdapter
    }

    fun setTitle(title:String){
        ActionBar.setTitle(this,title)
    }

    fun setBack(resId:Int){
        ActionBar.setLeftImg(this,resId, View.OnClickListener {
           finish()
        })
    }

    fun setBack(resId:Int,onClickListener: View.OnClickListener){
        ActionBar.setLeftImg(this,resId,onClickListener)
    }

    fun loadData(){

    }

    fun loadMoreData(){

    }

}