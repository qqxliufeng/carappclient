package com.android.ql.lf.carappclient.ui.fragments.bottom

import android.support.v4.widget.SwipeRefreshLayout
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import com.android.ql.lf.carapp.data.RefreshData
import com.android.ql.lf.carapp.ui.fragments.user.SettingFragment
import com.android.ql.lf.carappclient.R
import com.android.ql.lf.carappclient.data.UpdateNotifyBean
import com.android.ql.lf.carappclient.data.UserInfo
import com.android.ql.lf.carappclient.present.UserPresent
import com.android.ql.lf.carappclient.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carappclient.ui.activities.MainActivity
import com.android.ql.lf.carappclient.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carappclient.ui.fragments.message.MineMessageListFragment
import com.android.ql.lf.carappclient.ui.fragments.user.mine.MinePersonalInfoFragment
import com.android.ql.lf.carappclient.ui.fragments.user.mine.MineWalletFragment
import com.android.ql.lf.carappclient.utils.*
import kotlinx.android.synthetic.main.fragment_main_mine_layout.*
import org.json.JSONObject

/**
 * Created by lf on 18.1.24.
 * @author lf on 18.1.24
 */
class MainMineFragment : BaseNetWorkingFragment(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        val MINE_PERSONAL_INFO_TOKEN = "personal_info_token"
        val MINE_PERSONAL_INFO_TOKEN2 = "personal_info_token2"
        val MINE_STORE_COLLECTION_TOKEN = "store_collection_token"
        val MINE_GOODS_COLLECTION_TOKEN = "goods_collection_token"
        val MINE_FOOT_PRINT_TOKEN = "foot_print_token"

        val MY_WALLET_TOKEN = "my_wallet_token"
        val MINE_MALL_ORDER_FLAG_TOKEN = "mine_mall_order_flag_token"

        val MINE_EVALUATE_TOKEN = "mine_evaluate_token"

        val MINE_SETTING_TOKEN = "setting_token"
        val MINE_MESSAGE_TOKEN = "mine_message_token"

        val MINE_GOODS_COLLECTION_NUM_TOKEN = "mine_goods_collection_num_token"
        val MINE_STORE_COLLECTION_NUM_TOKEN = "mine_store_collection_num_token"
        val MINE_FOOTS_COLLECTION_NUM_TOKEN = "mine_foots_collection_num_token"

        fun newInstance(): MainMineFragment {
            return MainMineFragment()
        }
    }

    private val userLogoutSubscription by lazy {
        RxBus.getDefault().toObservable(String::class.java).subscribe {
            if (it == UserInfo.LOGOUT_FLAG) {
                onLogoutSuccess()
            }
        }
    }

    private val modifyInfoSubscription by lazy {
        RxBus.getDefault().toObservable(String::class.java).subscribe {
            if (it == "modify info success") {
                mTvMainMineName.text = UserInfo.getInstance().memberName
                GlideManager.loadFaceCircleImage(mContext, UserInfo.getInstance().memberPic, mIvMainMineFace)
            }
        }
    }

    private val updateMessageNotifySubscription by lazy {
        RxBus.getDefault().toObservable(UpdateNotifyBean::class.java).subscribe {
            mViewMainMineMessageNotify.visibility = it.status
        }
    }

    private val updateCollectionNumSubscription by lazy {
        RxBus.getDefault().toObservable(RefreshData::class.java).subscribe {
            if (it.isRefresh) {
                if (it.any == MINE_GOODS_COLLECTION_NUM_TOKEN) {
                    UserInfo.getInstance().goodsCollectionNum = "${UserInfo.getInstance().goodsCollectionNum.toInt() + 1}"
                    mTvMainMineCollectionGoodsCount.text = "${UserInfo.getInstance().goodsCollectionNum}"
                }
                if (it.any == MINE_STORE_COLLECTION_NUM_TOKEN) {
                    UserInfo.getInstance().storeCollectionNum = "${UserInfo.getInstance().storeCollectionNum.toInt() + 1}"
                    mTvMainMineCollectionStoreCount.text = "${UserInfo.getInstance().storeCollectionNum}"
                }
                if (it.any == MINE_FOOTS_COLLECTION_NUM_TOKEN) {
                    UserInfo.getInstance().footsCollectionNum = "${UserInfo.getInstance().footsCollectionNum.toInt() + 1}"
                    mTvMainMineCollectionFootPrintCount.text = "${UserInfo.getInstance().footsCollectionNum}"
                }
            }
        }
    }

    private val userPresent by lazy {
        UserPresent()
    }

    override fun getLayoutId() = R.layout.fragment_main_mine_layout

    override fun initView(view: View?) {
        val height = (mContext as MainActivity).statusHeight
        val param = mRlMineTitleContainer.layoutParams as ViewGroup.MarginLayoutParams
        param.topMargin = height
        mRlMineTitleContainer.layoutParams = param
        mSrlMainMineContainer.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        mSrlMainMineContainer.setOnRefreshListener(this)

        //注册登录成功事件，刷新界面
        registerLoginSuccessBus()
        //注册用户退出事件
        userLogoutSubscription
        //修改个人信息
        modifyInfoSubscription
        //接受修改消息提示事件
        updateMessageNotifySubscription

        //接受收藏数量
        updateCollectionNumSubscription
        setUserInfo(UserInfo.getInstance())
        mFlMainMineMessageNotifyContainer.doClickWithUserStatusStart(MINE_MESSAGE_TOKEN) {
            RxBus.getDefault().post(UpdateNotifyBean(View.GONE))
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "我的消息", true, false, MineMessageListFragment::class.java)
        }
        mLlMainMinePersonalInfoContainer.doClickWithUserStatusStart(MINE_PERSONAL_INFO_TOKEN) {
            FragmentContainerActivity.from(mContext).setClazz(MinePersonalInfoFragment::class.java).setTitle("个人中心").setNeedNetWorking(true).start()
        }
        mTvMainMinePersonal.doClickWithUserStatusStart(MINE_PERSONAL_INFO_TOKEN2) {
            FragmentContainerActivity.from(mContext).setClazz(MinePersonalInfoFragment::class.java).setTitle("个人中心").setNeedNetWorking(true).start()
        }
        mLlMainMineStoreContainer.doClickWithUserStatusStart(MINE_STORE_COLLECTION_TOKEN) {
            //            FragmentContainerActivity.startFragmentContainerActivity(mContext, "店铺收藏", MineStoreCollectionFragment::class.java)
        }
        mLlMainMineGoodsContainer.doClickWithUserStatusStart(MINE_GOODS_COLLECTION_TOKEN) {
            //            FragmentContainerActivity.startFragmentContainerActivity(mContext, "商品收藏", MineGoodsCollectionFragment::class.java)
        }
        mLlMainMineFootPrintContainer.doClickWithUserStatusStart(MINE_FOOT_PRINT_TOKEN) {
            //            FragmentContainerActivity.startFragmentContainerActivity(mContext, "我的足迹", MineFootPrintFragment::class.java)
        }
        mTvMainMineSetting.doClickWithUserStatusStart(MINE_SETTING_TOKEN) {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "设置", SettingFragment::class.java)
        }
        mTvMainWallet.doClickWithUserStatusStart(MY_WALLET_TOKEN) {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "我的钱包", MineWalletFragment::class.java)
        }
//        mTvMainWallet.doClickWithUserStatusStart(MY_WALLET_TOKEN) {
//            FragmentContainerActivity.startFragmentContainerActivity(mContext, "我的帖子", MineWalletFragment::class.java)
//        }
        mTvMainMineEvaluate.doClickWithUserStatusStart(MINE_EVALUATE_TOKEN) {
            //            FragmentContainerActivity.from(mContext).setClazz(MimeEvaluateFragment::class.java).setTitle("我的评价").start()
        }
        mTvMainMineShopOrder.doClickWithUserStatusStart(MINE_MALL_ORDER_FLAG_TOKEN) {
            //            FragmentContainerActivity.from(mContext).setTitle("购物订单").setClazz(MineMallOrderFragment::class.java).start()
        }
        onRefresh()
    }

    override fun onRefresh() {
        if (UserInfo.getInstance().isLogin) {
            mPresent.getDataByPost(0x0,
                    RequestParamsHelper.MEMBER_MODEL,
                    RequestParamsHelper.ACT_PERSONAL,
                    RequestParamsHelper.getPersonalParam(UserInfo.getInstance().memberId))
        } else {
            onRequestEnd(-1)
        }
    }

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        if (requestID == 0x1) {
            getFastProgressDialog("正在验证密码……")
        }
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        val check = checkResultCode(result)
        if (requestID == 0x0) {
            if (check != null && check.code == SUCCESS_CODE) {
                val json = check.obj as JSONObject
                userPresent.onLoginNoBus(json.optJSONObject("result"), json.optJSONObject("arr"))
                val arr1 = json.optJSONObject("arr1")
                val s1 = arr1.optString("s1")
                if (!TextUtils.isEmpty(s1)) {
                    UserInfo.getInstance().goodsCollectionNum = s1
                }
                val s2 = arr1.optString("s2")
                if (!TextUtils.isEmpty(s2)) {
                    UserInfo.getInstance().storeCollectionNum = s2
                }
                val s3 = arr1.optString("s3")
                if (!TextUtils.isEmpty(s3)) {
                    UserInfo.getInstance().footsCollectionNum = s3
                }
                mTvMainMineCollectionGoodsCount.text = "${UserInfo.getInstance().goodsCollectionNum}"
                mTvMainMineCollectionStoreCount.text = "${UserInfo.getInstance().storeCollectionNum}"
                mTvMainMineCollectionFootPrintCount.text = "${UserInfo.getInstance().footsCollectionNum}"
            }
        }
    }

    override fun onRequestEnd(requestID: Int) {
        super.onRequestEnd(requestID)
        if (requestID == 0x0) {
            if (mSrlMainMineContainer.isRefreshing) {
                mSrlMainMineContainer.post {
                    mSrlMainMineContainer.isRefreshing = false
                }
            }
        }
    }

    /**
     * 登录成功
     */
    override fun onLoginSuccess(it: UserInfo?) {
        if (it != null) {
            setUserInfo(it)
            when (UserInfo.loginToken) {
                MINE_PERSONAL_INFO_TOKEN, MINE_PERSONAL_INFO_TOKEN2 -> {
                    mLlMainMinePersonalInfoContainer.doClickWithUseStatusEnd()
                }
                MINE_MESSAGE_TOKEN -> {
                    mFlMainMineMessageNotifyContainer.doClickWithUseStatusEnd()
                }
                MINE_STORE_COLLECTION_TOKEN -> {
                    mLlMainMineStoreContainer.doClickWithUseStatusEnd()
                }
                MINE_GOODS_COLLECTION_TOKEN -> {
                    mLlMainMineGoodsContainer.doClickWithUseStatusEnd()
                }
                MINE_FOOT_PRINT_TOKEN -> {
                    mLlMainMineFootPrintContainer.doClickWithUseStatusEnd()
                }
                MINE_SETTING_TOKEN -> {
                    mTvMainMineSetting.doClickWithUseStatusEnd()
                }
                MY_WALLET_TOKEN -> {
                    mTvMainWallet.doClickWithUseStatusEnd()
                }
            }
        }
    }

    private fun setUserInfo(it: UserInfo) {
        if (it.isLogin) {
            GlideManager.loadFaceCircleImage(mContext, it!!.memberPic, mIvMainMineFace)
            mTvMainMineEditNameNotify.visibility = View.VISIBLE
            mTvMainMineName.text = it.memberName
            mTvMainMinePhone.text = it.memberPhone.let {
                it.substring(0, 3) + "****" + it.substring(7, it.length)
            }
        }
    }

    /**
     * 登录失败
     */
    private fun onLogoutSuccess() {
        mIvMainMineFace.setImageResource(R.drawable.img_default_mine_icon)
        mTvMainMineName.text = "登录/注册"
        mTvMainMinePhone.text = "暂无"
        mTvMainMineCollectionGoodsCount.text = "0"
        mTvMainMineCollectionStoreCount.text = "0"
        mTvMainMineCollectionFootPrintCount.text = "0"
    }

    override fun onDestroyView() {
        unsubscribe(userLogoutSubscription)
        unsubscribe(modifyInfoSubscription)
        unsubscribe(updateMessageNotifySubscription)
        super.onDestroyView()
    }
}