package com.zaigo.coordinatelayouttest.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import com.zaigo.coordinatelayouttest.App;
import com.zaigo.coordinatelayouttest.NavigationDrawerActivity;
import com.zaigo.coordinatelayouttest.R;
import com.zaigo.coordinatelayouttest.model.DaoSession;
import com.zaigo.coordinatelayouttest.model.Product;
import com.zaigo.coordinatelayouttest.model.ProductDao;

import java.util.List;

/**
 * Created by Ayo on 17/04/2017.
 */

public class OrderAdapter extends ArrayAdapter<Product> {
    private List<Product> list;
    private Activity context;

   // ImageView img_products;
    TextView currentFoodName,
            currentCost,
            quantityText,
            addMeal,
            subtractMeal,
            removeMeal;
    ImageView img_products;

    ProductDao productDao;
    DaoSession daoSession;
    Product productDbModel;


    public OrderAdapter(Activity context, List<Product> myOrders) {
        super(context, 0, myOrders);
        this.list = myOrders;
        this.context = context;
    }


    public View getView(final int position, View convertView, ViewGroup parent){
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_my_meal,parent,false
            );
        }
        daoSession = ((App)context.getApplication()).getDaoSession();
        productDao = daoSession.getProductDao();
        final Product currentFood = getItem(position);

        currentFoodName = (TextView)listItemView.findViewById(R.id.selected_food_name);
        currentCost = (TextView)listItemView.findViewById(R.id.selected_food_amount);
        subtractMeal = (TextView)listItemView.findViewById(R.id.minus_meal);
        quantityText = (TextView)listItemView.findViewById(R.id.quantity);
        addMeal = (TextView)listItemView.findViewById(R.id.plus_meal);
        removeMeal = (TextView)listItemView.findViewById(R.id.delete_item);
        img_products = (ImageView) listItemView.findViewById(R.id.img);


        //Set the text of the meal, amount and quantity
        currentFoodName.setText(currentFood.getTitle());
        currentCost.setText("AED"+ (currentFood.getPrice() ));
        quantityText.setText(""+ currentFood.getmQuantity()+" * ");

        System.out.println("iiii"+currentFood.getIMAGES());

        Picasso.get()
                .load(currentFood.getIMAGES())

                .error(R.mipmap.ic_launcher)
                .into(img_products);

        //OnClick listeners for all the buttons on the ListView Item
        addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFood.addToQuantity();
                quantityText.setText(""+ currentFood.getmQuantity()+" * ");
              //  currentCost.setText("GH"+"\u20B5"+" "+ (currentFood.getmAmount() * currentFood.getmQuantity()));



                if(productDao.queryBuilder()
                        .where(ProductDao.Properties.ID.eq(currentFood.getID()))
                        .list().size() == 0){

                  /*  productDbModel = new Product();

                    productDbModel.setID(currentFood.getID());
                    productDbModel.setTitle(currentFood.getTitle());
                    productDbModel.setIMAGES(currentFood.getIMAGES());
                    productDbModel.setCONTENT(currentFood.getCONTENT());
                    productDbModel.setPrice(currentFood.getPrice());
                    productDbModel.setMQuantity(currentFood.getmQuantity());
                    //  moviesViewHolder.btn_increment.setVisibility(View.VISIBLE);

                    productDao.insert(productDbModel);
                    List<Product>  categoryId = productDao.loadAll();

                    for(int i =0;i<categoryId.size();i++){

                        currentFood.setId(categoryId.get(i).getId());
                    }
*/


                }else{

                    System.out.println("ALREADYYYYYYYYY");








                    System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvv"+currentFood.getId());
                    Product productDbModel111 = new Product();

                    //  for(int i=0; i<categoryListdb.size();i++){


                    productDbModel111.setId(currentFood.getId());

                    productDbModel111.setID(currentFood.getID());
                    productDbModel111.setTitle(currentFood.getTitle());
                    productDbModel111.setIMAGES(currentFood.getIMAGES());
                    productDbModel111.setCONTENT(currentFood.getCONTENT());
                    productDbModel111.setPrice(currentFood.getPrice());
                    productDbModel111.setMQuantity(currentFood.getMQuantity());



                    System.out.println("111111111111111111"+currentFood.getMQuantity());
                    // }



                    productDao.update(productDbModel111);



                }



                notifyDataSetChanged();
            }
        });

        subtractMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFood.removeFromQuantity();
                quantityText.setText(""+currentFood.getmQuantity()+" * ");
              //  currentCost.setText("GH"+"\u20B5"+" "+ (currentFood.getmAmount() * currentFood.getmQuantity()));


                if(productDao.queryBuilder()
                        .where(ProductDao.Properties.ID.eq(currentFood.getID()))
                        .list().size() == 0){

                  /*  productDbModel = new Product();

                    productDbModel.setID(currentFood.getID());
                    productDbModel.setTitle(currentFood.getTitle());
                    productDbModel.setIMAGES(currentFood.getIMAGES());
                    productDbModel.setCONTENT(currentFood.getCONTENT());
                    productDbModel.setPrice(currentFood.getPrice());
                    productDbModel.setMQuantity(currentFood.getmQuantity());
                    //  moviesViewHolder.btn_increment.setVisibility(View.VISIBLE);

                    productDao.insert(productDbModel);
                    List<Product>  categoryId = productDao.loadAll();

                    for(int i =0;i<categoryId.size();i++){

                        currentFood.setId(categoryId.get(i).getId());
                    }
*/


                }else{

                    System.out.println("ALREADYYYYYYYYY");








                    System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvv"+currentFood.getId());
                    Product productDbModel111 = new Product();

                    //  for(int i=0; i<categoryListdb.size();i++){


                    productDbModel111.setId(currentFood.getId());

                    productDbModel111.setID(currentFood.getID());
                    productDbModel111.setTitle(currentFood.getTitle());
                    productDbModel111.setIMAGES(currentFood.getIMAGES());
                    productDbModel111.setCONTENT(currentFood.getCONTENT());
                    productDbModel111.setPrice(currentFood.getPrice());
                    productDbModel111.setMQuantity(currentFood.getMQuantity());



                    System.out.println("111111111111111111"+currentFood.getMQuantity());
                    // }



                    productDao.update(productDbModel111);



                }

                notifyDataSetChanged();
            }
        });

        removeMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(position);
              //  Note note = notesAdapter.getNote(position);
                Long noteId = currentFood.getId();

                productDao.deleteByKey(noteId);
                notifyDataSetChanged();
            }
        });

        return listItemView;
    }

}