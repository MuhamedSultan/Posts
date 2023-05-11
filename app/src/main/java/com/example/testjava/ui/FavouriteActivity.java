package com.example.testjava.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testjava.adapter.InteractionListener;
import com.example.testjava.adapter.PostAdapter;
import com.example.testjava.databinding.ActivityFavouriteBinding;
import com.example.testjava.db.PostsDao;
import com.example.testjava.db.PostsDatabase;
import com.example.testjava.models.Post;
import com.example.testjava.repository.PostsRepository;

public class FavouriteActivity extends AppCompatActivity implements InteractionListener {
    ActivityFavouriteBinding binding;
    PostViewModel postViewModel;
    PostAdapter postAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavouriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        PostsDao postsDao = PostsDatabase.getInstance(this).postsDao();
        PostsRepository postsRepository = new PostsRepository(postsDao);
        postViewModel= new PostViewModel(postsDao);
        postAdapter = new PostAdapter(this);
        postViewModel.getFavouritePosts().observe(this, postAdapter::setPosts);
        postViewModel.setPostsRepository(postsRepository);
        setUpRecycler();
    }
    private void setUpRecycler(){
        binding.favouriteRecyclerView.setAdapter(postAdapter);
        binding.favouriteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.favouriteRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onClickItem(Post post) {

    }
}