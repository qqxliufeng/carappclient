package com.android.ql.lf.carappclient.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lf on 2017/12/1 0001.
 *
 * @author lf on 2017/12/1 0001
 */

public class AddressBean implements Parcelable {

    private String merchant_address_id;
    private String merchant_address_name;
    private String merchant_address_phone;
    private String merchant_address_addres;
    private String merchant_address_user;
    private String merchant_address_token;
    private String merchant_address_detail;
    private String merchant_address_postcode;

    public String getMerchant_address_id() {
        return merchant_address_id;
    }

    public void setMerchant_address_id(String merchant_address_id) {
        this.merchant_address_id = merchant_address_id;
    }

    public String getMerchant_address_name() {
        return merchant_address_name;
    }

    public void setMerchant_address_name(String merchant_address_name) {
        this.merchant_address_name = merchant_address_name;
    }

    public String getMerchant_address_phone() {
        return merchant_address_phone;
    }

    public void setMerchant_address_phone(String merchant_address_phone) {
        this.merchant_address_phone = merchant_address_phone;
    }

    public String getMerchant_address_addres() {
        return merchant_address_addres;
    }

    public void setMerchant_address_addres(String merchant_address_addres) {
        this.merchant_address_addres = merchant_address_addres;
    }

    public String getMerchant_address_user() {
        return merchant_address_user;
    }

    public void setMerchant_address_user(String merchant_address_user) {
        this.merchant_address_user = merchant_address_user;
    }

    public String getMerchant_address_token() {
        return merchant_address_token;
    }

    public void setMerchant_address_token(String merchant_address_token) {
        this.merchant_address_token = merchant_address_token;
    }

    public String getMerchant_address_detail() {
        return merchant_address_detail;
    }

    public void setMerchant_address_detail(String merchant_address_detail) {
        this.merchant_address_detail = merchant_address_detail;
    }

    public String getMerchant_address_postcode() {
        return merchant_address_postcode;
    }

    public void setMerchant_address_postcode(String merchant_address_postcode) {
        this.merchant_address_postcode = merchant_address_postcode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.merchant_address_id);
        dest.writeString(this.merchant_address_name);
        dest.writeString(this.merchant_address_phone);
        dest.writeString(this.merchant_address_addres);
        dest.writeString(this.merchant_address_user);
        dest.writeString(this.merchant_address_token);
        dest.writeString(this.merchant_address_detail);
        dest.writeString(this.merchant_address_postcode);
    }

    public AddressBean() {
    }

    protected AddressBean(Parcel in) {
        this.merchant_address_id = in.readString();
        this.merchant_address_name = in.readString();
        this.merchant_address_phone = in.readString();
        this.merchant_address_addres = in.readString();
        this.merchant_address_user = in.readString();
        this.merchant_address_token = in.readString();
        this.merchant_address_detail = in.readString();
        this.merchant_address_postcode = in.readString();
    }

    public static final Creator<AddressBean> CREATOR = new Creator<AddressBean>() {
        @Override
        public AddressBean createFromParcel(Parcel source) {
            return new AddressBean(source);
        }

        @Override
        public AddressBean[] newArray(int size) {
            return new AddressBean[size];
        }
    };
}
