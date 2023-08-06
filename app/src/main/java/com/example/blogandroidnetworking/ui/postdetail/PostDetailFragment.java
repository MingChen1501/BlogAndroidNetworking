package com.example.blogandroidnetworking.ui.postdetail;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.blogandroidnetworking.R;
import com.example.blogandroidnetworking.databinding.FragmentNewPostBinding;

public class PostDetailFragment extends Fragment {
    private FragmentNewPostBinding binding;

    private PostDetailViewModel mViewModel;

    public static PostDetailFragment newInstance() {
        return new PostDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_detail, container, false);
    }


}