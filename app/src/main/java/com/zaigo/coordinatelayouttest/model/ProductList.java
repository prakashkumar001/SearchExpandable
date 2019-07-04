package com.zaigo.coordinatelayouttest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductList {

    @SerializedName("Product_ID")
    @Expose
    private String productID;
    @SerializedName("Product_Name")
    @Expose
    private String productName;
    @SerializedName("Order_Quantity")
    @Expose
    private String orderQuantity;
    @SerializedName("Product_total")
    @Expose
    private String productTotal;
    @SerializedName("Qty")
    @Expose
    private String qty;
    @SerializedName("PRICE")
    @Expose
    private String pRICE;
    @SerializedName("Img")
    @Expose
    private String img;
    @SerializedName("SKU")
    @Expose
    private String sKU;
    @SerializedName("STOCK")
    @Expose
    private String sTOCK;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(String orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(String productTotal) {
        this.productTotal = productTotal;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPRICE() {
        return pRICE;
    }

    public void setPRICE(String pRICE) {
        this.pRICE = pRICE;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSKU() {
        return sKU;
    }

    public void setSKU(String sKU) {
        this.sKU = sKU;
    }

    public String getSTOCK() {
        return sTOCK;
    }

    public void setSTOCK(String sTOCK) {
        this.sTOCK = sTOCK;
    }

}