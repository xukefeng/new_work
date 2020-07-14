package com.mingzheng.common.utils

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.mingzheng.common.R

class ActionBar {
    companion object{

        fun setLeftImg(activity:Activity,imgResId:Int,onClick:View.OnClickListener){
            var imageView=activity.findViewById<ImageView>(R.id.ivLeft)
            var linearLayout=activity.findViewById<LinearLayout>(R.id.llLeft)
            linearLayout.visibility=View.VISIBLE
            imageView.setImageResource(imgResId)
            imageView.setOnClickListener(onClick)
        }

        fun setTitle(activity: Activity,txtResId:Int){
            var textView=activity.findViewById<TextView>(R.id.tvTitle)
            textView.text=activity.getString(txtResId)
        }

        fun setTitle(activity: Activity,text:String)
        {
            var textView=activity.findViewById<TextView>(R.id.tvTitle)
            textView.text=text
        }

        fun setRightText(activity: Activity,txtResId:Int,onClick: View.OnClickListener){
            var textView=activity.findViewById<TextView>(R.id.tvRight)
            textView.visibility=View.VISIBLE
            textView.text=activity.getString(txtResId)
            textView.setOnClickListener(onClick)
        }

        fun setRightText(activity: Activity,text:String,onClick: View.OnClickListener){
            var textView=activity.findViewById<TextView>(R.id.tvRight)
            textView.visibility=View.VISIBLE
            textView.text=text
            textView.setOnClickListener(onClick)
        }



        fun setLeftImg(view:View,imgResId:Int,onClick:View.OnClickListener){
            var imageView=view.findViewById<ImageView>(R.id.ivLeft)
            var linearLayout=view.findViewById<LinearLayout>(R.id.llLeft)
            linearLayout.visibility=View.VISIBLE
            imageView.setImageResource(imgResId)
            imageView.setOnClickListener(onClick)
        }

        fun setTitle(view: View,txtResId:Int){
            var textView=view.findViewById<TextView>(R.id.tvTitle)
            textView.text=view.context.getString(txtResId)
        }

        fun setTitle(view: View,text:String)
        {
            var textView=view.findViewById<TextView>(R.id.tvTitle)
            textView.text=text
        }

        fun setRightText(view: View,txtResId:Int,onClick: View.OnClickListener){
            var textView=view.findViewById<TextView>(R.id.tvRight)
            textView.visibility=View.VISIBLE
            textView.text=view.context.getString(txtResId)
            textView.setOnClickListener(onClick)
        }

        fun setRightText(view: View,text:String,onClick: View.OnClickListener){
            var textView=view.findViewById<TextView>(R.id.tvRight)
            textView.visibility=View.VISIBLE
            textView.text=text
            textView.setOnClickListener(onClick)
        }
    }
}