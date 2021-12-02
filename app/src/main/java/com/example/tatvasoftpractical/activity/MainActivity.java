package com.example.tatvasoftpractical.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tatvasoftpractical.R;
import com.example.tatvasoftpractical.abstractcls.PaginationScrollListener;
import com.example.tatvasoftpractical.adapter.PaginationAdapter;
import com.example.tatvasoftpractical.adapter.UserDetailsAdapter;
import com.example.tatvasoftpractical.network.APIClient;
import com.example.tatvasoftpractical.network.APIInterface;
import com.example.tatvasoftpractical.network.ApiAuth;
import com.example.tatvasoftpractical.pojo.User;
import com.example.tatvasoftpractical.pojo.UserDetails;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvUserDetails;
    ProgressBar progressBar;
    private boolean isLoading, isLastPage;
    PaginationAdapter paginationAdapter;
    int currentPage = 0;
    APIInterface apiInterface;
    int pagelimit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        rvUserDetails = findViewById(R.id.rvUserDetails);
        progressBar = findViewById(R.id.progressBar);
        // rvUserDetails.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        //   ApiAuth.getAuthHandlerInstance().getFriendListAPI(MainActivity.this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        paginationAdapter = new PaginationAdapter(this);
        rvUserDetails.setLayoutManager(linearLayoutManager);
        rvUserDetails.setAdapter(paginationAdapter);

        rvUserDetails.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                loadNextPage();
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        loadFirstPage();

    }


   /* public void getListOfUSerData(UserDetails userdatumArrayList) {

        progressBar.setVisibility(View.GONE);
        rvUserDetails.setAdapter(new UserDetailsAdapter(MainActivity.this, (ArrayList<User>) userdatumArrayList.getData().getUsers()));
    }
*/

    public void loadFirstPage() {
        Call<JsonObject> call = apiInterface.getetUserData("0", "10");

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> calls, Response<JsonObject> response) {
                Log.e("krupali", "onResponse");
                if (response.body() != null) {
                    progressBar.setVisibility(View.GONE);
                    JsonObject jsonObject = new Gson().fromJson(response.body(), JsonObject.class);
                    UserDetails baseResponse = new Gson().fromJson(jsonObject, UserDetails.class);
                    Log.e("callApi friendList ", new Gson().toJson(baseResponse));
                    if (baseResponse.getStatus()) {
                        paginationAdapter.addAll(baseResponse.getData().getUsers());

                        if (baseResponse.getData().getHasMore()) {
                            paginationAdapter.addLoadingFooter();
                        } else {
                            isLastPage = true;
                        }


                        //  getListOfUSerData(baseResponse);
                    } else {
                        Log.e("krupali", "null response");
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("krupali", "onFailure");
            }
        });

    }

    public void loadNextPage() {
        pagelimit = pagelimit +10;
        Log.e("krupali", "currentpage " + pagelimit);
        Call<JsonObject> call = apiInterface.getetUserData(String.valueOf(pagelimit), "10");

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> calls, Response<JsonObject> response) {
                Log.e("krupali", "onResponse");
                if (response.body() != null) {
                    progressBar.setVisibility(View.GONE);
                    JsonObject jsonObject = new Gson().fromJson(response.body(), JsonObject.class);
                    UserDetails baseResponse = new Gson().fromJson(jsonObject, UserDetails.class);
                    Log.e("callApi friendList ", new Gson().toJson(baseResponse));
                    if (baseResponse.getStatus()) {
                        paginationAdapter.removeLoadingFooter();
                        isLoading = false;
                        paginationAdapter.addAll((baseResponse.getData().getUsers()));

                        if (baseResponse.getData().getHasMore()) {
                            paginationAdapter.addLoadingFooter();
                        } else {
                            isLastPage = true;
                        }


                        //  getListOfUSerData(baseResponse);
                    } else {
                        Log.e("krupali", "null response");
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("krupali", "onFailure");
            }
        });


    }

}