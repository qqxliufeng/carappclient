package com.android.ql.lf.carappclient.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by lf on 18.3.24.
 *
 * @author lf on 18.3.24
 */

public class StoreInfoBean implements Parcelable {

    private String shop_id;
    private String shop_name;
    private ArrayList<String> shop_pic;
    private String shop_attention;
    private String shop_uid;
    private String shop_num;

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public ArrayList<String> getShop_pic() {
        return shop_pic;
    }

    public void setShop_pic(ArrayList<String> shop_pic) {
        this.shop_pic = shop_pic;
    }

    public String getShop_attention() {
        return shop_attention;
    }

    public void setShop_attention(String shop_attention) {
        this.shop_attention = shop_attention;
    }

    public String getShop_uid() {
        return shop_uid;
    }

    public void setShop_uid(String shop_uid) {
        this.shop_uid = shop_uid;
    }

    public String getShop_num() {
        return shop_num;
    }

    public void setShop_num(String shop_num) {
        this.shop_num = shop_num;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.shop_id);
        dest.writeString(this.shop_name);
        dest.writeStringList(this.shop_pic);
        dest.writeString(this.shop_attention);
        dest.writeString(this.shop_uid);
        dest.writeString(this.shop_num);
    }

    public StoreInfoBean() {
    }

    protected StoreInfoBean(Parcel in) {
        this.shop_id = in.readString();
        this.shop_name = in.readString();
        this.shop_pic = in.createStringArrayList();
        this.shop_attention = in.readString();
        this.shop_uid = in.readString();
        this.shop_num = in.readString();
    }

    public static final Creator<StoreInfoBean> CREATOR = new Creator<StoreInfoBean>() {
        @Override
        public StoreInfoBean createFromParcel(Parcel source) {
            return new StoreInfoBean(source);
        }

        @Override
        public StoreInfoBean[] newArray(int size) {
            return new StoreInfoBean[size];
        }
    };
}
