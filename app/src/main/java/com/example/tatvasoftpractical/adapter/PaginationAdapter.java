package com.example.tatvasoftpractical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tatvasoftpractical.R;
import com.example.tatvasoftpractical.pojo.User;

import java.util.ArrayList;

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    ArrayList<User> userArrayList;
    private static final int LOADING = 0;
    private static final int ITEM = 1;
    private boolean isLoadingAdded = false;

    public PaginationAdapter(Context context) {
        this.context = context;
        userArrayList = new ArrayList<>();
    }

    public void setMovieList(ArrayList<User> userArrayList) {
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.rvuser_item, parent, false);
                viewHolder = new UserViewHolder(viewItem);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingViewHolder(viewLoading);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        switch (getItemViewType(position)) {
            case ITEM:
                UserViewHolder userViewHolder = (UserViewHolder) holder;
                User user = userArrayList.get(position);
                Glide.with(context).load(user.getImage()).into(userViewHolder.ivUserProfile);
                userViewHolder.tvUsername.setText(user.getName().toString());
                GridLayoutManager gridLayoutManager =new GridLayoutManager(context, 2);
                userViewHolder.rvUserItemList.setLayoutManager(new GridLayoutManager(context, 2));
                userViewHolder.rvUserItemList.setAdapter(new UserItemListAdapter(context, user.getItems()));
                break;

            case LOADING:
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return userArrayList == null ? 0 : userArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == userArrayList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new User());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = userArrayList.size() - 1;
        User result = getItem(position);

        if (result != null) {
            userArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void add(User movie) {
        userArrayList.add(movie);
        notifyItemInserted(userArrayList.size() - 1);
    }

    public void addAll(ArrayList<User> moveResults) {
        for (User result : moveResults) {
            add(result);
        }
    }

    public User getItem(int position) {
        return userArrayList.get(position);
    }


    public class UserViewHolder extends RecyclerView.ViewHolder {

        TextView tvUsername;
        ImageView ivUserProfile;
        RecyclerView rvUserItemList;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivUserProfile = itemView.findViewById(R.id.ivUserProfile);
            rvUserItemList = itemView.findViewById(R.id.rvUserItemList);
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.pbLoadMore);

        }
    }

}