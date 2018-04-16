package com.android.ql.lf.carappclient.ui.adapters

import android.text.Html
import com.android.ql.lf.carappclient.R
import com.android.ql.lf.carappclient.data.AddressBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 2017/11/13 0013.
 * @author lf on 2017/11/13 0013
 */
class AddressSelectListItemAdapter(layoutId: Int, list: ArrayList<AddressBean>) : BaseQuickAdapter<AddressBean, BaseViewHolder>(layoutId, list) {
    override fun convert(helper: BaseViewHolder?, item: AddressBean?) {
        helper!!.setText(R.id.mTvAddressSelectName, item!!.merchant_address_name)
        helper.setText(R.id.mTvAddressSelectPhone, item.merchant_address_phone)
        val default = if (item.merchant_address_token == "1") { "[默认地址]" } else { "" }
        val html = Html.fromHtml("<span style='color:#78BFFF'>$default</span> ${item.merchant_address_addres}  ${item.merchant_address_detail}")
        helper.setText(R.id.mTvAddressSelectDetail, html)
    }
}