package com.zaigo.coordinatelayouttest.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "bodydbmodel")
public class ProductDbModel {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "ID")
    @SerializedName("ID")
    @Expose
    private String iD;

    @Property(nameInDb = "Title")
    @SerializedName("Title")
    @Expose
    private String title;

    @Property(nameInDb = "Price")
    @SerializedName("Price")
    @Expose
    private String price;

    @Property(nameInDb = "CONTENT")
    @SerializedName("CONTENT")
    @Expose
    private String cONTENT;

    @Property(nameInDb = "mQuantity")
    private int mQuantity;

    @Generated(hash = 73041217)
    public ProductDbModel(Long id, String iD, String title, String price,
            String cONTENT, int mQuantity) {
        this.id = id;
        this.iD = iD;
        this.title = title;
        this.price = price;
        this.cONTENT = cONTENT;
        this.mQuantity = mQuantity;
    }

    @Generated(hash = 8818223)
    public ProductDbModel() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getID() {
        return this.iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCONTENT() {
        return this.cONTENT;
    }

    public void setCONTENT(String cONTENT) {
        this.cONTENT = cONTENT;
    }

    public int getMQuantity() {
        return this.mQuantity;
    }

    public void setMQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }
    public void addToQuantity(){
        this.mQuantity += 1;
    }

    public void removeFromQuantity(){
        if(this.mQuantity > 1){
            this.mQuantity -= 1;
        }
    }
}
