package com.android.ql.lf.carappclient.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by lf on 18.2.22.
 *
 * @author lf on 18.2.22
 */

public class ArticleBean implements Parcelable {

    private String merchant_quiz_id;
    private String merchant_quiz_content;
    private String merchant_quiz_type;
    private String merchant_quiz_uid;
    private String merchant_quiz_time;
    private String merchant_quiz_token;
    private String merchant_quiz_title;
    private String merchant_quiz_click;
    private String merchant_quiz_video;
    private String merchant_quiz_look;
    private String merchant_quiz_replies;
    private String users_id;
    private String users_name;
    private String users_pic;
    private ArrayList<String> merchant_quiz_pic;

    public String getMerchant_quiz_id() {
        return merchant_quiz_id;
    }

    public void setMerchant_quiz_id(String merchant_quiz_id) {
        this.merchant_quiz_id = merchant_quiz_id;
    }

    public String getMerchant_quiz_content() {
        return merchant_quiz_content;
    }

    public void setMerchant_quiz_content(String merchant_quiz_content) {
        this.merchant_quiz_content = merchant_quiz_content;
    }

    public String getMerchant_quiz_type() {
        return merchant_quiz_type;
    }

    public void setMerchant_quiz_type(String merchant_quiz_type) {
        this.merchant_quiz_type = merchant_quiz_type;
    }

    public String getMerchant_quiz_uid() {
        return merchant_quiz_uid;
    }

    public void setMerchant_quiz_uid(String merchant_quiz_uid) {
        this.merchant_quiz_uid = merchant_quiz_uid;
    }

    public String getMerchant_quiz_time() {
        return merchant_quiz_time;
    }

    public void setMerchant_quiz_time(String merchant_quiz_time) {
        this.merchant_quiz_time = merchant_quiz_time;
    }

    public String getMerchant_quiz_token() {
        return merchant_quiz_token;
    }

    public void setMerchant_quiz_token(String merchant_quiz_token) {
        this.merchant_quiz_token = merchant_quiz_token;
    }

    public String getMerchant_quiz_title() {
        return merchant_quiz_title;
    }

    public void setMerchant_quiz_title(String merchant_quiz_title) {
        this.merchant_quiz_title = merchant_quiz_title;
    }

    public String getMerchant_quiz_click() {
        return merchant_quiz_click;
    }

    public void setMerchant_quiz_click(String merchant_quiz_click) {
        this.merchant_quiz_click = merchant_quiz_click;
    }

    public String getMerchant_quiz_video() {
        return merchant_quiz_video;
    }

    public void setMerchant_quiz_video(String merchant_quiz_video) {
        this.merchant_quiz_video = merchant_quiz_video;
    }

    public String getMerchant_quiz_look() {
        return merchant_quiz_look;
    }

    public void setMerchant_quiz_look(String merchant_quiz_look) {
        this.merchant_quiz_look = merchant_quiz_look;
    }

    public String getMerchant_quiz_replies() {
        return merchant_quiz_replies;
    }

    public void setMerchant_quiz_replies(String merchant_quiz_replies) {
        this.merchant_quiz_replies = merchant_quiz_replies;
    }

    public String getUsers_id() {
        return users_id;
    }

    public void setUsers_id(String users_id) {
        this.users_id = users_id;
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

    public ArrayList<String> getMerchant_quiz_pic() {
        return merchant_quiz_pic;
    }

    public void setMerchant_quiz_pic(ArrayList<String> merchant_quiz_pic) {
        this.merchant_quiz_pic = merchant_quiz_pic;
    }

    public ArticleBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.merchant_quiz_id);
        dest.writeString(this.merchant_quiz_content);
        dest.writeString(this.merchant_quiz_type);
        dest.writeString(this.merchant_quiz_uid);
        dest.writeString(this.merchant_quiz_time);
        dest.writeString(this.merchant_quiz_token);
        dest.writeString(this.merchant_quiz_title);
        dest.writeString(this.merchant_quiz_click);
        dest.writeString(this.merchant_quiz_video);
        dest.writeString(this.merchant_quiz_look);
        dest.writeString(this.merchant_quiz_replies);
        dest.writeString(this.users_id);
        dest.writeString(this.users_name);
        dest.writeString(this.users_pic);
        dest.writeStringList(this.merchant_quiz_pic);
    }

    protected ArticleBean(Parcel in) {
        this.merchant_quiz_id = in.readString();
        this.merchant_quiz_content = in.readString();
        this.merchant_quiz_type = in.readString();
        this.merchant_quiz_uid = in.readString();
        this.merchant_quiz_time = in.readString();
        this.merchant_quiz_token = in.readString();
        this.merchant_quiz_title = in.readString();
        this.merchant_quiz_click = in.readString();
        this.merchant_quiz_video = in.readString();
        this.merchant_quiz_look = in.readString();
        this.merchant_quiz_replies = in.readString();
        this.users_id = in.readString();
        this.users_name = in.readString();
        this.users_pic = in.readString();
        this.merchant_quiz_pic = in.createStringArrayList();
    }

    public static final Creator<ArticleBean> CREATOR = new Creator<ArticleBean>() {
        @Override
        public ArticleBean createFromParcel(Parcel source) {
            return new ArticleBean(source);
        }

        @Override
        public ArticleBean[] newArray(int size) {
            return new ArticleBean[size];
        }
    };
}
