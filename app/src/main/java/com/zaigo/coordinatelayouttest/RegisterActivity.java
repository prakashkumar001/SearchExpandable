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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zaigo.coordinatelayouttest.common.Utility;
import com.zaigo.coordinatelayouttest.model.RegisterDataPassModel;
import com.zaigo.coordinatelayouttest.model.RegisterModel;
import com.zaigo.coordinatelayouttest.restapi.ApiClient;
import com.zaigo.coordinatelayouttest.restapi.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zaigo.coordinatelayouttest.common.Utility.isValidEmail;

public class RegisterActivity extends AppCompatActivity {


    EditText ed_username,ed_email,ed_mobile,ed_pwd,ed_conpwd,ed_area,ed_street,ed_building,ed_fullname;

    Button btn_submit;
    GPSTracker gps;
    public static final int LOCATION_REQUEST = 1000;
    public static final int GPS_REQUEST = 1001;

    double latitude=0.0,longitude=0.0;

    Spinner ed_country,ed_state,spin_countrycode;
    String str_country="",str_state,str_countrycode;
    List<String> spinnerlist;
    List<String> spinnerliststate;

    List<String> spinnerlistcountryCode;

    ImageView img_pickmarker;

    String str_area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#000000"));
        }

        setContentView(R.layout.activity_register);

        gps = new GPSTracker(RegisterActivity.this);


        Intent i = getIntent();

        str_area = i .getStringExtra("area");





        if (ActivityCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_REQUEST);

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


        }



        ed_username = (EditText) findViewById(R.id.ed_username);
        ed_email = (EditText) findViewById(R.id.ed_email);
        ed_mobile = (EditText) findViewById(R.id.ed_mobile);
        ed_pwd = (EditText) findViewById(R.id.ed_pwd);
        ed_conpwd = (EditText) findViewById(R.id.ed_conpwd);
        ed_area = (EditText) findViewById(R.id.ed_area);
        ed_street = (EditText) findViewById(R.id.ed_street);
        ed_building = (EditText) findViewById(R.id.ed_building);
        ed_state = (Spinner) findViewById(R.id.ed_state);
        ed_country = (Spinner) findViewById(R.id.ed_country);

        btn_submit= (Button) findViewById(R.id.btn_submit);

        ed_fullname = (EditText) findViewById(R.id.ed_fullname);

        img_pickmarker = (ImageView)findViewById(R.id.img_pickmarker);

        spin_countrycode = (Spinner)findViewById(R.id.spin_countrycode);




        if(str_area != null){

            RegisterDataPassModel registerDataPassModel = new RegisterDataPassModel();
            ed_area.setText(""+str_area);
            ed_username.setText(""+registerDataPassModel.getUsername());
            ed_email.setText(""+registerDataPassModel.getEmail());
            ed_fullname.setText(""+registerDataPassModel.getFullname());
            ed_pwd.setText(""+registerDataPassModel.getPassword());
            ed_conpwd.setText(""+registerDataPassModel.getConfirmpassword());
            ed_mobile.setText(""+registerDataPassModel.getMobile());
            ed_street.setText(""+registerDataPassModel.getStreet());
            ed_building.setText(""+registerDataPassModel.getBuilding());




        }else{
            ed_area.setText("");
        }


        img_pickmarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterDataPassModel registerDataPassModel = new RegisterDataPassModel();

                registerDataPassModel.setUsername(ed_username.getText().toString());
                registerDataPassModel.setPassword(ed_pwd.getText().toString());
                registerDataPassModel.setConfirmpassword(ed_conpwd.getText().toString());
                registerDataPassModel.setEmail(ed_email.getText().toString());
                registerDataPassModel.setFullname(ed_fullname.getText().toString());
                registerDataPassModel.setMobile(ed_mobile.getText().toString());
                registerDataPassModel.setStreet(ed_street.getText().toString());
                registerDataPassModel.setBuilding(ed_building.getText().toString());


                Intent i = new Intent(RegisterActivity.this,MapActivity.class);

                startActivity(i);
                finish();
            }
        });

        spinnerlist = new ArrayList<String>();
        spinnerlist.add("United Arab Emirates");
       // spinnerlist.add("list 2");
       // spinnerlist.add("list 3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, spinnerlist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ed_country.setAdapter(dataAdapter);




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


        spinnerliststate = new ArrayList<String>();
        spinnerliststate.add("DUBAI");
        spinnerliststate.add("SHARJAH");
        spinnerliststate.add("ABU DHABI");
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, spinnerliststate);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ed_state.setAdapter(stateAdapter);

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
                if (Utility.isConnected(RegisterActivity.this)) {

                   if(validate()) {

                       String str_lat,str_lan;

                       str_lan=""+longitude;
                       str_lat=""+latitude;

                       if(!str_lan.trim().equalsIgnoreCase("0.0") && !str_lat.equalsIgnoreCase("0.0")){
                           registerApiCalling();
                       }else{
                           Toast.makeText(getApplicationContext(), "Not detect your current location", Toast.LENGTH_SHORT).show();
                       }


                   }

                }else{
                    Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    public  void registerApiCalling(){








        ApiInterface apiService =
                ApiClient.getClientArrayParse().create(ApiInterface.class);






        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(RegisterActivity.this);
        progressDoalog.setMessage("Loading....");
        // show it
        progressDoalog.show();
        progressDoalog.setCancelable(false);

/*
        Super Admin Login Details:
        Username: admin@zaigoinfotech.com
        Password: Ex58D??Yy44-PaBG
        */

        Call<RegisterModel> call = apiService.getRegister(ed_username.getText().toString(),""+ed_fullname.getText().toString(),""+ed_mobile.getText().toString(),""+ed_pwd.getText().toString(),ed_email.getText().toString(),str_country,""+str_state,""+latitude,""+longitude,ed_area.getText().toString(),ed_street.getText().toString(),ed_building.getText().toString());
        call.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call,final Response<RegisterModel> response) {


                Log.e("TAG", "responseTestsssss: "+new Gson().toJson(response.body()) );
//                System.out.println("SHIPPINGSSSSSSSSS"+response.body().getBillingAddress().getFirstName()+",,"+response.body().getFirstName());





                if (response.body().getMessage() != null) {




                    if(response.body().getMessage().trim().equalsIgnoreCase("Registered Successfully")){


                        //  String cap = response.body().getDetails().getMessage().substring(0, 1).toUpperCase() + response.body().getDetails().getMessage().substring(1);

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(RegisterActivity.this);




                        builder1.setMessage(response.body().getMessage());
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();


                                        Intent i = new Intent(RegisterActivity.this,LoginActivity.class);



                                        startActivity(i);

                                        finish();


                                    }
                                });



                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                    }else{


                        Utility.alertDialog(RegisterActivity.this,response.body().getMessage());
                        //  Toast.makeText(LoginActivity.this, ""+response.body().getDetails().getMessage(), Toast.LENGTH_SHORT).show();
                    }



                } else {
                    //   Toast.makeText(LoginActivity.this, "Please enter correct email and password", Toast.LENGTH_SHORT).show();

                  //  alertDialog(""+upperCaseFirst(response.body().getError()));
//                        System.out.println("fffffffffffffff"+response.body().getError());
                }


                progressDoalog.dismiss();

                //recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("TAGggggggg","errrrrrrorrrrrrrr"+ t.toString());

                progressDoalog.dismiss();
            }
        });

    }


    private boolean validate() {
        boolean temp=true;

        String str_username=ed_username.getText().toString();
        String pass=ed_pwd.getText().toString();
        String cpass=ed_conpwd.getText().toString();
        String str_email = ed_email.getText().toString();

        String str_area = ed_area.getText().toString();
        String str_street = ed_street.getText().toString();
        String str_build = ed_building.getText().toString();

        String str_mobile = ed_mobile.getText().toString();

        String str_fullname = ed_fullname.getText().toString();
        //String str_hcpregistrationnumber = ed_hcpregistrationnumber.getText().toString();
        // String age = mAgeEditText.getText().toString();


        //
        if(str_username.length()<1){

           // ed_firstname.setError("");

            Utility.toastMessage(RegisterActivity.this,"Please enter your username");


            temp=false;
        } else if(pass.length()<1){
           // ed_password.setError("please enter password");
            Utility.toastMessage(RegisterActivity.this,"please enter password");
            temp=false;
        }else if(!pass.equals(cpass)){
           // ed_conpassword.setError("password not matching");
            Utility.toastMessage(RegisterActivity.this,"password not matching");
            temp=false;
        }else if(cpass.length()<1){
          //  ed_conpassword.setError("Please enter confirm password");
            Utility.toastMessage(RegisterActivity.this,"Please enter confirm password");
            temp=false;
        } else if(str_email.length()<1){
            // ed_email.setError("Please enter email address");
            Utility.toastMessage(RegisterActivity.this,"Please enter email address");
            temp=false;
        }else if(!isValidEmail(str_email)) {
            //  ed_email.setError("Please enter a valid email id");
            Utility.toastMessage(RegisterActivity.this,"Please enter a valid email id");
            temp = false;
        }else if(str_fullname.length()<1){

            // ed_lastname.setError("Please enter lastname");
            Utility.toastMessage(RegisterActivity.this,"Please enter full name");
            temp=false;
        }else if(str_mobile.length()<9){

            // ed_lastname.setError("Please enter lastname");
            Utility.toastMessage(RegisterActivity.this,"Please enter mobile number");
            temp=false;
        } else if(str_street.length()<1){

           // ed_lastname.setError("Please enter lastname");
            Utility.toastMessage(RegisterActivity.this,"Please enter street");
            temp=false;
        }else if(str_area.length()<1){
           // ed_hospitalorprac.setError("Please enter hospitalorpractice");
            Utility.toastMessage(RegisterActivity.this,"Please enter area");

            temp=false;
        }else if(str_build.length()<1){
          ///  mAddressEditText.setError("Please enter your location");
            Utility.toastMessage(RegisterActivity.this,"Please enter building");

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

}
