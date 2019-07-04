package com.zaigo.coordinatelayouttest.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;
import com.zaigo.coordinatelayouttest.App;
import com.zaigo.coordinatelayouttest.NavigationDrawerActivity;
import com.zaigo.coordinatelayouttest.R;
import com.zaigo.coordinatelayouttest.expand.MovieCategoryAdapter;
import com.zaigo.coordinatelayouttest.model.DaoSession;
import com.zaigo.coordinatelayouttest.model.Product;
import com.zaigo.coordinatelayouttest.model.ProductDao;

import java.util.HashMap;
import java.util.List;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private List<String> expandableListTitle;
    private HashMap<String, List<Product>> expandableListDetail;


    ProductDao productDao;
    DaoSession daoSession;
    Product productDbModel;

    private MovieCategoryAdapter.AdapterCallback adapterCallback;

    List<Product> categoryListdb;

    public CustomExpandableListAdapter(Activity context, List<String> expandableListTitle,
                                       HashMap<String, List<Product>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;


        daoSession = ((App)context.getApplication()).getDaoSession();
        productDao = daoSession.getProductDao();

        try {
            adapterCallback = ((MovieCategoryAdapter.AdapterCallback) context);
        } catch (ClassCastException e) {
            // do something
        }



    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final Product productchild = (Product) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.movies_view, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.tv_movies);

        TextView txt_description = (TextView) convertView
                .findViewById(R.id.txt_description);

        TextView txt_price = (TextView) convertView
                .findViewById(R.id.txt_price);

        ImageView img_background = (ImageView)convertView.findViewById(R.id.img_background);



        ImageView  plus_meal =(ImageView) convertView.findViewById(R.id.plus_meal);
        ImageView minus_meal =(ImageView) convertView.findViewById(R.id.minus_meal);

        TextView quantityText = (TextView)convertView.findViewById(R.id.quantity);

        LinearLayout linear_hideval = (LinearLayout)convertView.findViewById(R.id.linear_hideval);


        FrameLayout frameLayout = (FrameLayout) convertView.findViewById(R.id.frameLayout);


        expandedListTextView.setText(productchild.getTitle());


       // moviesViewHolder.mMoviesTextView.setText(movies.getTitle());

        txt_description.setText(productchild.getCONTENT());
         txt_price.setText("AED "+productchild.getPrice());
        //  https://organicfoodsandcafe.com/wp-content/uploads/2019/06/NR-BUTCHERY.png

        Picasso.get().load(""+productchild.getIMAGES()).fit().into(img_background);


        System.out.println();

        //   moviesViewHolder. quantityText.setText("x "+ movies.getmQuantity());
        categoryListdb = productDao.loadAll();


        calculateMealTotal();

        try {
            adapterCallback.onMethodCallback(calculateMealTotal());
        } catch (ClassCastException e) {
            // do something
        }


        if(categoryListdb.size()>0){
            NavigationDrawerActivity. txt_count.setText(""+categoryListdb.size());
        }else{

            NavigationDrawerActivity. txt_count.setText("0");
        }




        for(int i=0; i<categoryListdb.size();i++){




            if(productDao.queryBuilder()
                    .where(ProductDao.Properties.ID.eq(categoryListdb.get(i).getID()))
                    .list().size() != 0){

                // moviesViewHolder. quantityText.setText("x "+ movies.getmQuantity());
                if(productchild.getID().trim().equalsIgnoreCase(categoryListdb.get(i).getID())){

                    linear_hideval.setVisibility(View.VISIBLE);

                    quantityText.setVisibility(View.VISIBLE);

                     quantityText.setText(""+ categoryListdb.get(i).getMQuantity()+" * ");



                    productchild.setId(categoryListdb.get(i).getId());



                }
            }else{
                 quantityText.setText(""+ productchild.getmQuantity()+" * ");
                productchild.setId(null);
                linear_hideval.setVisibility(View.GONE);

                quantityText.setVisibility(View.GONE);
            }



            System.out.println("QUANTITYYYYYYYYYYYYYY"+categoryListdb.get(i).getMQuantity());



        }


        plus_meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(productDao.queryBuilder()
                        .where(ProductDao.Properties.ID.eq(productchild.getID()))
                        .list().size() == 0){
                    productchild.addToQuantity();
                    quantityText.setText(""+productchild.getmQuantity()+" * ");
                }else {
                    for (int i = 0; i < categoryListdb.size(); i++) {


                        if (productDao.queryBuilder()
                                .where(ProductDao.Properties.ID.eq(categoryListdb.get(i).getID()))
                                .list().size() != 0) {

                            // moviesViewHolder. quantityText.setText("x "+ movies.getmQuantity());
                            if (productchild.getID().trim().equalsIgnoreCase(categoryListdb.get(i).getID())) {

                                categoryListdb.get(i).addToQuantity();
                                productchild.setMQuantity(categoryListdb.get(i).getMQuantity());

                                quantityText.setText("" + categoryListdb.get(i).getMQuantity()+" * ");


                            }
                        }
                    }
                }

                //-------------------------------------------------------------------------------------------------------------


                if(productDao.queryBuilder()
                        .where(ProductDao.Properties.ID.eq(productchild.getID()))
                        .list().size() == 0){

                    productDbModel = new Product();

                    productDbModel.setID(productchild.getID());
                    productDbModel.setTitle(productchild.getTitle());

                    productDbModel.setCONTENT(productchild.getCONTENT());
                    productDbModel.setPrice(productchild.getPrice());
                    productDbModel.setMQuantity(productchild.getmQuantity());
                    productDbModel.setIMAGES(productchild.getIMAGES());
                    //  moviesViewHolder.btn_increment.setVisibility(View.VISIBLE);

                    productDao.insert(productDbModel);

                    List<Product>  categoryId = productDao.loadAll();

                    for(int i =0;i<categoryId.size();i++){

                        productchild.setId(categoryId.get(i).getId());
                    }

                    if(categoryListdb.size()>0){
                        NavigationDrawerActivity. txt_count.setText(""+categoryId.size());
                    }else{

                        NavigationDrawerActivity. txt_count.setText("0");
                    }


                }else{

                    System.out.println("ALREADYYYYYYYYY");




                    Product productDbModel111 = new Product();

                    //  for(int i=0; i<categoryListdb.size();i++){


                    productDbModel111.setId(productchild.getId());

                    productDbModel111.setID(productchild.getID());
                    productDbModel111.setTitle(productchild.getTitle());
                    productDbModel111.setIMAGES(productchild.getIMAGES());
                    productDbModel111.setCONTENT(productchild.getCONTENT());
                    productDbModel111.setPrice(productchild.getPrice());
                    productDbModel111.setMQuantity(productchild.getMQuantity());



                    System.out.println("111111111111111111"+productchild.getMQuantity());
                    // }



                    productDao.update(productDbModel111);

                }

                //  currentCost.setText("GH"+"\u20B5"+" "+ (currentFood.getmAmount() * currentFood.getmQuantity()));
                //  notifyDataSetChanged();


                //  moviesViewHolder.quantityText.setText("x "+ movies.getmQuantity());
                //currentCost.setText("GH"+"\u20B5"+" "+ (currentFood.getmAmount() * currentFood.getmQuantity()));
                notifyDataSetChanged();
            }
        });

        minus_meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* movies.removeFromQuantity();
                moviesViewHolder.quantityText.setText("x "+movies.getmQuantity());
              //  currentCost.setText("GH"+"\u20B5"+" "+ (currentFood.getmAmount() * currentFood.getmQuantity()));
                notifyDataSetChanged();*/








                if(productDao.queryBuilder()
                        .where(ProductDao.Properties.ID.eq(productchild.getID()))
                        .list().size() == 0){
                    productchild.removeFromQuantity();
                    quantityText.setText(""+productchild.getmQuantity()+" * ");
                }else {
                    for (int i = 0; i < categoryListdb.size(); i++) {


                        if (productDao.queryBuilder()
                                .where(ProductDao.Properties.ID.eq(categoryListdb.get(i).getID()))
                                .list().size() != 0) {

                            // moviesViewHolder. quantityText.setText("x "+ movies.getmQuantity());
                            if (productchild.getID().trim().equalsIgnoreCase(categoryListdb.get(i).getID())) {

                                categoryListdb.get(i).removeFromQuantity();
                                productchild.setMQuantity(categoryListdb.get(i).getMQuantity());

                               quantityText.setText("" + categoryListdb.get(i).getMQuantity()+" * ");


                            }
                        }
                    }
                }
//--------------------------------------------------------------------------------------------------------------------------

                if(productDao.queryBuilder()
                        .where(ProductDao.Properties.ID.eq(productchild.getID()))
                        .list().size() == 0){

                    productDbModel = new Product();

                    productDbModel.setID(productchild.getID());
                    productDbModel.setTitle(productchild.getTitle());
                    productDbModel.setIMAGES(productchild.getIMAGES());
                    productDbModel.setCONTENT(productchild.getCONTENT());
                    productDbModel.setPrice(productchild.getPrice());
                    productDbModel.setMQuantity(productchild.getmQuantity());
                    //  moviesViewHolder.btn_increment.setVisibility(View.VISIBLE);

                    productDao.insert(productDbModel);

                    List<Product>  categoryId = productDao.loadAll();

                    for(int i =0;i<categoryId.size();i++){

                        productchild.setId(categoryId.get(i).getId());
                    }

                    if(categoryListdb.size()>0){
                        NavigationDrawerActivity. txt_count.setText(""+categoryId.size());
                    }else{

                        NavigationDrawerActivity. txt_count.setText("0");
                    }

                }else{

                    System.out.println("ALREADYYYYYYYYY");



                    Product productDbModel111 = new Product();

                    //  for(int i=0; i<categoryListdb.size();i++){


                    productDbModel111.setId(productchild.getId());

                    productDbModel111.setID(productchild.getID());
                    productDbModel111.setTitle(productchild.getTitle());
                    productDbModel111.setIMAGES(productchild.getIMAGES());
                    productDbModel111.setCONTENT(productchild.getCONTENT());
                    productDbModel111.setPrice(productchild.getPrice());
                    productDbModel111.setMQuantity(productchild.getMQuantity());



                    System.out.println("111111111111111111"+productchild.getMQuantity());
                    // }



                    productDao.update(productDbModel111);

                }
                notifyDataSetChanged();
            }
        });

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(productDao.queryBuilder()
                        .where(ProductDao.Properties.ID.eq(productchild.getID()))
                        .list().size() == 0){
                    productchild.addToQuantity();
                    quantityText.setText(""+productchild.getmQuantity()+" * ");
                    productchild.setId(productchild.getId());
                }else {
                    for (int i = 0; i < categoryListdb.size(); i++) {


                        if (productDao.queryBuilder()
                                .where(ProductDao.Properties.ID.eq(categoryListdb.get(i).getID()))
                                .list().size() != 0) {

                            // moviesViewHolder. quantityText.setText("x "+ movies.getmQuantity());
                            if (productchild.getID().trim().equalsIgnoreCase(categoryListdb.get(i).getID())) {

                                categoryListdb.get(i).addToQuantity();
                                productchild.setMQuantity(categoryListdb.get(i).getMQuantity());
                                productchild.setId(categoryListdb.get(i).getId());

                                quantityText.setText("" + categoryListdb.get(i).getMQuantity()+" * ");


                            }
                        }
                    }
                }
                ///   --------------------------------------------------------------------------------------------------------




                if(productDao.queryBuilder()
                        .where(ProductDao.Properties.ID.eq(productchild.getID()))
                        .list().size() == 0){

                    productDbModel = new Product();

                    productDbModel.setID(productchild.getID());
                    productDbModel.setTitle(productchild.getTitle());
                    productDbModel.setIMAGES(productchild.getIMAGES());
                    productDbModel.setCONTENT(productchild.getCONTENT());
                    productDbModel.setPrice(productchild.getPrice());
                    productDbModel.setMQuantity(productchild.getmQuantity());
                    //  moviesViewHolder.btn_increment.setVisibility(View.VISIBLE);

                    productDao.insert(productDbModel);
                    List<Product>  categoryId = productDao.loadAll();

                    for(int i =0;i<categoryId.size();i++){

                        productchild.setId(categoryId.get(i).getId());
                    }

                    if(categoryListdb.size()>0){
                        NavigationDrawerActivity. txt_count.setText(""+categoryId.size());
                    }else{

                        NavigationDrawerActivity. txt_count.setText("0");
                    }

                }else{

                    System.out.println("ALREADYYYYYYYYY");








                    System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvv"+productchild.getId());
                    Product productDbModel111 = new Product();

                    //  for(int i=0; i<categoryListdb.size();i++){


                    productDbModel111.setId(productchild.getId());

                    productDbModel111.setID(productchild.getID());
                    productDbModel111.setTitle(productchild.getTitle());
                    productDbModel111.setIMAGES(productchild.getIMAGES());
                    productDbModel111.setCONTENT(productchild.getCONTENT());
                    productDbModel111.setPrice(productchild.getPrice());
                    productDbModel111.setMQuantity(productchild.getMQuantity());



                    System.out.println("111111111111111111"+productchild.getMQuantity());
                    // }



                    productDao.update(productDbModel111);



                }






               /* productDbModel = new ProductDbModel();

                productDbModel.setID(movies.getID());
                productDbModel.setTitle(movies.getTitle());
                productDbModel.setCONTENT(movies.getCONTENT());
                productDbModel.setPrice(movies.getPrice());
                productDbModel.setMQuantity(movies.getmQuantity());
                //  moviesViewHolder.btn_increment.setVisibility(View.VISIBLE);

                productDbModelDao.insert(productDbModel);*/


                System.out.println("childddddddddddddd"+productchild.getTitle());

                notifyDataSetChanged();

            }
        });


        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.movie_category_view, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.tv_movie_category);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }


    public  double calculateMealTotal(){
        double mealTotal = 0;
        for(Product order : categoryListdb){
            mealTotal += Double.parseDouble(order.getPrice()) * order.getmQuantity();
        }


        System.out.println("ggggggggggggggggggggg"+mealTotal);

        return mealTotal;
    }

}