package com.example.testjava.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.testjava.models.Post;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface PostsDao {
    @Insert
    Completable addPostToFavourite(Post post);


   @Query("SELECT * FROM posts_table")
   LiveData<List<Post>> getAllFavouritePosts() ;
}
