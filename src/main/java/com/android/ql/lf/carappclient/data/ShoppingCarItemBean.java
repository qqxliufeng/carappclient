package com.android.ql.lf.carappclient.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by lf on 2017/12/5 0005.
 *
 * @author lf on 2017/12/5 0005
 */

public class ShoppingCarItemBean implements Parcelable {
    private boolean isSelector = false;
    private boolean isEditorMode = false;

    private String merchant_shopcart_id;
    private String merchant_shopcart_name;
    private String merchant_shopcart_pic;
    private String merchant_shopcart_specification;
    private String merchant_shopcart_price;
    private String merchant_shopcart_num;
    private String merchant_shopcart_gid;
    private String merchant_shopcart_time;
    private String merchant_shopcart_uid;
    private String merchant_shopcart_shopid;
    private String merchant_shopcart_mdprice;
    private String merchant_sku_pic;
    private String shop_shopname;
    private String shop_shoppic;
    private String shop_id;
    private String merchant_shopcart_service = "0.00";
    private String merchant_shopcart_key;
    private String merchant_shopcart_freight;

    public float getSingleGoodsPrice() {
        return singleGoodsPrice;
    }

    public void setSingleGoodsPrice(float singleGoodsPrice) {
        this.singleGoodsPrice = singleGoodsPrice;
    }

    private float singleGoodsPrice;

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getMerchant_shopcart_freight() {
        return merchant_shopcart_freight;
    }

    public void setMerchant_shopcart_freight(String merchant_shopcart_freight) {
        this.merchant_shopcart_freight = merchant_shopcart_freight;
    }

    public String getMerchant_sku_pic() {
        return merchant_sku_pic;
    }

    public void setMerchant_sku_pic(String merchant_sku_pic) {
        this.merchant_sku_pic = merchant_sku_pic;
    }

    public String getMerchant_shopcart_service() {
        return merchant_shopcart_service;
    }

    public void setMerchant_shopcart_service(String merchant_shopcart_service) {
        this.merchant_shopcart_service = merchant_shopcart_service;
    }

    public String getMerchant_shopcart_key() {
        return merchant_shopcart_key;
    }

    public void setMerchant_shopcart_key(String merchant_shopcart_key) {
        this.merchant_shopcart_key = merchant_shopcart_key;
    }

    private String bbs;

    public boolean isSelector() {
        return isSelector;
    }

    public void setSelector(boolean selector) {
        isSelector = selector;
    }

    public boolean isEditorMode() {
        return isEditorMode;
    }

    public void setEditorMode(boolean editorMode) {
        isEditorMode = editorMode;
    }

    public String getMerchant_shopcart_id() {
        return merchant_shopcart_id;
    }

    public void setMerchant_shopcart_id(String merchant_shopcart_id) {
        this.merchant_shopcart_id = merchant_shopcart_id;
    }

    public String getMerchant_shopcart_name() {
        return merchant_shopcart_name;
    }

    public void setMerchant_shopcart_name(String merchant_shopcart_name) {
        this.merchant_shopcart_name = merchant_shopcart_name;
    }

    public String getMerchant_shopcart_pic() {
        return merchant_shopcart_pic;
    }

    public void setMerchant_shopcart_pic(String merchant_shopcart_pic) {
        this.merchant_shopcart_pic = merchant_shopcart_pic;
    }

    public String getMerchant_shopcart_specification() {
        return merchant_shopcart_specification;
    }

    public void setMerchant_shopcart_specification(String merchant_shopcart_specification) {
        this.merchant_shopcart_specification = merchant_shopcart_specification;
    }

    public String getMerchant_shopcart_price() {
        return merchant_shopcart_price;
    }

    public void setMerchant_shopcart_price(String merchant_shopcart_price) {
        this.merchant_shopcart_price = merchant_shopcart_price;
    }

    public String getMerchant_shopcart_num() {
        return merchant_shopcart_num;
    }

    public void setMerchant_shopcart_num(String merchant_shopcart_num) {
        this.merchant_shopcart_num = merchant_shopcart_num;
    }

    public String getMerchant_shopcart_gid() {
        return merchant_shopcart_gid;
    }

    public void setMerchant_shopcart_gid(String merchant_shopcart_gid) {
        this.merchant_shopcart_gid = merchant_shopcart_gid;
    }

    public String getMerchant_shopcart_time() {
        return merchant_shopcart_time;
    }

    public void setMerchant_shopcart_time(String merchant_shopcart_time) {
        this.merchant_shopcart_time = merchant_shopcart_time;
    }

    public String getMerchant_shopcart_uid() {
        return merchant_shopcart_uid;
    }

    public void setMerchant_shopcart_uid(String merchant_shopcart_uid) {
        this.merchant_shopcart_uid = merchant_shopcart_uid;
    }

    public String getMerchant_shopcart_shopid() {
        return merchant_shopcart_shopid;
    }

    public void setMerchant_shopcart_shopid(String merchant_shopcart_shopid) {
        this.merchant_shopcart_shopid = merchant_shopcart_shopid;
    }

    public String getMerchant_shopcart_mdprice() {
        return merchant_shopcart_mdprice;
    }

    public void setMerchant_shopcart_mdprice(String merchant_shopcart_mdprice) {
        this.merchant_shopcart_mdprice = merchant_shopcart_mdprice;
    }

    public String getShop_shopname() {
        return shop_shopname;
    }

    public void setShop_shopname(String shop_shopname) {
        this.shop_shopname = shop_shopname;
    }

    public String getShop_shoppic() {
        return shop_shoppic;
    }

    public void setShop_shoppic(String shop_shoppic) {
        this.shop_shoppic = shop_shoppic;
    }

    public String getBbs() {
        return bbs;
    }

    public void setBbs(String bbs) {
        this.bbs = bbs;
    }

    public ShoppingCarItemBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isSelector ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isEditorMode ? (byte) 1 : (byte) 0);
        dest.writeString(this.merchant_shopcart_id);
        dest.writeString(this.merchant_shopcart_name);
        dest.writeString(this.merchant_shopcart_pic);
        dest.writeString(this.merchant_shopcart_specification);
        dest.writeString(this.merchant_shopcart_price);
        dest.writeString(this.merchant_shopcart_num);
        dest.writeString(this.merchant_shopcart_gid);
        dest.writeString(this.merchant_shopcart_time);
        dest.writeString(this.merchant_shopcart_uid);
        dest.writeString(this.merchant_shopcart_shopid);
        dest.writeString(this.merchant_shopcart_mdprice);
        dest.writeString(this.merchant_sku_pic);
        dest.writeString(this.shop_shopname);
        dest.writeString(this.shop_shoppic);
        dest.writeString(this.shop_id);
        dest.writeString(this.merchant_shopcart_service);
        dest.writeString(this.merchant_shopcart_key);
        dest.writeString(this.merchant_shopcart_freight);
        dest.writeFloat(this.singleGoodsPrice);
        dest.writeString(this.bbs);
    }

    protected ShoppingCarItemBean(Parcel in) {
        this.isSelector = in.readByte() != 0;
        this.isEditorMode = in.readByte() != 0;
        this.merchant_shopcart_id = in.readString();
        this.merchant_shopcart_name = in.readString();
        this.merchant_shopcart_pic = in.readString();
        this.merchant_shopcart_specification = in.readString();
        this.merchant_shopcart_price = in.readString();
        this.merchant_shopcart_num = in.readString();
        this.merchant_shopcart_gid = in.readString();
        this.merchant_shopcart_time = in.readString();
        this.merchant_shopcart_uid = in.readString();
        this.merchant_shopcart_shopid = in.readString();
        this.merchant_shopcart_mdprice = in.readString();
        this.merchant_sku_pic = in.readString();
        this.shop_shopname = in.readString();
        this.shop_shoppic = in.readString();
        this.shop_id = in.readString();
        this.merchant_shopcart_service = in.readString();
        this.merchant_shopcart_key = in.readString();
        this.merchant_shopcart_freight = in.readString();
        this.singleGoodsPrice = in.readFloat();
        this.bbs = in.readString();
    }

    public static final Creator<ShoppingCarItemBean> CREATOR = new Creator<ShoppingCarItemBean>() {
        @Override
        public ShoppingCarItemBean createFromParcel(Parcel source) {
            return new ShoppingCarItemBean(source);
        }

        @Override
        public ShoppingCarItemBean[] newArray(int size) {
            return new ShoppingCarItemBean[size];
        }
    };
}
