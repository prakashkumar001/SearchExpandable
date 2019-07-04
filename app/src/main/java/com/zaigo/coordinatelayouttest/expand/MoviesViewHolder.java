package com.zaigo.coordinatelayouttest.expand;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zaigo.coordinatelayouttest.R;
import com.zaigo.coordinatelayouttest.model.Product;


public class MoviesViewHolder extends ChildViewHolder {

    public TextView mMoviesTextView;

    public TextView txt_description,txt_price,quantityText;

    LinearLayout linear_hideval;

    ImageView img_background,minus_meal,plus_meal;

    public MoviesViewHolder(View itemView) {
        super(itemView);
        mMoviesTextView = (TextView) itemView.findViewById(R.id.tv_movies);

        plus_meal =(ImageView) itemView.findViewById(R.id.plus_meal);
        minus_meal =(ImageView) itemView.findViewById(R.id.minus_meal);
        txt_description =(TextView) itemView.findViewById(R.id.txt_description);
        txt_price = (TextView)itemView.findViewById(R.id.txt_price);
        quantityText = (TextView)itemView.findViewById(R.id.quantity);

        linear_hideval = (LinearLayout)itemView.findViewById(R.id.linear_hideval);

        img_background = (ImageView)itemView.findViewById(R.id.img_background);

    }

    public void bind(Product movies) {
       // mMoviesTextView.setText(movies.getTitle());
    }
}