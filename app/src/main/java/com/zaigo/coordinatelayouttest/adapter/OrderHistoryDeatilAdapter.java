package com.zaigo.coordinatelayouttest.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import com.zaigo.coordinatelayouttest.OrderDeatilsActivity;
import com.zaigo.coordinatelayouttest.R;

import com.zaigo.coordinatelayouttest.model.ProductList;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryDeatilAdapter extends RecyclerView.Adapter<OrderHistoryDeatilAdapter.MyViewHolder> {

    private List<ProductList> productLists;
    List<Double> doubleList = new ArrayList<>();
    Activity activity;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView  txt_productname,txt_producttotal;

        ImageView img_product;

        public MyViewHolder(View view) {
            super(view);
            img_product = (ImageView) view.findViewById(R.id.img_product);
            txt_productname = (TextView) view.findViewById(R.id.txt_productname);
            //txt_trash = (TextView) view.findViewById(R.id.txt_trash);
            txt_producttotal = (TextView) view.findViewById(R.id.txt_producttotal);
        }
    }


    public OrderHistoryDeatilAdapter(Activity activity, List<ProductList> productLists) {

        this.activity=activity;
        this.productLists = productLists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orderhistorydeatilsitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ProductList product = productLists.get(position);
        holder.txt_productname.setText(""+product.getProductName()+" * "+product.getOrderQuantity());


        System.out.println("TTTTTTTTTTTTTTTTTT"+product.getProductTotal()+" * "+product.getOrderQuantity());

       holder.txt_producttotal.setText("AED "+product.getProductTotal());

        Picasso.get().load(product.getImg()).fit().into(holder.img_product);
       // doubleList.add(Double.parseDouble(product.getProductTotal()));
       // incassoMargherita(doubleList);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent i = new Intent(activity, OrderDeatilsActivity.class);

                i.putExtra("id",productLists.get(position).getOrderNumber());

                activity.startActivity(i);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return productLists.size();
    }


}