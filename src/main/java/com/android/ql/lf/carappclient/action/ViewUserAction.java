package com.android.ql.lf.carappclient.action;

import android.text.TextUtils;

import com.android.ql.lf.carapp.action.IViewUserAction;
import com.android.ql.lf.carappclient.application.CarAppClientApplication;
import com.android.ql.lf.carappclient.data.UserInfo;
import com.android.ql.lf.carappclient.utils.PreferenceUtils;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/**
 * Created by lf on 18.2.8.
 *
 * @author lf on 18.2.8
 */

public class ViewUserAction implements IViewUserAction {

    @Override
    public boolean onLogin(@NotNull JSONObject result, JSONObject shopInfo) {
        try {
            UserInfo.getInstance().setMemberId(result.optString("member_id"));
            UserInfo.getInstance().setMemberName(result.optString("member_name"));
            UserInfo.getInstance().setMemberPhone(result.optString("member_phone"));
            UserInfo.getInstance().setMemberPic(result.optString("member_pic"));
            UserInfo.getInstance().setMemberAddress(result.optString("member_address"));
            UserInfo.getInstance().setMemberGrade(result.optString("member_grade"));
            UserInfo.getInstance().setMemberInviteCode(result.optString("member_invite_code"));
            UserInfo.getInstance().setMemberOrderNum(result.optString("member_order_num"));
            UserInfo.getInstance().setMemberMyInviteCode(result.optString("member_mycode"));
            UserInfo.getInstance().setMemberIsEnsureMoney(result.optString("member_is_ensure_money"));
            UserInfo.getInstance().setMemberIsMaster(result.optString("member_ismaster"));
            UserInfo.getInstance().setMemberOpenid(result.optString("member_openid"));
            UserInfo.getInstance().setMemberRank(result.optString("member_rank"));
            UserInfo.getInstance().setMemberIdCard(result.optString("member_idcard"));
            UserInfo.getInstance().setMemberAuthentication(result.optString("member_authentication"));
            UserInfo.getInstance().setMemberQQOpenid(result.optString("member_qqopenid"));
            UserInfo.getInstance().setMemberAlias(result.optString("member_alias"));
            UserInfo.getInstance().setMemberIswxAuth(result.optString("member_iswxauth"));
            PreferenceUtils.setPrefString(CarAppClientApplication.application, UserInfo.USER_ID_FLAG, UserInfo.getInstance().getMemberId());
            if (shopInfo != null && !TextUtils.isEmpty(shopInfo.toString()) && !"null".equals(shopInfo.toString())) {
                UserInfo.ShopInfo shopInoBean = new Gson().fromJson(shopInfo.toString(), UserInfo.ShopInfo.class);
                UserInfo.getInstance().setShopInfo(shopInoBean);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean onLogout() {
        UserInfo.getInstance().loginOut();
        UserInfo.clearUserCache(CarAppClientApplication.application);
        return true;
    }

    @Override
    public void onRegister(@NotNull JSONObject result) {
    }

    @Override
    public void onForgetPassword(@NotNull JSONObject result) {
    }

    @Override
    public void onResetPassword(@NotNull JSONObject result) {
    }

    @Override
    public boolean modifyInfoForName(@NotNull String name) {
        try {
            UserInfo.getInstance().setMemberName(name);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean modifyInfoForPic(@NotNull String result) {
        try {
            UserInfo.getInstance().setMemberPic(result);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
