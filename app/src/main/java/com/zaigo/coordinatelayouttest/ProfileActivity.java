package com.zaigo.coordinatelayouttest;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zaigo.coordinatelayouttest.common.PreferenceManager;
import com.zaigo.coordinatelayouttest.common.Utility;
import com.zaigo.coordinatelayouttest.model.ForgetPasswordModel;
import com.zaigo.coordinatelayouttest.model.ProfileInformationModel;
import com.zaigo.coordinatelayouttest.restapi.ApiClient;
import com.zaigo.coordinatelayouttest.restapi.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zaigo.coordinatelayouttest.common.Utility.isValidEmail;

public class ProfileActivity extends AppCompatActivity {

    EditText ed_username,ed_email,ed_mobile,ed_area,ed_street,ed_building;

    Button btn_submit;

    GPSTracker gps;
    public static final int LOCATION_REQUEST = 1000;
    public static final int GPS_REQUEST = 1001;

    double latitude=0.0,longitude=0.0;

    Spinner ed_country,ed_state,spin_countrycode;
    String str_country="",str_state,str_countrycode;

    List<String> spinnerlistcountryCode;
    List<String> spinnerlist;
    List<String> spinnerliststate;

    ImageView img_pickmarker;
    String str_area="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#000000"));
        }

            setContentView(R.layout.activity_profile);


        Intent i = getIntent();



        str_area=  i.getStringExtra("area");

        gps = new GPSTracker(ProfileActivity.this);
        spinnerlist = new ArrayList<String>();
        spinnerliststate = new ArrayList<String>();

        if (ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("");
        }

        ed_username = (EditText) findViewById(R.id.ed_username);
        ed_username.setEnabled(false);
        ed_email = (EditText) findViewById(R.id.ed_email);
        ed_email.setEnabled(false);
        ed_mobile = (EditText) findViewById(R.id.ed_mobile);

        ed_area = (EditText) findViewById(R.id.ed_area);
        ed_street = (EditText) findViewById(R.id.ed_street);
        ed_building = (EditText) findViewById(R.id.ed_building);
        ed_state = (Spinner) findViewById(R.id.ed_state);
        ed_country = (Spinner) findViewById(R.id.ed_country);

        btn_submit= (Button) findViewById(R.id.btn_submit);


        spin_countrycode = (Spinner)findViewById(R.id.spin_countrycode);

        img_pickmarker = (ImageView)findViewById(R.id.img_pickmarker);

        if(Utility.isConnected(ProfileActivity.this)){
            profileInformationApiCalling();
        }else{
            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();

        }

        // spinnerlist.add("list 2");
        // spinnerlist.add("list 3");

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

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.isConnected(ProfileActivity.this)) {

                    if(validate()) {

                        String str_lat,str_lan;

                        str_lan=""+longitude;
                        str_lat=""+latitude;

                        if(!str_lan.trim().equalsIgnoreCase("0.0") && !str_lat.equalsIgnoreCase("0.0")){
                           profileUpdateApiCalling();
                        }else{
                            Toast.makeText(getApplicationContext(), "Not detect your current location", Toast.LENGTH_SHORT).show();
                        }


                    }

                }else{
                    Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                }
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

        img_pickmarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ProfileActivity.this,ProfileMapActivity.class);

                startActivity(i);
                finish();
            }
        });




    }

    public  void profileInformationApiCalling(){








        ApiInterface apiService =
                ApiClient.getClientArrayParse().create(ApiInterface.class);






        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(ProfileActivity.this);
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



                if (response.body().getResult() != null) {



                    ed_username.setText(response.body().getResult().get(0).getFullName());

                    ed_email.setText(response.body().getResult().get(0).getEmail());
                    ed_mobile.setText(response.body().getResult().get(0).getPhone());
                    if(str_area != null){

                        ed_area.setText(""+str_area);
                    }else{

                        ed_area.setText(response.body().getResult().get(0).getArea());
                        // ed_area.setText("");
                    }
                    ed_street.setText(response.body().getResult().get(0).getStreet());
                    ed_building.setText(response.body().getResult().get(0).getBuilding());

                    spinnerlist.add(response.body().getResult().get(0).getCountry());

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ProfileActivity.this,
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


                    ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(ProfileActivity.this,
                            R.layout.spinner_item, spinnerliststate);
                    stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    ed_state.setAdapter(stateAdapter);

                    //response.body().getResult().get(0).getFullName();


                   /* user_id: Current User Id
                    user_mobile : Mobile Number
                    user_country : Country
                    user_state : State
                    nw_area : Area
                    nw_lat : Latitude
                    nw_long : Longitude
                    nw_street : Street
                    nw_build : Building
*/




                } else {
                    //   Toast.makeText(LoginActivity.this, "Please enter correct email and password", Toast.LENGTH_SHORT).show();

                    //  alertDialog(""+upperCaseFirst(response.body().getError()));
//                        System.out.println("fffffffffffffff"+response.body().getError());
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


        //
        if(str_mobile.trim().length()<1){

            // ed_firstname.setError("");

            Utility.toastMessage(ProfileActivity.this,"Please enter your phone number");


            temp=false;
        } else

      /*  if(str_email.length()<1){
            // ed_email.setError("Please enter email address");
            Utility.toastMessage(ProfileActivity.this,"Please enter email address");
            temp=false;
        }else if(!isValidEmail(str_email)) {
            //  ed_email.setError("Please enter a valid email id");
            Utility.toastMessage(ProfileActivity.this,"Please enter a valid email id");
            temp = false;
        }else*/

         if(str_area.trim().length()<1){

            // ed_lastname.setError("Please enter lastname");
            Utility.toastMessage(ProfileActivity.this,"Please enter area");
            temp=false;
        }else if(str_street.trim().length()<1){
            // ed_hospitalorprac.setError("Please enter hospitalorpractice");
            Utility.toastMessage(ProfileActivity.this,"Please enter street");

            temp=false;
        }else if(str_build.trim().length()<1){
            ///  mAddressEditText.setError("Please enter your location");
            Utility.toastMessage(ProfileActivity.this,"Please enter building");

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
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


    public  void profileUpdateApiCalling(){








        ApiInterface apiService =
                ApiClient.getClientArrayParse().create(ApiInterface.class);






        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(ProfileActivity.this);
        progressDoalog.setMessage("Loading....");
        // show it
        progressDoalog.show();
        progressDoalog.setCancelable(false);

/*
        Super Admin Login Details:
        Username: admin@zaigoinfotech.com
        Password: Ex58D??Yy44-PaBG
        */

        Call<ForgetPasswordModel> call = apiService.getProfileUpdate(PreferenceManager.getUser_Id(getApplicationContext()),ed_mobile.getText().toString(),str_country,str_state,ed_area.getText().toString(),ed_street.getText().toString(),""+ed_building.getText().toString(),""+latitude,""+longitude);
        call.enqueue(new Callback<ForgetPasswordModel>() {
            @Override
            public void onResponse(Call<ForgetPasswordModel> call,final Response<ForgetPasswordModel> response) {


                Log.e("TAG", "responseTestsssss: "+new Gson().toJson(response.body()) );
//                System.out.println("SHIPPINGSSSSSSSSS"+response.body().getBillingAddress().getFirstName()+",,"+response.body().getFirstName());





                if (response.body().getMessage() != null) {




                    if(response.body().getMessage().trim().equalsIgnoreCase("Updated Successfully")){


                        //  String cap = response.body().getDetails().getMessage().substring(0, 1).toUpperCase() + response.body().getDetails().getMessage().substring(1);

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(ProfileActivity.this);




                        builder1.setMessage(response.body().getMessage());
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();


                                        Intent i = new Intent(ProfileActivity.this,NavigationDrawerActivity.class);



                                        startActivity(i);

                                        finish();


                                    }
                                });



                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                    }else{


                        Utility.alertDialog(ProfileActivity.this,response.body().getMessage());
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
            public void onFailure(Call<ForgetPasswordModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("TAGggggggg","errrrrrrorrrrrrrr"+ t.toString());

                progressDoalog.dismiss();
            }
        });

    }

}