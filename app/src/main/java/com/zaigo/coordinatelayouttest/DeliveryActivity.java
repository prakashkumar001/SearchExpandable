package com.zaigo.coordinatelayouttest;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zaigo.coordinatelayouttest.common.PreferenceManager;
import com.zaigo.coordinatelayouttest.common.Utility;
import com.zaigo.coordinatelayouttest.model.DataPassModel;
import com.zaigo.coordinatelayouttest.model.ForgetPasswordModel;
import com.zaigo.coordinatelayouttest.model.ProfileInformationModel;
import com.zaigo.coordinatelayouttest.restapi.ApiClient;
import com.zaigo.coordinatelayouttest.restapi.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryActivity extends AppCompatActivity {

    EditText ed_username,ed_email,ed_mobile,ed_area,ed_street,ed_building,ed_comments;

    Button btn_submit,btn_login;

    GPSTracker gps;
    public static final int LOCATION_REQUEST = 1000;
    public static final int GPS_REQUEST = 1001;

    double latitude=0.0,longitude=0.0;

    Spinner ed_country,ed_state,spin_countrycode;
    String str_country="",str_state,str_countrycode;
    List<String> spinnerlistcountryCode;
    List<String> spinnerlist;
    List<String> spinnerliststate;


    String str_subtotal="",str_minamount;

    ImageView img_pickmarker;

    String str_area="";

    String str_lat,str_lan;

    String nlat="",nlon;

    LinearLayout linear_notlog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#000000"));
        }
        setContentView(R.layout.activity_delivery);


        Intent i = getIntent();

        str_subtotal=  i.getStringExtra("subtotal");

        str_area=  i.getStringExtra("area");

        nlat=  i.getStringExtra("lat");

        nlon=  i.getStringExtra("lon");

        str_minamount= i.getStringExtra("minamu");

        System.out.println("lattttttttttttt"+nlat);

        gps = new GPSTracker(DeliveryActivity.this);
        spinnerlist = new ArrayList<String>();
        spinnerliststate = new ArrayList<String>();

        if (ActivityCompat.checkSelfPermission(DeliveryActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(DeliveryActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DeliveryActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_REQUEST);

        } else {


            if(gps.canGetLocation()){


               longitude=gps.getLatitude();
                latitude= gps.getLongitude();
                // \n is for new line
                // Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            }else{
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gps.showSettingsAlert();
            }


        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("");
        }

        ed_username = (EditText) findViewById(R.id.ed_username);
        ed_username.setEnabled(true);
        ed_email = (EditText) findViewById(R.id.ed_email);
        ed_email.setEnabled(true);
        ed_mobile = (EditText) findViewById(R.id.ed_mobile);

        ed_area = (EditText) findViewById(R.id.ed_area);
        ed_street = (EditText) findViewById(R.id.ed_street);
        ed_building = (EditText) findViewById(R.id.ed_building);
        ed_state = (Spinner) findViewById(R.id.ed_state);
        ed_country = (Spinner) findViewById(R.id.ed_country);

        ed_comments = (EditText) findViewById(R.id.ed_comments);

        btn_submit= (Button) findViewById(R.id.btn_submit);


        linear_notlog = (LinearLayout)findViewById(R.id.linear_notlog);

        btn_login=(Button)findViewById(R.id.btn_login);

        spin_countrycode = (Spinner)findViewById(R.id.spin_countrycode);

        img_pickmarker = (ImageView)findViewById(R.id.img_pickmarker);


        // spinnerlist.add("list 2");
        // spinnerlist.add("list 3");

        if(!PreferenceManager.getUser_Id(getApplicationContext()).isEmpty()){
            if(Utility.isConnected(DeliveryActivity.this)){
                profileInformationApiCalling();
            }else{
                Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();

            }
            linear_notlog.setVisibility(View.GONE);


        }else{
            withOutLogin();

            linear_notlog.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "Please login your account", Toast.LENGTH_SHORT).show();
        }






        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DeliveryActivity.this,LoginActivity.class);


                i.putExtra("notloged","notloged");


                startActivity(i);
                finish();
            }
        });



        img_pickmarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(DeliveryActivity.this,DeliveryMapActivity.class);

                i.putExtra("subtotal",str_subtotal);

                i.putExtra("minamu",str_minamount);


                DataPassModel dpm = new DataPassModel();

                dpm.set_billing_first_name(ed_username.getText().toString());
                dpm.set_billing_phone(ed_mobile.getText().toString());
                dpm.set_billing_email(ed_email.getText().toString());
                dpm.setNw_area(ed_area.getText().toString());
                dpm.setNw_street(ed_street.getText().toString());
                dpm.setNw_build(ed_building.getText().toString());
                dpm.set_billing_state(str_state);
                dpm.set_billing_country(str_country);
                dpm.setComment_content(ed_comments.getText().toString());


                startActivity(i);
               finish();
            }
        });



        ed_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //Change the selected item's text color

                str_country = spinnerlist.get(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });





        ed_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //Change the selected item's text color

                str_state = spinnerliststate.get(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });


        spinnerlistcountryCode = new ArrayList<String>();
        spinnerlistcountryCode.add("971");
        //   spinnerliststate.add("SHARJAH");
        //   spinnerliststate.add("ABU DHABI");
        ArrayAdapter<String> countrycodeAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, spinnerlistcountryCode);
        countrycodeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_countrycode.setAdapter(countrycodeAdapter);

        spin_countrycode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //Change the selected item's text color

                str_countrycode = spinnerlistcountryCode.get(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    if (Utility.isConnected(DeliveryActivity.this)) {



                        if(!PreferenceManager.getUser_Id(getApplicationContext()).isEmpty()){

                            if(validate()) {





                                if(!str_lan.trim().equalsIgnoreCase("0.0") && !str_lat.equalsIgnoreCase("0.0")){
                                    Intent i = new Intent(DeliveryActivity.this,MainDeliveryActivity.class);


                                    DataPassModel dpm = new DataPassModel();

                                    dpm.set_billing_first_name(ed_username.getText().toString());
                                    dpm.set_billing_phone(ed_mobile.getText().toString());
                                    dpm.set_billing_email(ed_email.getText().toString());
                                    dpm.setNw_area(ed_area.getText().toString());
                                    dpm.setNw_street(ed_street.getText().toString());
                                    dpm.setNw_build(ed_building.getText().toString());
                                    dpm.set_billing_state(str_state);
                                    dpm.set_billing_country(str_country);
                                    dpm.setComment_content(ed_comments.getText().toString());

                                    dpm.setNw_lat(str_lat);

                                    dpm.setNw_long(str_lan);



                                    i.putExtra("subtotal",str_subtotal);

                                    i.putExtra("state",str_state);

                                    i.putExtra("mobile",ed_mobile.getText().toString());

                                    i.putExtra("username",ed_username.getText().toString());

                                    i.putExtra("email",ed_email.getText().toString());

                                    i.putExtra("area",ed_area.getText().toString());

                                    i.putExtra("street",ed_street.getText().toString());

                                    i.putExtra("building",ed_building.getText().toString());

                                    i.putExtra("country",str_country);

                                    i.putExtra("comments",ed_comments.getText().toString());




                                    startActivity(i);
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(), "Not detect your current location", Toast.LENGTH_SHORT).show();
                                }


                            }



                        }else{


                            if(validate()) {





                                if(!str_lan.trim().equalsIgnoreCase("0.0") && !str_lat.equalsIgnoreCase("0.0")){
                                    Intent i = new Intent(DeliveryActivity.this,MainDeliveryActivity.class);


                                    DataPassModel dpm = new DataPassModel();

                                    dpm.set_billing_first_name(ed_username.getText().toString());
                                    dpm.set_billing_phone(ed_mobile.getText().toString());
                                    dpm.set_billing_email(ed_email.getText().toString());
                                    dpm.setNw_area(ed_area.getText().toString());
                                    dpm.setNw_street(ed_street.getText().toString());
                                    dpm.setNw_build(ed_building.getText().toString());
                                    dpm.set_billing_state(str_state);
                                    dpm.set_billing_country(str_country);
                                    dpm.setComment_content(ed_comments.getText().toString());

                                    dpm.setNw_lat(str_lat);

                                    dpm.setNw_long(str_lan);



                                    i.putExtra("subtotal",str_subtotal);

                                    i.putExtra("state",str_state);

                                    i.putExtra("mobile",ed_mobile.getText().toString());

                                    i.putExtra("username",ed_username.getText().toString());

                                    i.putExtra("email",ed_email.getText().toString());

                                    i.putExtra("area",ed_area.getText().toString());

                                    i.putExtra("street",ed_street.getText().toString());

                                    i.putExtra("building",ed_building.getText().toString());

                                    i.putExtra("country",str_country);

                                    i.putExtra("comments",ed_comments.getText().toString());




                                    startActivity(i);
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(), "Not detect your current location", Toast.LENGTH_SHORT).show();
                                }


                            }
                        }


                    }else{
                        Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                    }






            }
        });
    }


    public  void profileInformationApiCalling(){








        ApiInterface apiService =
                ApiClient.getClientArrayParse().create(ApiInterface.class);






        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(DeliveryActivity.this);
        progressDoalog.setMessage("Loading....");
        // show it
        progressDoalog.show();
        progressDoalog.setCancelable(false);

/*
        Super Admin Login Details:
        Username: admin@zaigoinfotech.com
        Password: Ex58D??Yy44-PaBG
        */

        Call<ProfileInformationModel> call = apiService.getProfileData(""+ PreferenceManager.getUser_Id(getApplicationContext()));
        call.enqueue(new Callback<ProfileInformationModel>() {
            @Override
            public void onResponse(Call<ProfileInformationModel> call,final Response<ProfileInformationModel> response) {


                Log.e("TAG", "responseTestsssss: "+new Gson().toJson(response.body()) );
//                System.out.println("SHIPPINGSSSSSSSSS"+response.body().getBillingAddress().getFirstName()+",,"+response.body().getFirstName());


                spinnerliststate.clear();
                spinnerlist.clear();


                if(!PreferenceManager.getUser_Id(getApplicationContext()).isEmpty()){
                    if (response.body() != null) {








                      /*  dpm.set_billing_first_name(ed_username.getText().toString());
                        dpm.set_billing_phone(ed_mobile.getText().toString());
                        dpm.set_billing_email(ed_email.getText().toString());
                        dpm.setNw_area(ed_area.getText().toString());
                        dpm.setNw_street(ed_street.getText().toString());
                        dpm.setNw_build(ed_building.getText().toString());
                        dpm.set_billing_state(str_state);
                        dpm.set_billing_country(str_country);
                        dpm.setComment_content(ed_comments.getText().toString());*/

                        if(str_area != null){

                            ed_area.setText(""+str_area);

                            DataPassModel dpm = new DataPassModel();
                            ed_username.setText(dpm.get_billing_first_name());

                            ed_email.setText(dpm.get_billing_email());
                            ed_mobile.setText(dpm.get_billing_phone());


                            ed_street.setText(dpm.getNw_street());
                            ed_building.setText(dpm.getNw_build());

                            str_lat=nlat;

                            str_lan=nlon;

                            System.out.println("ssssssssssssssssss"+str_lan+nlon);

                        }else{

                            ed_area.setText(response.body().getResult().get(0).getArea());
                            ed_username.setText(response.body().getResult().get(0).getFullName());

                            ed_email.setText(response.body().getResult().get(0).getEmail());
                            ed_mobile.setText(response.body().getResult().get(0).getPhone());


                            ed_street.setText(response.body().getResult().get(0).getStreet());
                            ed_building.setText(response.body().getResult().get(0).getBuilding());


                            str_lan =""+longitude;

                            str_lat=""+longitude;

                           // ed_area.setText("");
                        }


                        spinnerlist.add(response.body().getResult().get(0).getCountry());

                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(DeliveryActivity.this,
                                R.layout.spinner_item, spinnerlist);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        ed_country.setAdapter(dataAdapter);

                        System.out.println("vvvvvvvvvvvvv"+response.body().getResult().get(0).getArea());

                        if(response.body().getResult().get(0).getState().trim().equalsIgnoreCase("DUBAI")){
                            spinnerliststate.add(response.body().getResult().get(0).getState());
                            spinnerliststate.add("SHARJAH");
                            spinnerliststate.add("ABU DHABI");
                        }
                        if(response.body().getResult().get(0).getState().trim().equalsIgnoreCase("SHARJAH")){
                            spinnerliststate.add(response.body().getResult().get(0).getState());
                            spinnerliststate.add("DUBAI");
                            spinnerliststate.add("ABU DHABI");
                        }

                        if(response.body().getResult().get(0).getState().trim().equalsIgnoreCase("ABU DHABI")){
                            spinnerliststate.add(response.body().getResult().get(0).getState());
                            spinnerliststate.add("DUBAI");
                            spinnerliststate.add("SHARJAH");
                        }


                        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(DeliveryActivity.this,
                                R.layout.spinner_item, spinnerliststate);
                        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        ed_state.setAdapter(stateAdapter);

                        //response.body().getResult().get(0).getFullName();






                    } else {
                           Toast.makeText(DeliveryActivity.this, "Please login", Toast.LENGTH_SHORT).show();

                        //  alertDialog(""+upperCaseFirst(response.body().getError()));
//                        System.out.println("fffffffffffffff"+response.body().getError());
                    }

                }else{

                }



                progressDoalog.dismiss();

                //recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<ProfileInformationModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("TAGggggggg","errrrrrrorrrrrrrr"+ t.toString());

                progressDoalog.dismiss();
            }
        });

    }

    private boolean validate() {
        boolean temp=true;

        String str_username=ed_username.getText().toString();

        String str_mobile = ed_mobile.getText().toString();

        String str_area = ed_area.getText().toString();
        String str_street = ed_street.getText().toString();
        String str_build = ed_building.getText().toString();
        //String str_hcpregistrationnumber = ed_hcpregistrationnumber.getText().toString();
        // String age = mAgeEditText.getText().toString();


        if(ed_username.getText().toString().length()<1){

            Utility.toastMessage(DeliveryActivity.this,"Please enter your username");


            temp=false;
        }else

        if(str_mobile.trim().length()<9){

            // ed_firstname.setError("");

            Utility.toastMessage(DeliveryActivity.this,"Please enter your phone number");


            temp=false;
        } else

        if(ed_email.getText().toString().length()<1){
            // ed_email.setError("Please enter email address");
            Utility.toastMessage(DeliveryActivity.this,"Please enter email address");
            temp=false;
        }else if(!Utility.isValidEmail(ed_email.getText().toString())) {
            //  ed_email.setError("Please enter a valid email id");
            Utility.toastMessage(DeliveryActivity.this,"Please enter a valid email id");
            temp = false;
        }else

            if(str_area.trim().length()<1){

                // ed_lastname.setError("Please enter lastname");
                Utility.toastMessage(DeliveryActivity.this,"Please enter area");
                temp=false;
            }else if(str_street.trim().length()<1){
                // ed_hospitalorprac.setError("Please enter hospitalorpractice");
                Utility.toastMessage(DeliveryActivity.this,"Please enter street");

                temp=false;
            }else if(str_build.trim().length()<1){
                ///  mAddressEditText.setError("Please enter your location");
                Utility.toastMessage(DeliveryActivity.this,"Please enter building");

                temp=false;
            }/* if(str_hcpregistrationnumber.length()<1){
            ed_hcpregistrationnumber.setError("Please enter HCPRegistrationNumber");

            temp=false;
        }*//*else if(!mTermsConditionsCheckBox.isChecked()){

            dialog = CommonClass.createMessageDialog(SignUpActivity.this, "", "Please check terms and conditions", "ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            },CommonClass.NO_ICON);
            dialog.show();







            temp=false;
        }*//*else if(!isNetworkConnected()){


            alertDialogSingle("Network not available");



            temp=false;


        }*/

        return temp;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {

            Intent  i = new Intent(DeliveryActivity.this,CartItemActivity.class);
            i.putExtra("minamu",str_minamount);
            startActivity(i);


            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }




    public void withOutLogin(){

        spinnerliststate.clear();
        spinnerlist.clear();

        spinnerliststate.add("DUBAI");
        spinnerliststate.add("SHARJAH");
        spinnerliststate.add("ABU DHABI");


        spinnerlist.add("United Arab Emirates");

        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(DeliveryActivity.this,
                R.layout.spinner_item, spinnerliststate);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ed_state.setAdapter(stateAdapter);


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(DeliveryActivity.this,
                R.layout.spinner_item, spinnerlist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ed_country.setAdapter(dataAdapter);



        if(str_area != null){

            ed_area.setText(""+str_area);



            DataPassModel dpm = new DataPassModel();
            ed_username.setText(dpm.get_billing_first_name());

            ed_email.setText(dpm.get_billing_email());
            ed_mobile.setText(dpm.get_billing_phone());


            ed_street.setText(dpm.getNw_street());
            ed_building.setText(dpm.getNw_build());

            str_lat=nlat;

            str_lan=nlon;

            System.out.println("ssssssssssssssssss"+str_lan+nlon);

        }else{

            ed_area.setText("");

            str_lan =""+longitude;

            str_lat=""+longitude;

            // ed_area.setText("");
        }

    }

    @Override
    public void onBackPressed() {

        Intent  i = new Intent(DeliveryActivity.this,CartItemActivity.class);
        i.putExtra("minamu",str_minamount);
        startActivity(i);


        finish(); // close this activity and return to preview activity (if there is any)
        super.onBackPressed();
    }
}
