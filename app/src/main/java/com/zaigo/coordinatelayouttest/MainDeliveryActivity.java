package com.zaigo.coordinatelayouttest;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zaigo.coordinatelayouttest.common.PreferenceManager;
import com.zaigo.coordinatelayouttest.common.Utility;
import com.zaigo.coordinatelayouttest.model.CouponCodeModel;
import com.zaigo.coordinatelayouttest.model.DaoSession;
import com.zaigo.coordinatelayouttest.model.DataPassModel;
import com.zaigo.coordinatelayouttest.model.LoginModel;
import com.zaigo.coordinatelayouttest.model.PlaceOrderModel;
import com.zaigo.coordinatelayouttest.model.Product;
import com.zaigo.coordinatelayouttest.model.ProductDao;
import com.zaigo.coordinatelayouttest.model.UploadCartModel;
import com.zaigo.coordinatelayouttest.restapi.ApiClient;
import com.zaigo.coordinatelayouttest.restapi.ApiInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainDeliveryActivity extends AppCompatActivity {

    RadioButton radioButton1,radioButton2,radio_cardpayment,radioonlinepayment;
    RadioGroup radiogroup1,radiogroup2;

   TextView txt_shippingcharge,txt_subtotal,txt_discount,txt_total;

    String str_check="";

    Button btn_submit,btn_readeem;

    String str_states="",str_subtotal="";

    double dou_discount=0.0,dou_subtotal=0.0,dou_shipping=0.0,dou_total=0.0;


    EditText ed_vochercode;

    String str_pickfromshop;

    ProductDao productDao;

    String cardpay;

    DataPassModel dpmsub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#000000"));
        }
        setContentView(R.layout.activity_main_delivery);
        DaoSession daoSession = ((App)MainDeliveryActivity.this.getApplication()).getDaoSession();
        productDao = daoSession.getProductDao();
        dpmsub = new DataPassModel();
        Intent i = getIntent();







        str_states= i.getStringExtra("state");


        Bundle bundle = getIntent().getExtras();

//Extract the data…


      /*  if(str_pickfromshop != null){
            str_pickfromshop= i.getStringExtra("pickfromshop");


        }*/
        if(str_check != null){

            str_check= i.getStringExtra("delivery");
        }


        str_pickfromshop = bundle.getString("stuff");


        if(str_subtotal==null){

            str_subtotal= ""+dpmsub.getSubTotal();

            System.out.println("");


        }else{



            str_subtotal=i.getStringExtra("subtotal");


        }
        System.out.println("SUBTOTALhhhhhhhhhhhhh"+str_subtotal+","+dpmsub.getSubTotal());
        System.out.println("ttttttttttttttttt222222222222222"+str_pickfromshop+str_check+str_pickfromshop);

        radioButton1 = (RadioButton)findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton)findViewById(R.id.radioButton2);
        radiogroup1 = (RadioGroup)findViewById(R.id.radiogroup1);




        radiogroup2 = (RadioGroup)findViewById(R.id.radiogroup2);
        radio_cardpayment = (RadioButton)findViewById(R.id.radio_cardpayment);
        radioonlinepayment = (RadioButton)findViewById(R.id.radioonlinepayment);


        btn_submit = (Button)findViewById(R.id.btn_submit);

        txt_shippingcharge = (TextView) findViewById(R.id.txt_shippingcharge);

        txt_subtotal = (TextView) findViewById(R.id.txt_subtotal);
        txt_discount = (TextView) findViewById(R.id.txt_discount);
        txt_total = (TextView) findViewById(R.id.txt_total);

        ed_vochercode = (EditText)findViewById(R.id.ed_vochercode);
        btn_readeem = (Button)findViewById(R.id.btn_readeem);

        txt_subtotal.setText("AED "+""+dpmsub.getSubTotal());



        dou_subtotal =Double.parseDouble(""+dpmsub.getSubTotal());





        if(str_states.trim().equalsIgnoreCase("ABU DHABI")){

            if(Double.parseDouble(""+dpmsub.getSubTotal())>=300){
                txt_shippingcharge.setText("AED 0.0");

                dou_shipping=0.0;
                if(str_check != null) {
                    if (str_check.equalsIgnoreCase("delivery")) {
                        radioButton2.setChecked(true);
                    }
                }
                if(str_pickfromshop != null) {
                    if (str_pickfromshop.equalsIgnoreCase("pickfromshop")) {
                        radioButton1.setChecked(true);
                    }
                }


            }else{

                System.out.println("tttttttttttttttttttttt1111111111111111");
                if(str_check != null) {
                    if (str_check.equalsIgnoreCase("delivery")) {
                        txt_shippingcharge.setText("AED 50.0");
                        radioButton2.setChecked(true);
                        dou_shipping = 50.0;
                    } else {




                        txt_shippingcharge.setText("AED 0.0");

                        dou_shipping = 0.0;
                    }
                }

                if(str_pickfromshop != null) {
                    if (str_pickfromshop.equalsIgnoreCase("pickfromshop")) {
                        radioButton1.setChecked(true);
                        txt_shippingcharge.setText("AED 0.0");

                        dou_shipping = 0.0;
                    }
                }



            }


        }else if(str_states.trim().equalsIgnoreCase("DUBAI")){

            if(Double.parseDouble(""+dpmsub.getSubTotal())>=250){
                txt_shippingcharge.setText("AED 0.0");

                dou_shipping=0.0;

                if(str_check != null) {
                    if (str_check.equalsIgnoreCase("delivery")) {
                        radioButton2.setChecked(true);
                    }
                }

                if(str_pickfromshop != null) {
                    if (str_pickfromshop.equalsIgnoreCase("pickfromshop")) {
                        radioButton1.setChecked(true);
                    }
                }
            }else{

                if(str_check != null) {
                    if (str_check.equalsIgnoreCase("delivery")) {
                        txt_shippingcharge.setText("AED 25.0");
                        radioButton2.setChecked(true);
                        dou_shipping = 25.0;
                    } else {


                        txt_shippingcharge.setText("AED 0.0");

                        dou_shipping = 0.0;
                    }


                }

                if(str_pickfromshop != null) {
                    if (str_pickfromshop.equalsIgnoreCase("pickfromshop")) {
                        radioButton1.setChecked(true);
                        txt_shippingcharge.setText("AED 0.0");

                        dou_shipping = 0.0;
                    }
                }

            }

        }else  if(str_states.trim().equalsIgnoreCase("SHARJAH")){

            if(Double.parseDouble(""+dpmsub.getSubTotal())>=300){
                txt_shippingcharge.setText("AED 0.0");

                dou_shipping=0.0;
                if(str_check != null) {
                    if (str_check.equalsIgnoreCase("delivery")) {
                        radioButton2.setChecked(true);
                    }
                }

                if(str_pickfromshop != null) {
                    if (str_pickfromshop.equalsIgnoreCase("pickfromshop")) {
                        radioButton1.setChecked(true);
                    }
                }

            }else{

                if(str_check != null) {

                    if (str_check.equalsIgnoreCase("delivery")) {
                        txt_shippingcharge.setText("AED 35.0");

                        dou_shipping = 35.0;

                        radioButton2.setChecked(true);

                    } else {


                        txt_shippingcharge.setText("AED 0.0");

                        dou_shipping = 0.0;
                    }

                }
                if(str_pickfromshop != null) {
                    if (str_pickfromshop.equalsIgnoreCase("pickfromshop")) {
                        radioButton1.setChecked(true);
                        txt_shippingcharge.setText("AED 0.0");

                        dou_shipping = 0.0;
                    }
                }
            }

        }



        dou_total = dou_discount + dou_shipping + dou_subtotal;


        txt_total.setText("AED "+dou_total);


        DataPassModel dpm = new DataPassModel();

        dpm.setCoup_code("");
        dpm.setCoup_act_amt("");
        dpm.setShipping_charge(""+dou_shipping);
        dpm.set_order_total(""+dou_total);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataPassModel dpm = new DataPassModel();
                System.out.println("TESTTTTTTTTTTT  "+dpm.getNw_lat()+","+dpm.getNw_long()+","+dpm.getShip_opt_time()+","+dpm.getShipping_charge());

              //  System.out.println("TESTTTTTTTTTTT"+dpm.getComment_content()+","+dpm.getShip_opt_date()+","+dpm.getShip_opt_time()+","+dpm.getShipping_charge());



            //    System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRR"+dpm.get_billing_first_name()+","+dpm.getNw_area()+","+dpm.get_order_total()+","+dpm.getShip_opt()+","+dpm.getShip_opt_shop());


               // System.out.println("TTTTTTTTTTTYYYYYYY"+dpm.getCoup_code()+","+dpm.getCoup_act_amt()+","+dpm.get_billing_first_name()+","+dpm.get_billing_phone()+","+dpm.getNw_lat()+","+dpm.getNw_long()+","+dpm.get_billing_state()+","+dpm.get_billing_country()+","+dpm.get_billing_email()+","+dpm.get_order_total()+","+dpm.getShipping_charge()+","+dpm.getNw_street()+","+dpm.getNw_build()+","+dpm.getComment_content()+","+dpm.getShip_opt_shop()+","+dpm.getShip_opt_date()+","+dpm.getShip_opt_time()+","+dpm.getShip_opt());


                if(dpm.getShip_opt() !=null) {
                    if (!dpm.getShip_opt().isEmpty()) {


                        if(cardpay != null){
                            if (cardpay.trim().equalsIgnoreCase("2")) {




                                cartApiCalling();


                            } else {
                                Toast.makeText(MainDeliveryActivity.this, "Please select your payment method", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(MainDeliveryActivity.this, "Please select your payment method", Toast.LENGTH_SHORT).show();
                        }


                    } else {

                        Toast.makeText(MainDeliveryActivity.this, "Please select your shipping", Toast.LENGTH_SHORT).show();
                    }


                }else{
                    Toast.makeText(MainDeliveryActivity.this, "Please select your shipping", Toast.LENGTH_SHORT).show();
                }
                
                
                


                List<Product> orders = productDao.loadAll();
                List<UploadCartModel> test = new ArrayList<>();

              for(int i =0; i<orders.size(); i++){


                  UploadCartModel uc = new UploadCartModel();

                  uc.setId(orders.get(i).getID());
                  uc.setOrderItemName(orders.get(i).getTitle());
                  uc.setQty(""+orders.get(i).getmQuantity());

                  double ttt = Double.parseDouble(""+orders.get(i).getmQuantity()) * Double.parseDouble(""+orders.get(i).getPrice());

                  uc.setTotal(""+ttt);



                  test.add(uc);


              }







                System.out.println("RRRRRRRRRRRRRRRRRRRRR"+new Gson().toJson(test));



               /* if(str_check.equalsIgnoreCase("first")){

                    Intent i = new Intent(MainDeliveryActivity.this,PickfromShopActivity.class);
                    startActivity(i);

                }else{
                    Intent i = new Intent(MainDeliveryActivity.this,PaymentDeliveryActivity.class);
                    startActivity(i);
                }*/
            }
        });

        btn_readeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.isConnected(MainDeliveryActivity.this)) {

                    if(ed_vochercode.getText().toString().trim().length()>1){


                        couponCodeApiCalling();


                    }else{
                        Toast.makeText(getApplicationContext(), "Please enter coupon code", Toast.LENGTH_SHORT).show();

                    }

                }else{

                    Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        radiogroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(radioButton1.isChecked())
                {

                    Intent i = new Intent(MainDeliveryActivity.this,PickfromShopActivity.class);

                    i.putExtra("subtotal",""+dpmsub.getSubTotal());
                    i.putExtra("state",str_states);

                   // str_pickfromshop="pickfromshop";
                    startActivity(i);

                    finish();



                   // Toast.makeText(MainDeliveryActivity.this, "Male", Toast.LENGTH_SHORT).show();
                }
                else {

                    Intent i = new Intent(MainDeliveryActivity.this,PaymentDeliveryActivity.class);
                  //  str_check="delivery";
                    i.putExtra("subtotal",""+dpmsub.getSubTotal());
                    i.putExtra("state",str_states);
                    startActivity(i);
                 finish();
                   // Toast.makeText(MainDeliveryActivity.this, "Female", Toast.LENGTH_SHORT).show();
                }


            }
        });
        radiogroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(radio_cardpayment.isChecked())
                {


                     cardpay ="2";



                }
                else {


                    cardpay ="";
                    // Toast.makeText(MainDeliveryActivity.this, "Female", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }


    public  void couponCodeApiCalling(){








        ApiInterface apiService =
                ApiClient.getClientArrayParse().create(ApiInterface.class);






        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(MainDeliveryActivity.this);
        progressDoalog.setMessage("Loading....");
        // show it
        progressDoalog.show();
        progressDoalog.setCancelable(false);

/*
        Super Admin Login Details:
        Username: admin@zaigoinfotech.com
        Password: Ex58D??Yy44-PaBG
        */

        Call<CouponCodeModel> call = apiService.getCuponCode(PreferenceManager.getUser_Id(getApplicationContext()),""+ed_vochercode.getText().toString());
        call.enqueue(new Callback<CouponCodeModel>() {
            @Override
            public void onResponse(Call<CouponCodeModel> call,final Response<CouponCodeModel> response) {


                Log.e("TAG", "responseTestsssss: "+new Gson().toJson(response.body()) );
//                System.out.println("SHIPPINGSSSSSSSSS"+response.body().getBillingAddress().getFirstName()+",,"+response.body().getFirstName());





                if (response.body().getMessage() != null) {




                    if(response.body().getMessage().trim().equalsIgnoreCase("Invalid Coupon Code")){


                        //  String cap = response.body().getDetails().getMessage().substring(0, 1).toUpperCase() + response.body().getDetails().getMessage().substring(1);

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainDeliveryActivity.this);




                        builder1.setMessage(response.body().getMessage());
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();


                                        txt_subtotal.setText("AED "+""+dpmsub.getSubTotal());

                                        dou_subtotal =Double.parseDouble(""+dpmsub.getSubTotal());





                                        if(str_states.trim().equalsIgnoreCase("ABU DHABI")){

                                            if(Double.parseDouble(""+dpmsub.getSubTotal())>=300){
                                                txt_shippingcharge.setText("AED 0.0");

                                                dou_shipping=0.0;
                                                if(str_check != null) {
                                                    if (str_check.equalsIgnoreCase("delivery")) {
                                                        radioButton2.setChecked(true);
                                                    }
                                                }
                                                if(str_pickfromshop != null) {
                                                    if (str_pickfromshop.equalsIgnoreCase("pickfromshop")) {
                                                        radioButton1.setChecked(true);
                                                    }
                                                }


                                            }else{

                                                System.out.println("tttttttttttttttttttttt1111111111111111");
                                                if(str_check != null) {
                                                    if (str_check.equalsIgnoreCase("delivery")) {
                                                        txt_shippingcharge.setText("AED 50.0");
                                                        radioButton2.setChecked(true);
                                                        dou_shipping = 50.0;
                                                    } else {




                                                        txt_shippingcharge.setText("AED 0.0");

                                                        dou_shipping = 0.0;
                                                    }
                                                }

                                                if(str_pickfromshop != null) {
                                                    if (str_pickfromshop.equalsIgnoreCase("pickfromshop")) {
                                                        radioButton1.setChecked(true);
                                                        txt_shippingcharge.setText("AED 0.0");

                                                        dou_shipping = 0.0;
                                                    }
                                                }



                                            }


                                        }else if(str_states.trim().equalsIgnoreCase("DUBAI")){

                                            if(Double.parseDouble(""+dpmsub.getSubTotal())>=250){
                                                txt_shippingcharge.setText("AED 0.0");

                                                dou_shipping=0.0;

                                                if(str_check != null) {
                                                    if (str_check.equalsIgnoreCase("delivery")) {
                                                        radioButton2.setChecked(true);
                                                    }
                                                }

                                                if(str_pickfromshop != null) {
                                                    if (str_pickfromshop.equalsIgnoreCase("pickfromshop")) {
                                                        radioButton1.setChecked(true);
                                                    }
                                                }
                                            }else{

                                                if(str_check != null) {
                                                    if (str_check.equalsIgnoreCase("delivery")) {
                                                        txt_shippingcharge.setText("AED 25.0");
                                                        radioButton2.setChecked(true);
                                                        dou_shipping = 25.0;
                                                    } else {


                                                        txt_shippingcharge.setText("AED 0.0");

                                                        dou_shipping = 0.0;
                                                    }


                                                }

                                                if(str_pickfromshop != null) {
                                                    if (str_pickfromshop.equalsIgnoreCase("pickfromshop")) {
                                                        radioButton1.setChecked(true);
                                                        txt_shippingcharge.setText("AED 0.0");

                                                        dou_shipping = 0.0;
                                                    }
                                                }

                                            }

                                        }else  if(str_states.trim().equalsIgnoreCase("SHARJAH")){

                                            if(Double.parseDouble(""+dpmsub.getSubTotal())>=300){
                                                txt_shippingcharge.setText("AED 0.0");

                                                dou_shipping=0.0;
                                                if(str_check != null) {
                                                    if (str_check.equalsIgnoreCase("delivery")) {
                                                        radioButton2.setChecked(true);
                                                    }
                                                }

                                                if(str_pickfromshop != null) {
                                                    if (str_pickfromshop.equalsIgnoreCase("pickfromshop")) {
                                                        radioButton1.setChecked(true);
                                                    }
                                                }

                                            }else{

                                                if(str_check != null) {

                                                    if (str_check.equalsIgnoreCase("delivery")) {
                                                        txt_shippingcharge.setText("AED 35.0");

                                                        dou_shipping = 35.0;

                                                        radioButton2.setChecked(true);

                                                    } else {


                                                        txt_shippingcharge.setText("AED 0.0");

                                                        dou_shipping = 0.0;
                                                    }

                                                }
                                                if(str_pickfromshop != null) {
                                                    if (str_pickfromshop.equalsIgnoreCase("pickfromshop")) {
                                                        radioButton1.setChecked(true);
                                                        txt_shippingcharge.setText("AED 0.0");

                                                        dou_shipping = 0.0;
                                                    }
                                                }
                                            }

                                        }


                                        dou_total = dou_discount + dou_shipping + dou_subtotal;


                                        txt_total.setText("AED "+dou_total);



                                        ed_vochercode.setText("");

                                        txt_discount.setText("AED "+dou_discount);




                                        DataPassModel dpm = new DataPassModel();

                                        dpm.setCoup_code("");
                                        dpm.setCoup_act_amt(""+dou_discount);
                                        dpm.setShipping_charge(""+dou_shipping);

                                        dpm.set_order_total(""+dou_total);




                                    }
                                });



                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                    }else{


                      //  Utility.alertDialog(MainDeliveryActivity.this,response.body().getMessage());
                        //  Toast.makeText(LoginActivity.this, ""+response.body().getDetails().getMessage(), Toast.LENGTH_SHORT).show();
                    }



                } else {



                    Toast.makeText(MainDeliveryActivity.this, "Your  coupon code applied", Toast.LENGTH_SHORT).show();






                    ed_vochercode.setText(ed_vochercode.getText().toString());


                    double perchantage = Double.parseDouble(response.body().getPercentage());




                    dou_subtotal =Double.parseDouble(""+dpmsub.getSubTotal());


                    System.out.println("AAAAAAAAAAAAAAAAAAA  "+dou_subtotal+","+perchantage);

                    System.out.println(
                            "Percentage obtained: " + calculatePercentage(perchantage, dou_subtotal));

                double calPrechantage=    calculatePercentage(perchantage, dou_subtotal);


                double minAmount = Double.parseDouble(response.body().getMinimumAmount());



                    if(calPrechantage >= minAmount){

                        System.out.println("trueeeeeeeeeeeeee");

                        txt_discount.setText("AED "+minAmount);




                        double subtractTotal =dou_subtotal - minAmount;

                       // txt_subtotal.setText("AED "+subtractTotal);

                        txt_subtotal.setText("AED "+""+dpmsub.getSubTotal());
                        //  alertDialog(""+upperCaseFirst(response.body().getError()));
//                        System.out.println("fffffffffffffff"+response.body().getError());

                        dou_total =   dou_shipping + subtractTotal;


                        txt_total.setText("AED "+dou_total);


                        DataPassModel dpm = new DataPassModel();

                        dpm.setCoup_code(ed_vochercode.getText().toString());
                        dpm.setCoup_act_amt(""+minAmount);
                        dpm.setShipping_charge(""+dou_shipping);
                        dpm.set_order_total(""+dou_total);


                    }else{
                        txt_discount.setText("AED "+calculatePercentage(perchantage, dou_subtotal));


                        double subtractTotal =dou_subtotal -calculatePercentage(perchantage, dou_subtotal);

                       // txt_subtotal.setText("AED "+subtractTotal);

                        txt_subtotal.setText("AED "+""+dpmsub.getSubTotal());
                        //  alertDialog(""+upperCaseFirst(response.body().getError()));
//                        System.out.println("fffffffffffffff"+response.body().getError());

                        dou_total =   dou_shipping + subtractTotal;


                        txt_total.setText("AED "+dou_total);



                        DataPassModel dpm = new DataPassModel();

                        dpm.setCoup_code(ed_vochercode.getText().toString());
                        dpm.setCoup_act_amt(""+calculatePercentage(perchantage, dou_subtotal));
                        dpm.setShipping_charge(""+dou_shipping);
                        dpm.set_order_total(""+dou_total);
                    }






                }


                progressDoalog.dismiss();

                //recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<CouponCodeModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("TAGggggggg","errrrrrrorrrrrrrr"+ t.toString());

                progressDoalog.dismiss();
            }
        });

    }
    public double calculatePercentage(double obtained, double total) {
        return total * obtained / 100;
    }





    public  void cartApiCalling(){








        List<Product> orders = productDao.loadAll();
        List<UploadCartModel> test = new ArrayList<>();

        for(int i =0; i<orders.size(); i++){


            UploadCartModel uc = new UploadCartModel();

            uc.setId(orders.get(i).getID());
            uc.setOrderItemName(orders.get(i).getTitle());
            uc.setQty(""+orders.get(i).getmQuantity());

            double ttt = Double.parseDouble(""+orders.get(i).getmQuantity()) * Double.parseDouble(""+orders.get(i).getPrice());

            uc.setTotal(""+ttt);



            test.add(uc);


        }




        String str_json =""+new Gson().toJson(test);



        DataPassModel dataPassModel = new DataPassModel();


        System.out.println("vvvvvvvvvvvvvvvvvvvvvv"+dataPassModel.getShipping_charge()+","+dataPassModel.getNw_lat()+","+dataPassModel.getNw_long()+","+dataPassModel.get_order_total());

        System.out.println("RRRRRRRRRRRRRRRRRRRRR"+str_json);




        ApiInterface apiService =
                ApiClient.getClientArrayParse().create(ApiInterface.class);






        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(MainDeliveryActivity.this);
        progressDoalog.setMessage("Loading....");
        // show it
        progressDoalog.show();
        progressDoalog.setCancelable(false);

/*
        Super Admin Login Details:
        Username: admin@zaigoinfotech.com
        Password: Ex58D??Yy44-PaBG
        */

        Call<PlaceOrderModel> call = apiService.getPlaceOrder(""+PreferenceManager.getUser_Id(getApplicationContext()),""+str_json,""+dataPassModel.getCoup_code(),""+dataPassModel.getCoup_act_amt(),""+dataPassModel.get_billing_first_name(),""+dataPassModel.get_billing_phone(),""+dataPassModel.getNw_lat(),""+dataPassModel.getNw_long(),""+dataPassModel.get_billing_state(),""+dataPassModel.get_billing_country(),""+dataPassModel.get_billing_email(),""+dataPassModel.get_order_total(),""+txt_shippingcharge.getText().toString(),""+dataPassModel.getNw_area(),""+dataPassModel.getNw_street(),""+dataPassModel.getNw_build(),""+dataPassModel.getComment_content(),""+dataPassModel.getShip_opt_shop(),""+dataPassModel.getShip_opt_date(),""+dataPassModel.getShip_opt_time(),""+dataPassModel.getShip_opt(),""+cardpay);
        call.enqueue(new Callback<PlaceOrderModel>() {
            @Override
            public void onResponse(Call<PlaceOrderModel> call,final Response<PlaceOrderModel> response) {


                Log.e("TAG", "responseTestsssss: "+new Gson().toJson(response.body()) );
//                System.out.println("SHIPPINGSSSSSSSSS"+response.body().getBillingAddress().getFirstName()+",,"+response.body().getFirstName());





                if (response.body().getMessage() != null) {



                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainDeliveryActivity.this);




                    builder1.setMessage(response.body().getMessage());
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();


                                    Intent i = new Intent(MainDeliveryActivity.this,NavigationDrawerActivity.class);


                                    productDao.deleteAll();

                                    startActivity(i);

                                    finish();


                                }
                            });



                    AlertDialog alert11 = builder1.create();
                    alert11.show();






                } else {
                       Toast.makeText(MainDeliveryActivity.this, " Order Placed Not Successfully", Toast.LENGTH_SHORT).show();

                    //  alertDialog(""+upperCaseFirst(response.body().getError()));
//                        System.out.println("fffffffffffffff"+response.body().getError());
                }


                progressDoalog.dismiss();

                //recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<PlaceOrderModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("TAGggggggg","errrrrrrorrrrrrrr"+ t.toString());

                progressDoalog.dismiss();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(MainDeliveryActivity.this,NavigationDrawerActivity.class);




        startActivity(i);

        finish();

        super.onBackPressed();
    }
}
