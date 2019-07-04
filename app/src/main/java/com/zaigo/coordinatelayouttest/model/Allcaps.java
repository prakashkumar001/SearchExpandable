package com.zaigo.coordinatelayouttest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Allcaps {

    @SerializedName("read")
    @Expose
    private Boolean read;
    @SerializedName("edit_posts")
    @Expose
    private Boolean editPosts;
    @SerializedName("delete_posts")
    @Expose
    private Boolean deletePosts;
    @SerializedName("nr_customer")
    @Expose
    private Boolean nrCustomer;

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Boolean getEditPosts() {
        return editPosts;
    }

    public void setEditPosts(Boolean editPosts) {
        this.editPosts = editPosts;
    }

    public Boolean getDeletePosts() {
        return deletePosts;
    }

    public void setDeletePosts(Boolean deletePosts) {
        this.deletePosts = deletePosts;
    }

    public Boolean getNrCustomer() {
        return nrCustomer;
    }

    public void setNrCustomer(Boolean nrCustomer) {
        this.nrCustomer = nrCustomer;
    }

}