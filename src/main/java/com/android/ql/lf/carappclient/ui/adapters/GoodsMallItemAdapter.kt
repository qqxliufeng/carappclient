package com.android.ql.lf.carappclient.ui.adapters

import android.widget.ImageView
import com.android.ql.lf.carappclient.R
import com.android.ql.lf.carappclient.data.GoodsBean
import com.android.ql.lf.carappclient.utils.GlideManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.3.20.
 * @author lf on 18.3.20
 */
class GoodsMallItemAdapter(layoutId: Int, list: ArrayList<GoodsBean>) : BaseQuickAdapter<GoodsBean, BaseViewHolder>(layoutId, list) {
    override fun convert(helper: BaseViewHolder?, item: GoodsBean?) {
        if (item!!.product_pic.size > 0) {
            GlideManager.loadImage(mContext, item.product_pic[0], helper!!.getView(R.id.mTvGoodsInfoItemPic))
        }
        helper!!.setText(R.id.mTvGoodsInfoItemName, item.product_name)
        helper.setText(R.id.mTvGoodsInfoItemPrice, "￥${item.product_price}")
        val imageCollect = helper.getView<ImageView>(R.id.mIvGoodsInfoItemCollection)
        if (item.product_collect == "0") { //0 未收藏
            imageCollect.setImageResource(R.drawable.img_icon_goods_unselect)
        } else {
            imageCollect.setImageResource(R.drawable.img_icon_goods_select)
        }
        helper.addOnClickListener(R.id.mIvGoodsInfoItemCollection)
    }
}