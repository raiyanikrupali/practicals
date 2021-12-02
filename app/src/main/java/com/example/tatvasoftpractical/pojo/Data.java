package com.example.tatvasoftpractical.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable {

    @SerializedName("users")
    @Expose
    private ArrayList<User> users = null;
    @SerializedName("has_more")
    @Expose
    private Boolean hasMore;
    private final static long serialVersionUID = -4879791100137971366L;

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

}