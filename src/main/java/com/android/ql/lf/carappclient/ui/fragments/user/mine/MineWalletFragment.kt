package com.android.ql.lf.carappclient.ui.fragments.user.mine

import android.content.Context
import android.text.TextUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.android.ql.lf.carappclient.R
import com.android.ql.lf.carappclient.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carappclient.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carappclient.utils.RequestParamsHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import org.json.JSONObject

/**
 * Created by lf on 18.2.3.
 * @author lf on 18.2.3
 */
class MineWalletFragment : BaseRecyclerViewFragment<MineWalletFragment.HistoryWalletBean>() {


    private var mTvReleaseMoney: TextView? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    override fun createAdapter(): BaseQuickAdapter<HistoryWalletBean, BaseViewHolder> =
            MineWalletListAdapter(R.layout.adapter_mine_wallet_history_item_layout, mArrayList)

    override fun initView(view: View?) {
        super.initView(view)
        val topView = View.inflate(mContext, R.layout.layout_top_mine_wallet_layout, null)
        mTvReleaseMoney = topView.findViewById(R.id.mTvTopMineWalletMoney)
        mTvReleaseMoney!!.text = "0.0"
        topView.findViewById<TextView>(R.id.mTvMineWalletEnsureMoney).setOnClickListener {
            FragmentContainerActivity.from(mContext).setClazz(MineAccountMoneyFragment::class.java).setTitle("充值").setNeedNetWorking(true).start()
        }
        topView.findViewById<TextView>(R.id.mTvMineWalletMoneyAccount).setOnClickListener {
            FragmentContainerActivity.from(mContext).setTitle("收款账号绑定").setClazz(MineWalletAccountFragment::class.java).start()
        }
        mBaseAdapter.addHeaderView(topView)
        mBaseAdapter.setHeaderAndEmpty(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.wallet_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.mMenuWallet) {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "提现", MineCashFragment::class.java)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRefresh() {
        super.onRefresh()
        mPresent.getDataByPost(0x0, RequestParamsHelper.MEMBER_MODEL, RequestParamsHelper.ACT_INTEGRAL, RequestParamsHelper.getIntegralParam(currentPage))
    }

    override fun onLoadMore() {
        super.onLoadMore()
        mPresent.getDataByPost(0x0, RequestParamsHelper.MEMBER_MODEL, RequestParamsHelper.ACT_INTEGRAL, RequestParamsHelper.getIntegralParam(currentPage))
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        val check = checkResultCode(result)
        if (check != null && check.code == SUCCESS_CODE) {
            mTvReleaseMoney!!.text = (check.obj as JSONObject).optString("arr") ?: "0.0"
        }
        processList(result as String, HistoryWalletBean::class.java)
    }

    override fun onRequestFail(requestID: Int, e: Throwable) {
        super.onRequestFail(requestID, e)
        mTvReleaseMoney!!.text = "0.0"
    }

    override fun getEmptyMessage() = "暂无信息"

    class MineWalletListAdapter(resId: Int, list: ArrayList<HistoryWalletBean>) : BaseQuickAdapter<HistoryWalletBean, BaseViewHolder>(resId, list) {
        override fun convert(helper: BaseViewHolder?, item: HistoryWalletBean?) {
            helper!!.setText(R.id.mTvWalletHistoryItemTitle, "${item!!.merchant_integral_title}: ${if (TextUtils.isEmpty(item.merchant_integral_sn)) {
                "暂无订单号"
            } else {
                item.merchant_integral_sn
            }}")
            helper.setText(R.id.mTvWalletHistoryItemTime, item.merchant_integral_time)
            helper.setText(R.id.mTvWalletHistoryItemCount, "￥${item.merchant_integral_price}")
        }
    }

    class HistoryWalletBean {
        var merchant_integral_id: String? = null
        var merchant_integral_title: String? = null
        var merchant_integral_price: String? = null
        var merchant_integral_sn: String? = null
        var merchant_integral_sym: String? = null
        var merchant_integral_time: String? = null
        var merchant_integral_uid: String? = null
    }

}