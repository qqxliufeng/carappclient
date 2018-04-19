package com.android.ql.lf.carappclient.ui.fragments.user.mine

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.view.View
import android.widget.RadioButton
import com.android.ql.lf.carappclient.R
import com.android.ql.lf.carappclient.data.PayResult
import com.android.ql.lf.carappclient.data.WXPayBean
import com.android.ql.lf.carappclient.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carappclient.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carappclient.ui.fragments.PayResultFragment
import com.android.ql.lf.carappclient.ui.views.SelectPayTypeView
import com.android.ql.lf.carappclient.utils.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_account_money_layout.*
import org.json.JSONObject

/**
 * Created by liufeng on 2018/4/15.
 */
class MineAccountMoneyFragment : BaseNetWorkingFragment() {

    private val handle = @SuppressLint("HandlerLeak")
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
                        bundle.putInt(PayResultFragment.PAY_CODE_FLAG, PayResultFragment.PAY_SUCCESS_CODE)
                    } else {
                        //支付失败
                        bundle.putInt(PayResultFragment.PAY_CODE_FLAG, PayResultFragment.PAY_FAIL_CODE)
                    }
                    FragmentContainerActivity.startFragmentContainerActivity(mContext, "支付结果", true, false, bundle, PayResultFragment::class.java)
                }
            }
        }
    }

    private var payType = SelectPayTypeView.WX_PAY

    override fun getLayoutId() = R.layout.fragment_account_money_layout

    override fun initView(view: View?) {
        mEtAccountMoney.setText("50")
        mEtAccountMoney.setSelection("50".length)
        setText(mRbAccountMoneyOne, "50")
        setText(mRbAccountMoneyTwo, "100")
        setText(mRbAccountMoneyThree, "500")
        setText(mRbAccountMoneyFour, "1000")
        mBtAccountMoneySubmit.setOnClickListener {
            if (mEtAccountMoney.isEmpty()) {
                toast("请输入充值金额")
                return@setOnClickListener
            }
            if (!TextUtils.isDigitsOnly(mEtAccountMoney.getTextString())) {
                toast("请输入合法的金额")
                return@setOnClickListener
            }
            payType = mSPTVAccountMoney.payType
            mPresent.getDataByPost(0x0,
                    RequestParamsHelper.MEMBER_MODEL,
                    RequestParamsHelper.ACT_RECHARGE_PAY,
                    RequestParamsHelper.getRechargePayParam(mSPTVAccountMoney.payType, mEtAccountMoney.getTextString()))
        }
    }

    private fun setText(radioButton: RadioButton, account: String) {
        radioButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mEtAccountMoney.setText(account)
                mEtAccountMoney.setSelection(account.length)
            }
        }
    }

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        getFastProgressDialog("正在充值……")
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        val check = checkResultCode(result)
        if (check != null) {
            if (check.code == SUCCESS_CODE) {
                if (payType == SelectPayTypeView.WX_PAY) {
                    val wxBean = Gson().fromJson((check.obj as JSONObject).optJSONObject("result").toString(), WXPayBean::class.java)
                    PayManager.wxPay(mContext, wxBean)
                } else {
                    PayManager.aliPay(mContext, handle, (check.obj as JSONObject).optString("result"))
                }
            } else {
                toast((check.obj as JSONObject).optString("msg"))
            }
        }
    }
}