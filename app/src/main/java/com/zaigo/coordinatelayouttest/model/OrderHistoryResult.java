package com.zaigo.coordinatelayouttest.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderHistoryResult {

    @SerializedName("Order_Number")
    @Expose
    private String orderNumber;
    @SerializedName("Total")
    @Expose
    private String total;
    @SerializedName("Date_Status")
    @Expose
    private List<DateStatus> dateStatus = null;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<DateStatus> getDateStatus() {
        return dateStatus;
    }

    public void setDateStatus(List<DateStatus> dateStatus) {
        this.dateStatus = dateStatus;
    }

}