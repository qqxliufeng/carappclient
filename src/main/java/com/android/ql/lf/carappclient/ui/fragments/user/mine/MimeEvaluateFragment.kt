package com.android.ql.lf.carappclient.ui.fragments.user.mine

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.View
import com.android.ql.lf.carappclient.R
import com.android.ql.lf.carappclient.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carappclient.ui.fragments.BaseFragment
import com.android.ql.lf.carappclient.ui.views.SliderLayout
import kotlinx.android.synthetic.main.fragment_mine_evaluate_layout.*
import org.jetbrains.anko.bundleOf

/**
 * Created by liufeng on 2018/2/25.
 */
class MimeEvaluateFragment : BaseFragment() {

    private val titles by lazy {
        arrayListOf("所有", "好评", "中评", "差评")
    }

    override fun getLayoutId() = R.layout.fragment_mine_evaluate_layout

    override fun initView(view: View?) {
        mVpMineEvaluateContainer.adapter = MineEvaluateViewPagerAdapter(childFragmentManager)
        mTlMineEvaluate.setupWithViewPager(mVpMineEvaluateContainer)
        mVpMineEvaluateContainer.addOnPageChangeListener(SliderLayout.SliderOnPageChangeListener(mTlMineEvaluate, mSlMineEvaluate))
        mVpMineEvaluateContainer.offscreenPageLimit = 4
    }

    inner class MineEvaluateViewPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
        override fun getItem(position: Int) = when (position) {
            0 -> {
                MineEvaluateItemFragment.newInstance(bundleOf(Pair(MineEvaluateItemFragment.COMMENT_FLAG, "")))
            }
            1 -> {
                MineEvaluateItemFragment.newInstance(bundleOf(Pair(MineEvaluateItemFragment.COMMENT_FLAG, "3")))
            }
            2 -> {
                MineEvaluateItemFragment.newInstance(bundleOf(Pair(MineEvaluateItemFragment.COMMENT_FLAG, "2")))
            }
            3 -> {
                MineEvaluateItemFragment.newInstance(bundleOf(Pair(MineEvaluateItemFragment.COMMENT_FLAG, "1")))
            }
            else -> {
                MineEvaluateItemFragment.newInstance(bundleOf(Pair(MineEvaluateItemFragment.COMMENT_FLAG, "")))
            }
        }

        override fun getCount() = titles.size
        override fun getPageTitle(position: Int) = titles[position]
    }
}