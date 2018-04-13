package com.android.ql.lf.carappclient.ui.activities

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.android.ql.lf.carappclient.R
import com.android.ql.lf.carappclient.data.UserInfo
import com.android.ql.lf.carappclient.ui.fragments.bottom.*
import com.android.ql.lf.carappclient.utils.BottomNavigationViewHelper
import com.android.ql.lf.carappclient.utils.toast
import kotlinx.android.synthetic.main.activity_main_layout.*

/**
 * Created by lf on 18.1.23.
 * @author lf on 18.1.23
 */
class MainActivity : BaseActivity() {

    private var exitTime: Long = 0L

    override fun getLayoutId() = R.layout.activity_main_layout

    override fun initView() {
        setSwipeBackEnable(false)
        BottomNavigationViewHelper.disableShiftMode(mMainNavigation)
        mMainNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        mMainContent.adapter = MainViewPagerAdapter(supportFragmentManager)
        mMainContent.offscreenPageLimit = 5
        mMainNavigation.selectedItemId = R.id.navigation_store
        mMainContent.currentItem = 2
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                mMainContent.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_classify -> {
                mMainContent.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_store -> {
                mMainContent.currentItem = 2
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_community -> {
                mMainContent.currentItem = 3
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_mine -> {
                mMainContent.currentItem = 4
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            toast("再按一次退出")
            exitTime = System.currentTimeMillis()
        } else {
            UserInfo.getInstance().loginOut()
            finish()
        }
    }

    inner class MainViewPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment = when (position) {
            0 -> {
                MainMallFragment.newInstance()
            }
            1 -> {
                MainGoodsClassifyFragment.newInstance()
            }
            2 -> {
                MainStoreInfoFragment.newInstance()
            }
            3 -> {
                MainCommunityFragment.newInstance()
            }
            4 -> {
                MainMineFragment.newInstance()
            }
            else -> {
                MainMineFragment.newInstance()
            }
        }

        override fun getCount() = 5
    }

}