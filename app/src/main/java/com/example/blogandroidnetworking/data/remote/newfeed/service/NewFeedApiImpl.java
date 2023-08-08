package com.example.blogandroidnetworking.data.remote.newfeed.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blogandroidnetworking.model.dto.CommentDto;
import com.example.blogandroidnetworking.model.dto.PostDto;
import com.example.blogandroidnetworking.utils.ApiUrl;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.Timestamp;
import java.time.Instant;
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
            List<PostDto> posts = parseJsonToPosts(response);
            listener.onResponse(posts);
        }, errorListener);
        requestQueue.add(stringRequest);
    }

    @Override
    public void getPostById(String id, Response.Listener<PostDto> listener, Response.ErrorListener errorListener) {
        String url = ApiUrl.BASE_URL + "posts/" + id + "?_expand=user&_embed=liked";
        StringRequest stringRequest = new StringRequest(url, response -> {
            PostDto post = parseJsonToPost(response);
            listener.onResponse(post);
        }, errorListener);
        requestQueue.add(stringRequest);
    }

    @Override
    public void getCommendInPost(String id, Response.Listener<List<CommentDto>> listener, Response.ErrorListener errorListener) {
        String url = ApiUrl.BASE_URL + "posts/" + id + "/comments?_expand=user&_sort=published_at&_order=desc";
        StringRequest stringRequest = new StringRequest(url, response -> {
            List<CommentDto> comments = parseJsonToComment(response);
            listener.onResponse(comments);
        }, errorListener);
        requestQueue.add(stringRequest);
    }

    @Override
    public void createComment(String pid, String comment, String uid, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        String url = ApiUrl.BASE_URL + "comments";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            listener.onResponse("OK");
        }, e -> {
            Log.e(TAG, "createComment: ", e);
            errorListener.onErrorResponse(e);
        }) {
            @Override
            public byte[] getBody() {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("userId", uid);
                jsonObject.addProperty("postId", pid);
                jsonObject.addProperty("content", comment);
                jsonObject.addProperty("published_at", String.valueOf(Instant.ofEpochMilli(System.currentTimeMillis())));
                return jsonObject.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void deleteComment(String id, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        String url = ApiUrl.BASE_URL + "comments/" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, response -> {
            listener.onResponse("OK");
        }, errorListener);
        requestQueue.add(stringRequest);
    }

    @Override
    public void updateComment(String id, String comment, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        String url = ApiUrl.BASE_URL + "comments/" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, url, response -> {
            listener.onResponse("OK");
        }, errorListener) {
            @Override
            public byte[] getBody() {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("content", comment);
                return jsonObject.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        requestQueue.add(stringRequest);
    }

    private List<CommentDto> parseJsonToComment(String response) {
        List<CommentDto> comments = new ArrayList<>();
        JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);
        for (JsonElement element : jsonArray) {
            JsonObject objectComment = element.getAsJsonObject();
            CommentDto comment = new CommentDto();
            comment.setId(objectComment.get("id").getAsString());
            comment.setContent(objectComment.get("content").getAsString());
            JsonObject objectUser = objectComment.get("user").getAsJsonObject();
            comment.setPublishedAt(objectComment.has("published_at") ? objectComment.get("published_at").getAsString() : "");
            comment.setAvatar_url(objectUser.get("avatar").getAsString());
            comment.setUsername(objectUser.get("username").getAsString());
            comment.setUserId(objectUser.get("id").getAsString());
            comments.add(comment);
        }
        return comments;
    }

    private PostDto parseJsonToPost(String response) {
        JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);
        PostDto post = new PostDto();
        post.setId(jsonObject.get("id").getAsLong());
        post.setTitle(jsonObject.get("title").getAsString());
        post.setContent(jsonObject.get("content").getAsString());
        post.setPublishedAt(jsonObject.get("published_at").getAsString());
        JsonArray likedByArray = jsonObject.get("liked").getAsJsonArray();
        String[] likedBy = new String[likedByArray.size()];
        int j = 0;
        for (JsonElement element1 : likedByArray) {
            likedBy[j++] = element1.getAsJsonObject().get("userId").getAsString();
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
        return post;
    }

    private List<PostDto> parseJsonToPosts(String response) {
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
                likedBy[j++] = element1.getAsJsonObject().get("userId").getAsString();
                Log.d(TAG, "parseJsonToPost: " + element1.getAsJsonObject().get("userId").getAsString());
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
