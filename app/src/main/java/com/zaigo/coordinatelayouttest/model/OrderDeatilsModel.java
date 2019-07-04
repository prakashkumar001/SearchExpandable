package com.zaigo.coordinatelayouttest.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OrderDeatilsModel {

    @SerializedName("status_code")
    @Expose
    private String statusCode;
    @SerializedName("result")
    @Expose
    private OrderDeatilsResult result;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public OrderDeatilsResult getResult() {
        return result;
    }

    public void setResult(OrderDeatilsResult result) {
        this.result = result;
    }
}
