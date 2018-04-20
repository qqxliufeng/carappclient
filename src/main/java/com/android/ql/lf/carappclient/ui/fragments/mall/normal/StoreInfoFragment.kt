package com.android.ql.lf.carappclient.ui.fragments.mall.normal

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import com.amap.api.maps2d.model.LatLng
import com.android.ql.lf.carappclient.R
import com.android.ql.lf.carappclient.data.GoodsBean
import com.android.ql.lf.carappclient.data.StoreInfoBean
import com.android.ql.lf.carappclient.data.UserInfo
import com.android.ql.lf.carappclient.ui.activities.CityMapActivity
import com.android.ql.lf.carappclient.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carappclient.ui.adapters.GoodsMallItemAdapter
import com.android.ql.lf.carappclient.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carappclient.ui.fragments.bottom.MainMallFragment
import com.android.ql.lf.carappclient.ui.fragments.user.LoginFragment
import com.android.ql.lf.carappclient.ui.views.DividerGridItemDecoration
import com.android.ql.lf.carappclient.utils.*
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_normal_store_info_layout.*
import org.jetbrains.anko.bundleOf
import org.json.JSONObject

/**
 * Created by lf on 18.3.20.
 * @author lf on 18.3.20
 */
class StoreInfoFragment : BaseRecyclerViewFragment<GoodsBean>() {

    companion object {
        val STORE_ID_FLAG = "store_id_flag"
    }

    private var collectStatus = 0

    private var sort = ""
    private var sortFlag = true
    private var saleFlag = true

    private var keyword = ""

    private var tempGoodsBean: GoodsBean? = null

    private val storeInfoBean by lazy {
        arguments.classLoader = this@StoreInfoFragment.javaClass.classLoader
        arguments.getParcelable<StoreInfoBean>(STORE_ID_FLAG)
    }

//    private var storeInfoBean: StoreInfoBean? = null


    override fun createAdapter(): BaseQuickAdapter<GoodsBean, BaseViewHolder> = GoodsMallItemAdapter(R.layout.adapter_main_mall_item_layout, mArrayList)

    override fun getLayoutId() = R.layout.fragment_normal_store_info_layout

    override fun initView(view: View?) {
        super.initView(view)
        val statusHeight = (mContext as FragmentContainerActivity).statusHeight
        mAblStoreInfoContainer.setPadding(0, statusHeight, 0, 0)
        mAblStoreInfoContainer.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            mTlStoreInfoContainer.alpha = 1 - Math.abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange.toFloat()
        }
        setStoreInfo()
        mIvSearchGoodsBack.setOnClickListener {
            finish()
        }
        mTvStoreInfoFocus.setOnClickListener {
            mTvStoreInfoFocus.isEnabled = false
            mPresent.getDataByPost(0x1, RequestParamsHelper.PRODUCT_MODEL, RequestParamsHelper.ACT_CONCERM_SHOP, RequestParamsHelper.getConcermShopParams(storeInfoBean!!.shop_id))
        }
        mTvStoreInfoTopProductClassify.setOnClickListener {
            if (storeInfoBean != null) {
                FragmentContainerActivity
                        .from(mContext)
                        .setNeedNetWorking(true)
                        .setTitle("产品分类")
                        .setExtraBundle(bundleOf(Pair(StoreClassifyFragment.SID_FLAG, storeInfoBean!!.shop_id)))
                        .setClazz(StoreClassifyFragment::class.java)
                        .start()
            }
        }
        mTvStoreInfoProductClassify.setOnClickListener {
            mTvStoreInfoTopProductClassify.performClick()
        }
        mEtSearchContent.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                mContext.hiddenKeyBoard(mEtSearchContent.windowToken)
                keyword = if (mEtSearchContent.isEmpty()) {
                    ""
                } else {
                    mEtSearchContent.getTextString()
                }
                onPostRefresh()
            }
            false
        }
        mRbStoreInfoSort1.isChecked = true
        mRbStoreInfoSort1.setOnClickListener {
            if (sort == "") {
                return@setOnClickListener
            }
            sort = ""
            onPostRefresh()
        }
        mRbStoreInfoSort2.setOnClickListener {
            sort = if (saleFlag) {
                "sv1"
            } else {
                "sv2"
            }
            saleFlag = !saleFlag
            onPostRefresh()
        }
        mRbStoreInfoSort3.setOnClickListener {
            sort = if (sortFlag) {
                mRbStoreInfoSort3.text = "价格从高到低"
                "p1"
            } else {
                mRbStoreInfoSort3.text = "价格从低到高"
                "p2"
            }
            sortFlag = !sortFlag
            onPostRefresh()
        }
        mTvStoreInfoKeFu.setOnClickListener {
            //            val intent = Intent(mContext, ChatActivity::class.java)
//            intent.putExtra(ChatActivity.CHAT_TITLE_FLAG, storeInfoBean!!.wholesale_shop_name)
//            intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE)
//            intent.putExtra(EaseConstant.EXTRA_USER_ID, "zw123")
//            startActivity(intent)
        }
        mTvStoreInfoProductLocation.setOnClickListener {
            if (storeInfoBean != null && storeInfoBean.shop_coorp != null) {
                CityMapActivity.startMapActivity(mContext,
                        storeInfoBean!!.shop_name,
                        LatLng(storeInfoBean!!.shop_coorp[0].toDouble(), storeInfoBean!!.shop_coorp[1].toDouble()))
            }
        }
    }

    override fun onRefresh() {
        super.onRefresh()
        mPresent.getDataByPost(0x0, RequestParamsHelper.PRODUCT_MODEL, RequestParamsHelper.ACT_PRODUCT_SEARCH,
                RequestParamsHelper.getWithPageParams(currentPage)
                        .addParam("sid", storeInfoBean!!.shop_id)
                        .addParam("sort", sort)
                        .addParam("keyword", keyword))
    }

    override fun onLoadMore() {
        super.onLoadMore()
        mPresent.getDataByPost(0x0, RequestParamsHelper.PRODUCT_MODEL, RequestParamsHelper.ACT_PRODUCT_SEARCH,
                RequestParamsHelper.getWithPageParams(currentPage)
                        .addParam("sid", storeInfoBean!!.shop_id)
                        .addParam("sort", sort)
                        .addParam("keyword", keyword))
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        val manager = GridLayoutManager(mContext, 2)
        mManager = manager
        return manager
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        return DividerGridItemDecoration(mContext)
    }

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        when (requestID) {
            0x1 -> getFastProgressDialog("正在关注……")
            0x2 -> getFastProgressDialog("正在收藏……")
        }
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        when (requestID) {
            0x0 -> {
                processList(result as String, GoodsBean::class.java)
                val check = checkResultCode(result)
                if (check != null && check.code == SUCCESS_CODE) {
                    if (currentPage == 0) {
                        collectStatus = (check.obj as JSONObject).optInt("arr")
                        setFocusText()
//                        storeInfoBean = Gson().fromJson((check.obj as JSONObject).optJSONObject("arr1").toString(), StoreInfoBean::class.java)
//                        setStoreInfo()
                    }
                }
            }
            0x1 -> {
                mTvStoreInfoFocus.isEnabled = true
                val check = checkResultCode(result)
                if (check != null) {
                    if (check.code == SUCCESS_CODE) {
                        collectStatus = if (collectStatus == 0) {
                            1
                        } else {
                            0
                        }
                        setFocusText()
                    }
                    toast((check.obj as JSONObject).optString(MSG_FLAG))
                }
            }
            0x2 -> {
                val check = checkResultCode(result)
                if (check != null && check.code == SUCCESS_CODE) {
                    toast((check.obj as JSONObject).optString(MSG_FLAG))
                    refreshCollectionStatus()
                }
            }
        }
    }

    private fun setStoreInfo() {
        if (storeInfoBean!!.shop_pic != null && !storeInfoBean!!.shop_pic.isEmpty()) {
            GlideManager.loadImage(mContext, storeInfoBean!!.shop_pic[0], mIvStoreInfoPic)
        }
        mTvStoreInfoName.text = storeInfoBean!!.shop_name
        mTvStoreInfoFansCount.text = storeInfoBean!!.shop_attention
    }

    private fun refreshCollectionStatus() {
        if (tempGoodsBean != null) {
            if (tempGoodsBean!!.merchant_product_collect == "0") {
                tempGoodsBean!!.merchant_product_collect = "1"
            } else {
                tempGoodsBean!!.merchant_product_collect = "0"
            }
            mBaseAdapter.notifyItemChanged(mArrayList.indexOf(tempGoodsBean))
        }
    }

    private fun setFocusText() {
        mTvStoreInfoFocus.text = if (collectStatus == 0) {
            "+ 关注"
        } else {
            "已关注"
        }
    }

    override fun onMyItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        tempGoodsBean = mArrayList[position]
        enterGoodsInfo(tempGoodsBean!!)
    }

    override fun onMyItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        super.onMyItemChildClick(adapter, view, position)
        tempGoodsBean = mArrayList[position]
        when (view!!.id) {
            R.id.mIvGoodsInfoItemCollection -> {
                if (UserInfo.getInstance().isLogin) {
                    //收藏
                    collectionGoods(tempGoodsBean!!)
                } else {
                    UserInfo.loginToken = MainMallFragment.MAIN_MALL_COLLECTION_FLAG
                    LoginFragment.startLogin(mContext)
                }
            }
        }
    }

    /**
     * 添加 收藏
     */
    private fun collectionGoods(goodsBean: GoodsBean) {
        mPresent.getDataByPost(0x2,
                RequestParamsHelper.PRODUCT_MODEL,
                RequestParamsHelper.ACT_COLLECT_PRODUCT,
                RequestParamsHelper.getCollectProductParam(goodsBean.merchant_product_id))
    }

    /**
     * 进入商品详情
     */
    private fun enterGoodsInfo(goodsBean: GoodsBean) {
        FragmentContainerActivity.from(mContext)
                .setNeedNetWorking(true)
                .setTitle("商品详情")
                .setExtraBundle(bundleOf(Pair(NewGoodsInfoFragment.GOODS_ID_FLAG, goodsBean.merchant_product_id)))
                .setClazz(NewGoodsInfoFragment::class.java)
                .start()
    }

}