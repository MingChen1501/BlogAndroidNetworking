package com.example.blogandroidnetworking.data.remote.newfeed.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blogandroidnetworking.model.Post;
import com.example.blogandroidnetworking.utils.Server;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class NewFeedApiImpl implements NewFeedsApi {
    private static final String TAG = "NewFeedApiImpl";
    private final RequestQueue requestQueue;

    public NewFeedApiImpl(Context context) {
        this.requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    @Override
    public void getNewFeeds(Response.Listener<List<Post>> listener, Response.ErrorListener errorListener) {
        String url = Server.BASE_URL + "posts";
        StringRequest stringRequest = new StringRequest(url, response -> {
            List<Post> posts = parseJsonToPost(response);
            Log.d(TAG, "getNewFeeds: " + posts);
            listener.onResponse(posts);
        }, errorListener);
        requestQueue.add(stringRequest);
    }

    private List<Post> parseJsonToPost(String response) {
        List<Post> posts = new ArrayList<>();
        JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);
        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();
            Post post = new Gson().fromJson(jsonObject, Post.class);
            posts.add(post);
        }
        return posts;
    }
}
