package com.zaigo.coordinatelayouttest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zaigo.coordinatelayouttest.adapter.OrderHistoryAdapter;
import com.zaigo.coordinatelayouttest.adapter.OrderHistoryDeatilAdapter;
import com.zaigo.coordinatelayouttest.common.MyDividerItemDecoration;
import com.zaigo.coordinatelayouttest.common.PreferenceManager;
import com.zaigo.coordinatelayouttest.model.DaoSession;
import com.zaigo.coordinatelayouttest.model.OrderDeatilsModel;
import com.zaigo.coordinatelayouttest.model.OrderHistoryModel;
import com.zaigo.coordinatelayouttest.model.Product;
import com.zaigo.coordinatelayouttest.model.ProductDao;
import com.zaigo.coordinatelayouttest.model.ProductList;
import com.zaigo.coordinatelayouttest.restapi.ApiClient;
import com.zaigo.coordinatelayouttest.restapi.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDeatilsActivity extends AppCompatActivity {


    String str_orderId;

    TextView txt_shipping,txt_paymenttotal,txt_email,txt_phone,txt_address;

    RecyclerView recycler_productlist;
    List<Double> doubleList = new ArrayList<>();
    LinearLayoutManager linearVertical;
  TextView txt_total;

  String str_status="";

  Button btn_reorder;
    ProductDao productDao;
    DaoSession daoSession;

    List<ProductList> productLists ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#000000"));
        }
        setContentView(R.layout.activity_order_deatils);

        daoSession = ((App)OrderDeatilsActivity.this.getApplication()).getDaoSession();
        productDao = daoSession.getProductDao();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("");
        }

        Intent i = getIntent();
         str_orderId = i.getStringExtra("id");

         str_status = i.getStringExtra("status");


        txt_total = (TextView)findViewById(R.id.txt_total);

        txt_shipping = (TextView)findViewById(R.id.txt_shipping);
        txt_paymenttotal = (TextView)findViewById(R.id.txt_paymenttotal);
        txt_email = (TextView)findViewById(R.id.txt_email);
        txt_phone = (TextView)findViewById(R.id.txt_phone);
        txt_address = (TextView)findViewById(R.id.txt_address);

        btn_reorder = (Button)findViewById(R.id.btn_reorder);

        recycler_productlist = (RecyclerView) findViewById(R.id.recycler_productlist);
//intialize linear layout manager vertically
        linearVertical = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);


//attach linear layout to recyclerview
        recycler_productlist.setLayoutManager(linearVertical);


        System.out.println("IDDDDDDDDDDD  "+str_orderId+","+str_status);


        if(str_status.trim().equalsIgnoreCase("Completed")){
            btn_reorder.setVisibility(View.VISIBLE);
        }else{
            btn_reorder.setVisibility(View.GONE);
        }



        OrderHistoryDeatilsApiCalling(str_orderId);


        btn_reorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                for(int i =0;i<productLists.size();i++){



                    if(productDao.queryBuilder()
                            .where(ProductDao.Properties.ID.eq(productLists.get(i).getProductID()))
                            .list().size() == 0){

                        Product  productDbModel = new Product();

                        productDbModel.setID(productLists.get(i).getProductID());
                        productDbModel.setTitle(productLists.get(i).getProductName());

                        productDbModel.setCONTENT(productLists.get(i).getSKU());
                        productDbModel.setPrice(productLists.get(i).getPRICE());
                        productDbModel.setMQuantity(Integer.parseInt(productLists.get(i).getQty()));
                        productDbModel.setIMAGES(productLists.get(i).getImg());
                        //  moviesViewHolder.btn_increment.setVisibility(View.VISIBLE);

                        productDao.insert(productDbModel);

                   /* List<Product>  categoryId = productDao.loadAll();

                    for(int i =0;i<categoryId.size();i++){

                        movies.setId(categoryId.get(i).getId());
                    }*/



                    }else{

                        System.out.println("ALREADYYYYYYYYY");

                        Toast.makeText(OrderDeatilsActivity.this, "Already Added", Toast.LENGTH_SHORT).show();



                   /* Product productDbModel111 = new Product();

                    //  for(int i=0; i<categoryListdb.size();i++){


                    productDbModel111.setId(movies.getId());

                    productDbModel111.setID(movies.getID());
                    productDbModel111.setTitle(movies.getTitle());
                    productDbModel111.setIMAGES(movies.getIMAGES());
                    productDbModel111.setCONTENT(movies.getCONTENT());
                    productDbModel111.setPrice(movies.getPrice());
                    productDbModel111.setMQuantity(movies.getMQuantity());



                    System.out.println("111111111111111111"+movies.getMQuantity());
                    // }



                    productDao.update(productDbModel111);*/

                    }


                }






            }
        });
    }

    public  void OrderHistoryDeatilsApiCalling(String id){








        ApiInterface apiService =
                ApiClient.getClientArrayParse().create(ApiInterface.class);






        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(OrderDeatilsActivity.this);
        progressDoalog.setMessage("Loading....");
        // show it
        progressDoalog.show();
        progressDoalog.setCancelable(false);

/*
        Super Admin Login Details:
        Username: admin@zaigoinfotech.com
        Password: Ex58D??Yy44-PaBG
        */

        Call<OrderDeatilsModel> call = apiService.getOrderHistoryDeatilsData(""+id);
        call.enqueue(new Callback<OrderDeatilsModel>() {
            @Override
            public void onResponse(Call<OrderDeatilsModel> call,final Response<OrderDeatilsModel> response) {


                Log.e("TAG", "OrderHistoryModelTestsssss: "+new Gson().toJson(response.body()) );
//                System.out.println("SHIPPINGSSSSSSSSS"+response.body().getBillingAddress().getFirstName()+",,"+response.body().getFirstName());


                doubleList.clear();




                if (response.body().getResult() != null) {


               /*     OrderHistoryAdapter orderHistoryAdapter = new OrderHistoryAdapter(response.body().getResult());
                    recycler_orderlist.setAdapter(orderHistoryAdapter);
*/


               //txt_total.setText(""+response.body().getResult().getDelivery().getOrderTotal());


                    OrderHistoryDeatilAdapter OrderHistoryDeatilAdapter = new OrderHistoryDeatilAdapter(OrderDeatilsActivity.this,response.body().getResult().getProductList());


                   // recycler_productlist.addItemDecoration(new MyDividerItemDecoration(OrderDeatilsActivity.this, LinearLayoutManager.VERTICAL, 6));


                    recycler_productlist.setAdapter(OrderHistoryDeatilAdapter);

                    if(response.body().getResult().getProductList() != null){
                        productLists = response.body().getResult().getProductList();
                        for(int i =0;i<response.body().getResult().getProductList().size();i++){

                            String str = response.body().getResult().getProductList().get(i).getProductTotal();

                            doubleList.add(Double.parseDouble(response.body().getResult().getProductList().get(i).getProductTotal()));


                        }
                    }



               txt_shipping.setText("AED "+response.body().getResult().getDelivery().getShipping());

               txt_email.setText(""+response.body().getResult().getDelivery().getEmail());
                    txt_phone.setText(""+response.body().getResult().getDelivery().getPhone());
                    txt_address.setText(""+response.body().getResult().getDelivery().getAddress());
                    incassoMargherita(doubleList,""+response.body().getResult().getDelivery().getShipping());

                } else {
                    //   Toast.makeText(LoginActivity.this, "Please enter correct email and password", Toast.LENGTH_SHORT).show();

                    //  alertDialog(""+upperCaseFirst(response.body().getError()));
//                        System.out.println("fffffffffffffff"+response.body().getError());
                }


                progressDoalog.dismiss();

                //recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<OrderDeatilsModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("TAGggggggg","OrderHistoryModelerrrrrrrorrrrrrrr"+ t.toString());

                progressDoalog.dismiss();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }



    public  double incassoMargherita(List<Double> m,String str_shipping)
    {
        double sum = 0;
        double dd=0.0;
        for(int i = 0; i < m.size(); i++)
        {
            sum += m.get(i);
        }

        System.out.println("ADDITION"+sum);

        txt_total.setText("AED "+sum);

        System.out.println("STTTTTTTTTTTTTT"+str_shipping);



            if(!str_shipping.isEmpty()){
                dd=  sum+Double.parseDouble(str_shipping);
            }else{
                dd=  sum+Double.parseDouble("0.0");
            }







        txt_paymenttotal.setText("AED "+dd);
        return sum;
    }

}
