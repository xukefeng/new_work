package com.mingzheng.lwzzr.model.bean

import java.io.Serializable

class HomeBean:Serializable {
    val advertiseList: ArrayList<Advertise>?=null
    val brandList: ArrayList<Brand>?=null
    val homeFlashPromotion: HomeFlashPromotion?=null
    val hotProductList: ArrayList<HotProduct>?=null
    val newProductList: ArrayList<NewProduct>?=null
    val productList: ArrayList<Product>?=null
    val subjectList: ArrayList<Any>?=null


data class Advertise(
    val clickCount: Int,
    val endTime: String,
    val id: Int,
    val name: String,
    val note: String,
    val orderCount: Int,
    val pic: String,
    val sort: Int,
    val startTime: String,
    val status: Int,
    val type: Int,
    val url: String
)

data class Brand(
    val bigPic: String,
    val factoryStatus: Int,
    val firstLetter: String,
    val id: Int,
    val logo: String,
    val name: String,
    val productCommentCount: Int,
    val productCount: Int,
    val showStatus: Int,
    val sort: Int
)

class HomeFlashPromotion(
)

data class HotProduct(
    val albumPics: String,
    val brandId: Int,
    val brandName: String,
    val deleteStatus: Int,
    val description: String,
    val detailTitle: String,
    val feightTemplateId: Int,
    val giftGrowth: Int,
    val giftPoint: Int,
    val id: Int,
    val keywords: String,
    val lowStock: Int,
    val name: String,
    val newStatus: Int,
    val note: String,
    val originalPrice: Int,
    val pic: String,
    val previewStatus: Int,
    val price: Int,
    val productAttributeCategoryId: Int,
    val productCategoryId: Int,
    val productCategoryName: String,
    val productSn: String,
    val promotionPerLimit: Int,
    val promotionType: Int,
    val publishStatus: Int,
    val recommandStatus: Int,
    val sale: Int,
    val serviceIds: String,
    val sort: Int,
    val stock: Int,
    val subTitle: String,
    val unit: String,
    val usePointLimit: Int,
    val verifyStatus: Int,
    val weight: Int
)

data class NewProduct(
    val albumPics: String,
    val brandId: Int,
    val brandName: String,
    val deleteStatus: Int,
    val description: String,
    val detailTitle: String,
    val feightTemplateId: Int,
    val giftGrowth: Int,
    val giftPoint: Int,
    val id: Int,
    val keywords: String,
    val lowStock: Int,
    val name: String,
    val newStatus: Int,
    val note: String,
    val originalPrice: Int,
    val pic: String,
    val previewStatus: Int,
    val price: Int,
    val productAttributeCategoryId: Int,
    val productCategoryId: Int,
    val productCategoryName: String,
    val productSn: String,
    val promotionPerLimit: Int,
    val promotionType: Int,
    val publishStatus: Int,
    val recommandStatus: Int,
    val sale: Int,
    val serviceIds: String,
    val sort: Int,
    val stock: Int,
    val subTitle: String,
    val unit: String,
    val usePointLimit: Int,
    val verifyStatus: Int,
    val weight: Int
)

data class Product(
    val albumPics: String,
    val brandId: Int,
    val brandName: String,
    val channelId: Int,
    val detailTitle: String,
    val id: Int,
    val name: String,
    val originalPrice: Double,
    val pic: String,
    val price: Double,
    val productCategoryId: Int,
    val productCategoryName: String,
    val productSn: String,
    val sellType: Int,
    val subTitle: String,
    val unit: String
)
}