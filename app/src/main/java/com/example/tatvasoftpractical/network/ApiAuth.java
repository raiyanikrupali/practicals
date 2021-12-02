package com.example.tatvasoftpractical.network;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.tatvasoftpractical.activity.MainActivity;
import com.example.tatvasoftpractical.pojo.UserDetails;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiAuth {
    private static ApiAuth instance ;

    public ApiAuth() {
    }
    public static ApiAuth getAuthHandlerInstance() {
        if (instance == null) {
            instance = new ApiAuth();
        }
    return instance;
    }
    public void getFriendListAPI(Context context,int page) {
      APIInterface  apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<JsonObject> call = apiInterface.getetUserData("0" , String.valueOf(page));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> calls, Response<JsonObject> response) {
                Log.e("krupali", "onResponse");
                if (response.body() != null) {
                    JsonObject jsonObject = new Gson().fromJson(response.body(), JsonObject.class);
                    UserDetails baseResponse = new Gson().fromJson(jsonObject, UserDetails.class);
                    Log.e("callApi friendList ", new Gson().toJson(baseResponse));
                    if (baseResponse.getStatus()) {
                        if (context != null && context instanceof MainActivity) {
                           // ((MainActivity) context).getListOfUSerData(baseResponse);
                        } else {
                            Toast.makeText(context, "api not working", Toast.LENGTH_SHORT).show();
                        }

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
