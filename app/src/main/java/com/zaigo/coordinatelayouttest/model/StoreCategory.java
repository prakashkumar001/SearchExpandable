package com.zaigo.coordinatelayouttest.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.zaigo.coordinatelayouttest.expand.Movies;
import com.zaigo.coordinatelayouttest.expand.ParentListItem;

import java.util.List;

public class StoreCategory implements ParentListItem {

    @SerializedName("main_id")
    @Expose
    private String mainId;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("cat_desc")
    @Expose
    private String catDesc;
    private List<Movies> mMovies;

    public StoreCategory(String name, List<Movies> movies) {
        name = name;
        mMovies = movies;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatDesc() {
        return catDesc;
    }

    public void setCatDesc(String catDesc) {
        this.catDesc = catDesc;
    }

    @Override
    public List<?> getChildItemList() {
        return mMovies;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
