package com.zaigo.coordinatelayouttest.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreCategoryModel {


    @SerializedName("status_code")
    @Expose
    private String statusCode;
    @SerializedName("store_list")
    @Expose
    private List<StoreList> storeList = null;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public List<StoreList> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<StoreList> storeList) {
        this.storeList = storeList;
    }


}
