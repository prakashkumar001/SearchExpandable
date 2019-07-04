package com.zaigo.coordinatelayouttest;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zaigo.coordinatelayouttest.common.Utility;
import com.zaigo.coordinatelayouttest.model.ForgetPasswordModel;
import com.zaigo.coordinatelayouttest.model.LoginModel;
import com.zaigo.coordinatelayouttest.restapi.ApiClient;
import com.zaigo.coordinatelayouttest.restapi.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zaigo.coordinatelayouttest.common.Utility.isValidEmail;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText ed_email;
    Button btn_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#000000"));
        }
        setContentView(R.layout.activity_forget_password);


        btn_submit = (Button)findViewById(R.id.btn_submit);

        ed_email = (EditText)findViewById(R.id.ed_email);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.isConnected(ForgetPasswordActivity.this)) {


                    if (ed_email.getText().toString().length() > 0) {

                        if (isValidEmail(ed_email.getText().toString())) {


                            forgetpasswordApiCalling();


                        } else {
                            Toast.makeText(ForgetPasswordActivity.this, "Please enter correct email", Toast.LENGTH_SHORT).show();
                        }

                        } else {

                            Toast.makeText(ForgetPasswordActivity.this, "Please enter all  fields", Toast.LENGTH_SHORT).show();

                        }
                    } else {

                        Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                    }
                }

        });
    }


    public  void forgetpasswordApiCalling(){








        ApiInterface apiService =
                ApiClient.getClientArrayParse().create(ApiInterface.class);






        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(ForgetPasswordActivity.this);
        progressDoalog.setMessage("Loading....");
        // show it
        progressDoalog.show();
        progressDoalog.setCancelable(false);

/*
        Super Admin Login Details:
        Username: admin@zaigoinfotech.com
        Password: Ex58D??Yy44-PaBG
        */

        Call<ForgetPasswordModel> call = apiService.getForgetPassword(ed_email.getText().toString());
        call.enqueue(new Callback<ForgetPasswordModel>() {
            @Override
            public void onResponse(Call<ForgetPasswordModel> call,final Response<ForgetPasswordModel> response) {


                Log.e("TAG", "responseTestsssss: "+new Gson().toJson(response.body()) );
//                System.out.println("SHIPPINGSSSSSSSSS"+response.body().getBillingAddress().getFirstName()+",,"+response.body().getFirstName());





                if (response.body().getMessage() != null) {




                    if(response.body().getMessage().trim().equalsIgnoreCase("Password send your email id")){


                        //  String cap = response.body().getDetails().getMessage().substring(0, 1).toUpperCase() + response.body().getDetails().getMessage().substring(1);

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(ForgetPasswordActivity.this);




                        builder1.setMessage("Password reset email has been sent to the email address on\n" +
                                "file for your account, but may take several minutes to show\n" +
                                "up in your inbox. Please wait at least 10 minutes before\n" +
                                "attempting another reset");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();


                                        Intent i = new Intent(ForgetPasswordActivity.this,LoginActivity.class);



                                        startActivity(i);

                                        finish();


                                    }
                                });



                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                    }else{


                        Utility.alertDialog(ForgetPasswordActivity.this,response.body().getMessage());
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

    @Override
    public void onBackPressed() {

        Intent i = new Intent(ForgetPasswordActivity.this,LoginActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}
