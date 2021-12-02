package com.example.tatvasoftpractical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tatvasoftpractical.R;
import com.example.tatvasoftpractical.decoration.GridSpacingItemDecoration;
import com.example.tatvasoftpractical.pojo.User;

import java.util.ArrayList;

public class UserDetailsAdapter extends RecyclerView.Adapter<UserDetailsAdapter.ViewHolder> {
    public Context context;
    ArrayList<User> userArrayList;

    // RecyclerView recyclerView;
    public UserDetailsAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.rvuser_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = userArrayList.get(position);
        Glide.with(context).load(user.getImage()).into(holder.ivUserProfile);
        holder.tvUsername.setText(user.getName().toString());
        holder.rvUserItemList.setLayoutManager(new GridLayoutManager(context, 2));
        int spanCount = 2; // 3 columns
        int spacing = 50; // 50px
        boolean includeEdge = true;
        holder.rvUserItemList.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        holder.rvUserItemList.setAdapter(new UserItemListAdapter(context, user.getItems()));
    }


    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername;
        ImageView ivUserProfile;
        RecyclerView rvUserItemList;

        public ViewHolder(View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivUserProfile = itemView.findViewById(R.id.ivUserProfile);
            rvUserItemList = itemView.findViewById(R.id.rvUserItemList);
        }
    }
}  