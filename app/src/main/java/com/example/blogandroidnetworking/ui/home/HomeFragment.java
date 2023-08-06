package com.example.blogandroidnetworking.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blogandroidnetworking.data.remote.newfeed.FeedRepository;
import com.example.blogandroidnetworking.adapter.PostAdapter;
import com.example.blogandroidnetworking.data.remote.newfeed.service.NewFeedApiImpl;
import com.example.blogandroidnetworking.data.remote.newfeed.service.NewFeedsApi;
import com.example.blogandroidnetworking.databinding.FragmentHomeBinding;
import com.example.blogandroidnetworking.model.Post;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    String TAG = "HomeFragment";

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;

    private NewFeedsApi newFeedsApi;
    private FeedRepository feedRepository;
    HomeViewModel HomeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        newFeedsApi = new NewFeedApiImpl(requireContext());
        feedRepository = new FeedRepository(newFeedsApi);
        homeViewModel.setFeedRepository(feedRepository);

        recyclerView = binding.recyclerView;
        List<Post> posts = new ArrayList<>();
        postAdapter = new PostAdapter(posts);
        recyclerView.setAdapter(postAdapter);
        recyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(requireContext()));
        homeViewModel.getPostsLiveData().observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                postAdapter.setPosts(posts);
                Log.d(TAG, "onChanged: ");
            }
        });
        homeViewModel.fetchPosts();
        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}