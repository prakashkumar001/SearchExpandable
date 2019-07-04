package com.zaigo.coordinatelayouttest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "product")
public class Product {



    @Id(autoincrement = true)
    private Long id;
    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Price")
    @Expose
    private String price;
    @SerializedName("CONTENT")
    @Expose
    private String cONTENT;
    @SerializedName("IMAGES")
    @Expose
    private String iMAGES;

    public void setmQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

    private int mQuantity;



    public Product(String iD, String title,String price,String cONTENT) {
        this.iD = iD;
        this.title = title;
        this.price =price;
        this.cONTENT =cONTENT;
        this.mQuantity = 1;
    }


    @Generated(hash = 1556911914)
    public Product(Long id, String iD, String title, String price, String cONTENT,
            String iMAGES, int mQuantity) {
        this.id = id;
        this.iD = iD;
        this.title = title;
        this.price = price;
        this.cONTENT = cONTENT;
        this.iMAGES = iMAGES;
        this.mQuantity = mQuantity;
    }


    @Generated(hash = 1890278724)
    public Product() {
    }


    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCONTENT() {
        return cONTENT;
    }

    public void setCONTENT(String cONTENT) {
        this.cONTENT = cONTENT;
    }
    public int getmQuantity(){
        return mQuantity;
    }

    public void addToQuantity(){
        this.mQuantity += 1;
    }

    public void removeFromQuantity(){
        if(this.mQuantity > 1){
            this.mQuantity -= 1;
        }
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public int getMQuantity() {
        return this.mQuantity;
    }


    public void setMQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

    public String getIMAGES() {
        return iMAGES;
    }

    public void setIMAGES(String iMAGES) {
        this.iMAGES = iMAGES;
    }
    /*public int getMQuantity() {
        return this.mQuantity;
    }


    public void setMQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }*/
}