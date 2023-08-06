package com.example.blogandroidnetworking.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.blogandroidnetworking.data.remote.newfeed.FeedRepository;
import com.example.blogandroidnetworking.model.dto.PostDto;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private final String TAG = "HomeViewModel";
    private FeedRepository feedRepository;

    private String errorMessage = "";

    private final MutableLiveData<List<PostDto>> postsLiveData;

    public HomeViewModel() {
        postsLiveData = new MutableLiveData<>();
    }
    public LiveData<List<PostDto>> getPostsLiveData() {
        return postsLiveData;
    }
    public void fetchPosts() {
        feedRepository.getPosts(new Response.Listener<List<PostDto>>() {
            @Override
            public void onResponse(List<PostDto> response) {
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