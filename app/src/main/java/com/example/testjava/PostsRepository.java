package com.example.testjava;

import java.util.List;

import retrofit2.Call;

public class PostsRepository {
    public Call<List<Post>> get(){
        return RetrofitClient.getInstance().getPosts();
    }
}
