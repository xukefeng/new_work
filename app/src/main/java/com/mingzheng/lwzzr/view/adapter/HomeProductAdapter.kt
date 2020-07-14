package com.mingzheng.lwzzr.view.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.alibaba.android.vlayout.LayoutHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mingzheng.common.utils.DensityUtils
import com.mingzheng.common.utils.ToastUtils
import com.mingzheng.common.view.BaseDelegateAdapter
import com.mingzheng.common.view.BaseViewHolder
import com.mingzheng.lwzzr.R
import com.mingzheng.lwzzr.model.bean.HomeBean
import com.mingzheng.lwzzr.view.ProductDetailsActivity
import kotlinx.android.synthetic.main.layout_home_content.view.*

class HomeProductAdapter(
    mContext: Context,
    mCount: Int,
    layoutHelper: LayoutHelper,
    layoutId: Int, data: ArrayList<HomeBean.Product>
) : BaseDelegateAdapter(mContext, mCount, layoutHelper, layoutId) {
    var size: Int = 120
    var data: ArrayList<HomeBean.Product>? = null

    init {
        this.data = data
        size = DensityUtils.instance.getScreenWidth() / 2
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        var ivPic = holder.getView<ImageView>(R.id.ivPic)
        var params = ivPic.layoutParams
        params.height = size
        params.width = size
        ivPic.layoutParams = params
        var tvProductTitle = holder.getView<TextView>(R.id.tvProductTitle)
        var tvProductDes = holder.getView<TextView>(R.id.tvProductDes)
        var tvProductPrice = holder.getView<TextView>(R.id.tvProductPrice)
        var tvProductUnit = holder.getView<TextView>(R.id.tvProductUnit)
        var llItem = holder.getView<CardView>(R.id.cardViewItem)
        llItem.setOnClickListener {
            //ToastUtils.showToast(data!![position].name)
            val intent = Intent(mContext, ProductDetailsActivity::class.java)
            intent.putExtra("id", data!![position].id.toString())
            mContext!!.startActivity(intent)
        }
        Glide.with(ivPic.context)
            .load(data!![position].pic)
            .apply(
                RequestOptions()
//                    .transform(CenterCrop(), RoundedCorners(12))
                    .placeholder(mContext!!.resources.getDrawable(R.color.color_f5f5f5))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
            )
            .into(ivPic)

        tvProductTitle.text = data!![position].name
        tvProductDes.text = data!![position].subTitle
        tvProductPrice.text = "¥" + data!![position].price
        tvProductUnit.text = "/" + data!![position].unit
    }
}