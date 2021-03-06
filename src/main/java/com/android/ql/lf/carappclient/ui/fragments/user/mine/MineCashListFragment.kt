package com.android.ql.lf.carappclient.ui.fragments.user.mine

import android.graphics.Color
import android.widget.TextView
import com.android.ql.lf.carappclient.R
import com.android.ql.lf.carappclient.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carappclient.utils.RequestParamsHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.2.3.
 * @author lf on 18.2.3
 */
class MineCashListFragment : BaseRecyclerViewFragment<MineCashListFragment.CashListBean>() {

    override fun createAdapter(): BaseQuickAdapter<CashListBean, BaseViewHolder> = CashListAdapter(R.layout.adapter_mine_cash_list_item_layout, mArrayList)

    override fun onRefresh() {
        super.onRefresh()
        mPresent.getDataByPost(0x0, RequestParamsHelper.MEMBER_MODEL, RequestParamsHelper.ACT_MY_WITHDRAW_RECORD, RequestParamsHelper.getMyWithdrawRecordParam(currentPage))
    }

    override fun onLoadMore() {
        super.onLoadMore()
        mPresent.getDataByPost(0x0, RequestParamsHelper.MEMBER_MODEL, RequestParamsHelper.ACT_MY_WITHDRAW_RECORD, RequestParamsHelper.getMyWithdrawRecordParam(currentPage))
    }

    override fun getEmptyMessage() = "暂无提现记录"

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        processList(result as String, CashListBean::class.java)
    }

    class CashListAdapter(resId: Int, list: ArrayList<CashListBean>) : BaseQuickAdapter<CashListBean, BaseViewHolder>(resId, list) {
        override fun convert(helper: BaseViewHolder?, item: CashListBean?) {
            val tv_time = helper!!.getView<TextView>(R.id.mTvCashItemTime)
            val tv_count = helper.getView<TextView>(R.id.mTvCashItemCount)
            val tv_result = helper.getView<TextView>(R.id.mTvCashItemResult)
            tv_time.text = item!!.merchant_withdraw_record_time
            tv_count.text = "￥${item.merchant_withdraw_record_price}"
            tv_result.text = when (item.merchant_withdraw_record_status) {
                "0" -> {
                    tv_time.setTextColor(mContext.resources.getColor(R.color.text_deep_dark_color))
                    tv_count.setTextColor(mContext.resources.getColor(R.color.text_deep_dark_color))
                    tv_result.setTextColor(mContext.resources.getColor(R.color.colorPrimary))
                    "处理中"
                }
                "1" -> {
                    tv_time.setTextColor(mContext.resources.getColor(R.color.text_dark_color))
                    tv_count.setTextColor(mContext.resources.getColor(R.color.text_dark_color))
                    tv_result.setTextColor(mContext.resources.getColor(R.color.text_dark_color))
                    "已完成"
                }
                "2" -> {
                    tv_time.setTextColor(mContext.resources.getColor(R.color.text_dark_color))
                    tv_count.setTextColor(mContext.resources.getColor(R.color.text_dark_color))
                    tv_result.setTextColor(Color.RED)
                    "已驳回"
                }
                else->{
                    "其它"
                }
            }
        }
    }

    class CashListBean {
        var merchant_withdraw_record_id: String? = null
        var merchant_withdraw_record_uid: String? = null
        var merchant_withdraw_record_price: String? = null
        var merchant_withdraw_record_status: String? = null
        var merchant_withdraw_record_time: String? = null
    }
}