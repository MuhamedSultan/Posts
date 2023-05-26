package com.example.testjava.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testjava.R;
import com.example.testjava.adapter.InteractionListener;
import com.example.testjava.adapter.PostAdapter;
import com.example.testjava.databinding.ActivityMainBinding;
import com.example.testjava.models.Post;

public class MainActivity extends AppCompatActivity implements InteractionListener {
    ActivityMainBinding binding;
    PostViewModel postViewModel;
    PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpRecycler();
        postViewModel = new PostViewModel(getApplication());
        postViewModel.posts.observe(this, postAdapter::setPosts);
        postViewModel.fetchPosts();
    }

    private void setUpRecycler() {
        postAdapter = new PostAdapter(this,false);
        binding.recyclerView.setAdapter(postAdapter);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.favourite_item) {
            Intent intent = new Intent(this, FavouriteActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onClickItem(Post post) {
        postViewModel.addPostToFavourite(post);
        Toast.makeText(this,"Added to favourite successfully",Toast.LENGTH_SHORT).show();
        post.setFavorite(!post.isFavorite());
        postAdapter.notifyDataSetChanged();
    }
}