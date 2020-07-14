package com.mingzheng.lwzzr.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.alibaba.android.vlayout.layout.GridLayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.mingzheng.common.model.IBaseModel
import com.mingzheng.common.presenter.MultiplePresenter
import com.mingzheng.common.utils.ActionBar
import com.mingzheng.common.view.*
import com.mingzheng.lwzzr.R
import com.mingzheng.lwzzr.model.home.HomeContract
import com.mingzheng.lwzzr.model.bean.HomeBean
import com.mingzheng.lwzzr.presenter.ProductListPresenter
import com.mingzheng.lwzzr.view.adapter.BannerImageAdapter
import com.mingzheng.lwzzr.view.adapter.HomeProductAdapter
import com.youth.banner.Banner
import com.youth.banner.config.IndicatorConfig
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseListMVPFragment<HomeContract.IHomeView,ProductListPresenter>(),
    HomeContract.IHomeView {
    var productListPresenter:ProductListPresenter?=null
    override fun requestFails(code: Int, msg: String) {

    }
   companion object{
       fun newInstance(): HomeFragment {

           val args = Bundle()

           val fragment = HomeFragment()
           fragment.arguments = args
           return fragment
       }
   }


    override fun createPresenter():ProductListPresenter {
//        productListPresenter= ProductListPresenter()
//        var multiplePresent=MultiplePresenter<IBaseView>(this)
//         multiplePresent.addPresenter(productListPresenter)
        return ProductListPresenter()
    }
//
//    override fun getLayoutId(): Int {
//        return R.layout.fragment_home
//    }

    override fun initView(contentView: View,savedInstanceState: Bundle?) {
        super.initView(contentView,savedInstanceState)
        setTitle("首頁")
//        ActionBar.setLeftImg(contentView, R.mipmap.ic_back, View.OnClickListener { })
//        ActionBar.setTitle(contentView, R.string.app_name)
        mPresenter!!.request("1")
    }

    override fun requestSuccess(result: HomeBean) {
        /*添加banner*/
        var bannerAdapter =
            object : BaseDelegateAdapter(
                mActivityContext!!, 1, LinearLayoutHelper(),
                R.layout.layout_banner
            ) {
                override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
                    var banner = holder.getView<Banner<*, *>>(R.id.banner)
                    banner.adapter = mContext?.let {
                        result.brandList?.let { it1 ->
                            BannerImageAdapter(
                                it,
                                it1
                            )
                        }
                    }
                    banner.isAutoLoop(true)
                    banner.setDelayTime(3000)
                    banner.setIndicatorGravity(IndicatorConfig.Direction.CENTER)
                    banner.start()
                    banner.setOnBannerListener { data, position ->
                        run {
                            Toast.makeText(
                                mContext,
                                result.brandList!![position]?.name,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
            }

        var desAdapter = BaseDelegateAdapter(mActivityContext!!, 1, LinearLayoutHelper(), R.layout.layout_des)

        var gridLayoutHelper = GridLayoutHelper(2);
        gridLayoutHelper.hGap = 20//设置水平间距
        gridLayoutHelper.vGap = 15//设置垂直间距
        gridLayoutHelper.setPadding(20, 0, 20, 10)//设置左右的padding为20
        gridLayoutHelper.setAutoExpand(false)//必须要设置为false否则奇数行item会出现问题

        var contentAdapter = HomeProductAdapter(
            mActivityContext!!,
            result.productList!!.size,
            gridLayoutHelper,
            R.layout.layout_home_content,
            result.productList
        )
        addAdapter(bannerAdapter)
        addAdapter(desAdapter)
        addAdapter(contentAdapter)
        notifyAdapter()
    }

    override fun onFail(msg: String) {

    }
}