package com.android.ql.lf.carappclient.ui.fragments.mall.order

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.content.ContextCompat
import android.text.Html
import android.text.TextUtils
import android.view.View
import com.android.ql.lf.carapp.data.RefreshData
import com.android.ql.lf.carappclient.R
import com.android.ql.lf.carappclient.data.MallOrderInfoBean
import com.android.ql.lf.carappclient.data.PayResult
import com.android.ql.lf.carappclient.present.MallOrderPresent
import com.android.ql.lf.carappclient.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carappclient.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carappclient.ui.fragments.mall.normal.RefundFragment
import com.android.ql.lf.carappclient.ui.fragments.user.mine.MainMallOrderItemFragment.Companion.REFRESH_ORDER_FLAG
import com.android.ql.lf.carappclient.ui.views.SelectPayTypeView
import com.android.ql.lf.carappclient.utils.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_order_info_layout.*
import org.jetbrains.anko.bundleOf
import org.json.JSONObject

/**
 * Created by lf on 18.3.29.
 * @author lf on 18.3.29
 */
class OrderInfoFragment : BaseNetWorkingFragment() {

    companion object {
        val OID_FLAG = "oid_flag"
    }

    private var mallOrderInfoContainer: MallOrderInfoBean? = null

    private var payType: String = SelectPayTypeView.WX_PAY

    private val handle by lazy {
        @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                when (msg!!.what) {
                    PayManager.SDK_PAY_FLAG -> {
                        val payResult = PayResult(msg.obj as Map<String, String>)
                        val resultInfo = payResult.result// 同步返回需要验证的信息
                        val resultStatus = payResult.resultStatus
                        val bundle = Bundle()
                        if (TextUtils.equals(resultStatus, "9000")) {
                            //支付成功
                            bundle.putInt(OrderPayResultFragment.PAY_CODE_FLAG, OrderPayResultFragment.PAY_SUCCESS_CODE)
                            MallOrderPresent.notifyRefreshOrderList()
                        } else {
                            //支付失败
                            bundle.putInt(OrderPayResultFragment.PAY_CODE_FLAG, OrderPayResultFragment.PAY_FAIL_CODE)
                        }
                        FragmentContainerActivity
                                .from(mContext)
                                .setTitle("支付结果")
                                .setNeedNetWorking(true)
                                .setExtraBundle(bundle)
                                .setClazz(OrderPayResultFragment::class.java)
                                .start()
                    }
                }
            }
        }
    }

    private val orderSubscription by lazy {
        RxBus.getDefault().toObservable(RefreshData::class.java).subscribe {
            if (it.isRefresh && it.any == REFRESH_ORDER_FLAG) {
                if (mallOrderInfoContainer!!.merchant_order_token == MallOrderPresent.MallOrderStatus.MALL_ORDER_APPLY_BACK.index) {
                    finish()
                }
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_order_info_layout

    override fun initView(view: View?) {
        orderSubscription
        mPresent.getDataByPost(0x0,
                RequestParamsHelper.MEMBER_MODEL,
                RequestParamsHelper.ACT_ORDER_DETAIL,
                RequestParamsHelper.getMallOrderDetailParam(arguments.getString(OID_FLAG)))
    }

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        when (requestID) {
            0x1 -> getFastProgressDialog("正在取消订单……")
            0x2 -> getFastProgressDialog("正在支付……")
            0x4 -> getFastProgressDialog("正在收货……")
        }
    }

    override fun onRequestFail(requestID: Int, e: Throwable) {
        super.onRequestFail(requestID, e)
        when (requestID) {
            0x0 -> toast("加载详情失败……")
            0x1 -> toast("订单取消失败，请重试……")
            0x2 -> toast("订单支付失败，请重试……")
            0x4 -> toast("收货失败，请重试……")
        }
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        val check = checkResultCode(result)
        if (check != null) {
            when (requestID) {
                0x0 -> {
                    if (check.code == SUCCESS_CODE) {
                        mallOrderInfoContainer = Gson().fromJson((check.obj as JSONObject).optJSONObject("result").toString(), MallOrderInfoBean::class.java)
                        bindData()
                    }
                }
                0x1 -> {
                    if (check.code == SUCCESS_CODE) {
                        toast("订单取消成功")
                        MallOrderPresent.onOrderCancel()
                        finish()
                    } else {
                        toast((check.obj as JSONObject).optString(MSG_FLAG))
                    }
                }
                0x2 -> {
                    if (check.code == SUCCESS_CODE) {
                        MallOrderPresent.onOrderPaySuccess(mContext, (check.obj as JSONObject), payType, mallOrderInfoContainer!!.merchant_order_id, handle)
                        finish()
                    } else {
                        toast((check.obj as JSONObject).optString(MSG_FLAG))
                    }
                }
                0x4 -> {
                    if (check.code == SUCCESS_CODE) {
                        toast("收货成功")
                        MallOrderPresent.onOrderReceiver()
                        finish()
                    }
                    toast((check.obj as JSONObject).optString(MSG_FLAG))
                }
            }
        }
    }

    private fun bindData() {
        if (mallOrderInfoContainer != null && !TextUtils.isEmpty(mallOrderInfoContainer!!.merchant_order_token)) {
            mTvOrderInfoTopState.text = MallOrderPresent.MallOrderStatus.getDescriptionByIndex(mallOrderInfoContainer!!.merchant_order_token)
            mTvOrderInfoTopState1.text = mTvOrderInfoTopState.text
            mTvOrderInfoPhone.text = "手机号码  ${mallOrderInfoContainer!!.merchant_address_phone}"
            mTvOrderInfoAddress.text = "收货地址  ${mallOrderInfoContainer!!.merchant_address_addres}"

            GlideManager.loadImage(mContext, mallOrderInfoContainer!!.merchant_product_pic[0], mIvOrderListItemPic)
            mTvOrderListItemTitle.text = mallOrderInfoContainer!!.merchant_product_name
            mTvOrderListItemSpecification.text = mallOrderInfoContainer!!.merchant_order_specification
            mIvOrderListItemNum.text = "X${mallOrderInfoContainer!!.merchant_order_num}"
            mTvOrderListItemPrice.text = "￥${mallOrderInfoContainer!!.merchant_product_price}"
            mTvOrderInfoExpressMoney.text = "￥${mallOrderInfoContainer!!.merchant_order_mdprice}"

            mTvOrderInfoAllMoney.text = "总价:￥${mallOrderInfoContainer!!.merchant_order_num.toInt() * mallOrderInfoContainer!!.merchant_product_price.toFloat() + mallOrderInfoContainer!!.merchant_order_mdprice.toFloat()}"
            mTvOrderInfoDetailOrderNum.text = mallOrderInfoContainer!!.merchant_order_sn
            mTvOrderInfoDetailGoodsOrderTime.text = mallOrderInfoContainer!!.merchant_order_ctime

            mTvOrderInfoRemark.text = Html.fromHtml("<font color='${ContextCompat.getColor(mContext, R.color.colorPrimary)}'>备注：</font>" +
                    if (TextUtils.isEmpty(mallOrderInfoContainer!!.merchant_order_mliuyan)) {
                        "暂无"
                    } else {
                        mallOrderInfoContainer!!.merchant_order_mliuyan
                    })
            mTvOrderInfoDetailOrderName.text = mallOrderInfoContainer!!.merchant_address_name
            when (mallOrderInfoContainer!!.merchant_order_token) {
                MallOrderPresent.MallOrderStatus.WAITING_FOR_MONEY.index -> {
                    mBtOrderInfoAction1.visibility = View.VISIBLE
                    mBtOrderInfoAction2.visibility = View.VISIBLE
                    mTvOrderInfoTopStatePic.setImageResource(R.drawable.img_order_status_money)
                    mBtOrderInfoAction1.text = "取消订单"
                    mBtOrderInfoAction2.text = "去支付"
                    mBtOrderInfoAction1.setOnClickListener {
                        //取消订单
                        alert("是否要取消订单？", "是", "否") { dialog, which ->
                            mPresent.getDataByPost(0x1,
                                    RequestParamsHelper.MEMBER_MODEL,
                                    RequestParamsHelper.ACT_EDIT_ORDER_STATUS,
                                    RequestParamsHelper.getEditOrderStatusParam(mallOrderInfoContainer!!.merchant_order_id, MallOrderPresent.MallOrderStatus.MALL_ORDER_CANCEL.index))
                        }
                    }
                    mBtOrderInfoAction2.setOnClickListener {
                        MallOrderPresent.showPayDialog(mContext) {
                            mPresent.getDataByPost(0x2, RequestParamsHelper.ORDER_MODEL, RequestParamsHelper.ACT_PAY,
                                    RequestParamsHelper.getPayParam(mallOrderInfoContainer!!.merchant_order_id, mallOrderInfoContainer!!.merchant_product_id, it))
                        }
                    }
                }
                MallOrderPresent.MallOrderStatus.WAITING_FOR_SEND.index -> {
                    mBtOrderInfoAction1.visibility = View.VISIBLE
                    mBtOrderInfoAction2.visibility = View.GONE
                    mTvOrderInfoTopStatePic.setImageResource(R.drawable.img_order_status_send)
                    mBtOrderInfoAction1.text = "申请退款"
                    mBtOrderInfoAction1.setOnClickListener {
                        //申请退款
                        alert("是否要申请退款？", "是", "否") { dialog, which ->
                            FragmentContainerActivity
                                    .from(mContext)
                                    .setClazz(RefundFragment::class.java)
                                    .setTitle("申请退款")
                                    .setNeedNetWorking(true)
                                    .setExtraBundle(bundleOf(Pair(RefundFragment.OID_FLAG, mallOrderInfoContainer!!.merchant_order_id)))
                                    .start()
                        }
                    }
                }
                MallOrderPresent.MallOrderStatus.WAITING_FOR_RECEIVER.index -> {
                    mTvOrderInfoTopStatePic.setImageResource(R.drawable.img_order_status_reveicer)
                    mBtOrderInfoAction1.visibility = View.GONE
                    mBtOrderInfoAction2.visibility = View.VISIBLE
                    mBtOrderInfoAction2.text = "确认收货"
                    mBtOrderInfoAction2.setOnClickListener {
                        //确定收货
                        alert("是否要确定收货？", "是", "否") { dialog, which ->
                            mPresent.getDataByPost(0x4,
                                    RequestParamsHelper.MEMBER_MODEL,
                                    RequestParamsHelper.ACT_EDIT_ORDER_STATUS,
                                    RequestParamsHelper.getEditOrderStatusParam(mallOrderInfoContainer!!.merchant_order_id, MallOrderPresent.MallOrderStatus.WAITING_FOR_EVALUATE.index))
                        }
                    }
                }
                MallOrderPresent.MallOrderStatus.WAITING_FOR_EVALUATE.index -> {
                    mTvOrderInfoTopStatePic.setImageResource(R.drawable.img_order_status_ele)
                    mBtOrderInfoAction1.visibility = View.GONE
                    mBtOrderInfoAction2.visibility = View.VISIBLE
                    mBtOrderInfoAction2.text = "去评价"
                    mBtOrderInfoAction2.setOnClickListener {
                        //去评价
                        FragmentContainerActivity
                                .from(mContext)
                                .setTitle("评价")
                                .setNeedNetWorking(true)
                                .setClazz(OrderCommentSubmitFragment::class.java)
                                .setExtraBundle(bundleOf(Pair(OrderCommentSubmitFragment.ORDER_ID_FLAG, mallOrderInfoContainer!!.merchant_order_id),
                                        Pair(OrderCommentSubmitFragment.PRODUCT_ID_FLAG, mallOrderInfoContainer!!.merchant_product_id)))
                                .start()
                    }
                }
                MallOrderPresent.MallOrderStatus.MALL_ORDER_COMPLEMENT.index -> {
                    mTvOrderInfoTopStatePic.setImageResource(R.drawable.img_order_status_complement)
                    mBtOrderInfoAction1.visibility = View.GONE
                    mBtOrderInfoAction2.visibility = View.GONE
                }
                MallOrderPresent.MallOrderStatus.MALL_ORDER_CANCEL.index -> {
                    mTvOrderInfoTopStatePic.setImageResource(R.drawable.img_order_status_cancel)
                    mBtOrderInfoAction1.visibility = View.GONE
                    mBtOrderInfoAction2.visibility = View.GONE
                }
                MallOrderPresent.MallOrderStatus.MALL_ORDER_HAS_BACK.index -> {
                    mTvOrderInfoTopStatePic.setImageResource(R.drawable.img_order_status_complement)
                    mBtOrderInfoAction1.visibility = View.GONE
                    mBtOrderInfoAction2.visibility = View.GONE
                }
                MallOrderPresent.MallOrderStatus.MALL_ORDER_APPLY_BACK.index -> {
                    mTvOrderInfoTopStatePic.setImageResource(R.drawable.img_order_status_apply_money)
                    mBtOrderInfoAction1.visibility = View.GONE
                    mBtOrderInfoAction2.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        unsubscribe(orderSubscription)
        super.onDestroyView()
    }

}