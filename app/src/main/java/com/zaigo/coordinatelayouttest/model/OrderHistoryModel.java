package com.zaigo.coordinatelayouttest.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderHistoryModel {

    @SerializedName("status_code")
    @Expose
    private String statusCode;
    @SerializedName("result")
    @Expose
    private List<OrderHistoryResult> result = null;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public List<OrderHistoryResult> getResult() {
        return result;
    }

    public void setResult(List<OrderHistoryResult> result) {
        this.result = result;
    }

}