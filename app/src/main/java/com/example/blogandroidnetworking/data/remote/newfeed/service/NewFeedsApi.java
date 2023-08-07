package com.example.blogandroidnetworking.data.remote.newfeed.service;

import com.android.volley.Response;
import com.example.blogandroidnetworking.model.dto.CommentDto;
import com.example.blogandroidnetworking.model.dto.PostDto;

import java.util.List;

public interface NewFeedsApi {
    void getNewPosts(Response.Listener<List<PostDto>> listener, Response.ErrorListener errorListener);
    void getPostById(String id, Response.Listener<PostDto> listener, Response.ErrorListener errorListener);
    void getCommendInPost(String id, Response.Listener<List<CommentDto>> listener, Response.ErrorListener errorListener);

    void createComment(String pid, String comment, String uid, Response.Listener<String> listener, Response.ErrorListener errorListener);

    void deleteComment(String id, Response.Listener<String> listener, Response.ErrorListener errorListener);

    void updateComment(String id, String comment, Response.Listener<String> listener, Response.ErrorListener errorListener);
}
