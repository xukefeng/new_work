package com.mingzheng.common.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        //initView(savedInstanceState)
        initListener()
        initData(savedInstanceState)

    }

    /**
     * 获取布局ID,子类必须实现
     */
    protected abstract fun getLayoutId():Int

    /**
     * 初始化 View 的相关操作，若有需要可在子类实现
     */

    //protected abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 初始化 Listener 事件的相关操作，若有需要可在子类实现
     */

    protected open fun initListener(){};

    /**
     * 初始化 Data 数据的相关操作，若有需要可在子类实现
     */
    protected open fun initData(savedInstanceState: Bundle?){}

    /**
     * 在主线程弹出Toast 提示
     * @param msg 需要弹出的提示信息
     */

   protected open fun showToast(msg:String){
   runOnUiThread{
     toast(msg)
    }
    }

    /**
     * 在主线程弹出Toast 提示
     * @param stringRes 需要弹出的提示信息的string资源ID
     */

    protected open fun showToast(stringResId:Int){
        runOnUiThread{
        toast(getString(stringResId))
        }
    }

    private fun toast(msg: String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }
}

