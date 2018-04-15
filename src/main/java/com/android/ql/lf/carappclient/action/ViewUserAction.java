package com.android.ql.lf.carappclient.action;

import com.android.ql.lf.carapp.action.IViewUserAction;
import com.android.ql.lf.carappclient.application.CarAppClientApplication;
import com.android.ql.lf.carappclient.data.UserInfo;
import com.android.ql.lf.carappclient.utils.PreferenceUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/**
 * Created by lf on 18.2.8.
 *
 * @author lf on 18.2.8
 */

public class ViewUserAction implements IViewUserAction {

    @Override
    public boolean onLogin(@NotNull JSONObject result) {
        try {
            UserInfo.getInstance().setMemberId(result.optString("users_id"));
            UserInfo.getInstance().setMemberName(result.optString("users_name"));
            UserInfo.getInstance().setMemberPhone(result.optString("users_phone"));
            UserInfo.getInstance().setMemberPic(result.optString("users_pic"));
            UserInfo.getInstance().setMemberRank(result.optString("users_rank"));
            UserInfo.getInstance().setMemberAddress(result.optString("users_address"));
            UserInfo.getInstance().setMemberGrade(result.optString("users_grade"));
            UserInfo.getInstance().setMemberIdCard(result.optString("users_idcard"));
            UserInfo.getInstance().setMemberInviteCode(result.optString("users_invite_code"));
            UserInfo.getInstance().setMemberOrderNum(result.optString("users_order_num"));
            UserInfo.getInstance().setMemberMyInviteCode(result.optString("users_mycode"));
            UserInfo.getInstance().setMemberOpenid(result.optString("users_openid"));
            UserInfo.getInstance().setMemberQQOpenid(result.optString("users_qqopenid"));
            UserInfo.getInstance().setMemberAlias(result.optString("users_alias"));
            UserInfo.getInstance().setMemberIswxAuth(result.optString("users_iswxauth"));

            UserInfo.getInstance().setGoodsCollectionNum(result.optString("users_product_collect"));
            UserInfo.getInstance().setStoreCollectionNum(result.optString("users_product_concerm"));
            UserInfo.getInstance().setFootsCollectionNum(result.optString("users_product_spoor"));

            PreferenceUtils.setPrefString(CarAppClientApplication.application, UserInfo.USER_ID_FLAG, UserInfo.getInstance().getMemberId());
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
