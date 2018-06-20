package com.android.ql.lf.carappclient.ui.adapters

import android.widget.CheckedTextView
import com.android.ql.lf.carapp.ui.fragments.message.SystemMessageListFragment
import com.android.ql.lf.carappclient.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.1.27.
 * @author lf on 18.1.27
 */
class SystemMessageListAdapter(layoutId: Int, list: ArrayList<SystemMessageListFragment.SystemMessageItem>) : BaseQuickAdapter<SystemMessageListFragment.SystemMessageItem, BaseViewHolder>(layoutId, list) {
    override fun convert(helper: BaseViewHolder?, item: SystemMessageListFragment.SystemMessageItem?) {
        helper!!.setText(R.id.mTvSystemMessageItemTitle, item!!.merchant_message_title)
        helper.setText(R.id.mTvSystemMessageItemDescription, item.merchant_message_content)
        helper.setText(R.id.mTvSystemMessageItemTime, item.merchant_message_time)
        val ctvNotify = helper.getView<CheckedTextView>(R.id.mCtvSystemMessageItemNotify)
        ctvNotify.isChecked = !item.merchant_isRead
    }
}