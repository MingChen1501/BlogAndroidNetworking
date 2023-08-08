package com.example.blogandroidnetworking.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.blogandroidnetworking.R;
import com.example.blogandroidnetworking.model.dto.CommentDto;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private OnButtonClickListener onButtonClickListener;

    private static final String TAG = "CommentAdapter";
    private final List<CommentDto> comments;
    private final String pid;

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    public CommentAdapter(List<CommentDto> comments, String pid) {
        this.comments = comments;
        this.pid = pid;
    }
    public void setComments(List<CommentDto> newComments) {
        comments.clear();
        comments.addAll(newComments);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        CommentDto comment = comments.get(position);
        if (comment.getUserId().equals(pid)) {
            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(androidx.cardview.R.color.cardview_light_background));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onButtonClickListener != null) {
                        onButtonClickListener.onButtonClick(String.valueOf(comment.getId()));
                    }
                }
            });
        }
        holder.authorName.setText(comment.getUsername());
        holder.commentContent.setText(comment.getContent());
        holder.publishAt.setText(comment.getPublishedAt());
        Glide.with(holder.itemView.getContext())
                .load(comment.getAvatar_url())
                .into(holder.authorAvatar);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        ImageView authorAvatar;
        TextView authorName;
        TextView publishAt;
        TextView commentContent;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            authorAvatar = itemView.findViewById(R.id.iv_author_avatar);
            authorName = itemView.findViewById(R.id.tv_author_name);
            publishAt = itemView.findViewById(R.id.tv_published_at);
            commentContent = itemView.findViewById(R.id.tv_comment);
        }
    }
}
