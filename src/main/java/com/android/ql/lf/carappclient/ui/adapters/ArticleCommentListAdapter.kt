package com.android.ql.lf.carappclient.ui.adapters

import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.android.ql.lf.carappclient.R
import com.android.ql.lf.carappclient.data.ArticleAnswerBean
import com.android.ql.lf.carappclient.data.UserInfo
import com.android.ql.lf.carappclient.utils.GlideManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.2.6.
 * @author lf on 18.2.6
 */
class ArticleCommentListAdapter(resId: Int, list: ArrayList<ArticleAnswerBean>) : BaseQuickAdapter<ArticleAnswerBean, BaseViewHolder>(resId, list) {
    override fun convert(helper: BaseViewHolder?, item: ArticleAnswerBean?) {
        if (!TextUtils.isEmpty(item!!.users_pic)) {
            GlideManager.loadFaceCircleImage(mContext, item.users_pic, helper!!.getView(R.id.mIvAnswerInfoItemFace))
        }
        helper!!.setText(R.id.mTvAnswerInfoItemName, if (TextUtils.isEmpty(item.users_name)) {
            "暂无"
        } else {
            item.users_name
        })
        helper.setText(R.id.mTvAnswerInfoItemContent, item.merchant_answer_content)
        helper.setText(R.id.mTvAnswerInfoItemTime, item.merchant_answer_time)
        val mTvDelete = helper.getView<TextView>(R.id.mTvAnswerInfoItemDelete)
        if (UserInfo.getInstance().memberId == item.users_id) {
            mTvDelete.visibility = View.VISIBLE
            helper.addOnClickListener(R.id.mTvAnswerInfoItemDelete)
        } else {
            mTvDelete.visibility = View.GONE
        }
        if (item.merchant_answer_click == "0") {
            helper.setText(R.id.mTvPraiseText, "赞")
        } else {
            helper.setText(R.id.mTvPraiseText, item.merchant_answer_click)
        }
        helper.addOnClickListener(R.id.mPraiseView)
    }
}