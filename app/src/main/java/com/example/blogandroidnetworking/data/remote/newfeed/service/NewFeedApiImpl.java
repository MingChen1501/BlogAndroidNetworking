package com.example.blogandroidnetworking.data.remote.newfeed.service;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blogandroidnetworking.model.Author;
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
    public void getNewPosts(Response.Listener<List<Post>> listener, Response.ErrorListener errorListener) {
        String url = Server.BASE_URL + "posts?_sort=published_at&_order=desc";
        StringRequest stringRequest = new StringRequest(url, response -> {
            List<Post> posts = parseJsonToPost(response);
            listener.onResponse(posts);
        }, errorListener);
        requestQueue.add(stringRequest);
    }

    @Override
    public void getAuthorOfPost(long authorId, Response.Listener<Author> listener, Response.ErrorListener errorListener) {
        String url = Server.BASE_URL + "users/" + authorId;
        StringRequest stringRequest = new StringRequest(url, response -> {
            Author author = new Gson().fromJson(response, Author.class);
            listener.onResponse(author);
        }, errorListener);
        requestQueue.add(stringRequest);
    }

    private List<Post> parseJsonToPost(String response) {
        List<Post> posts = new ArrayList<>();
        JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);
        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();
            Post post = new Post();
            post.setId(jsonObject.get("id").getAsLong());
            post.setTitle(jsonObject.get("title").getAsString());
            post.setContent(jsonObject.get("content").getAsString());
            post.setPublishedAt(jsonObject.get("published_at").getAsString());

            JsonArray jsonElements = jsonObject.get("images").getAsJsonArray();
            String[] images = new String[jsonElements.size()];
            int i = 0;
            for (JsonElement element1 : jsonElements) {
                images[i++] = (element1.getAsString());
            }
            post.setImagesUrl(images);

            JsonObject jsonObject1 = jsonObject.get("author").getAsJsonObject();
            post.setAuthorId(jsonObject1.get("id").getAsLong());
            post.setAuthorName(jsonObject1.get("name").getAsString());
            post.setAuthorAvatar(jsonObject1.get("avatar").getAsString());

            posts.add(post);
        }
        return posts;
    }
}
