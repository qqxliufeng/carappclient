package com.android.ql.lf.carappclient.ui.fragments.bottom

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import com.amap.api.maps2d.model.LatLng
import com.android.ql.lf.carappclient.R
import com.android.ql.lf.carappclient.data.GoodsBean
import com.android.ql.lf.carappclient.data.StoreInfoBean
import com.android.ql.lf.carappclient.data.UserInfo
import com.android.ql.lf.carappclient.ui.activities.ChatActivity
import com.android.ql.lf.carappclient.ui.activities.CityMapActivity
import com.android.ql.lf.carappclient.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carappclient.ui.activities.MainActivity
import com.android.ql.lf.carappclient.ui.adapters.GoodsMallItemAdapter
import com.android.ql.lf.carappclient.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carappclient.ui.fragments.mall.normal.NewGoodsInfoFragment
import com.android.ql.lf.carappclient.ui.fragments.mall.normal.StoreClassifyFragment
import com.android.ql.lf.carappclient.ui.fragments.user.LoginFragment
import com.android.ql.lf.carappclient.ui.views.DividerGridItemDecoration
import com.android.ql.lf.carappclient.utils.*
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_store_info_layout.*
import org.jetbrains.anko.bundleOf
import org.json.JSONObject

/**
 * Created by lf on 18.3.20.
 * @author lf on 18.3.20
 */
class MainStoreInfoFragment : BaseRecyclerViewFragment<GoodsBean>() {

    companion object {
        val STORE_ID_FLAG = "store_id_flag"
        val STORE_MALL_ENTER_GOODS_INFO_FLAG = "store_mall_enter_goods_info_flag"
        val STORE_MALL_COLLECTION_FLAG = "store_mall_collection_flag"

        fun newInstance() = MainStoreInfoFragment()
    }

    private var collectStatus = 0

    private var sort = ""
    private var sortFlag = true
    private var saleFlag = true

    private var keyword = ""

    private var tempGoodsBean: GoodsBean? = null

    private var storeInfoBean: StoreInfoBean? = null

    override fun createAdapter(): BaseQuickAdapter<GoodsBean, BaseViewHolder> = GoodsMallItemAdapter(R.layout.adapter_main_mall_item_layout, mArrayList)

    override fun getLayoutId() = R.layout.fragment_store_info_layout

    override fun initView(view: View?) {
        super.initView(view)
        registerLoginSuccessBus()
        val statusHeight = (mContext as MainActivity).statusHeight
        mAblStoreInfoContainer.setPadding(0, statusHeight, 0, 0)
        mAblStoreInfoContainer.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            mTlStoreInfoContainer.alpha = 1 - Math.abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange.toFloat()
        }
        mTvStoreInfoKeFu.setOnClickListener {
            if (storeInfoBean != null && !TextUtils.isEmpty(storeInfoBean!!.shop_hxname)) {
                ChatActivity.startChat(mContext, storeInfoBean!!.shop_name, storeInfoBean!!.shop_hxname)
            }
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
        mTvStoreInfoProductLocation.setOnClickListener {
            if (storeInfoBean != null) {
                CityMapActivity.startMapActivity(mContext, storeInfoBean!!.shop_name, LatLng(storeInfoBean!!.shop_coorp[0].toDouble(), storeInfoBean!!.shop_coorp[1].toDouble()))
            }
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
    }

    override fun getEmptyMessage() = "暂无商品"

    override fun onRefresh() {
        super.onRefresh()
        mPresent.getDataByPost(0x0, RequestParamsHelper.PRODUCT_MODEL, RequestParamsHelper.ACT_MY_SHOP,
                RequestParamsHelper.getMyShopParam(currentPage, sort))
    }

    override fun onLoadMore() {
        super.onLoadMore()
        mPresent.getDataByPost(0x0, RequestParamsHelper.PRODUCT_MODEL, RequestParamsHelper.ACT_MY_SHOP,
                RequestParamsHelper.getMyShopParam(currentPage, sort))
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
                if (check != null) {
                    collectStatus = (check.obj as JSONObject).optInt("arr")
                    storeInfoBean = Gson().fromJson((check.obj as JSONObject).optJSONObject("arr1").toString(), StoreInfoBean::class.java)
                    bindStoreData()
                    setFocusText()
                }
            }
            0x1 -> {
                mTvStoreInfoFocus.isEnabled = true
                val check = checkResultCode(result)
                if (check != null) {
                    if (check.code == SUCCESS_CODE) {
                        collectStatus = if (collectStatus == 0) {
                            storeInfoBean!!.shop_attention = (storeInfoBean!!.shop_attention.toInt() + 1).toString()
                            1
                        } else {
                            storeInfoBean!!.shop_attention = (storeInfoBean!!.shop_attention.toInt() - 1).toString()
                            0
                        }
                        mTvStoreInfoFansCount.text = storeInfoBean!!.shop_attention
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

    private fun bindStoreData() {
        if (storeInfoBean != null) {
            if (storeInfoBean!!.shop_mpic != null) {
                GlideManager.loadFaceCircleImage(mContext, storeInfoBean!!.shop_mpic, mIvStoreInfoPic)
            }
            mTvStoreInfoName.text = storeInfoBean!!.shop_name
            mTvStoreInfoFansCount.text = storeInfoBean!!.shop_attention
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
        if (UserInfo.getInstance().isLogin) {
            enterGoodsInfo(tempGoodsBean!!)
        } else {
            UserInfo.loginToken = STORE_MALL_ENTER_GOODS_INFO_FLAG
            LoginFragment.startLogin(mContext)
        }
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
                    UserInfo.loginToken = STORE_MALL_COLLECTION_FLAG
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

    override fun onLoginSuccess(userInfo: UserInfo?) {
        super.onLoginSuccess(userInfo)
        when (UserInfo.loginToken) {
            STORE_MALL_COLLECTION_FLAG -> {
                collectionGoods(tempGoodsBean!!)
                UserInfo.resetLoginSuccessDoActionToken()
            }
            STORE_MALL_ENTER_GOODS_INFO_FLAG -> {
                enterGoodsInfo(tempGoodsBean!!)
                UserInfo.resetLoginSuccessDoActionToken()
            }
        }
    }
}