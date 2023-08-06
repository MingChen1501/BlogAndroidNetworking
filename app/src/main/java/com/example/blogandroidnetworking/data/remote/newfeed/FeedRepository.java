package com.example.blogandroidnetworking.data.remote.newfeed;

import com.android.volley.Response;
import com.example.blogandroidnetworking.data.remote.newfeed.service.NewFeedsApi;
import com.example.blogandroidnetworking.model.dto.PostDto;

import java.util.List;

public class FeedRepository {
    private final NewFeedsApi newFeedsApi;

    public FeedRepository(NewFeedsApi newFeedsApi) {
        this.newFeedsApi = newFeedsApi;
    }
    public void getPosts(Response.Listener<List<PostDto>> listener, Response.ErrorListener errorListener) {
        newFeedsApi.getNewPosts(listener, errorListener);
    }
}
