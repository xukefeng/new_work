package com.mingzheng.common.view.widget

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.mingzheng.common.R

class CustomKeyboard : LinearLayout, View.OnClickListener {

    enum class NumberKeyboardEnum {
        number,
        delete,
        finish
    }

    var securityEditText: SecurityEditText? = null
    var contentView: View? = null
    var number_0: TextView? = null
    var number_1: TextView? = null
    var number_2: TextView? = null
    var number_3: TextView? = null
    var number_4: TextView? = null
    var number_5: TextView? = null
    var number_6: TextView? = null
    var number_7: TextView? = null
    var number_8: TextView? = null
    var number_9: TextView? = null
    var number_finish: TextView? = null
    var number_delete: LinearLayout? = null
    var number_all_delete: TextView? = null

    var size: Int = 0
    var size_0:Int=0
    var size_complate:Int=0
    var size_margins:Int=0

    var arrayList: ArrayList<String>? = ArrayList()
    var onNumberClickListener: OnNumberClickListener? = null

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleRes
    ) {
        var w=context!!.resources.displayMetrics.widthPixels
        this.size = (context!!.resources.displayMetrics.widthPixels-dp2px(60)) / 4
        this.size_0=this.size*3+dp2px(20)
        this.size_complate=this.size*2+dp2px(10)
        this.size_margins=dp2px(5)
        initView()
    }

    private fun initView() {
        this.contentView = LayoutInflater.from(context).inflate(R.layout.custom_key_board, null)
        this.securityEditText = this.contentView!!.findViewById(R.id.editText)
        addView(this.contentView)
        this.number_0 = this.contentView!!.findViewById(R.id.number_0)
        var params_0: LayoutParams = this.number_0!!.layoutParams as LayoutParams
        params_0.width = this.size_0
        params_0.height = this.size
        params_0.setMargins(size_margins,size_margins,size_margins,size_margins)
        this.number_0!!.layoutParams = params_0

        this.number_1 = this.contentView!!.findViewById(R.id.number_1)
        var params_1: LayoutParams = this.number_1!!.layoutParams as LayoutParams
        params_1.width = this.size
        params_1.height = this.size
        params_1.setMargins(size_margins,size_margins,size_margins,size_margins)
        this.number_1!!.layoutParams = params_1

        this.number_2 = this.contentView!!.findViewById(R.id.number_2)
        var params_2: LayoutParams = this.number_2!!.layoutParams as LayoutParams
        params_2.width = this.size
        params_2.height = this.size
        params_2.setMargins(size_margins,size_margins,size_margins,size_margins)
        this.number_2!!.layoutParams = params_2

        this.number_3 = this.contentView!!.findViewById(R.id.number_3)
        var params_3: LayoutParams = this.number_3!!.layoutParams as LayoutParams
        params_3.width = this.size
        params_3.height = this.size
        params_3.setMargins(size_margins,size_margins,size_margins,size_margins)
        this.number_3!!.layoutParams = params_3

        this.number_4 = this.contentView!!.findViewById(R.id.number_4)
        var params_4: LayoutParams = this.number_4!!.layoutParams as LayoutParams
        params_4.width = this.size
        params_4.height = this.size
        params_4.setMargins(size_margins,size_margins,size_margins,size_margins)
        this.number_4!!.layoutParams = params_4

        this.number_5 = this.contentView!!.findViewById(R.id.number_5)
        var params_5: LayoutParams = this.number_5!!.layoutParams as LayoutParams
        params_5.width = this.size
        params_5.height = this.size
        params_5.setMargins(size_margins,size_margins,size_margins,size_margins)
        this.number_5!!.layoutParams = params_5

        this.number_6 = this.contentView!!.findViewById(R.id.number_6)
        var params_6: LayoutParams = this.number_6!!.layoutParams as LayoutParams
        params_6.width = this.size
        params_6.height = this.size
        params_6.setMargins(size_margins,size_margins,size_margins,size_margins)
        this.number_6!!.layoutParams = params_6

        this.number_7 = this.contentView!!.findViewById(R.id.number_7)
        var params_7: LayoutParams = this.number_7!!.layoutParams as LayoutParams
        params_7.width = this.size
        params_7.height = this.size
        params_7.setMargins(size_margins,size_margins,size_margins,size_margins)
        this.number_7!!.layoutParams = params_7

        this.number_8 = this.contentView!!.findViewById(R.id.number_8)
        var params_8: LayoutParams = this.number_8!!.layoutParams as LayoutParams
        params_8.width = this.size
        params_8.height = this.size
        params_8.setMargins(size_margins,size_margins,size_margins,size_margins)
        this.number_8!!.layoutParams = params_8

        this.number_9 = this.contentView!!.findViewById(R.id.number_9)
        var params_9: LayoutParams = this.number_9!!.layoutParams as LayoutParams
        params_9.width = this.size
        params_9.height = this.size
        params_9.setMargins(size_margins,size_margins,size_margins,size_margins)
        this.number_9!!.layoutParams = params_9

        this.number_finish = this.contentView!!.findViewById(R.id.number_finish)
        var params_finish: LayoutParams = this.number_finish!!.layoutParams as LayoutParams
        params_finish.width = this.size
        params_finish.height = this.size_complate
        params_finish.setMargins(size_margins,size_margins,size_margins,size_margins)
        this.number_finish!!.layoutParams = params_finish

        this.number_delete = this.contentView!!.findViewById(R.id.number_delete)
        var params_delete: LayoutParams = this.number_delete!!.layoutParams as LayoutParams
        params_delete.width = this.size
        params_delete.height = this.size
        params_delete.setMargins(size_margins,size_margins,size_margins,size_margins)
        this.number_delete!!.layoutParams = params_delete

        this.number_all_delete = this.contentView!!.findViewById(R.id.number_all_delete)
        var params_all_delete: LayoutParams = this.number_all_delete!!.layoutParams as LayoutParams
        params_all_delete.width = this.size
        params_all_delete.height = this.size
        params_all_delete.setMargins(size_margins,size_margins,size_margins,size_margins)
        this.number_all_delete!!.layoutParams = params_all_delete

        this.number_0!!.setOnClickListener(this)
        this.number_1!!.setOnClickListener(this)
        this.number_2!!.setOnClickListener(this)
        this.number_3!!.setOnClickListener(this)
        this.number_4!!.setOnClickListener(this)
        this.number_5!!.setOnClickListener(this)
        this.number_6!!.setOnClickListener(this)
        this.number_7!!.setOnClickListener(this)
        this.number_8!!.setOnClickListener(this)
        this.number_9!!.setOnClickListener(this)
        this.number_delete!!.setOnClickListener(this)
        this.number_finish!!.setOnClickListener(this)
        this.number_all_delete!!.setOnClickListener(this)


    }

    /**
     * 添加参数
     */

    private fun addValue(value: String) {
        this.arrayList!!.add(value)
        this.arrayList!!.joinToString("").apply {
            securityEditText!!.text = Editable.Factory.getInstance().newEditable(this)
        }
    }

    /**
     * 移除参数
     */
    private fun removeValue() {
        if (this.arrayList!!.size > 0)
            this.arrayList!!.removeAt(this.arrayList!!.size - 1)
        this.arrayList!!.joinToString("").apply {
            securityEditText!!.text = Editable.Factory.getInstance().newEditable(this)
        }
    }

    /*清空*/
    private fun removeAllValue() {
        if (this.arrayList != null && this.arrayList!!.size > 0) {
            this.arrayList!!.clear()
            this.securityEditText!!.text = Editable.Factory.getInstance().newEditable("")
        }
    }

    override fun onClick(v: View?) {
        var id = v!!.id
        when (id) {
            R.id.number_0 -> {
                addValue("0")

            }
            R.id.number_1 -> {
                addValue("1")
            }
            R.id.number_2 -> {
                addValue("2")
            }
            R.id.number_3 -> {
                addValue("3")
            }
            R.id.number_4 -> {
                addValue("4")
            }
            R.id.number_5 -> {
                addValue("5")
            }
            R.id.number_6 -> {
                addValue("6")
            }
            R.id.number_7 -> {
                addValue("7")
            }
            R.id.number_8 -> {
                addValue("8")
            }
            R.id.number_9 -> {
                addValue("9")
            }
            R.id.number_finish -> {
                if (this.onNumberClickListener != null)
                    this.onNumberClickListener!!.onCompleteClick(this.arrayList!!.joinToString(""))
            }
            R.id.number_delete -> {
                removeValue()
            }

            R.id.number_all_delete -> {
                removeAllValue()
            }
        }
    }

    interface OnNumberClickListener {
        fun onCompleteClick(code: String);
    }

    private fun dp2px(dp: Int): Int {
        return (context.resources.displayMetrics.density * dp + 0.5).toInt()
    }
}