package com.android.ql.lf.carappclient.ui.adapters

import android.widget.ImageView
import com.android.ql.lf.carappclient.R
import com.android.ql.lf.carappclient.data.ShoppingCarItemBean
import com.android.ql.lf.carappclient.utils.GlideManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 2017/11/8 0008.
 * @author lf on 2017/11/8 0008
 */
class ShoppingCarItemAdapter(layoutId: Int, list: ArrayList<ShoppingCarItemBean>) : BaseQuickAdapter<ShoppingCarItemBean, BaseViewHolder>(layoutId, list) {

    override fun convert(helper: BaseViewHolder?, item: ShoppingCarItemBean?) {
        helper!!.addOnClickListener(R.id.mIvShoppingCarItemSelector)
        helper.addOnClickListener(R.id.mIvShoppingCarDeleteNum)
        helper.addOnClickListener(R.id.mIvShoppingCarAddNum)
        val ivSelector = helper.getView<ImageView>(R.id.mIvShoppingCarItemSelector)
        if (item!!.isSelector) {
            ivSelector?.setImageResource(R.drawable.img_shopping_car_selector_icon)
        } else {
            ivSelector?.setImageResource(R.drawable.img_shopping_car_unselector_icon)
        }
        helper.setText(R.id.mTvShoppingCarItemName, item.shopcart_name)
        helper.setText(R.id.mTvShoppingCarItemStoreName, item.shop_shopname)
        GlideManager.loadImage(mContext, item.shop_shoppic, helper.getView(R.id.mIvShoppingCarItemStorePic))
        helper.setText(R.id.mTvShoppingCarItemPrice, "￥ ${item.shopcart_price}")
        helper.setText(R.id.mTvShoppingCarItemSpe, item.shopcart_specification)
        helper.setText(R.id.mTvShoppingCarNum, item.shopcart_num)
        val goods_pic = helper.getView<ImageView>(R.id.mTvShoppingCarItemPic)
        if (!item.shopcart_pic.isEmpty()) {
            GlideManager.loadImage(goods_pic.context, item.shopcart_pic[0], goods_pic)
        }
    }
}