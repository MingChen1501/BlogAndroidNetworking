package com.example.blogandroidnetworking.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.blogandroidnetworking.R;
import com.example.blogandroidnetworking.model.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder>{
    private static final String TAG = "PostAdapter";
    private final List<Post> posts;

    public PostAdapter(List<Post> posts) {
        this.posts = posts;
    }
    public void setPosts(List<Post> newPosts) {
        posts.clear();
        posts.addAll(newPosts);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.post_item, parent, false);
        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);

        holder.authorName.setText(post.getAuthorName());
        holder.publishAt.setText(post.getPublishedAt());
        holder.title.setText(post.getTitle());
        holder.content.setText(post.getContent());
        Glide.with(holder.itemView.getContext())
                .load("https://picsum.photos/200/200")
                .into(holder.authorAvatar);

        if (post.getImageUrl().length != 0) {
            Glide.with(holder.itemView.getContext())
                    .load(post.getImageUrl()[0])
                    .into(holder.postImage);
        } else {
            holder.postImage.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView authorAvatar;
        TextView authorName;
        TextView publishAt;
        TextView title;
        TextView content;
        ImageView postImage;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            authorAvatar = itemView.findViewById(R.id.iv_author_avatar);
            authorName = itemView.findViewById(R.id.tv_author_name);
            publishAt = itemView.findViewById(R.id.tv_published_at);
            title = itemView.findViewById(R.id.tv_post_title);
            content = itemView.findViewById(R.id.tv_post_content);
            postImage = itemView.findViewById(R.id.iv_post_image);
        }
    }
}
