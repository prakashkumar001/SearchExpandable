package com.zaigo.coordinatelayouttest.model;

public class DataPassModel {

  public static   String user_id;        //1
    public static  String json_string;        //2
    public static String coup_code;        //3
    public static String coup_act_amt;        //4
    public static String _billing_first_name;        //5
    public static String _billing_phone;        //6
    public static String nw_lat;        //7
    public static  String nw_long;        //8
    public static String _billing_state;        //9
    public static String _billing_country;        //10
    public static  String _billing_email;        //11
    public static String _order_total;        //12
    public static String shipping_charge;        //13
    public static String nw_area;        //14
    public static String nw_street;        //15
    public static String nw_build;        //16
    public static String comment_content;        //17
    public static  String ship_opt_shop;        //18
    public static String ship_opt_date;        //19
    public static  String ship_opt_time;        //20
    public static  String ship_opt;        //21
    public static String payment_method;        //22

    public static String getSubTotal() {
        return subTotal;
    }

    public static void setSubTotal(String subTotal) {
        DataPassModel.subTotal = subTotal;
    }

    public static String subTotal;




    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getJson_string() {
        return json_string;
    }

    public void setJson_string(String json_string) {
        this.json_string = json_string;
    }

    public String getCoup_code() {
        return coup_code;
    }

    public void setCoup_code(String coup_code) {
        this.coup_code = coup_code;
    }

    public String getCoup_act_amt() {
        return coup_act_amt;
    }

    public void setCoup_act_amt(String coup_act_amt) {
        this.coup_act_amt = coup_act_amt;
    }

    public String get_billing_first_name() {
        return _billing_first_name;
    }

    public void set_billing_first_name(String _billing_first_name) {
        this._billing_first_name = _billing_first_name;
    }

    public String get_billing_phone() {
        return _billing_phone;
    }

    public void set_billing_phone(String _billing_phone) {
        this._billing_phone = _billing_phone;
    }

    public String getNw_lat() {
        return nw_lat;
    }

    public void setNw_lat(String nw_lat) {
        this.nw_lat = nw_lat;
    }

    public String getNw_long() {
        return nw_long;
    }

    public void setNw_long(String nw_long) {
        this.nw_long = nw_long;
    }

    public String get_billing_state() {
        return _billing_state;
    }

    public void set_billing_state(String _billing_state) {
        this._billing_state = _billing_state;
    }

    public String get_billing_country() {
        return _billing_country;
    }

    public void set_billing_country(String _billing_country) {
        this._billing_country = _billing_country;
    }

    public String get_billing_email() {
        return _billing_email;
    }

    public void set_billing_email(String _billing_email) {
        this._billing_email = _billing_email;
    }

    public String get_order_total() {
        return _order_total;
    }

    public void set_order_total(String _order_total) {
        this._order_total = _order_total;
    }

    public String getShipping_charge() {
        return shipping_charge;
    }

    public void setShipping_charge(String shipping_charge) {
        this.shipping_charge = shipping_charge;
    }

    public String getNw_area() {
        return nw_area;
    }

    public void setNw_area(String nw_area) {
        this.nw_area = nw_area;
    }

    public String getNw_street() {
        return nw_street;
    }

    public void setNw_street(String nw_street) {
        this.nw_street = nw_street;
    }

    public String getNw_build() {
        return nw_build;
    }

    public void setNw_build(String nw_build) {
        this.nw_build = nw_build;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public String getShip_opt_shop() {
        return ship_opt_shop;
    }

    public void setShip_opt_shop(String ship_opt_shop) {
        this.ship_opt_shop = ship_opt_shop;
    }

    public String getShip_opt_date() {
        return ship_opt_date;
    }

    public void setShip_opt_date(String ship_opt_date) {
        this.ship_opt_date = ship_opt_date;
    }

    public String getShip_opt_time() {
        return ship_opt_time;
    }

    public void setShip_opt_time(String ship_opt_time) {
        this.ship_opt_time = ship_opt_time;
    }

    public String getShip_opt() {
        return ship_opt;
    }

    public void setShip_opt(String ship_opt) {
        this.ship_opt = ship_opt;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }




/*
  1  json_string : send products with details like in ofc application
  2  user_id  : current user id

    Coupon code:
 3   coup_code : copoun code
  4  coup_act_amt :discounted amount (after applied coupon code)

    Customer Details :
 5   _billing_first_name : Customer Full Name
 6   _billing_phone : Contact number
 7   nw_lat : latitude
 8   nw_long : longtitude
  9  _billing_state : state
  10  _billing_country : country
  11  _billing_email : email address
  12  _order_total : Total amount
  13  shipping_charge  : shipping charges
  14  nw_area : Area
  15  nw_street : Street
  16  nw_build : Building

 17   comment_content  : comments

    Pickup from shop :
  18  ship_opt_shop : pickup Shop
  19  ship_opt_date : pickup date
 20   ship_opt_time : pickup time

    Delivery :
    ship_opt_date  : Delivery Date
    ship_opt_time : Delivery Time

    Delivery OPtion  :
    If customer choose pickup from shop then
     21       ship_opt=1
    If customer choose Delivery then
            ship_opt=2



    Payment Method:
    If customer choose Online payment then
  22  payment_method = 1
    If customer choose card payment on delivery then
    payment_method= 2*/






}
