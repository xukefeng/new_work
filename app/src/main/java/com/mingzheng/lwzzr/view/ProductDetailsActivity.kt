package com.mingzheng.lwzzr.view

import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.mingzheng.common.utils.ToastUtils
import com.mingzheng.common.view.BaseActivity
import com.mingzheng.common.view.BaseMvpActivity
import com.mingzheng.lwzzr.R
import com.mingzheng.lwzzr.model.bean.ProductDetailsBean
import com.mingzheng.lwzzr.model.details.ProductDetailsContract
import com.mingzheng.lwzzr.presenter.ProductDetailsPresenter
import kotlinx.android.synthetic.main.activity_product_details.*

class ProductDetailsActivity : BaseMvpActivity<ProductDetailsContract.IProductDetailsView,ProductDetailsPresenter>(),ProductDetailsContract.IProductDetailsView {
    override fun createPresenter(): ProductDetailsPresenter {
        return ProductDetailsPresenter()
    }

    override fun requestFails(code: Int, msg: String) {
      var message=msg
    }

    override fun productInfoRequestSuccess(result: String) {
        var bean=result
    }

    var id:String?=null
    override fun getLayoutId(): Int {
        return R.layout.activity_product_details
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        this.id=intent.getStringExtra("id")
       initWebView()
        mPresenter.productInfoRequest(this.id!!)
    }

    fun initWebView(){
        var webSettings =nestedWebView.settings
        webSettings.javaScriptEnabled = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.allowFileAccess = true// 设置允许访问文件数据
        webSettings.setSupportZoom(true)//支持缩放
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        webSettings.domStorageEnabled = true
        webSettings.databaseEnabled = true
        //nestedWebView!!.setOnKeyListener(OnKeyEvent)
        nestedWebView!!.setWebViewClient(WebViewClient())
        //nestedWebView.loadUrl("https://songzhaopian.com/")
        nestedWebView.loadUrl("https://h5.lwzzr.com/#")
    }
}
