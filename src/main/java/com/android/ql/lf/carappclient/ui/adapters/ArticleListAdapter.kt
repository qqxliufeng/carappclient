package com.android.ql.lf.carappclient.ui.adapters

import android.view.View
import com.android.ql.lf.carappclient.R
import com.android.ql.lf.carappclient.data.ArticleBean
import com.android.ql.lf.carappclient.ui.views.ImageContainerLinearLayout
import com.android.ql.lf.carappclient.utils.GlideManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.2.5.
 * @author lf on 18.2.5
 */
class ArticleListAdapter(resId: Int, list: ArrayList<ArticleBean>) : BaseQuickAdapter<ArticleBean, BaseViewHolder>(resId, list) {
    override fun convert(helper: BaseViewHolder?, item: ArticleBean?) {
        helper!!.setText(R.id.mTvArticleItemTime, item!!.merchant_quiz_time)
        helper.setText(R.id.mCtvArticleItemSeeCount, item.merchant_quiz_look)
        helper.setText(R.id.mCtvArticleItemCommentCount, item.merchant_quiz_replies)
        helper.setText(R.id.mCtvArticleItemPraiseCount, item.merchant_quiz_click)
        helper.setText(R.id.mTvArticleItemContent, item.merchant_quiz_title)
        if (item.users_pic != null) {
            GlideManager.loadFaceCircleImage(mContext, item.users_pic, helper.getView(R.id.mIvArticleItemUserFace))
        }
        helper.setText(R.id.mTvArticleItemUserName, item.users_name ?: "暂无")
        val imageContainer = helper.getView<ImageContainerLinearLayout>(R.id.mLlArticleItemImageContainer)
        if (item.merchant_quiz_pic != null && !item.merchant_quiz_pic.isEmpty()) {
            imageContainer.visibility = View.VISIBLE
            imageContainer.setImages(item.merchant_quiz_pic)
        } else {
            imageContainer.visibility = View.GONE
        }
    }
}