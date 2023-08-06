package com.example.blogandroidnetworking.data.remote.newfeed.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blogandroidnetworking.model.dto.PostDto;
import com.example.blogandroidnetworking.utils.ApiUrl;
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
    public void getNewPosts(Response.Listener<List<PostDto>> listener, Response.ErrorListener errorListener) {
        String url = ApiUrl.BASE_URL + "posts?_expand=user&_sort=published_at&_order=desc&_embed=liked";
        StringRequest stringRequest = new StringRequest(url, response -> {
            List<PostDto> posts = parseJsonToPost(response);
            listener.onResponse(posts);
        }, errorListener);
        requestQueue.add(stringRequest);
    }


    private List<PostDto> parseJsonToPost(String response) {
        List<PostDto> posts = new ArrayList<>();
        JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);
        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();
            PostDto post = new PostDto();
            post.setId(jsonObject.get("id").getAsLong());
            post.setTitle(jsonObject.get("title").getAsString());
            post.setContent(jsonObject.get("content").getAsString());
            post.setPublishedAt(jsonObject.get("published_at").getAsString());
            JsonArray likedByArray = jsonObject.get("liked").getAsJsonArray();
            String[] likedBy = new String[likedByArray.size()];
            int j = 0;
            for (JsonElement element1 : likedByArray) {
                likedBy[j++] = element.getAsJsonObject().get("userId").getAsString();
            }
            post.setLikedBy(likedBy);

            JsonArray imagesArray = jsonObject.get("images").getAsJsonArray();
            String[] images = new String[imagesArray.size()];
            int i = 0;
            for (JsonElement element1 : imagesArray) {
                images[i++] = (element1.getAsString());
            }
            post.setImagesUrl(images);

            JsonObject jsonObject1 = jsonObject.get("user").getAsJsonObject();
            post.setAuthorId(jsonObject1.get("id").getAsLong());
            post.setAuthorName(jsonObject1.get("username").getAsString());
            post.setAuthorAvatar(jsonObject1.get("avatar").getAsString());

            posts.add(post);
            Log.d(TAG, "parseJsonToPost: " + post.toString());
        }
        return posts;
    }
}
