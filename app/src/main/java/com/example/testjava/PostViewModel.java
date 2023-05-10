package com.example.testjava;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostViewModel extends ViewModel {
     MutableLiveData<List<Post>> posts = new MutableLiveData<>();

    public void  fetchPosts() {
        Call<List<Post>> call;
        call = RetrofitClient.getInstance().getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                posts.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e("PostViewModel", "Failed to fetch posts: " + t.getMessage());
            }
        });
    }
}

