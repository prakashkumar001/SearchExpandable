package com.zaigo.coordinatelayouttest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Delivery {

    @SerializedName("Shipping")
    @Expose
    private Object shipping;
    @SerializedName("Payment_method")
    @Expose
    private Object paymentMethod;
    @SerializedName("Order_Total")
    @Expose
    private Object orderTotal;
    @SerializedName("Email")
    @Expose
    private Object email;
    @SerializedName("Phone")
    @Expose
    private Object phone;
    @SerializedName("Address")
    @Expose
    private Object address;

    public Object getShipping() {
        return shipping;
    }

    public void setShipping(Object shipping) {
        this.shipping = shipping;
    }

    public Object getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Object paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Object getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Object orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

}