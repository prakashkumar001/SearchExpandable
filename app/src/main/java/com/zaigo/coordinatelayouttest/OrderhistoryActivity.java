package com.zaigo.coordinatelayouttest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zaigo.coordinatelayouttest.adapter.OrderHistoryAdapter;
import com.zaigo.coordinatelayouttest.common.PreferenceManager;
import com.zaigo.coordinatelayouttest.common.Utility;
import com.zaigo.coordinatelayouttest.model.OrderHistoryModel;
import com.zaigo.coordinatelayouttest.model.ProfileInformationModel;
import com.zaigo.coordinatelayouttest.restapi.ApiClient;
import com.zaigo.coordinatelayouttest.restapi.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderhistoryActivity extends NavigationDrawerActivity {

    RecyclerView recycler_orderlist;
    LinearLayoutManager linearVertical;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#000000"));
        }

        setContentView(R.layout.activity_orderhistory);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("");
        }

        recycler_orderlist= (RecyclerView)findViewById(R.id.recycler_orderlist);
        linearVertical = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);


//attach linear layout to recyclerview
        recycler_orderlist.setLayoutManager(linearVertical);


        if(Utility.isConnected(OrderhistoryActivity.this)){
            OrderHistoryApiCalling();
        }else{
            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();

        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent i = new Intent(OrderhistoryActivity.this,NavigationDrawerActivity.class);
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    public  void OrderHistoryApiCalling(){








        ApiInterface apiService =
                ApiClient.getClientArrayParse().create(ApiInterface.class);






        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(OrderhistoryActivity.this);
        progressDoalog.setMessage("Loading....");
        // show it
        progressDoalog.show();
        progressDoalog.setCancelable(false);

/*
        Super Admin Login Details:
        Username: admin@zaigoinfotech.com
        Password: Ex58D??Yy44-PaBG
        */

        Call<OrderHistoryModel> call = apiService.getOrderHistoryData(""+PreferenceManager.getUser_Id(getApplicationContext()));
        call.enqueue(new Callback<OrderHistoryModel>() {
            @Override
            public void onResponse(Call<OrderHistoryModel> call,final Response<OrderHistoryModel> response) {


                Log.e("TAG", "OrderHistoryModelTestsssss: "+new Gson().toJson(response.body()) );
//                System.out.println("SHIPPINGSSSSSSSSS"+response.body().getBillingAddress().getFirstName()+",,"+response.body().getFirstName());






                if (response.body().getResult() != null) {


                    OrderHistoryAdapter orderHistoryAdapter = new OrderHistoryAdapter(OrderhistoryActivity.this,response.body().getResult());
                    recycler_orderlist.setAdapter(orderHistoryAdapter);





                } else {
                    //   Toast.makeText(LoginActivity.this, "Please enter correct email and password", Toast.LENGTH_SHORT).show();

                    //  alertDialog(""+upperCaseFirst(response.body().getError()));
//                        System.out.println("fffffffffffffff"+response.body().getError());
                }


                progressDoalog.dismiss();

                //recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<OrderHistoryModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("TAGggggggg","OrderHistoryModelerrrrrrrorrrrrrrr"+ t.toString());

                progressDoalog.dismiss();
            }
        });

    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(OrderhistoryActivity.this,NavigationDrawerActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}