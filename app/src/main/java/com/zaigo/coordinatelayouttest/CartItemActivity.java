package com.zaigo.coordinatelayouttest;

import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zaigo.coordinatelayouttest.adapter.OrderAdapter;
import com.zaigo.coordinatelayouttest.common.Utility;
import com.zaigo.coordinatelayouttest.model.DaoSession;
import com.zaigo.coordinatelayouttest.model.DataPassModel;
import com.zaigo.coordinatelayouttest.model.Product;
import com.zaigo.coordinatelayouttest.model.ProductDao;

import java.util.ArrayList;
import java.util.List;

public class CartItemActivity extends AppCompatActivity {

    TextView mealTotalText;
    List<Product> orders ;

    ProductDao productDao;

    ListView storedOrders;
    Button btn_checkout;

    String str_minamount="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#000000"));
        }
        setContentView(R.layout.activity_cart_item);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("");
        }



        DaoSession daoSession = ((App)CartItemActivity.this.getApplication()).getDaoSession();
        productDao = daoSession.getProductDao();


        Intent i = getIntent();

        str_minamount = i.getStringExtra("minamu");

        System.out.println("nullllcheckkkkkkkkkk"+str_minamount);


         storedOrders = (ListView)findViewById(R.id.selected_food_list);

      //  orders = getListItemData();
        dbAdapter();
        mealTotalText = (TextView)findViewById(R.id.meal_total);

        btn_checkout = (Button)findViewById(R.id.btn_checkout);

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(calculateMealTotal()>=Double.parseDouble(str_minamount)){
                    Intent i = new Intent(CartItemActivity.this,DeliveryActivity.class);

                    i.putExtra("subtotal",""+calculateMealTotal());

                    i.putExtra("minamu",str_minamount);

                    DataPassModel dpm = new DataPassModel();

                   dpm.set_order_total(""+calculateMealTotal());
                    dpm.setSubTotal(""+calculateMealTotal());

                    startActivity(i);

                    finish();

                    System.out.println("trueeeeeeeeeeeeee");
                }else{
                    Utility.alertDialog(CartItemActivity.this,"Your cart is less amount to place an order.Minimum AED "+str_minamount+" need to deliver the order" );

                }




            }
        });
        setMealTotal();
    }

    public double calculateMealTotal(){
        double mealTotal = 0;
        for(Product order : orders){
            mealTotal += Double.parseDouble(order.getPrice()) * order.getmQuantity();
        }
        return mealTotal;
    }

    DataSetObserver observer = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            setMealTotal();
        }
    };

/*
    private ArrayList<Food> getListItemData(){
        ArrayList<Food> listViewItems = new ArrayList<Food>();
        listViewItems.add(new Food("Rice",30));
        listViewItems.add(new Food("Beans",40));
        listViewItems.add(new Food("Yam",60));
        listViewItems.add(new Food("Pizza",80));
        listViewItems.add(new Food("Fries",100));


        return listViewItems;
    }
*/

    public void setMealTotal(){
        mealTotalText.setText("AED"+" "+ calculateMealTotal());
    }


    public  void dbAdapter(){


        orders = productDao.loadAll();


        OrderAdapter adapter = new OrderAdapter(CartItemActivity.this, orders);

        storedOrders.setAdapter(adapter);
        adapter.registerDataSetObserver(observer);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent i = new Intent(CartItemActivity.this,NavigationDrawerActivity.class);
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(CartItemActivity.this,NavigationDrawerActivity.class);
        startActivity(i);
        finish();

        super.onBackPressed();
    }
}
