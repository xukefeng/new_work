package com.mingzheng.common.utils

import android.graphics.Bitmap
import android.graphics.Matrix

class ImageUtils {
    companion object{
        fun setImgSize(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
            // 获得图片的宽高.
            val width = bitmap.width
            val height = bitmap.height
            // 计算缩放比例.
            val scaleWidth = newWidth.toFloat() / width
            val scaleHeight = newHeight.toFloat() / height
            // 取得想要缩放的matrix参数.
            val matrix = Matrix()
            matrix.postScale(scaleWidth, scaleHeight)
            // 得到新的图片.
            return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
        }
    }
}