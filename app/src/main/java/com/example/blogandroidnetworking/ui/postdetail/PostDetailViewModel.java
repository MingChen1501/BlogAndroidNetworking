package com.example.blogandroidnetworking.ui.postdetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.blogandroidnetworking.model.dto.CommentDto;
import com.example.blogandroidnetworking.model.dto.PostDto;

import java.util.List;

public class PostDetailViewModel extends ViewModel {
    private MutableLiveData<String> postSelectedId;
    private MutableLiveData<PostDto> postDetail;
    private MutableLiveData<List<CommentDto>> commentList;

    public PostDetailViewModel() {
        postDetail = new MutableLiveData<>();
        postSelectedId = new MutableLiveData<>();
        commentList = new MutableLiveData<>();
    }
    public void setPostSelectedId(String id) {
        postSelectedId.setValue(id);
    }
    public LiveData<String> getPostSelectedId() {
        return postSelectedId;
    }
    public void setPostDetail(PostDto postDto) {
        postDetail.setValue(postDto);
    }
    public LiveData<PostDto> getPostDetail() {
        return postDetail;
    }
    public void setCommentList(List<CommentDto> commentDtoList) {
        commentList.setValue(commentDtoList);
    }
    public LiveData<List<CommentDto>> getCommentList() {
        return commentList;
    }
}