package com.example.blogandroidnetworking.data.remote.newfeed.service;

import com.android.volley.Response;
import com.example.blogandroidnetworking.model.Post;

import java.util.List;

public interface NewFeedsApi {
    void getNewFeeds(Response.Listener<List<Post>> listener, Response.ErrorListener errorListener);
}
