package com.zaigo.coordinatelayouttest.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class CouponCodeModel {


    @SerializedName("status_code")
    @Expose
    private String statusCode;
    @SerializedName("Percentage")
    @Expose
    private String percentage;
    @SerializedName("Minimum_amount")
    @Expose
    private String minimumAmount;

    @SerializedName("message")
    @Expose
    private String message;



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount(String minimumAmount) {
        this.minimumAmount = minimumAmount;
    }


}
