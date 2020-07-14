package com.mingzheng.lwzzr.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mingzheng.common.view.BaseViewHolder
import com.mingzheng.lwzzr.R
import com.mingzheng.lwzzr.model.bean.HomeBean
import com.youth.banner.adapter.BannerAdapter

class BannerImageAdapter(mContext: Context, datas:ArrayList<HomeBean.Brand>): BannerAdapter<HomeBean.Brand, BaseViewHolder>(datas) {
    var mContext:Context?=null
    init {
        this.mContext=mContext
    }
    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
     return BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_banner,parent,false))
    }

    override fun onBindView(holder: BaseViewHolder?, data: HomeBean.Brand?, position: Int, size: Int) {
        var itemView=holder!!.getView<ImageView>(R.id.img_banner)
        mContext?.let {
            Glide.with(it)
                .load(data!!.bigPic)
                .apply(RequestOptions()
//                    .transform(CenterCrop(),RoundedCorners(12))
                    .placeholder(mContext!!.resources.getDrawable(R.color.color_f5f5f5))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(itemView)
        }
    }
}