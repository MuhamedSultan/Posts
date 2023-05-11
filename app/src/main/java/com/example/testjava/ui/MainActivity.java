package com.example.testjava.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testjava.R;
import com.example.testjava.adapter.InteractionListener;
import com.example.testjava.adapter.PostAdapter;
import com.example.testjava.db.PostsDao;
import com.example.testjava.db.PostsDatabase;
import com.example.testjava.models.Post;
import com.example.testjava.repository.PostsRepository;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements InteractionListener {

    private PostAdapter postAdapter;
    private List<Post> posts = new ArrayList<>();
    PostViewModel postViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PostsDao postsDao = PostsDatabase.getInstance(this).postsDao();
        PostsRepository postsRepository = new PostsRepository(postsDao);
        postViewModel = new PostViewModel(postsDao);
        postViewModel.setPostsRepository(postsRepository);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        postAdapter = new PostAdapter(this);
        recyclerView.setAdapter(postAdapter);

        postViewModel.fetchPosts();
        postViewModel.posts.observe(this, posts -> postAdapter.setPosts(posts));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.favourite_item){
            Intent intent = new Intent(this,FavouriteActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickItem(Post post) {
        postViewModel.addPostToFavourite(post);
    }
}