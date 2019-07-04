package com.zaigo.coordinatelayouttest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Caps {

    @SerializedName("nr_customer")
    @Expose
    private Boolean nrCustomer;

    public Boolean getNrCustomer() {
        return nrCustomer;
    }

    public void setNrCustomer(Boolean nrCustomer) {
        this.nrCustomer = nrCustomer;
    }

}