package com.tomchua.accenturehackathon.Object;

public class YogurtObject {
    public int id;
    public String yogurtname;
    public String price;
    public int quantity;
    public String totalprice;


    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }



    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYogurtname() {
        return yogurtname;
    }

    public void setYogurtname(String yogurtname) {
        this.yogurtname = yogurtname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}
