package com.android.ql.lf.carappclient.ui.fragments.mall.order

import com.android.ql.lf.carappclient.R
import com.android.ql.lf.carappclient.data.CommentForGoodsBean
import com.android.ql.lf.carappclient.ui.adapters.GoodsCommentAdapter
import com.android.ql.lf.carappclient.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carappclient.utils.RequestParamsHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.3.28.
 * @author lf on 18.3.28
 */
class OrderCommentListFragment : BaseRecyclerViewFragment<CommentForGoodsBean>() {

    companion object {
        val GID_FLAG = "gid_flag"
    }

    override fun createAdapter(): BaseQuickAdapter<CommentForGoodsBean, BaseViewHolder> =
            GoodsCommentAdapter(R.layout.adapter_goods_comment_item_layout, mArrayList)

    override fun onRefresh() {
        super.onRefresh()
        mPresent.getDataByPost(0x0,
                RequestParamsHelper.PRODUCT_MODEL,
                RequestParamsHelper.ACT_ALL_COMMENT,
                RequestParamsHelper.getAllCommentParams(arguments.getString(GID_FLAG), currentPage))
    }

    override fun onLoadMore() {
        super.onLoadMore()
        mPresent.getDataByPost(0x0,
                RequestParamsHelper.PRODUCT_MODEL,
                RequestParamsHelper.ACT_ALL_COMMENT,
                RequestParamsHelper.getAllCommentParams(arguments.getString(GID_FLAG), currentPage))
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        processList(result as String, CommentForGoodsBean::class.java)
    }

}