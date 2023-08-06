package com.example.blogandroidnetworking.ui.postdetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PostDetailViewModel extends ViewModel {
    private MutableLiveData<String> postSelectedId;

    public PostDetailViewModel() {
        postSelectedId = new MutableLiveData<>();
    }
    public void setPostSelectedId(String id) {
        postSelectedId.setValue(id);
    }
    public LiveData<String> getPostSelectedId() {
        return postSelectedId;
    }
}