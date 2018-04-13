package com.android.ql.lf.carappclient.ui.fragments.mall.normal

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.view.View
import com.android.ql.lf.carappclient.R
import com.android.ql.lf.carappclient.data.MallOrderBean
import com.android.ql.lf.carappclient.ui.fragments.BaseNetWorkingFragment

/**
 * Created by lf on 2017/11/4 0004.
 * @author lf on 2017/11/4 0004
 */
class ExpressInfoFragment{

//    companion object {
//        val ORDER_BEAN_FLAG = "order_bean"
//    }
//
//    private val orderBean by lazy {
//        arguments.classLoader = this.javaClass.classLoader
//        arguments.getParcelable<MallOrderBean>(ORDER_BEAN_FLAG)
//    }
//
//    private val stepList = mutableListOf<String>()
//
//    private var expressNum: String = ""
//
//    override fun getLayoutId(): Int = R.layout.fragment_express_info_layout
//
//    override fun initView(view: View?) {
//        (0..10).forEach {
//            stepList.add("到济南了")
//        }
//        setStepData()
////        GlideManager.loadImage(mContext, if (orderBean.product_pic.isEmpty()) {
////            ""
////        } else {
////            orderBean.product_pic[0]
////        }, mIvExpressGoodsImage)
////        mTvExpressGoodsName.text = orderBean.product_name
////        mPresent.getDataByPost(0x0, RequestParamsHelper.MEMBER_MODEL, RequestParamsHelper.ACT_GETLOGISTICS,
////                RequestParamsHelper.getGetlogisticsParam(orderBean.order_tn))
//    }
//
//    private fun setStepData() {
//        mVerticalStepView.setStepsViewIndicatorComplectingPosition(stepList.size)//设置完成的步数
//                .setTextSize(12)
//                .setStepViewTexts(stepList)//总步骤
//                .setStepsViewIndicatorCompletedLineColor(Color.parseColor("#E6E6E6"))//设置StepsViewIndicator完成线的颜色
//                .setStepsViewIndicatorUnCompletedLineColor(Color.parseColor("#E6E6E6"))//设置StepsViewIndicator未完成线的颜色
//                .setStepViewComplectedTextColor(Color.parseColor("#666666"))//设置StepsView text完成线的颜色
//                .setStepViewUnComplectedTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))//设置StepsView text未完成线的颜色
//                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(mContext, R.drawable.default_icon))//设置StepsViewIndicator CompleteIcon
//                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(mContext, R.drawable.my_complted_icon))//设置StepsViewIndicator DefaultIcon
//                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(mContext, R.drawable.default_icon))//设置StepsViewIndicator AttentionIcon
//                .setLinePaddingProportion(1.0f)
//    }
//
//    override fun onRequestStart(requestID: Int) {
//        super.onRequestStart(requestID)
//        getFastProgressDialog("正在查询……")
//    }
//
//    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
//        super.onRequestSuccess(requestID, result)
////        val json = checkResultCode(result)
////        if (json != null) {
////            mTvExpressName.text = "快递名称：${json.optString("arr")}"
////            val resultObj = json.optJSONObject("result")
////            expressNum = resultObj.optString("nu")
////            mTvExpressNum.text = "快递编号：$expressNum"
////            val dataArray = resultObj.optJSONArray("data")
////            if (dataArray != null) {
////                val tempList = arrayListOf<ExpressBean>()
////                (0 until dataArray.length()).forEach {
////                    tempList.add(Gson().fromJson(dataArray.optJSONObject(it).toString(), ExpressBean::class.java))
////                }
////                tempList.reverse()
////                tempList.forEach {
////                    stepList.add("${it.context}\n${it.time}")
////                }
////                setStepData()
////            }
////        } else {
////            toast("查询失败")
////        }
//    }
//
//    override fun onRequestFail(requestID: Int, e: Throwable) {
//        super.onRequestFail(requestID, e)
//        toast("查询失败")
//    }
}