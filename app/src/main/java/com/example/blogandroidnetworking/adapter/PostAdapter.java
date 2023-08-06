package com.example.blogandroidnetworking.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.blogandroidnetworking.R;
import com.example.blogandroidnetworking.model.dto.PostDto;
import com.example.blogandroidnetworking.model.dto.UserDto;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder>{
    private static final String TAG = "PostAdapter";
    private final List<PostDto> posts;
    private final UserDto userLogged;

    public PostAdapter(List<PostDto> posts) {
        this.posts = posts;
        userLogged = new UserDto("", "", "", "");
    }
    public void setUserLogged(UserDto userLogged) {
        this.userLogged.setId(userLogged.getId());
        this.userLogged.setName(userLogged.getName());
        this.userLogged.setAvatar(userLogged.getAvatar());
    }
    public void setPosts(List<PostDto> newPosts) {
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
        PostDto post = posts.get(position);

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

        holder.countLike.setText(String.valueOf(post.getLikedBy().length));
        final boolean[] isLiked = {false};
        for (String likedBy : post.getLikedBy()) {
            if (likedBy.equals(userLogged.getId())) {
                isLiked[0] = true;
                break;
            }
        }
        if (isLiked[0]) {
            holder.btnLike.setColorFilter(1);
        } else {
            holder.btnLike.setColorFilter(null);
        }
        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLiked[0]) {
                    holder.btnLike.setColorFilter(null);
                    holder.countLike.setText(String.valueOf(post.getLikedBy().length));
                    isLiked[0] = false;
                } else {
                    holder.btnLike.setColorFilter(1);
                    holder.countLike.setText(String.valueOf(post.getLikedBy().length));
                    isLiked[0] = true;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        ImageButton btnLike, btnComment, btnShare;
        ImageView authorAvatar;
        TextView countLike, countComment, countShare;
        TextView authorName;
        TextView publishAt;
        TextView title;
        TextView content;
        ImageView postImage;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            btnLike = itemView.findViewById(R.id.btn_like);
            btnComment = itemView.findViewById(R.id.btn_comment);
            btnShare = itemView.findViewById(R.id.btn_share);
            authorAvatar = itemView.findViewById(R.id.iv_author_avatar);
            authorName = itemView.findViewById(R.id.tv_author_name);
            publishAt = itemView.findViewById(R.id.tv_published_at);
            title = itemView.findViewById(R.id.tv_post_title);
            content = itemView.findViewById(R.id.tv_post_content);
            postImage = itemView.findViewById(R.id.iv_post_image);
            countLike = itemView.findViewById(R.id.tv_like_count);
            countComment = itemView.findViewById(R.id.tv_comment_count);
            countShare = itemView.findViewById(R.id.tv_share_count);
        }
    }
}
