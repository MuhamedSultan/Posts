package com.example.testjava.ui;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testjava.db.PostsDao;
import com.example.testjava.models.Post;
import com.example.testjava.repository.PostsRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostViewModel extends ViewModel {
    MutableLiveData<List<Post>> posts = new MutableLiveData<>();
    private PostsRepository postsRepository;
    public void setPostsRepository(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }
    public PostViewModel(PostsDao postsDao) {
        postsRepository = new PostsRepository(postsDao);
    }


    public void fetchPosts() {
        postsRepository.get().enqueue(new Callback<List<Post>>() {
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

    @SuppressLint("CheckResult")
    public void addPostToFavourite(Post post) {
        postsRepository.addPostToFavourite(post)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    // Handle completion
                }, throwable -> {
                    // Handle error
                });
    }
    public LiveData<List<Post>> getFavouritePosts(){
      return postsRepository.getFavouritePosts();
    }
}

