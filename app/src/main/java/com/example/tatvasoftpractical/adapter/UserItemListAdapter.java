package com.example.tatvasoftpractical.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tatvasoftpractical.R;
import com.example.tatvasoftpractical.pojo.User;

import java.util.ArrayList;

public class UserItemListAdapter extends RecyclerView.Adapter<UserItemListAdapter.ViewHolder> {
    public Context context;
    ArrayList<String> userItemArrayList;

    // RecyclerView recyclerView;
    public UserItemListAdapter(Context context, ArrayList<String> userItemArrayList) {
        this.context = context;
        this.userItemArrayList = userItemArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.rvuseritem_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(userItemArrayList.get(position)).into(holder.ivUserItemImage);
    }


    @Override
    public int getItemCount() {
        return userItemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivUserItemImage;


        public ViewHolder(View itemView) {
            super(itemView);
            ivUserItemImage = itemView.findViewById(R.id.ivUserItemImage);
        }
    }
}  