package com.mingzheng.common.view
import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var views:SparseArray<View>?=null
    init {
        this.views= SparseArray()
    }

     fun <T:View> getView(viewId:Int):T{
     var view=views!!.get(viewId)
        if (view==null)
        {
            view=this.itemView.findViewById(viewId)
            views!!.put(viewId,view)
        }
        return view as T
    }
}