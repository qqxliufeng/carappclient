package com.android.ql.lf.carappclient.data;

import java.util.ArrayList;

/**
 * Created by lf on 2017/12/8 0008.
 *
 * @author lf on 2017/12/8 0008
 */

public class CommentForGoodsBean {
    private String merchant_comment_id;
    private String merchant_comment_uid;
    private ArrayList<String> merchant_comment_pic;
    private String merchant_comment_content;
    private String merchant_comment_time;
    private String merchant_comment_f;
    private String users_name;
    private String users_pic;

    public String getMerchant_comment_id() {
        return merchant_comment_id;
    }

    public void setMerchant_comment_id(String merchant_comment_id) {
        this.merchant_comment_id = merchant_comment_id;
    }

    public String getMerchant_comment_uid() {
        return merchant_comment_uid;
    }

    public void setMerchant_comment_uid(String merchant_comment_uid) {
        this.merchant_comment_uid = merchant_comment_uid;
    }

    public ArrayList<String> getMerchant_comment_pic() {
        return merchant_comment_pic;
    }

    public void setMerchant_comment_pic(ArrayList<String> merchant_comment_pic) {
        this.merchant_comment_pic = merchant_comment_pic;
    }

    public String getMerchant_comment_content() {
        return merchant_comment_content;
    }

    public void setMerchant_comment_content(String merchant_comment_content) {
        this.merchant_comment_content = merchant_comment_content;
    }

    public String getMerchant_comment_time() {
        return merchant_comment_time;
    }

    public void setMerchant_comment_time(String merchant_comment_time) {
        this.merchant_comment_time = merchant_comment_time;
    }

    public String getMerchant_comment_f() {
        return merchant_comment_f;
    }

    public void setMerchant_comment_f(String merchant_comment_f) {
        this.merchant_comment_f = merchant_comment_f;
    }

    public String getUsers_name() {
        return users_name;
    }

    public void setUsers_name(String users_name) {
        this.users_name = users_name;
    }

    public String getUsers_pic() {
        return users_pic;
    }

    public void setUsers_pic(String users_pic) {
        this.users_pic = users_pic;
    }
}
