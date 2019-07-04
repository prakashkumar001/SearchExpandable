package com.zaigo.coordinatelayouttest.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadCartModel {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("order_item_name")
    @Expose
    private String orderItemName;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("total")
    @Expose
    private String total;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderItemName() {
        return orderItemName;
    }

    public void setOrderItemName(String orderItemName) {
        this.orderItemName = orderItemName;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }


}
