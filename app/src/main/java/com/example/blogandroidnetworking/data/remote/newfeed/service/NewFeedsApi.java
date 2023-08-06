package com.example.blogandroidnetworking.data.remote.newfeed.service;

import com.android.volley.Response;
import com.example.blogandroidnetworking.model.Author;
import com.example.blogandroidnetworking.model.Post;

import java.util.List;

public interface NewFeedsApi {
    void getNewPosts(Response.Listener<List<Post>> listener, Response.ErrorListener errorListener);
    void getAuthorOfPost(long authorId, Response.Listener<Author> listener, Response.ErrorListener errorListener);
}
