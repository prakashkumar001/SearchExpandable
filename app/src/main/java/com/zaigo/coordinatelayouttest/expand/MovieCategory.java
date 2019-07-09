package com.zaigo.coordinatelayouttest.expand;

import com.zaigo.coordinatelayouttest.model.Product;

import java.util.List;

public class MovieCategory implements ParentListItem {
    private String mName;
    private List<Product> mMovies;

    public MovieCategory(String name, List<Product> movies) {
        mName = name;
        mMovies = movies;
    }
    public MovieCategory( ) {
    }

    public String getName() {
        return mName;
    }

    @Override
    public List<Product> getChildItemList() {
        return mMovies;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }


}