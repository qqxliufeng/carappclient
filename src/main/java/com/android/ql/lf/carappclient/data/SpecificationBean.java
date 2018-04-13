package com.android.ql.lf.carappclient.data;

import java.util.ArrayList;

/**
 * Created by liufeng on 2017/12/4.
 */

public class SpecificationBean {

    private String  name;
    private ArrayList<String> item;
    private ArrayList<String> pic;
    private ArrayList<String> repertory;
    private ArrayList<String> yprice;
    private ArrayList<String> price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getItem() {
        return item;
    }

    public void setItem(ArrayList<String> item) {
        this.item = item;
    }

    public ArrayList<String> getPic() {
        return pic;
    }

    public void setPic(ArrayList<String> pic) {
        this.pic = pic;
    }

    public ArrayList<String> getRepertory() {
        return repertory;
    }

    public void setRepertory(ArrayList<String> repertory) {
        this.repertory = repertory;
    }

    public ArrayList<String> getYprice() {
        return yprice;
    }

    public void setYprice(ArrayList<String> yprice) {
        this.yprice = yprice;
    }

    public ArrayList<String> getPrice() {
        return price;
    }

    public void setPrice(ArrayList<String> price) {
        this.price = price;
    }
}
