package com.mingzheng.lwzzr.view

import android.database.DataSetObserver
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.mingzheng.common.view.BaseActivity
import com.mingzheng.common.view.BaseFragment
import com.mingzheng.lwzzr.R
import com.mingzheng.lwzzr.model.bean.LoginInfoBean
import com.mingzheng.common.utils.sp.DataSerializable
import com.mingzheng.lwzzr.view.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : BaseActivity() {
    private val fragments = ArrayList<BaseFragment>()
    var homeFragment: BaseFragment? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        this.homeFragment = HomeFragment.newInstance()
        var loginInfoBean=LoginInfoBean()
        loginInfoBean.tokenHead="Bearer"
        loginInfoBean.token="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdW5ueSIsImNyZWF0ZWQiOjE1OTQ0NDM5NTI3NTIsImV4cCI6MTU5NTA0ODc1Mn0.pOwCFZivh5nfyKHZz3mEdD23dJLXYf1MZEiRjt8yxVlhXTC3X9iwwfenm52w9gc_K8qfH-p7pt-qIXfkfqClcA"
        var dataSerializable= DataSerializable<LoginInfoBean>()
        dataSerializable.setData(DataSerializable.LOGIN_INFO,loginInfoBean)


        fragments.add(this.homeFragment as HomeFragment)

        tabPager.setAdapter(object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getCount(): Int {
                return fragments.size
            }

            override fun unregisterDataSetObserver(observer: DataSetObserver) {
                if (observer != null) {
                    super.unregisterDataSetObserver(observer)
                }

            }
        })

        tabPager.setCurrentItem(0)
    }
}
