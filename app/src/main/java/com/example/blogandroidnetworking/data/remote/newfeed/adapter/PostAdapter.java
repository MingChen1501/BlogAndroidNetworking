package com.example.blogandroidnetworking.data.remote.newfeed.adapter;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blogandroidnetworking.R;
import com.example.blogandroidnetworking.model.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder>{
    private static final String TAG = "PostAdapter";
    private final List<Post> posts;

    public PostAdapter(List<Post> posts) {
        Log.d(TAG, "PostAdapter: ");
        this.posts = posts;
    }
    public void setPosts(List<Post> newPosts) {
        Log.d(TAG, "setPosts: ");
        posts.clear();
        posts.addAll(newPosts);
    }
    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: create view holder");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.post_item, parent, false);
        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: bind view holder");
        Post post = posts.get(position);
        holder.authorName.setText("author name");
        holder.publishAt.setText("publish at");
        holder.title.setText(post.getTitle());
        holder.content.setText(post.getContent());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView authorName;
        TextView publishAt;
        TextView title;
        TextView content;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "PostViewHolder: ");
            authorName = itemView.findViewById(R.id.tv_author_name);
            publishAt = itemView.findViewById(R.id.tv_published_at);
            title = itemView.findViewById(R.id.tv_post_title);
            content = itemView.findViewById(R.id.tv_post_content);
        }
    }
}
