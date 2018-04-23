package com.android.ql.lf.carappclient.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by lf on 18.3.27.
 *
 * @author lf on 18.3.27
 */

public class MallSaleOrderBean implements Parcelable {

    private String merchant_product_id;
    private String merchant_product_name;
    private String merchant_product_md;
    private String merchant_product_mdprice;
    private String merchant_product_price;
    private String merchant_product_shopname;
    private String merchant_product_shoppic;
    private String merchant_order_num;
    private String merchant_order_sn;
    private String merchant_order_oprice;
    private String merchant_order_fc;
    private String merchant_order_token;
    private String merchant_order_id;
    private String merchant_order_specification;
    private String merchant_order_uid;
    private String merchant_order_ctime;
    private String merchant_order_tn;
    private String merchant_order_mdprice;
    private List<String> merchant_product_pic;

    public String getMerchant_product_id() {
        return merchant_product_id;
    }

    public void setMerchant_product_id(String merchant_product_id) {
        this.merchant_product_id = merchant_product_id;
    }

    public String getMerchant_product_name() {
        return merchant_product_name;
    }

    public void setMerchant_product_name(String merchant_product_name) {
        this.merchant_product_name = merchant_product_name;
    }

    public String getMerchant_product_md() {
        return merchant_product_md;
    }

    public void setMerchant_product_md(String merchant_product_md) {
        this.merchant_product_md = merchant_product_md;
    }

    public String getMerchant_product_mdprice() {
        return merchant_product_mdprice;
    }

    public void setMerchant_product_mdprice(String merchant_product_mdprice) {
        this.merchant_product_mdprice = merchant_product_mdprice;
    }

    public String getMerchant_product_price() {
        return merchant_product_price;
    }

    public void setMerchant_product_price(String merchant_product_price) {
        this.merchant_product_price = merchant_product_price;
    }

    public String getMerchant_product_shopname() {
        return merchant_product_shopname;
    }

    public void setMerchant_product_shopname(String merchant_product_shopname) {
        this.merchant_product_shopname = merchant_product_shopname;
    }

    public String getMerchant_product_shoppic() {
        return merchant_product_shoppic;
    }

    public void setMerchant_product_shoppic(String merchant_product_shoppic) {
        this.merchant_product_shoppic = merchant_product_shoppic;
    }

    public String getMerchant_order_num() {
        return merchant_order_num;
    }

    public void setMerchant_order_num(String merchant_order_num) {
        this.merchant_order_num = merchant_order_num;
    }

    public String getMerchant_order_sn() {
        return merchant_order_sn;
    }

    public void setMerchant_order_sn(String merchant_order_sn) {
        this.merchant_order_sn = merchant_order_sn;
    }

    public String getMerchant_order_oprice() {
        return merchant_order_oprice;
    }

    public void setMerchant_order_oprice(String merchant_order_oprice) {
        this.merchant_order_oprice = merchant_order_oprice;
    }

    public String getMerchant_order_fc() {
        return merchant_order_fc;
    }

    public void setMerchant_order_fc(String merchant_order_fc) {
        this.merchant_order_fc = merchant_order_fc;
    }

    public String getMerchant_order_token() {
        return merchant_order_token;
    }

    public void setMerchant_order_token(String merchant_order_token) {
        this.merchant_order_token = merchant_order_token;
    }

    public String getMerchant_order_id() {
        return merchant_order_id;
    }

    public void setMerchant_order_id(String merchant_order_id) {
        this.merchant_order_id = merchant_order_id;
    }

    public String getMerchant_order_specification() {
        return merchant_order_specification;
    }

    public void setMerchant_order_specification(String merchant_order_specification) {
        this.merchant_order_specification = merchant_order_specification;
    }

    public String getMerchant_order_uid() {
        return merchant_order_uid;
    }

    public void setMerchant_order_uid(String merchant_order_uid) {
        this.merchant_order_uid = merchant_order_uid;
    }

    public String getMerchant_order_ctime() {
        return merchant_order_ctime;
    }

    public void setMerchant_order_ctime(String merchant_order_ctime) {
        this.merchant_order_ctime = merchant_order_ctime;
    }

    public String getMerchant_order_tn() {
        return merchant_order_tn;
    }

    public void setMerchant_order_tn(String merchant_order_tn) {
        this.merchant_order_tn = merchant_order_tn;
    }

    public String getMerchant_order_mdprice() {
        return merchant_order_mdprice;
    }

    public void setMerchant_order_mdprice(String merchant_order_mdprice) {
        this.merchant_order_mdprice = merchant_order_mdprice;
    }

    public List<String> getMerchant_product_pic() {
        return merchant_product_pic;
    }

    public void setMerchant_product_pic(List<String> merchant_product_pic) {
        this.merchant_product_pic = merchant_product_pic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.merchant_product_id);
        dest.writeString(this.merchant_product_name);
        dest.writeString(this.merchant_product_md);
        dest.writeString(this.merchant_product_mdprice);
        dest.writeString(this.merchant_product_price);
        dest.writeString(this.merchant_product_shopname);
        dest.writeString(this.merchant_product_shoppic);
        dest.writeString(this.merchant_order_num);
        dest.writeString(this.merchant_order_sn);
        dest.writeString(this.merchant_order_oprice);
        dest.writeString(this.merchant_order_fc);
        dest.writeString(this.merchant_order_token);
        dest.writeString(this.merchant_order_id);
        dest.writeString(this.merchant_order_specification);
        dest.writeString(this.merchant_order_uid);
        dest.writeString(this.merchant_order_ctime);
        dest.writeString(this.merchant_order_tn);
        dest.writeString(this.merchant_order_mdprice);
        dest.writeStringList(this.merchant_product_pic);
    }

    public MallSaleOrderBean() {
    }

    protected MallSaleOrderBean(Parcel in) {
        this.merchant_product_id = in.readString();
        this.merchant_product_name = in.readString();
        this.merchant_product_md = in.readString();
        this.merchant_product_mdprice = in.readString();
        this.merchant_product_price = in.readString();
        this.merchant_product_shopname = in.readString();
        this.merchant_product_shoppic = in.readString();
        this.merchant_order_num = in.readString();
        this.merchant_order_sn = in.readString();
        this.merchant_order_oprice = in.readString();
        this.merchant_order_fc = in.readString();
        this.merchant_order_token = in.readString();
        this.merchant_order_id = in.readString();
        this.merchant_order_specification = in.readString();
        this.merchant_order_uid = in.readString();
        this.merchant_order_ctime = in.readString();
        this.merchant_order_tn = in.readString();
        this.merchant_order_mdprice = in.readString();
        this.merchant_product_pic = in.createStringArrayList();
    }

    public static final Parcelable.Creator<MallSaleOrderBean> CREATOR = new Parcelable.Creator<MallSaleOrderBean>() {
        @Override
        public MallSaleOrderBean createFromParcel(Parcel source) {
            return new MallSaleOrderBean(source);
        }

        @Override
        public MallSaleOrderBean[] newArray(int size) {
            return new MallSaleOrderBean[size];
        }
    };
}
