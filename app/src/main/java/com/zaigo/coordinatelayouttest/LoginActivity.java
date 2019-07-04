package com.zaigo.coordinatelayouttest;

import android.Manifest;
import android.annotation.SuppressLint;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Color;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;




import com.google.gson.Gson;

import com.zaigo.coordinatelayouttest.common.PreferenceManager;
import com.zaigo.coordinatelayouttest.common.Utility;
import com.zaigo.coordinatelayouttest.model.LoginModel;

import com.zaigo.coordinatelayouttest.restapi.ApiClient;
import com.zaigo.coordinatelayouttest.restapi.ApiInterface;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class LoginActivity extends AppCompatActivity  {

    TextView txt_register,txt_forgetpassword;
    EditText ed_email,ed_password;
    Button btn_submit,btn_withoutlogin;
    GPSTracker gps;



    public static final int LOCATION_REQUEST = 1000;
    public static final int GPS_REQUEST = 1001;


    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;



    private boolean isContinue = false;
    private boolean isGPS = false;

    double latitude,longitude;

    String str_notloged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#000000"));
        }
        setContentView(R.layout.activity_login);


        Intent i = getIntent();

        str_notloged =  i.getStringExtra("notloged");

        gps = new GPSTracker(LoginActivity.this);

        if (ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_REQUEST);

        } else {
            if (isContinue) {
               // mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
            } else {

                if(gps.canGetLocation()){
                     latitude = gps.getLatitude();
                     longitude = gps.getLongitude();
                    // \n is for new line
                  //  Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

            }
        }





        txt_register = (TextView)findViewById(R.id.txt_register);
        txt_forgetpassword = (TextView)findViewById(R.id.txt_forgetpassword);

        ed_email=(EditText)findViewById(R.id.ed_email);
        ed_password=(EditText)findViewById(R.id.ed_password);

        btn_submit=(Button)findViewById(R.id.btn_submit);
        btn_withoutlogin=(Button)findViewById(R.id.btn_withoutlogin);
        btn_withoutlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,NavigationDrawerActivity.class);
                PreferenceManager.setUser_Id("",getApplicationContext());

                startActivity(i);
                finish();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.isConnected(LoginActivity.this)) {


                    if (ed_email.getText().toString().length() > 0 & ed_password.getText().toString().length() > 0) {

                      //  if (isValidEmail(ed_email.getText().toString())) {


                            loginApiCalling();


                       /* } else {
                            Toast.makeText(LoginActivity.this, "Please enter correct email", Toast.LENGTH_SHORT).show();
                        }
*/

                    } else {

                        Toast.makeText(LoginActivity.this, "Please enter all  fields", Toast.LENGTH_SHORT).show();

                    }
                }else{

                    Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txt_forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(i);
                finish();
            }
        });
        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
    }


    public  void loginApiCalling(){








        ApiInterface apiService =
                ApiClient.getClientArrayParse().create(ApiInterface.class);






        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(LoginActivity.this);
        progressDoalog.setMessage("Loading....");
        // show it
        progressDoalog.show();
        progressDoalog.setCancelable(false);

/*
        Super Admin Login Details:
        Username: admin@zaigoinfotech.com
        Password: Ex58D??Yy44-PaBG
        */

        Call<LoginModel> call = apiService.getLogin(ed_email.getText().toString(),""+ed_password.getText().toString(),"","Andriod");
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call,final Response<LoginModel> response) {


                Log.e("TAG", "responseTestsssss: "+new Gson().toJson(response.body()) );
//                System.out.println("SHIPPINGSSSSSSSSS"+response.body().getBillingAddress().getFirstName()+",,"+response.body().getFirstName());





                if (response.body().getMessage() != null) {



                    if(str_notloged == null){


                        if(response.body().getMessage().trim().equalsIgnoreCase("Logged in Success")){


                            //  String cap = response.body().getDetails().getMessage().substring(0, 1).toUpperCase() + response.body().getDetails().getMessage().substring(1);

                            AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);




                            builder1.setMessage(response.body().getMessage());
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();


                                            Intent i = new Intent(LoginActivity.this,NavigationDrawerActivity.class);


                                            PreferenceManager.setUser_Id(""+response.body().getResult().getID(),getApplicationContext());

                                            startActivity(i);

                                            finish();


                                        }
                                    });



                            AlertDialog alert11 = builder1.create();
                            alert11.show();

                        }else{


                            Utility.alertDialog(LoginActivity.this,response.body().getMessage());
                            //  Toast.makeText(LoginActivity.this, ""+response.body().getDetails().getMessage(), Toast.LENGTH_SHORT).show();
                        }



                    }else{



                        if(response.body().getMessage().trim().equalsIgnoreCase("Logged in Success")){


                            //  String cap = response.body().getDetails().getMessage().substring(0, 1).toUpperCase() + response.body().getDetails().getMessage().substring(1);

                            AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);




                            builder1.setMessage(response.body().getMessage());
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();


                                            Intent i = new Intent(LoginActivity.this,DeliveryActivity.class);


                                            PreferenceManager.setUser_Id(""+response.body().getResult().getID(),getApplicationContext());

                                            startActivity(i);

                                            finish();


                                        }
                                    });



                            AlertDialog alert11 = builder1.create();
                            alert11.show();

                        }else{


                            Utility.alertDialog(LoginActivity.this,response.body().getMessage());
                            //  Toast.makeText(LoginActivity.this, ""+response.body().getDetails().getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }





                } else {
                    //   Toast.makeText(LoginActivity.this, "Please enter correct email and password", Toast.LENGTH_SHORT).show();

                    //  alertDialog(""+upperCaseFirst(response.body().getError()));  Thabresh
//                        System.out.println("fffffffffffffff"+response.body().getError());
                }


                progressDoalog.dismiss();

                //recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("TAGggggggg","errrrrrrorrrrrrrr"+ t.toString());

                progressDoalog.dismiss();
            }
        });

    }








    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (isContinue) {
                     //   mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    } else {

                        if(gps.canGetLocation()){
                            latitude = gps.getLatitude();
                            longitude = gps.getLongitude();
                            // \n is for new line
                           // Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                        }else{
                            // can't get location
                            // GPS or Network is not enabled
                            // Ask user to enable GPS/network in settings
                            gps.showSettingsAlert();
                        }
                       /* mFusedLocationClient.getLastLocation().addOnSuccessListener(LoginActivity.this, location -> {
                            if (location != null) {
                                wayLatitude = location.getLatitude();
                                wayLongitude = location.getLongitude();
                                Toast.makeText(this, ""+wayLongitude+","+wayLongitude, Toast.LENGTH_SHORT).show();
                            } else {
                                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                            }
                        });*/
                    }
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GPS_REQUEST) {
                isGPS = true; // flag maintain before get location
            }
        }
    }




}
