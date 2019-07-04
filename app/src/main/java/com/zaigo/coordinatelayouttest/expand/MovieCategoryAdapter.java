package com.zaigo.coordinatelayouttest.expand;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;
import com.zaigo.coordinatelayouttest.App;
import com.zaigo.coordinatelayouttest.NavigationDrawerActivity;
import com.zaigo.coordinatelayouttest.R;
import com.zaigo.coordinatelayouttest.model.DaoSession;
import com.zaigo.coordinatelayouttest.model.Product;
import com.zaigo.coordinatelayouttest.model.ProductDao;
import com.zaigo.coordinatelayouttest.model.ProductDbModel;

import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class MovieCategoryAdapter extends ExpandableRecyclerAdapter<MovieCategoryViewHolder, MoviesViewHolder> implements Filterable {
    List<Product> categoryListdb;
    List<MovieCategory> categorys;
    List<MovieCategory> filtercategoryListdb;

    private LayoutInflater mInflator;
    ProductDao productDao;
    DaoSession daoSession;
    Product productDbModel;
public int count=-1;
    private AdapterCallback adapterCallback;

    public MovieCategoryAdapter(Activity context, List<MovieCategory> movieCategories) {
        super(movieCategories);
        mInflator = LayoutInflater.from(context);

        daoSession = ((App) context.getApplication()).getDaoSession();
        productDao = daoSession.getProductDao();
        categoryListdb =productDao.loadAll();
        categorys=movieCategories;
        filtercategoryListdb =movieCategories;
        try {
            adapterCallback = ((AdapterCallback) context);
        } catch (ClassCastException e) {
            // do something
        }


    }

    @Override
    public MovieCategoryViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View movieCategoryView = mInflator.inflate(R.layout.movie_category_view, parentViewGroup, false);
        return new MovieCategoryViewHolder(movieCategoryView);
    }

    @Override
    public MoviesViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View moviesView = mInflator.inflate(R.layout.movies_view, childViewGroup, false);
        return new MoviesViewHolder(moviesView);
    }

    @Override
    public void onBindParentViewHolder(MovieCategoryViewHolder movieCategoryViewHolder, int position, ParentListItem parentListItem) {
        MovieCategory movieCategory = (MovieCategory) parentListItem;
        count=count+1;
        Log.e("HEADER","HEADER"+count);

        if(filtercategoryListdb.size()>count)
        {
            movieCategoryViewHolder.bind(filtercategoryListdb.get(count));

        }
        //count=count++;

    }

    @Override
    public void onBindChildViewHolder(MoviesViewHolder moviesViewHolder, int position, final Object childListItem) {
        Product movies = (Product) childListItem;


        moviesViewHolder.bind(movies);

        moviesViewHolder.mMoviesTextView.setText(movies.getTitle());

        moviesViewHolder.txt_description.setText(movies.getCONTENT());
        moviesViewHolder.txt_price.setText("AED " + movies.getPrice());
        //  https://organicfoodsandcafe.com/wp-content/uploads/2019/06/NR-BUTCHERY.png

        Picasso.get().load("" + movies.getIMAGES()).fit().into(moviesViewHolder.img_background);


        System.out.println();

        //   moviesViewHolder. quantityText.setText("x "+ movies.getmQuantity());


        calculateMealTotal();

        try {
            adapterCallback.onMethodCallback(calculateMealTotal());
        } catch (ClassCastException e) {
            // do something
        }


        if (categoryListdb.size() > 0) {
            NavigationDrawerActivity.txt_count.setText("" + categoryListdb.size());
        } else {

            NavigationDrawerActivity.txt_count.setText("0");
        }


        System.out.println("DPPPPPPPPPPPPP" + categoryListdb.size());


/*

        for(int i=0; i<categoryListdb.size();i++){




            if(productDbModelDao.queryBuilder()
                    .where(ProductDbModelDao.Properties.ID.eq(categoryListdb.get(i).getID()))
                    .list().size() != 0){

                // moviesViewHolder. quantityText.setText("x "+ movies.getmQuantity());
                if(movies.getID().trim().equalsIgnoreCase(categoryListdb.get(i).getID())){
                    moviesViewHolder. quantityText.setText("x "+ categoryListdb.get(i).getMQuantity());

                }
            }else{
                moviesViewHolder. quantityText.setText("x "+ movies.getmQuantity());
            }



            System.out.println("QUANTITYYYYYYYYYYYYYY"+categoryListdb.get(i).getMQuantity());
        }
*/


        for (int i = 0; i < categoryListdb.size(); i++) {


            if (productDao.queryBuilder()
                    .where(ProductDao.Properties.ID.eq(categoryListdb.get(i).getID()))
                    .list().size() != 0) {

                // moviesViewHolder. quantityText.setText("x "+ movies.getmQuantity());
                if (movies.getID().trim().equalsIgnoreCase(categoryListdb.get(i).getID())) {

                    moviesViewHolder.linear_hideval.setVisibility(View.VISIBLE);

                    moviesViewHolder.quantityText.setVisibility(View.VISIBLE);

                    moviesViewHolder.quantityText.setText("" + categoryListdb.get(i).getMQuantity() + " * ");


                    movies.setId(categoryListdb.get(i).getId());


                }
            } else {
                moviesViewHolder.quantityText.setText("" + movies.getmQuantity() + " * ");
                movies.setId(null);
                moviesViewHolder.linear_hideval.setVisibility(View.GONE);

                moviesViewHolder.quantityText.setVisibility(View.GONE);
            }


            System.out.println("QUANTITYYYYYYYYYYYYYY" + categoryListdb.get(i).getMQuantity());
        }
        //OnClick listeners for all the buttons on the ListView Item
        moviesViewHolder.plus_meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (productDao.queryBuilder()
                        .where(ProductDao.Properties.ID.eq(movies.getID()))
                        .list().size() == 0) {
                    movies.addToQuantity();
                    moviesViewHolder.quantityText.setText("" + movies.getmQuantity() + " * ");
                } else {
                    for (int i = 0; i < categoryListdb.size(); i++) {


                        if (productDao.queryBuilder()
                                .where(ProductDao.Properties.ID.eq(categoryListdb.get(i).getID()))
                                .list().size() != 0) {

                            // moviesViewHolder. quantityText.setText("x "+ movies.getmQuantity());
                            if (movies.getID().trim().equalsIgnoreCase(categoryListdb.get(i).getID())) {

                                categoryListdb.get(i).addToQuantity();
                                movies.setMQuantity(categoryListdb.get(i).getMQuantity());

                                moviesViewHolder.quantityText.setText("" + categoryListdb.get(i).getMQuantity() + " * ");


                            }
                        }
                    }
                }

                //-------------------------------------------------------------------------------------------------------------


                if (productDao.queryBuilder()
                        .where(ProductDao.Properties.ID.eq(movies.getID()))
                        .list().size() == 0) {

                    productDbModel = new Product();

                    productDbModel.setID(movies.getID());
                    productDbModel.setTitle(movies.getTitle());

                    productDbModel.setCONTENT(movies.getCONTENT());
                    productDbModel.setPrice(movies.getPrice());
                    productDbModel.setMQuantity(movies.getmQuantity());
                    productDbModel.setIMAGES(movies.getIMAGES());
                    //  moviesViewHolder.btn_increment.setVisibility(View.VISIBLE);

                    productDao.insert(productDbModel);

                    List<Product> categoryId = productDao.loadAll();

                    for (int i = 0; i < categoryId.size(); i++) {

                        movies.setId(categoryId.get(i).getId());
                    }

                    if (categoryListdb.size() > 0) {
                        NavigationDrawerActivity.txt_count.setText("" + categoryId.size());
                    } else {

                        NavigationDrawerActivity.txt_count.setText("0");
                    }


                } else {

                    System.out.println("ALREADYYYYYYYYY");


                    Product productDbModel111 = new Product();

                    //  for(int i=0; i<categoryListdb.size();i++){


                    productDbModel111.setId(movies.getId());

                    productDbModel111.setID(movies.getID());
                    productDbModel111.setTitle(movies.getTitle());
                    productDbModel111.setIMAGES(movies.getIMAGES());
                    productDbModel111.setCONTENT(movies.getCONTENT());
                    productDbModel111.setPrice(movies.getPrice());
                    productDbModel111.setMQuantity(movies.getMQuantity());


                    System.out.println("111111111111111111" + movies.getMQuantity());
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

        moviesViewHolder.minus_meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* movies.removeFromQuantity();
                moviesViewHolder.quantityText.setText("x "+movies.getmQuantity());
              //  currentCost.setText("GH"+"\u20B5"+" "+ (currentFood.getmAmount() * currentFood.getmQuantity()));
                notifyDataSetChanged();*/


                if (productDao.queryBuilder()
                        .where(ProductDao.Properties.ID.eq(movies.getID()))
                        .list().size() == 0) {
                    movies.removeFromQuantity();
                    moviesViewHolder.quantityText.setText("" + movies.getmQuantity() + " * ");
                } else {
                    for (int i = 0; i < categoryListdb.size(); i++) {


                        if (productDao.queryBuilder()
                                .where(ProductDao.Properties.ID.eq(categoryListdb.get(i).getID()))
                                .list().size() != 0) {

                            // moviesViewHolder. quantityText.setText("x "+ movies.getmQuantity());
                            if (movies.getID().trim().equalsIgnoreCase(categoryListdb.get(i).getID())) {

                                categoryListdb.get(i).removeFromQuantity();
                                movies.setMQuantity(categoryListdb.get(i).getMQuantity());

                                moviesViewHolder.quantityText.setText("" + categoryListdb.get(i).getMQuantity() + " * ");


                            }
                        }
                    }
                }
//--------------------------------------------------------------------------------------------------------------------------

                if (productDao.queryBuilder()
                        .where(ProductDao.Properties.ID.eq(movies.getID()))
                        .list().size() == 0) {

                    productDbModel = new Product();

                    productDbModel.setID(movies.getID());
                    productDbModel.setTitle(movies.getTitle());
                    productDbModel.setIMAGES(movies.getIMAGES());
                    productDbModel.setCONTENT(movies.getCONTENT());
                    productDbModel.setPrice(movies.getPrice());
                    productDbModel.setMQuantity(movies.getmQuantity());
                    //  moviesViewHolder.btn_increment.setVisibility(View.VISIBLE);

                    productDao.insert(productDbModel);

                    List<Product> categoryId = productDao.loadAll();

                    for (int i = 0; i < categoryId.size(); i++) {

                        movies.setId(categoryId.get(i).getId());
                    }

                    if (categoryListdb.size() > 0) {
                        NavigationDrawerActivity.txt_count.setText("" + categoryId.size());
                    } else {

                        NavigationDrawerActivity.txt_count.setText("0");
                    }

                } else {

                    System.out.println("ALREADYYYYYYYYY");


                    Product productDbModel111 = new Product();

                    //  for(int i=0; i<categoryListdb.size();i++){


                    productDbModel111.setId(movies.getId());

                    productDbModel111.setID(movies.getID());
                    productDbModel111.setTitle(movies.getTitle());
                    productDbModel111.setIMAGES(movies.getIMAGES());
                    productDbModel111.setCONTENT(movies.getCONTENT());
                    productDbModel111.setPrice(movies.getPrice());
                    productDbModel111.setMQuantity(movies.getMQuantity());


                    System.out.println("111111111111111111" + movies.getMQuantity());
                    // }


                    productDao.update(productDbModel111);

                }
                notifyDataSetChanged();
            }
        });

        moviesViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (productDao.queryBuilder()
                        .where(ProductDao.Properties.ID.eq(movies.getID()))
                        .list().size() == 0) {
                    movies.addToQuantity();
                    moviesViewHolder.quantityText.setText("" + movies.getmQuantity() + " * ");
                    movies.setId(movies.getId());
                } else {
                    for (int i = 0; i < categoryListdb.size(); i++) {


                        if (productDao.queryBuilder()
                                .where(ProductDao.Properties.ID.eq(categoryListdb.get(i).getID()))
                                .list().size() != 0) {

                            // moviesViewHolder. quantityText.setText("x "+ movies.getmQuantity());
                            if (movies.getID().trim().equalsIgnoreCase(categoryListdb.get(i).getID())) {

                                categoryListdb.get(i).addToQuantity();
                                movies.setMQuantity(categoryListdb.get(i).getMQuantity());
                                movies.setId(categoryListdb.get(i).getId());

                                moviesViewHolder.quantityText.setText("" + categoryListdb.get(i).getMQuantity() + " * ");


                            }
                        }
                    }
                }
                ///   --------------------------------------------------------------------------------------------------------


                if (productDao.queryBuilder()
                        .where(ProductDao.Properties.ID.eq(movies.getID()))
                        .list().size() == 0) {

                    productDbModel = new Product();

                    productDbModel.setID(movies.getID());
                    productDbModel.setTitle(movies.getTitle());
                    productDbModel.setIMAGES(movies.getIMAGES());
                    productDbModel.setCONTENT(movies.getCONTENT());
                    productDbModel.setPrice(movies.getPrice());
                    productDbModel.setMQuantity(movies.getmQuantity());
                    //  moviesViewHolder.btn_increment.setVisibility(View.VISIBLE);

                    productDao.insert(productDbModel);
                    List<Product> categoryId = productDao.loadAll();

                    for (int i = 0; i < categoryId.size(); i++) {

                        movies.setId(categoryId.get(i).getId());
                    }

                    if (categoryListdb.size() > 0) {
                        NavigationDrawerActivity.txt_count.setText("" + categoryId.size());
                    } else {

                        NavigationDrawerActivity.txt_count.setText("0");
                    }

                } else {

                    System.out.println("ALREADYYYYYYYYY");


                    System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvv" + movies.getId());
                    Product productDbModel111 = new Product();

                    //  for(int i=0; i<categoryListdb.size();i++){


                    productDbModel111.setId(movies.getId());

                    productDbModel111.setID(movies.getID());
                    productDbModel111.setTitle(movies.getTitle());
                    productDbModel111.setIMAGES(movies.getIMAGES());
                    productDbModel111.setCONTENT(movies.getCONTENT());
                    productDbModel111.setPrice(movies.getPrice());
                    productDbModel111.setMQuantity(movies.getMQuantity());


                    System.out.println("111111111111111111" + movies.getMQuantity());
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


                System.out.println("childddddddddddddd" + movies.getTitle());

                notifyDataSetChanged();

            }
        });
    }

    public double calculateMealTotal() {
        double mealTotal = 0;
        for (Product order : categoryListdb) {
            mealTotal += Double.parseDouble(order.getPrice()) * order.getmQuantity();
        }


        System.out.println("ggggggggggggggggggggg" + mealTotal);

        return mealTotal;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filtercategoryListdb = categorys;
                } else {
                    List<MovieCategory> filteredList = new ArrayList<>();
                    for (MovieCategory row : categorys) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    filtercategoryListdb = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filtercategoryListdb;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filtercategoryListdb = (ArrayList<MovieCategory>) filterResults.values;

                collapseAllParents();
                count=-1;
                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

    public interface AdapterCallback {
        void onMethodCallback(double d);
    }
/*
    public void filterData(String query) {

        query = query.toLowerCase();
        Log.v("MyListAdapter", String.valueOf(continentList.size()));
        continentList.clear();

        if (query.isEmpty()) {
            continentList.addAll(originalList);
        } else {

            for (Continent continent : originalList) {

                ArrayList<Country> countryList = continent.getCountryList();
                ArrayList<Country> newList = new ArrayList<Country>();
                for (Country ountry : countryList) {
                    if (country.getCode().toLowerCase().contains(query) ||
                            country.getName().toLowerCase().contains(query)) {
                        newList.add(country);
                    }
                }
                if (newList.size() > 0) {
                    Continent nContinent = new Continent(continent.getName(), newList);
                    continentList.add(nContinent);
                }
            }
        }

        Log.v("MyListAdapter", String.valueOf(continentList.size()));
        notifyDataSetChanged();

    }
*/




}