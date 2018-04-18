package com.android.ql.lf.carappclient.ui.fragments.mall.order

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.android.ql.lf.carapp.data.RefreshData
import com.android.ql.lf.carappclient.R
import com.android.ql.lf.carappclient.data.*
import com.android.ql.lf.carappclient.present.MallOrderPresent
import com.android.ql.lf.carappclient.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carappclient.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carappclient.ui.fragments.mall.address.AddressSelectFragment
import com.android.ql.lf.carappclient.ui.views.PopupWindowDialog
import com.android.ql.lf.carappclient.ui.views.SelectPayTypeView
import com.android.ql.lf.carappclient.utils.*
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_order_submit_layout.*
import org.jetbrains.anko.bundleOf
import org.json.JSONObject
import java.text.DecimalFormat

/**
 * Created by lf on 18.3.22.
 * @author lf on 18.3.22
 */
class OrderSubmitFragment : BaseRecyclerViewFragment<ShoppingCarItemBean>() {

    companion object {
        val GOODS_ID_FLAG = "goods_id_flag"
        val PAY_MALL_ORDER_FLAG = "pay_mall_order_flag"
    }

    private var tempList: ArrayList<ShoppingCarItemBean>? = null

    /**
     * 头部View
     */
    private val headerView by lazy {
        View.inflate(mContext, R.layout.layout_submit_new_order_header_layout, null)
    }

    /**
     * 空地址View
     */
    private val emptyAddressButton by lazy {
        headerView.findViewById<Button>(R.id.mBtSubmitOrderHeaderNoAddress)
    }

    /**
     * 地址包含View
     */
    private val selectAddressContainerView by lazy {
        headerView.findViewById<LinearLayout>(R.id.mLlSubmitOrderAddress)
    }

    /**
     * 地址名
     */
    private val addressName by lazy {
        headerView.findViewById<TextView>(R.id.mIvSubmitOrderAddressName)
    }

    /**
     * 地址手机号
     */
    private val addressPhone by lazy {
        headerView.findViewById<TextView>(R.id.mIvSubmitOrderAddressPhone)
    }

    /**
     * 地址详细信息
     */
    private val addressDetail by lazy {
        headerView.findViewById<TextView>(R.id.mIvSubmitOrderAddressDetail)
    }

    /**
     * 底部View
     */
    private val footerView by lazy { View.inflate(mContext, R.layout.layout_submit_new_order_footer_layout, null) }

    private val selectTypeView by lazy {
        footerView.findViewById<SelectPayTypeView>(R.id.mStvPay)
    }

    private val contentView: View by lazy {
        View.inflate(mContext, R.layout.dialog_bbs_layout, null)
    }

    private val addressSubscription by lazy {
        RxBus.getDefault().toObservable(AddressBean::class.java).subscribe {
            if (it != null) {
                addressBean = it
                setAddressInfo(addressBean!!)
            }
        }
    }

    private val paySubscription by lazy {
        RxBus.getDefault().toObservable(RefreshData::class.java).subscribe {
            if (it.isRefresh && it.any == PAY_MALL_ORDER_FLAG) {
                finish()
            }
        }
    }

    private var addressBean: AddressBean? = null

    private val orderList = arrayListOf<MallOrderBean>()

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
                        } else {
                            //支付失败
                            bundle.putInt(OrderPayResultFragment.PAY_CODE_FLAG, OrderPayResultFragment.PAY_FAIL_CODE)
                        }
//                    OrderPresent.notifyRefreshOrderNum()
                        FragmentContainerActivity
                                .from(mContext)
                                .setTitle("支付结果")
                                .setNeedNetWorking(true)
                                .setExtraBundle(bundle)
                                .setClazz(OrderPayResultFragment::class.java)
                                .start()
                        finish()
                    }
                }
            }
        }
    }

    override fun createAdapter(): BaseQuickAdapter<ShoppingCarItemBean, BaseViewHolder> {
        return object : BaseQuickAdapter<ShoppingCarItemBean, BaseViewHolder>(R.layout.adapter_submit_order_info_item_layout, mArrayList) {
            override fun convert(helper: BaseViewHolder?, item: ShoppingCarItemBean?) {
                val iv_pic = helper!!.getView<ImageView>(R.id.mIvSubmitOrderGoodsPic)
                GlideManager.loadImage(iv_pic.context, if (item!!.merchant_shopcart_pic.isEmpty()) "" else item.merchant_shopcart_pic[0], iv_pic)
                helper.setText(R.id.mIvSubmitOrderGoodsName, item.merchant_shopcart_name)
                helper.setText(R.id.mTvSubmitOrderItemStoreName, item.shop_shopname)
                GlideManager.loadImage(mContext, item.shop_shoppic, helper.getView(R.id.mIvSubmitOrderItemStorePic))
                helper.setText(R.id.mIvSubmitOrderGoodsSpe, item.merchant_shopcart_specification)
                helper.setText(R.id.mIvSubmitOrderGoodsPrice, "￥${item.merchant_shopcart_price}")
                helper.setText(R.id.mIvSubmitOrderGoodsNum, "X${item.merchant_shopcart_num}")
                helper.setText(R.id.mTvSubmitOrderGoodsBBSContent, if (TextUtils.isEmpty(item.bbs)) "选填" else item.bbs)
                helper.addOnClickListener(R.id.mRlSubmitOrderGoodsBBS)
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_order_submit_layout

    override fun initView(view: View?) {
        arguments.classLoader = this.javaClass.classLoader
        tempList = arguments.getParcelableArrayList(GOODS_ID_FLAG)
        super.initView(view)
        addressSubscription
        paySubscription
        mSwipeRefreshLayout.isEnabled = false
        mBaseAdapter.addHeaderView(headerView)
        mBaseAdapter.addFooterView(footerView)
        emptyAddressButton.setOnClickListener {
            FragmentContainerActivity.from(mContext).setTitle("选择地址").setNeedNetWorking(true).setClazz(AddressSelectFragment::class.java).start()
        }
        selectAddressContainerView.setOnClickListener {
            FragmentContainerActivity.from(mContext).setTitle("选择地址").setNeedNetWorking(true).setClazz(AddressSelectFragment::class.java).start()
        }
        mTvSubmitOrder.setOnClickListener {
            if (addressBean == null) {
                toast("请选择收货地址")
                return@setOnClickListener
            }
            mArrayList.forEach {
                val orderBean = MallOrderBean()
                orderBean.address = addressBean!!.merchant_address_id
                orderBean.gid = it.merchant_shopcart_gid
                orderBean.cid = it.merchant_shopcart_id
                orderBean.mliuyan = it.bbs
                orderBean.num = it.merchant_shopcart_num
                orderBean.specification = it.merchant_shopcart_specification
                orderBean.price = it.merchant_shopcart_price
                orderBean.mdprice = it.merchant_shopcart_mdprice
                orderBean.bbs = it.bbs
                orderBean.service = it.service
                orderBean.key = it.key
                orderList.add(orderBean)
            }
            val json = Gson().toJson(orderList)
            payType = selectTypeView.payType
            mPresent.getDataByPost(0x1, RequestParamsHelper.ORDER_MODEL, RequestParamsHelper.ACT_ADD_ORDER,
                    RequestParamsHelper.getAddOrderParams(payType, json))
        }
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        val itemDecoration = super.getItemDecoration() as DividerItemDecoration
        itemDecoration.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.recycler_view_height_divider))
        return itemDecoration
    }

    private fun setAddressInfo(addressBean: AddressBean) {
        addressName.text = addressBean.merchant_address_name
        addressPhone.text = addressBean.merchant_address_phone
        addressDetail.text = "${addressBean.merchant_address_addres}  ${addressBean.merchant_address_detail}"
        emptyAddressButton.visibility = View.GONE
        selectAddressContainerView.visibility = View.VISIBLE
    }

    override fun onRefresh() {
        super.onRefresh()
        setRefreshEnable(false)
        setLoadEnable(false)
        if (tempList != null && !tempList!!.isEmpty()) {
            var money = 0.00f
            var num = 0
            tempList!!.forEach {
                money += ((it.merchant_shopcart_price.toFloat() * it.merchant_shopcart_num.toInt()) + it.merchant_shopcart_mdprice.toFloat())
                num += it.merchant_shopcart_num.toInt()
            }
            mTvSubmitOrderGoodsCount.text = Html.fromHtml("共<span style='color:#78BFFF'> $num </span>件")
            mTvSubmitOrderGoodsPrice.text = "￥ " + DecimalFormat("0.00").format(money)
            mArrayList.addAll(tempList!!)
            mBaseAdapter.notifyDataSetChanged()
        }
        //加载地址
        mPresent.getDataByPost(0x0, RequestParamsHelper.ORDER_MODEL, RequestParamsHelper.ACT_DEFAULT_ADDRESS, RequestParamsHelper.getDefaultAddress())
    }

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        when (requestID) {
            0x0 -> getFastProgressDialog("正在加载地址……")
            0x1 -> getFastProgressDialog("正在提交订单……")
        }
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        if (requestID == 0x0) {
            //加载地址
            try {
                val check = checkResultCode(result)
                if (check != null && check.code == SUCCESS_CODE) {
                    val resultJson = check.obj as JSONObject
                    addressBean = Gson().fromJson(resultJson.optJSONObject("result").toString(), AddressBean::class.java)
                    if (addressBean != null) {
                        setAddressInfo(addressBean!!)
                    }
                } else {
                    emptyAddressButton.visibility = View.VISIBLE
                    selectAddressContainerView.visibility = View.GONE
                }
            } catch (e: Exception) {
                emptyAddressButton.visibility = View.VISIBLE
                selectAddressContainerView.visibility = View.GONE
            }
        } else {
            //提交订单
            val check = checkResultCode(result)
            if (check != null) {
                if (check.code == SUCCESS_CODE) {
                    MallOrderPresent.notifyRefreshShoppingCarList()
                    when (payType) {
                        SelectPayTypeView.WX_PAY -> {
                            PreferenceUtils.setPrefBoolean(mContext, "is_mall_order", true)
                            val wxBean = Gson().fromJson((check.obj as JSONObject).optJSONObject("result").toString(), WXPayBean::class.java)
                            PayManager.wxPay(mContext, wxBean)
                        }
                        SelectPayTypeView.ALI_PAY -> PayManager.aliPay(mContext, handle, (check.obj as JSONObject).optString("result"))
                        SelectPayTypeView.ACCOUNT_PAY -> {
                            FragmentContainerActivity
                                    .from(mContext)
                                    .setTitle("支付结果")
                                    .setNeedNetWorking(true)
                                    .setExtraBundle(bundleOf(Pair(OrderPayResultFragment.PAY_CODE_FLAG, OrderPayResultFragment.PAY_SUCCESS_CODE)))
                                    .setClazz(OrderPayResultFragment::class.java)
                                    .start()
                            finish()
                        }
                    }
                } else {
                    toast((check.obj as JSONObject).optString(MSG_FLAG))
                }
            }
        }
    }

    override fun onRequestFail(requestID: Int, e: Throwable) {
        super.onRequestFail(requestID, e)
        if (requestID == 0x0) {
            emptyAddressButton.visibility = View.VISIBLE
            selectAddressContainerView.visibility = View.GONE
        }
    }

    override fun onMyItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val currentItem = mArrayList[position]
        when (view!!.id) {
            R.id.mRlSubmitOrderGoodsBBS -> {
                val popupDialog = PopupWindowDialog.showReplyDialog(mContext, contentView)
                val et_content = contentView.findViewById<EditText>(R.id.mEtDialogBbsContent)
                val tv_finish = contentView.findViewById<TextView>(R.id.mTvDialogBbsFinish)
                et_content.setText("")
                tv_finish.setOnClickListener {
                    if (et_content.isEmpty()) {
                        toast("请输入留言内容")
                        return@setOnClickListener
                    }
                    et_content.clearFocus()
                    currentItem.bbs = et_content.getTextString()
                    mBaseAdapter.notifyItemChanged(position + 1)
                    popupDialog.dismiss()
                }
            }
        }
    }

    override fun onDestroyView() {
        unsubscribe(addressSubscription)
        unsubscribe(paySubscription)
        super.onDestroyView()
    }

}