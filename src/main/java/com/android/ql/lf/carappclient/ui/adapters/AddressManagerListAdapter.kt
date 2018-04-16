package com.android.ql.lf.carappclient.ui.adapters

import com.android.ql.lf.carappclient.R
import com.android.ql.lf.carappclient.data.AddressBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 2017/11/9 0009.
 * @author lf on 2017/11/9 0009
 */
class AddressManagerListAdapter(layoutId: Int, list: ArrayList<AddressBean>) : BaseQuickAdapter<AddressBean, BaseViewHolder>(layoutId, list) {
    override fun convert(helper: BaseViewHolder?, item: AddressBean?) {
        helper!!.setText(R.id.mTvAddressItemName, item!!.merchant_address_name)
        helper.setText(R.id.mTvAddressItemPhone, item.merchant_address_phone)
        helper.setText(R.id.mTvAddressItemDetail, "${item.merchant_address_addres}  ${item.merchant_address_detail}")
        helper.setChecked(R.id.mCIbAddressItemDefault, "1" == item.merchant_address_token)
        helper.addOnClickListener(R.id.mCIbAddressItemDefault)
        helper.addOnClickListener(R.id.mTvAddressItemDel)
        helper.addOnClickListener(R.id.mTvAddressItemEdit)
    }
}