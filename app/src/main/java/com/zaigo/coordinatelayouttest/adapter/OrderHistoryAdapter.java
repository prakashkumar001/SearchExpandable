package com.zaigo.coordinatelayouttest.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zaigo.coordinatelayouttest.OrderDeatilsActivity;
import com.zaigo.coordinatelayouttest.R;
import com.zaigo.coordinatelayouttest.model.OrderHistoryResult;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MyViewHolder> {

    private List<OrderHistoryResult> moviesList;

    Activity activity;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_orderid, txt_date, txt_trash,txt_total;

        public MyViewHolder(View view) {
            super(view);
            txt_orderid = (TextView) view.findViewById(R.id.txt_orderid);
            txt_date = (TextView) view.findViewById(R.id.txt_date);
            txt_trash = (TextView) view.findViewById(R.id.txt_trash);
            txt_total = (TextView) view.findViewById(R.id.txt_total);
        }
    }


    public OrderHistoryAdapter(Activity activity,List<OrderHistoryResult> moviesList) {

        this.activity=activity;
        this.moviesList = moviesList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orderhistoryitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        OrderHistoryResult movie = moviesList.get(position);
        holder.txt_orderid.setText(movie.getOrderNumber());
        holder.txt_date.setText(movie.getDateStatus().get(0).getDate());
        holder.txt_trash.setText(movie.getDateStatus().get(0).getStatus());
        holder.txt_total.setText(movie.getTotal());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(activity, OrderDeatilsActivity.class);

                i.putExtra("id",moviesList.get(position).getOrderNumber());
                i.putExtra("status",moviesList.get(position).getDateStatus().get(0).getStatus());

                activity.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}