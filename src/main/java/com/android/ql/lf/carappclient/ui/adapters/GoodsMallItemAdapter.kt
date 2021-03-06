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
        GlideManager.loadImage(mContext, item!!.merchant_product_main_pic, helper!!.getView(R.id.mTvGoodsInfoItemPic))
        helper.setText(R.id.mTvGoodsInfoItemName, item.merchant_product_name)
        helper.setText(R.id.mTvGoodsInfoItemPrice, "￥${item.merchant_product_price}")
        val imageCollect = helper.getView<ImageView>(R.id.mIvGoodsInfoItemCollection)
        if (item.merchant_product_collect == "1") { //1 收藏
            imageCollect.setImageResource(R.drawable.img_icon_goods_select)
        } else {
            imageCollect.setImageResource(R.drawable.img_icon_goods_unselect)
        }
        helper.addOnClickListener(R.id.mIvGoodsInfoItemCollection)
    }
}