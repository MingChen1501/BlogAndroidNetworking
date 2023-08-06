package com.example.blogandroidnetworking.data.remote.newfeed.service;

import com.android.volley.Response;
import com.example.blogandroidnetworking.model.dto.PostDto;

import java.util.List;

public interface NewFeedsApi {
    void getNewPosts(Response.Listener<List<PostDto>> listener, Response.ErrorListener errorListener);
}
