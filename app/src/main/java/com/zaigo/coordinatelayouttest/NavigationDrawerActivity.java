package com.zaigo.coordinatelayouttest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;
import com.zaigo.coordinatelayouttest.adapter.OrderHistoryDeatilAdapter;
import com.zaigo.coordinatelayouttest.common.MyDividerItemDecoration;
import com.zaigo.coordinatelayouttest.common.PreferenceManager;
import com.zaigo.coordinatelayouttest.common.Utility;
import com.zaigo.coordinatelayouttest.expand.ExpandableRecyclerAdapter;
import com.zaigo.coordinatelayouttest.expand.MovieCategory;
import com.zaigo.coordinatelayouttest.expand.MovieCategoryAdapter;
import com.zaigo.coordinatelayouttest.expand.Movies;
import com.zaigo.coordinatelayouttest.model.DaoSession;
import com.zaigo.coordinatelayouttest.model.OrderDeatilsModel;
import com.zaigo.coordinatelayouttest.model.Product;
import com.zaigo.coordinatelayouttest.model.ProductDao;
import com.zaigo.coordinatelayouttest.model.ProductDbModelDao;
import com.zaigo.coordinatelayouttest.model.StoreCategory;
import com.zaigo.coordinatelayouttest.model.StoreCategoryModel;
import com.zaigo.coordinatelayouttest.model.StoreChildModel;
import com.zaigo.coordinatelayouttest.model.UploadCartModel;
import com.zaigo.coordinatelayouttest.restapi.ApiClient;
import com.zaigo.coordinatelayouttest.restapi.ApiInterface;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import android.app.SearchManager;

import android.widget.SearchView.OnQueryTextListener;



import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MovieCategoryAdapter.AdapterCallback {

    DrawerLayout drawer;
    private MovieCategoryAdapter mAdapter;
    private RecyclerView recyclerView;

    List<MovieCategory> movieCategories = new ArrayList<>();
    List<Product> storeCategoryList;

    Toolbar toolbar;
    private boolean appBarExpanded = true;
    private Menu collapsedMenu;
    LinearLayout linear_hide;
  // SearchView svMenu;

    Timer timer;
    LinearLayoutManager linearVertical;
    private List<String> mDataList = new ArrayList<>();
    MagicIndicator magicIndicator;
    FragmentContainerHelper mFramentContainerHelper;

    CoordinatorLayout testhide;

    List<StoreCategory> storeCategoryparentList  = new ArrayList<>();

    ProductDao productDao;

   public static TextView txt_count;

   TextView txt_call,txt_minorder,txt_delivery;
    ImageView imageViewMusic;

    NavigationView navigationView;


    public static final int LOCATION_REQUEST = 1000;
    public static final int GPS_REQUEST = 1001;


    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;




    private boolean isContinue = false;
    private boolean isGPS = false;

    double latitude,longitude;

    GPSTracker gps;

    Button btn_cart;


    List<Product> orders ;

    TextView txt_carttotal,txt_decscription;

    double dou_total =0.0,dou_minorder=0.0;

    LinearLayout linear_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#000000"));
        }
        setContentView(R.layout.activity_navigation_drawer);

        gps = new GPSTracker(NavigationDrawerActivity.this);

        if (ActivityCompat.checkSelfPermission(NavigationDrawerActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(NavigationDrawerActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NavigationDrawerActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DaoSession daoSession = ((App)NavigationDrawerActivity.this.getApplication()).getDaoSession();
        productDao = daoSession.getProductDao();


        orders = productDao.loadAll();

        AppBarLayout appBarLayout = (AppBarLayout)findViewById(R.id.appBar);
        magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator8);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        SearchView simpleSearchView = (SearchView) findViewById(R.id.svMenu);

        EditText searchEditText = (EditText)simpleSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(Color.parseColor("#ffffff"));
        searchEditText.setHintTextColor(Color.parseColor("#ffffff"));


        linear_search = (LinearLayout)findViewById(R.id.linear_search);
        testhide = (CoordinatorLayout) findViewById(R.id.testhide);
        testhide.setVisibility(View.VISIBLE);

        linearVertical = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearVertical);

        txt_decscription = (TextView)findViewById(R.id.txt_decscription);
        linear_hide = (LinearLayout)findViewById(R.id.linear_hide);


        txt_count =(TextView)findViewById(R.id.txt_count);


        txt_call = (TextView)findViewById(R.id.txt_call);
        txt_minorder = (TextView)findViewById(R.id.txt_minorder);
        txt_delivery = (TextView)findViewById(R.id.txt_delivery);

        btn_cart = (Button)findViewById(R.id.btn_cart);



        //changes

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0 && s.length() >= 3) {

                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                    mAdapter.getFilter().filter(s.toString());
                                }
                            });

                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }, 500);
                }

            }
        });







    txt_carttotal=(TextView)findViewById(R.id.txt_carttotal);

        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Product> productList = productDao.loadAll();
                if(productList.size()>0){


                    if(dou_total>=dou_minorder){
                        Intent i = new Intent(NavigationDrawerActivity.this,CartItemActivity.class);



                        String vvv =""+dou_minorder;

                        i.putExtra("minamu",vvv);

                        startActivity(i);
                        finish();
                    }else{

                        Utility.alertDialog(NavigationDrawerActivity.this,"Your cart is less amount to place an order.Minimum AED "+dou_minorder+" need to deliver the order" );
                    }

                    //  Toast.makeText(NavigationDrawerActivity.this, "TEST", Toast.LENGTH_SHORT).show();
                }else{

                    Toast.makeText(NavigationDrawerActivity.this, "No cart items", Toast.LENGTH_SHORT).show();
                }
            }
        });

        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 0));

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
       /* NavigationMenuView navMenuView = (NavigationMenuView) navigationView.getChildAt(0);
        navMenuView.addItemDecoration(new DividerItemDecoration(NavigationDrawerActivity.this, DividerItemDecoration.VERTICAL));*/
        navigationView.setNavigationItemSelectedListener(this);

        imageViewMusic = findViewById(R.id.imaViewMusic);


        txt_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Product> productList = productDao.loadAll();

                if(productList.size()>0){


                    if(dou_total>=dou_minorder){
                        Intent i = new Intent(NavigationDrawerActivity.this,CartItemActivity.class);


                        String vvv =""+dou_minorder;

                        i.putExtra("minamu",vvv);
                        startActivity(i);
                        finish();
                    }else{

                        Utility.alertDialog(NavigationDrawerActivity.this,"Your cart is less amount to place an order.Minimum AED "+dou_minorder+" need to deliver the order" );
                    }



                  //  Toast.makeText(NavigationDrawerActivity.this, "TEST", Toast.LENGTH_SHORT).show();
                }else{

                    Toast.makeText(NavigationDrawerActivity.this, "No cart items", Toast.LENGTH_SHORT).show();
                }

            }
        });


        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d(NavigationDrawerActivity.class.getSimpleName(), "onOffsetChanged: verticalOffset: " + verticalOffset);

                //  Vertical offset == 0 indicates appBar is fully expanded.
                if (Math.abs(verticalOffset) > 230) {
                    appBarExpanded = false;
                    // recyclerTabLayout.setVisibility(View.VISIBLE);

                    magicIndicator.setVisibility(View.VISIBLE);

                    linear_hide.setVisibility(View.GONE);
                   // linear_search.setVisibility(View.GONE);
                  //  linear_scrollissuse.setVisibility(View.GONE);
                  //  txt_decscription.setVisibility(View.INVISIBLE);
                    invalidateOptionsMenu();
                } else {
                    appBarExpanded = true;
                    magicIndicator.setVisibility(View.GONE);
                  //  txt_decscription.setVisibility(View.VISIBLE);
                    linear_hide.setVisibility(View.VISIBLE);
                   // linear_scrollissuse.setVisibility(View.GONE);

                 //   linear_hide.setVisibility(View.VISIBLE);
                 //   linear_search.setVisibility(View.GONE);
                    invalidateOptionsMenu();
                }
            }
        });




        if (Utility.isConnected(NavigationDrawerActivity.this)) {
            mainProductCategoryApiCalling();
        }else{
            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();

        }











        findViewById(R.id.drawer_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open right drawer

                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }
                else
                    drawer.openDrawer(GravityCompat.END);
            }
        });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.app_name, R.string.app_name);
        toggle.setDrawerIndicatorEnabled(false);
        drawer.addDrawerListener(toggle);

        toggle.syncState();


        if(!PreferenceManager.getUser_Id(getApplicationContext()).isEmpty()){
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_previousorder).setVisible(true);
            nav_Menu.findItem(R.id.nav_profile).setVisible(true);

            System.out.println("ttttttttttttttttt"+PreferenceManager.getUser_Id(getApplicationContext()));

        }else{
            hideItem();
        }




    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
           // Toast.makeText(getApplicationContext(), "Camera is clicked", Toast.LENGTH_SHORT).show();
            startAnimatedActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            //startAnimatedActivity(new Intent(getApplicationContext(), OrderhistoryActivity.class));
           // testhide.setVisibility(View.GONE);

        } else if (id == R.id.nav_previousorder) {
            //startAnimatedActivity(new Intent(getApplicationContext(), OrderhistoryActivity.class));

            Intent i = new Intent(NavigationDrawerActivity.this,OrderhistoryActivity.class);
            startActivity(i);
            finish();

           // startAnimatedActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        } else if (id == R.id.nav_faq) {
            Toast.makeText(getApplicationContext(), "Faq is clicked", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_termandpolicy) {
            Toast.makeText(getApplicationContext(), "Help is clicked", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_login) {
            //Toast.makeText(getApplicationContext(), "Share is clicked", Toast.LENGTH_SHORT).show();

            if(item.getTitle().toString().trim().equalsIgnoreCase("LogOut")){


                new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to LogOut?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(NavigationDrawerActivity.this,LoginActivity.class);

                                PreferenceManager.setUser_Id("",getApplicationContext());

                                startActivity(i);
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }else{

                Intent i = new Intent(NavigationDrawerActivity.this,LoginActivity.class);
                PreferenceManager.setUser_Id("",getApplicationContext());
                startActivity(i);
                finish();

            }

        } else if (id == R.id.nav_deliveryschedule) {
            startAnimatedActivity(new Intent(getApplicationContext(), DeliveryScheduleActivity.class));

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    protected void startAnimatedActivity(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    private void hideItem()
    {

        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_previousorder).setVisible(false);
        nav_Menu.findItem(R.id.nav_profile).setVisible(false);

        nav_Menu.findItem(R.id.nav_login).setTitle("LogIn");
    }



    private void initMagicIndicator8() {

        magicIndicator.setBackgroundColor(Color.BLACK);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setScrollPivotX(0.35f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#ffffff"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#ffffff"));


                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // viewPager.setCurrentItem(index);

                        mAdapter.collapseAllParents();

                        mFramentContainerHelper.handlePageSelected(index);

                        mAdapter.expandParent(index);



                       // childListDeatilsApiCalling(index,storeCategoryparentList.get(index).getMainId());
                        System.out.println("RRRRRRRRRRRRRRR"+index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {

                System.out.println("VVVVVVVVVVVVVVVVVVVV");
                WrapPagerIndicator indicator = new WrapPagerIndicator(context);
                indicator.setFillColor(Color.parseColor("#FF2600"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);

        mFramentContainerHelper = new FragmentContainerHelper(magicIndicator);
        // ViewPagerHelper.bind(magicIndicator, viewPager);
    }



    public void mainProductCategoryApiCalling() {


        ApiInterface apiService =
                ApiClient.getClientArrayParse().create(ApiInterface.class);


        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(NavigationDrawerActivity.this);
        progressDoalog.setMessage("Loading....");

        // show it
        progressDoalog.show();
        progressDoalog.setCancelable(false);


        Call<StoreCategoryModel> call = apiService.getStoreCategory();
        call.enqueue(new Callback<StoreCategoryModel>() {
            @Override
            public void onResponse(Call<StoreCategoryModel> call, final Response<StoreCategoryModel> response) {


                Log.e("TAG", "OrderHistoryModelTestsssss: " + new Gson().toJson(response.body()));
//                System.out.println("SHIPPINGSSSSSSSSS"+response.body().getBillingAddress().getFirstName()+",,"+response.body().getFirstName());


                storeCategoryparentList.clear();
                mDataList.clear();


                if (response.body() != null) {
                    storeCategoryparentList.addAll(response.body().getStoreList().get(0).getCategories());

                    txt_call.setText(response.body().getStoreList().get(0).getPhone());

                    txt_minorder.setText(response.body().getStoreList().get(0).getMinOrder());


                    dou_minorder = Double.parseDouble(response.body().getStoreList().get(0).getMinOrder());

                    txt_delivery.setText(response.body().getStoreList().get(0).getDelivery());

                    storeCategoryparentList.addAll(response.body().getStoreList().get(0).getCategories());

                    txt_decscription.setText(""+response.body().getStoreList().get(0).getDescription());

                    Picasso.get().load(response.body().getStoreList().get(0).getStoreImage()).fit().into(imageViewMusic);


                    System.out.println("storeimageeeeee"+response.body().getStoreList().get(0).getStoreImage());




                    for (int i = 0; i < response.body().getStoreList().get(0).getCategories().size(); i++) {
                        mDataList.add(response.body().getStoreList().get(0).getCategories().get(i).getName());


                        System.out.println("MAINNNNNNNNNNNNNNN" + response.body().getStoreList().get(0).getCategories().get(i).getMainId());


                    }

                    childListDeatilsApiCalling(0);
                    initMagicIndicator8();
                }


                progressDoalog.dismiss();


            }

            @Override
            public void onFailure(Call<StoreCategoryModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("TAGggggggg", "OrderHistoryModelerrrrrrrorrrrrrrr" + t.toString());

                progressDoalog.dismiss();
            }
        });

    }

    public void childListDeatilsApiCalling(final int posi) {


        ApiInterface apiService =
                ApiClient.getClientArrayParse().create(ApiInterface.class);


        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(NavigationDrawerActivity.this);
        progressDoalog.setMessage("Loading....");
        // show it
        progressDoalog.show();
        // progressDoalog.setIndeterminate(true);
        progressDoalog.setCancelable(false);

/*
        Super Admin Login Details:
        Username: admin@zaigoinfotech.com
        Password: Ex58D??Yy44-PaBG
        */

        Call<StoreChildModel> call = apiService.getStoreChildList(storeCategoryparentList.get(posi).getMainId());
        call.enqueue(new Callback<StoreChildModel>() {
            @Override
            public void onResponse(Call<StoreChildModel> call, final Response<StoreChildModel> response) {

                Log.e("TAG", "CMAIN: " + storeCategoryparentList.get(posi).getMainId());

                //    movieCategories.clear();
                //Log.e("TAG", "childddddddddddddddd: " + new Gson().toJson(response.body()));
//                System.out.println("SHIPPINGSSSSSSSSS"+response.body().getBillingAddress().getFirstName()+",,"+response.body().getFirstName());


                if (response.body() != null) {


                    // Gson gson=new Gson();
                    List<StoreChildModel.StoreListBean.ProductsBean> storeChildModel=response.body().getStore_list().get(0).getProducts();
                    Log.e("Prakash","Prakash"+ storeChildModel);

                    expandData(storeChildModel,storeCategoryparentList.get(posi).getName());

                    if (mDataList.size() != posi + 1) {
                        childListDeatilsApiCalling(posi + 1);

                    }else if(mDataList.size()==posi+1)
                    {
                        mAdapter = new MovieCategoryAdapter(NavigationDrawerActivity.this, movieCategories);

                        recyclerView.setAdapter(mAdapter);

                        mAdapter.collapseAllParents();

                        mAdapter.expandParent(0);

                        mFramentContainerHelper.handlePageSelected(0);

                        mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
                            @Override
                            public void onListItemExpanded(int position) {

                                System.out.println("valuessssssssssssssss"+storeCategoryparentList.get(position).getName());
                                // OrderHistoryDeatilsApiCalling(position,storeCategoryparentList.get(position).getMainId());
                                //  childListDeatilsApiCalling(position,storeCategoryparentList.get(position).getMainId());
                                mAdapter.collapseAllParents();
                                mAdapter.expandParent(position);

                                mFramentContainerHelper.handlePageSelected(position);


                            }

                            @Override
                            public void onListItemCollapsed(int position) {

                            }
                        });

                        //System.out.println("QQQQQQQQQQQQQQQQ" + name + "," +storeCategoryList);
                        // Log.e("TAG", "RESULTTTTTTTTT: " + new Gson().toJson(result));
                        //System.out.println("SIZEEEEEEEEEEEEE" + result.size());

                    }




                    // result.add(response.body().getStoreList().get(0).getProducts());


                    Log.e("TAG", "ChildProducrrrrrrrrrrrrrr: " + new Gson().toJson(response.body().getStore_list().get(0).getProducts()));




                }


                progressDoalog.dismiss();

                //recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<StoreChildModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("TAGggggggg", "OrderHistoryModelerrrrrrrorrrrrrrr" + t.toString());

                progressDoalog.dismiss();
            }
        });

    }


/*
    public  void mainProductCategoryApiCalling(){








        ApiInterface apiService =
                ApiClient.getClientArrayParse().create(ApiInterface.class);






        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(NavigationDrawerActivity.this);
        progressDoalog.setMessage("Loading....");

        // show it
        progressDoalog.show();
        progressDoalog.setCancelable(false);





        Call<StoreCategoryModel> call = apiService.getStoreCategory();
        call.enqueue(new Callback<StoreCategoryModel>() {
            @Override
            public void onResponse(Call<StoreCategoryModel> call,final Response<StoreCategoryModel> response) {


                Log.e("TAG", "OrderHistoryModelTestsssss: "+new Gson().toJson(response.body()) );
//                System.out.println("SHIPPINGSSSSSSSSS"+response.body().getBillingAddress().getFirstName()+",,"+response.body().getFirstName());


                storeCategoryparentList.clear();
                mDataList.clear();




                if (response.body() != null) {


                    txt_call.setText(response.body().getStoreList().get(0).getPhone());

                    txt_minorder.setText(response.body().getStoreList().get(0).getMinOrder());


                    dou_minorder = Double.parseDouble(response.body().getStoreList().get(0).getMinOrder());

                    txt_delivery.setText(response.body().getStoreList().get(0).getDelivery());

                    storeCategoryparentList.addAll(response.body().getStoreList().get(0).getCategories());

                    txt_decscription.setText(""+response.body().getStoreList().get(0).getDescription());

                    Picasso.get().load(response.body().getStoreList().get(0).getStoreImage()).fit().into(imageViewMusic);


                    System.out.println("storeimageeeeee"+response.body().getStoreList().get(0).getStoreImage());
                    //  List<StoreCategory> list = new ArrayList<>();


                   // list.addAll(storeCategoryparentList);

                   for(int i=0;i<response.body().getStoreList().get(0).getCategories().size();i++){
                       mDataList.add(response.body().getStoreList().get(0).getCategories().get(i).getName());


                       System.out.println("MAINNNNNNNNNNNNNNN"+response.body().getStoreList().get(0).getCategories().get(i).getMainId());

                       childListDeatilsApiCalling(0,response.body().getStoreList().get(0).getCategories().get(i).getMainId());

                   }


                    initMagicIndicator8();

                }


                progressDoalog.dismiss();






                //recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<StoreCategoryModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("TAGggggggg","OrderHistoryModelerrrrrrrorrrrrrrr"+ t.toString());

                progressDoalog.dismiss();
            }
        });

    }*/


/*
    public  void childListDeatilsApiCalling(int  posi,String id){








        ApiInterface apiService =
                ApiClient.getClientArrayParse().create(ApiInterface.class);






        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(NavigationDrawerActivity.this);
        progressDoalog.setMessage("Loading....");
        // show it
        progressDoalog.show();
       // progressDoalog.setIndeterminate(true);
        progressDoalog.setCancelable(false);





        Call<StoreChildModel> call = apiService.getStoreChildList(id);
        call.enqueue(new Callback<StoreChildModel>() {
            @Override
            public void onResponse(Call<StoreChildModel> call,final Response<StoreChildModel> response) {


                movieCategories.clear();
                Log.e("TAG", "childddddddddddddddd: "+new Gson().toJson(response.body()) );
//                System.out.println("SHIPPINGSSSSSSSSS"+response.body().getBillingAddress().getFirstName()+",,"+response.body().getFirstName());








                if (response.body() != null) {




                    storeCategoryList = response.body().getStoreList().get(0).getProducts();


                    for(int ii=0; ii<storeCategoryList.size();ii++){

                        response.body().getStoreList().get(0).getProducts().get(ii).setmQuantity(0);

                    }


                    for(int i =0; i<storeCategoryparentList.size();i++){

                        // System.out.println("vvvvvvvvvvvvvvvvvvvv"+list.get(i).getName());




                        MovieCategory molvie_category_one = new MovieCategory(storeCategoryparentList.get(i).getName(), storeCategoryList);

                        movieCategories.add(molvie_category_one);
                    }

                    mAdapter = new MovieCategoryAdapter(NavigationDrawerActivity.this, movieCategories);

                    recyclerView.setAdapter(mAdapter);



                    mAdapter.expandParent(posi);

                    mFramentContainerHelper.handlePageSelected(posi);

                    mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
                        @Override
                        public void onListItemExpanded(int position) {

                            System.out.println("valuessssssssssssssss"+storeCategoryparentList.get(position).getName());
                           // OrderHistoryDeatilsApiCalling(position,storeCategoryparentList.get(position).getMainId());
                            childListDeatilsApiCalling(position,storeCategoryparentList.get(position).getMainId());
                            mAdapter.collapseAllParents();

                        }

                        @Override
                        public void onListItemCollapsed(int position) {

                        }
                    });

                }


                progressDoalog.dismiss();

                //recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<StoreChildModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("TAGggggggg","OrderHistoryModelerrrrrrrorrrrrrrr"+ t.toString());

                progressDoalog.dismiss();
            }
        });

    }
*/

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
    public void onMethodCallback(double dTotal) {

        dou_total = dTotal;

        txt_carttotal.setText("AED "+dTotal);
        System.out.println("ttttttttttttttttttttt"+dTotal);





        // do something
    }

   /* public double calculateMealTotal(){
        double mealTotal = 0;
        for(Product order : orders){
            mealTotal += Double.parseDouble(order.getPrice()) * order.getmQuantity();
        }
        return mealTotal;
    }*/

    public void expandData(List<StoreChildModel.StoreListBean.ProductsBean> results, String name) {

       /* for (int i = 0; i < storeCategoryparentList.size(); i++) {

            expandableListTitle.add(storeCategoryparentList.get(i).getName());


            System.out.println("FFFFFFFFFFFFFFFFFFFF" + new Gson().toJson(results.get(0)));


          */

      //  expandableListTitle.add(name);

        storeCategoryList = new ArrayList<Product>();

        //   System.out.println("SIZEEEEEEEEEEEEE"+result.size());
        for (int ii = 0; ii < results.size(); ii++) {

            Product pp = new Product();
            pp.setID(results.get(ii).getID());
            pp.setCONTENT(results.get(ii).getCONTENT());
            pp.setIMAGES(results.get(ii).getIMAGES());
            pp.setPrice(results.get(ii).getPrice());
            pp.setTitle(results.get(ii).getTitle());
            pp.setMQuantity(results.get(ii).getMQuantity());


            storeCategoryList.add(pp);
        }

        MovieCategory molvie_category_one = new MovieCategory(name, storeCategoryList);

        movieCategories.add(molvie_category_one);

      //  expandableListDetail.put(name, ChildList);
        // }


    }

}