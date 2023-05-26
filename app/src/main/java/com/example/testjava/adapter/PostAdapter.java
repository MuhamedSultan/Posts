package com.example.testjava.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testjava.R;
import com.example.testjava.models.Post;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private List<Post> posts = new ArrayList<>();
    private final boolean isFavoriteActivity;

    private final InteractionListener list;

    public PostAdapter(InteractionListener list, boolean isFavoriteActivity) {
        this.list = list;
        this.isFavoriteActivity=isFavoriteActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.titleTextView.setText(post.getTitle());
        holder.bodyTextView.setText(post.getBody());
        boolean isFavorite = post.isFavorite();

        holder.favouriteButton.setOnClickListener(view -> {
            list.onClickItem(post);
            post.setFavorite(!post.isFavorite());
            notifyDataSetChanged();
        });

        if (isFavoriteActivity) {
            holder.favouriteButton.setText("Delete from Favorite");
        } else {
            if (isFavorite) {
                holder.favouriteButton.setText("Delete from Favorite");
            } else {
                holder.favouriteButton.setText("Add to Favorite");
            }
        }
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView bodyTextView;
        private final Button favouriteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            bodyTextView = itemView.findViewById(R.id.bodyTextView);
            favouriteButton = itemView.findViewById(R.id.button);
        }
    }
}
