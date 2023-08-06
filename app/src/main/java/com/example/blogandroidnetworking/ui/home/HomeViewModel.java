package com.example.blogandroidnetworking.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.blogandroidnetworking.data.remote.newfeed.FeedRepository;
import com.example.blogandroidnetworking.model.Post;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private final String TAG = "HomeViewModel";
    private FeedRepository feedRepository;

    private String errorMessage = "";

    private final MutableLiveData<List<Post>> postsLiveData;

    public HomeViewModel() {
        postsLiveData = new MutableLiveData<>();
    }
    public LiveData<List<Post>> getPostsLiveData() {
        return postsLiveData;
    }
    public void fetchPosts() {
        feedRepository.getPosts(new Response.Listener<List<Post>>() {
            @Override
            public void onResponse(List<Post> response) {
                postsLiveData.setValue(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMessage = error.getMessage();
            }
        });
    }

    public void setFeedRepository(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }
}