package com.android.ql.lf.carappclient.ui.fragments.user.mine

import android.view.View
import android.widget.RadioButton
import com.android.ql.lf.carappclient.R
import com.android.ql.lf.carappclient.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carappclient.utils.isEmpty
import com.android.ql.lf.carappclient.utils.toast
import kotlinx.android.synthetic.main.fragment_account_money_layout.*

/**
 * Created by liufeng on 2018/4/15.
 */
class MineAccountMoneyFragment :BaseNetWorkingFragment(){

    override fun getLayoutId() = R.layout.fragment_account_money_layout

    override fun initView(view: View?) {
        mEtAccountMoney.setText("50")
        mEtAccountMoney.setSelection("50".length)
        setText(mRbAccountMoneyOne,"50")
        setText(mRbAccountMoneyTwo,"100")
        setText(mRbAccountMoneyThree,"500")
        setText(mRbAccountMoneyFour,"1000")
        mBtAccountMoneySubmit.setOnClickListener {
            if (mEtAccountMoney.isEmpty()){
                toast("请输入充值金额")
                return@setOnClickListener
            }
        }
    }

    private fun setText(radioButton: RadioButton,account:String){
        radioButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                mEtAccountMoney.setText(account)
                mEtAccountMoney.setSelection(account.length)
            }
        }
    }

}