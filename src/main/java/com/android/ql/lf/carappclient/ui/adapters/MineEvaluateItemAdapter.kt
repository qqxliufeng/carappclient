package com.android.ql.lf.carappclient.ui.adapters

import android.widget.RatingBar
import com.android.ql.lf.carappclient.R
import com.android.ql.lf.carappclient.data.CommentForGoodsBean
import com.android.ql.lf.carappclient.ui.views.ImageContainerLinearLayout
import com.android.ql.lf.carappclient.utils.GlideManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by liufeng on 2018/2/25.
 */
class MineEvaluateItemAdapter(resId: Int, list: ArrayList<CommentForGoodsBean>) : BaseQuickAdapter<CommentForGoodsBean, BaseViewHolder>(resId, list) {
    override fun convert(helper: BaseViewHolder?, item: CommentForGoodsBean?) {
        GlideManager.loadCircleImage(mContext, item!!.users_pic, helper!!.getView(R.id.mIvGoodsInfoCommentItemFace))
        helper.setText(R.id.mTvGoodsInfoCommentItemName, item.users_name)
        helper.setText(R.id.mTvGoodsInfoCommentItemContent, item.merchant_comment_content)
        val ratingBar = helper.getView<RatingBar>(R.id.mRbGoodsInfoCommentItemLevel)
        ratingBar.rating = item.merchant_comment_f.toFloat()
        val picContainer = helper.getView<ImageContainerLinearLayout>(R.id.mLlGoodsInfoCommentItemPicContainer)
        picContainer.setImages(item.merchant_comment_pic)
    }
}