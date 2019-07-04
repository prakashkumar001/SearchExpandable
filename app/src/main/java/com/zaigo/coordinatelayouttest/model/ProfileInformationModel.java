package com.zaigo.coordinatelayouttest.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class ProfileInformationModel {

    @SerializedName("status_code")
    @Expose
    private String statusCode;
    @SerializedName("result")
    @Expose
    private List<ProfileResult> result = null;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public List<ProfileResult> getResult() {
        return result;
    }

    public void setResult(List<ProfileResult> result) {
        this.result = result;
    }
}
