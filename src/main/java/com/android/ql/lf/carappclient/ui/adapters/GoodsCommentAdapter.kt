package com.android.ql.lf.carappclient.ui.adapters

import android.widget.TextView
import com.android.ql.lf.carappclient.R
import com.android.ql.lf.carappclient.data.CommentForGoodsBean
import com.android.ql.lf.carappclient.ui.views.ImageContainerLinearLayout
import com.android.ql.lf.carappclient.utils.GlideManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.3.21.
 * @author lf on 18.3.21
 */
class GoodsCommentAdapter(layoutId: Int, list: ArrayList<CommentForGoodsBean>) : BaseQuickAdapter<CommentForGoodsBean, BaseViewHolder>(layoutId, list) {

    private var maxLines:Int = 4

    override fun convert(helper: BaseViewHolder?, item: CommentForGoodsBean?) {
        GlideManager.loadCircleImage(mContext, item!!.users_pic, helper!!.getView(R.id.mIvGoodsInfoCommentItemFace))
        helper.setText(R.id.mTvGoodsInfoCommentItemName, item.users_name)
        helper.setText(R.id.mTvGoodsInfoCommentItemTime, item.merchant_comment_time)
        val tv_content =  helper.getView<TextView>(R.id.mTvGoodsInfoCommentItemContent)
        helper.setText(R.id.mTvGoodsInfoCommentItemContent, item.merchant_comment_content)
        tv_content.maxLines = maxLines
        val picContainer = helper.getView<ImageContainerLinearLayout>(R.id.mLlGoodsInfoCommentItemPicContainer)
        picContainer.setImages(item.merchant_comment_pic)
    }

    fun setContentLines(maxLines:Int = 4){
        this.maxLines = maxLines
    }

}