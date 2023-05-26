package com.example.testjava.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testjava.adapter.InteractionListener;
import com.example.testjava.adapter.PostAdapter;
import com.example.testjava.databinding.ActivityFavouriteBinding;
import com.example.testjava.models.Post;

public class FavouriteActivity extends AppCompatActivity implements InteractionListener {
    ActivityFavouriteBinding binding;
    PostViewModel postViewModel;
    PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavouriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpRecycler();
        postViewModel = new PostViewModel(getApplication());
        postViewModel.getFavouritePosts().observe(this, postAdapter::setPosts);
    }

    private void setUpRecycler() {
        postAdapter = new PostAdapter(this,true);
        binding.favouriteRecyclerView.setAdapter(postAdapter);
        binding.favouriteRecyclerView.setHasFixedSize(true);
        binding.favouriteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onClickItem(Post post) {
        postViewModel.deleteFromFavourite(post);
        Toast.makeText(this,"Deleted from favourite successfully",Toast.LENGTH_SHORT).show();
        post.setFavorite(!post.isFavorite());
        postAdapter.notifyDataSetChanged();
    }
}