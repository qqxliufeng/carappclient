package com.android.ql.lf.carappclient.ui.fragments.mall.normal

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Html
import android.text.TextPaint
import android.text.TextUtils
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import com.android.ql.lf.carappclient.R
import com.android.ql.lf.carappclient.application.CarAppClientApplication
import com.android.ql.lf.carappclient.data.*
import com.android.ql.lf.carappclient.present.GoodsPresent
import com.android.ql.lf.carappclient.ui.activities.ChatActivity
import com.android.ql.lf.carappclient.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carappclient.ui.adapters.GoodsCommentAdapter
import com.android.ql.lf.carappclient.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carappclient.ui.fragments.BrowserImageFragment
import com.android.ql.lf.carappclient.ui.fragments.mall.order.OrderCommentListFragment
import com.android.ql.lf.carappclient.ui.fragments.mall.order.OrderSubmitFragment
import com.android.ql.lf.carappclient.ui.views.BottomGoodsParamDialog
import com.android.ql.lf.carappclient.ui.views.ScrollLinearLayoutManager
import com.android.ql.lf.carappclient.ui.views.SlideDetailsLayout
import com.android.ql.lf.carappclient.utils.GlideManager
import com.android.ql.lf.carappclient.utils.RequestParamsHelper
import com.android.ql.lf.carappclient.utils.toast
import com.google.gson.Gson
import com.youth.banner.BannerConfig
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.fragment_new_goods_info_layout.*
import kotlinx.android.synthetic.main.layout_goods_info_foot_view_layout.*
import org.jetbrains.anko.bundleOf
import org.json.JSONObject


/**
 * Created by lf on 18.4.4.
 * @author lf on 18.4.4
 */
@SuppressLint("RestrictedApi")
class NewGoodsInfoFragment : BaseNetWorkingFragment() {

    companion object {
        val GOODS_ID_FLAG = "goods_id_flag"
    }

    private val mArrayList: ArrayList<CommentForGoodsBean> = arrayListOf()

    private var goodsInfoBean: GoodsInfoBean? = null

    private var paramsDialog: BottomGoodsParamDialog? = null

    private val commentAdapter by lazy {
        GoodsCommentAdapter(R.layout.adapter_goods_comment_item_layout, mArrayList)
    }

    private val topView by lazy {
        View.inflate(mContext, R.layout.layout_goods_info_top_view_layout, null)
    }

    private val footView by lazy {
        View.inflate(mContext, R.layout.layout_goods_info_foot_view_layout, null)
    }

    private val mTvCommentCount by lazy {
        topView.findViewById<TextView>(R.id.mTvGoodsInfoTopViewCommentNum)
    }

    private val mTvCommentAll by lazy {
        topView.findViewById<TextView>(R.id.mTvGoodsInfoTopViewCommentAll)
    }

    enum class ACTION_MODE {
        SHOPPING_CAR, BUY
    }

    private var actionMode: ACTION_MODE = ACTION_MODE.SHOPPING_CAR

    override fun getLayoutId() = R.layout.fragment_new_goods_info_layout

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        CarAppClientApplication.getInstance().activityQueue.addItem(this)
    }

    override fun initView(view: View?) {
        mWebGoodsInfo.settings.javaScriptEnabled = true
        mWebGoodsInfo.settings.domStorageEnabled = true
        mWebGoodsInfo.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        mWebGoodsInfo.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
        mCibGoodsInfoCollection.setOnClickListener {
            if (goodsInfoBean != null) {
                mPresent.getDataByPost(0x1,
                        RequestParamsHelper.PRODUCT_MODEL,
                        RequestParamsHelper.ACT_COLLECT_PRODUCT,
                        RequestParamsHelper.getCollectProductParam(goodsInfoBean!!.result!!.merchant_product_id))
            }
        }
        val linearLayoutManager = ScrollLinearLayoutManager(mContext)
        linearLayoutManager.setScrollEnable(false)
        mRvGoodsInfoComment.layoutManager = linearLayoutManager
        mRvGoodsInfoComment.adapter = commentAdapter
        commentAdapter.addHeaderView(topView)
        commentAdapter.addFooterView(footView)
        mTvGoodsInfoBuy.setOnClickListener {
            actionMode = ACTION_MODE.BUY
            showGoodsSpe()
        }
        mTvGoodsInfoCollection.setOnClickListener {
            actionMode = ACTION_MODE.SHOPPING_CAR
            showGoodsSpe()
        }
        mTvGoodsInfoAskOnline.setOnClickListener {
            if (goodsInfoBean != null && !TextUtils.isEmpty(goodsInfoBean!!.arr1!!.shop_hxname)) {
                ChatActivity.startChat(mContext, goodsInfoBean!!.arr1!!.shop_name, goodsInfoBean!!.arr1!!.shop_hxname)
            }
        }
        mCBPersonalGoodsInfo!!.setImageLoader(object : ImageLoader() {
            override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
                GlideManager.loadImage(mContext, path as String, imageView)
            }
        })
        mCBPersonalGoodsInfo.setOnBannerListener {
            if (goodsInfoBean != null) {
                BrowserImageFragment.startBrowserImage(context, goodsInfoBean!!.result!!.merchant_product_pic, it)
            }
        }
        mTvCommentAll.setOnClickListener {
            if (goodsInfoBean != null) {
                FragmentContainerActivity
                        .from(mContext)
                        .setTitle("订单评价")
                        .setClazz(OrderCommentListFragment::class.java)
                        .setNeedNetWorking(true)
                        .setExtraBundle(bundleOf(Pair(OrderCommentListFragment.GID_FLAG, goodsInfoBean!!.result!!.merchant_product_id)))
                        .start()
            }
        }
        slideDetailsLayout.setOnSlideDetailsListener {
            if (goodsInfoBean !== null) {
                if (it == SlideDetailsLayout.Status.OPEN) {
                    val linkCss = "<style type=\"text/css\"> " +
                            "img {" +
                            "width:100%;" +
                            "height:auto;" +
                            "}" +
                            "body {" +
                            "margin-right:10px;" +
                            "margin-left:10px;" +
                            "}" +
                            "</style>"
                    val htmlContent = "<html><header>" + linkCss + "</header>" + goodsInfoBean!!.result!!.merchant_product_content + "</body></html>"
                    mWebGoodsInfo.loadData(htmlContent, "text/html", "UTF-8")
                }
            }
        }
    }

    private fun showGoodsSpe() {
        if (goodsInfoBean != null) {
            if (paramsDialog == null) {
                paramsDialog = BottomGoodsParamDialog(mContext)
                paramsDialog!!.bindDataToView(
                        "￥${goodsInfoBean!!.result!!.merchant_product_price}",
                        "库存${goodsInfoBean!!.result!!.merchant_product_entrepot}件",
                        goodsInfoBean!!.result!!.merchant_product_name,
                        goodsInfoBean!!.result!!.merchant_product_pic[0],
                        goodsInfoBean!!.result!!.merchant_product_specification)
                paramsDialog!!.setOnGoodsConfirmClickListener { specification, picPath, num, serviceName, servicePrice, price, key ->
                    if (actionMode == ACTION_MODE.SHOPPING_CAR) {
                        mPresent.getDataByPost(0x2,
                                RequestParamsHelper.MEMBER_MODEL,
                                RequestParamsHelper.ACT_ADD_SHOPCART,
                                RequestParamsHelper.getAddShopcartParam(
                                        goodsInfoBean!!.result!!.merchant_product_id,
                                        goodsInfoBean!!.arr1!!.shop_id,
                                        num,
                                        "$picPath,$specification,$serviceName",
                                        servicePrice,
                                        price,
                                        key
                                ))
                    } else {
                        val shoppingCarItem = ShoppingCarItemBean()
                        shoppingCarItem.merchant_shopcart_mdprice = goodsInfoBean!!.result!!.merchant_product_mdprice
                        shoppingCarItem.merchant_shopcart_num = num
                        shoppingCarItem.merchant_shopcart_price = goodsInfoBean!!.result!!.merchant_product_price
                        shoppingCarItem.merchant_shopcart_name = goodsInfoBean!!.result!!.merchant_product_name
                        shoppingCarItem.merchant_shopcart_gid = goodsInfoBean!!.result!!.merchant_product_id
                        shoppingCarItem.merchant_shopcart_service = servicePrice
                        shoppingCarItem.merchant_shopcart_key = key
                        if (goodsInfoBean!!.arr1!!.shop_pic != null && !goodsInfoBean!!.arr1!!.shop_pic.isEmpty()) {
                            shoppingCarItem.shop_shoppic = goodsInfoBean!!.arr1!!.shop_pic[0]
                        } else {
                            shoppingCarItem.shop_shoppic = ""
                        }
                        shoppingCarItem.shop_shopname = goodsInfoBean!!.arr1!!.shop_name
                        shoppingCarItem.merchant_shopcart_id = ""
                        if (TextUtils.isEmpty(picPath)) {
                            shoppingCarItem.merchant_shopcart_pic = goodsInfoBean!!.result!!.merchant_product_pic as ArrayList<String>
                        } else {
                            shoppingCarItem.merchant_shopcart_pic = arrayListOf(picPath)
                        }
                        shoppingCarItem.merchant_shopcart_specification = specification + serviceName
                        val bundle = Bundle()
                        bundle.putParcelableArrayList(OrderSubmitFragment.GOODS_ID_FLAG, arrayListOf(shoppingCarItem))
                        FragmentContainerActivity
                                .from(mContext).setTitle("确认订单")
                                .setNeedNetWorking(true)
                                .setClazz(OrderSubmitFragment::class.java)
                                .setExtraBundle(bundle)
                                .start()
                    }
                }
            }
            paramsDialog!!.show()
        }
    }

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        when (requestID) {
            0x0 -> getFastProgressDialog("正在加载详情……")
            0x1 -> getFastProgressDialog("正在收藏……")
            0x2 -> getFastProgressDialog("正在添加到购物车……")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresent.getDataByPost(0x0,
                RequestParamsHelper.PRODUCT_MODEL,
                RequestParamsHelper.ACT_PRODUCT_DETAIL,
                RequestParamsHelper.getPoductDetailParams(arguments.getString(GOODS_ID_FLAG, "")))
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        val check = checkResultCode(result)
        when (requestID) {
            0x0 -> { // 加载列表
                if (check != null && check.code == SUCCESS_CODE) {
                    val jsonObject = check.obj as JSONObject
                    goodsInfoBean = Gson().fromJson((jsonObject).toString(), GoodsInfoBean::class.java)
                    val commentJsonArray = jsonObject.optJSONObject("arr").optJSONArray("list")
                    if (commentJsonArray != null && commentJsonArray.length() > 0) {
                        mTvCommentCount.text = "订单评价(${commentJsonArray.length()})"
                        (0 until commentJsonArray.length()).forEach {
                            mArrayList.add(Gson().fromJson(commentJsonArray.optJSONObject(it).toString(), CommentForGoodsBean::class.java))
                        }
                        commentAdapter.notifyDataSetChanged()
                    }
                    bindData()
                }
            }
            0x1 -> { //收藏商品
                if (check != null && check.code == SUCCESS_CODE) {
                    toast((check.obj as JSONObject).optString(MSG_FLAG))
                    GoodsPresent.notifyRefreshGoodsStatus()
                    mCibGoodsInfoCollection.toggle()
                }
            }
            0x2 -> {// 加入到购物车
                if (check != null && check.code == SUCCESS_CODE) {
                    toast((check.obj as JSONObject).optString(MSG_FLAG))
                } else {
                    toast("添加购物车失败")
                }
            }
        }
    }

    override fun onRequestFail(requestID: Int, e: Throwable) {
        super.onRequestFail(requestID, e)
        if (requestID == 0x2) {
            toast("添加购物车失败")
        }
    }

    private fun bindData() {
        mCBPersonalGoodsInfo.setImages(goodsInfoBean!!.result!!.merchant_product_pic).setDelayTime(3000).setBannerStyle(BannerConfig.CIRCLE_INDICATOR).start()
        mCibGoodsInfoCollection.isChecked = goodsInfoBean!!.result!!.merchant_product_collect != "0"
        mTvGoodsInfoPrice.text = "￥ ${goodsInfoBean!!.result!!.merchant_product_price}"
        mTvGoodsInfoOldPrice.paint.flags = TextPaint.STRIKE_THRU_TEXT_FLAG
        mTvGoodsInfoOldPrice.text = "￥ ${goodsInfoBean!!.result!!.merchant_product_yprice}"
        mTvGoodsInfoInfoReleaseCount.text = goodsInfoBean!!.result!!.merchant_product_entrepot
        mTvGoodsInfoTitle.text = goodsInfoBean!!.result!!.merchant_product_name
        mTvGoodsInfoDescription.text = Html.fromHtml(goodsInfoBean!!.result!!.merchant_product_description)
        if (goodsInfoBean!!.arr1!!.shop_pic != null && !goodsInfoBean!!.arr1!!.shop_pic.isEmpty()) {
            GlideManager.loadImage(mContext, goodsInfoBean!!.arr1!!.shop_pic[0], mIvGoodsInfoStorePic)
        }
        mTvGoodsInfoStoreName.text = goodsInfoBean!!.arr1!!.shop_name
        mTvGoodsInfoStoreAllGoodsNum.text = goodsInfoBean!!.arr1!!.shop_num
        mTvGoodsInfoStoreInfoFocusNum.text = goodsInfoBean!!.arr1!!.shop_attention
        mTvGoodsInfoStoreInfoCommentNum.text = goodsInfoBean!!.arr1!!.shop_ping
        footView.findViewById<TextView>(R.id.mTvGoodsInfoEnterStore).setOnClickListener {
            FragmentContainerActivity
                    .from(mContext)
                    .setNeedNetWorking(true)
                    .setHiddenToolBar(true)
                    .setClazz(StoreInfoFragment::class.java)
                    .setExtraBundle(bundleOf(Pair(StoreInfoFragment.STORE_ID_FLAG, goodsInfoBean!!.arr1!!.shop_id)))
                    .start()
        }
    }

    override fun onStart() {
        super.onStart()
        mCBPersonalGoodsInfo.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        mCBPersonalGoodsInfo.stopAutoPlay()
    }

    override fun onDestroyView() {
        mCBPersonalGoodsInfo.releaseBanner()
        super.onDestroyView()
    }

    override fun onDestroy() {
        CarAppClientApplication.getInstance().activityQueue.removeItem(this)
        super.onDestroy()
    }

    class GoodsInfoBean {
        var code: Int = 0
        var msg: String? = null
        var result: GoodsBean? = null
        var arr1: StoreInfoBean? = null
        var arr2: AdsBean? = null
    }
}
