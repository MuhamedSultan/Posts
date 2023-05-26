package com.example.testjava.repository;

import androidx.lifecycle.LiveData;

import com.example.testjava.api.RetrofitClient;
import com.example.testjava.db.PostsDao;
import com.example.testjava.models.Post;

import java.util.List;

import io.reactivex.Completable;
import retrofit2.Call;

public class PostsRepository {
    private final PostsDao postsDao;

    public PostsRepository(PostsDao postsDao) {
        this.postsDao = postsDao;
    }

    public Call<List<Post>> get() {
        return RetrofitClient.getInstance().getPosts();
    }

    public Completable addPostToFavourite(Post post) {
        return postsDao.addPostToFavourite(post);
    }

    public LiveData<List<Post>> getFavouritePosts() {
        return postsDao.getAllFavouritePosts();
    }


    public  Completable deleteFromFavourite(Post post){
       return postsDao.deleteFromFavourite(post);
    }
}
