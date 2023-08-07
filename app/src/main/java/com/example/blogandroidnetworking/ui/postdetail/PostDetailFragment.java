package com.example.blogandroidnetworking.ui.postdetail;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.blogandroidnetworking.R;
import com.example.blogandroidnetworking.adapter.CommentAdapter;
import com.example.blogandroidnetworking.adapter.OnButtonClickListener;
import com.example.blogandroidnetworking.data.remote.newfeed.service.NewFeedApiImpl;
import com.example.blogandroidnetworking.data.remote.newfeed.service.NewFeedsApi;
import com.example.blogandroidnetworking.databinding.FragmentPostDetailBinding;
import com.example.blogandroidnetworking.model.dto.CommentDto;
import com.example.blogandroidnetworking.ui.viewmodel.UserLoggedViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PostDetailFragment extends Fragment implements OnButtonClickListener {
    private BottomNavigationView bottomNavigationView;
    private String TAG = "PostDetailFragment";
    private FragmentPostDetailBinding binding;

    private PostDetailViewModel mViewModel;
    private UserLoggedViewModel mUserLoggedViewModel;
    private NewFeedsApi newFeedsApi;
    private RecyclerView recyclerView;
    private CommentAdapter commentAdapter;
    private ImageView ivAvatarAuthor;
    private TextView tvAuthorName;
    private TextView tvPostContent;
    private TextView tvPostTime;
    private TextView tvPostTitle;
    private ImageView ivPostImage;
    private TextView tvLikeCount;
    private TextView tvCommentCount;
    private TextView tvShareCount;
    private ImageButton btnLike, btnComment, btnShare;
    private EditText etComment;
    private AppCompatImageButton btnSendComment;

    public static PostDetailFragment newInstance() {
        return new PostDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(PostDetailViewModel.class);
        mUserLoggedViewModel = new ViewModelProvider(requireActivity()).get(UserLoggedViewModel.class);
        binding = FragmentPostDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        bottomNavigationView = requireActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.GONE);
        newFeedsApi = new NewFeedApiImpl(requireContext());
        recyclerView = binding.rvComments;
        List<CommentDto> comments = new ArrayList<>();
        commentAdapter = new CommentAdapter(comments, Objects.requireNonNull(mUserLoggedViewModel.getData().getValue()).getId());
        commentAdapter.setOnButtonClickListener(this);
        recyclerView.setAdapter(commentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        mViewModel.getCommentList().observe(getViewLifecycleOwner(), s -> {
            Log.d(TAG, "onCreateView: " + s.size());
            commentAdapter.setComments(s);
        });
        getPostDetail();
        getComment();

        return root;
    }

    private void getPostDetail() {
        ivAvatarAuthor = binding.ivAuthorAvatar;
        tvAuthorName = binding.tvAuthorName;
        tvPostContent = binding.tvPostContent;
        tvPostTime = binding.tvPublishedAt;
        tvPostTitle = binding.tvPostTitle;
        ivPostImage = binding.ivPostImage;
        btnLike = binding.btnLike;
        btnComment = binding.btnComment;
        btnShare = binding.btnShare;
        tvLikeCount = binding.tvLikeCount;
        tvCommentCount = binding.tvCommentCount;
        tvShareCount = binding.tvShareCount;
        etComment = binding.etComment;
        btnSendComment = binding.btnSend;
        newFeedsApi.getPostById(mViewModel.getPostSelectedId().getValue(), res -> {
            if (res != null) {
                tvAuthorName.setText(res.getAuthorName());
                tvPostContent.setText(res.getContent());
                tvPostTime.setText(res.getPublishedAt());
                tvPostTitle.setText(res.getTitle());
                Glide.with(requireContext()).load(res.getAuthorAvatar()).into(ivAvatarAuthor);
                if (res.getImageUrl().length != 0) {
                    Glide.with(requireContext()).load(res.getImageUrl()[0]).into(ivPostImage);
                } else {
                    ivPostImage.setVisibility(View.GONE);
                }
                tvLikeCount.setText(res.getLikedBy().length + "");
            }
        },e -> {
            Toast.makeText(requireContext(), "Lỗi", Toast.LENGTH_SHORT).show();
        });
        btnSendComment.setOnClickListener(v -> {
            String comment = etComment.getText().toString();
            if (comment.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng nhập bình luận", Toast.LENGTH_SHORT).show();
            } else {
                newFeedsApi.createComment(mViewModel.getPostSelectedId().getValue(), comment, mUserLoggedViewModel.getData().getValue().getId(), res -> {
                    if (res != null) {
                        Toast.makeText(requireContext(), "Bình luận thành công", Toast.LENGTH_SHORT).show();
                        etComment.setText("");
                        getComment();
                    }
                }, e -> {
                    Toast.makeText(requireContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    public void getComment() {
        Log.d(TAG, "getComment:  start");
        newFeedsApi.getCommendInPost(mViewModel.getPostSelectedId().getValue()
            , res -> {
                if (res != null) {
                    Log.d(TAG, "getComment: " + res.size());
                    mViewModel.setCommentList(res);
                }
            }, e -> {
                    Log.d(TAG, "getComment: " + e.getMessage());
                Toast.makeText(requireContext(), "Lỗi", Toast.LENGTH_SHORT).show();
            }
        );
        Log.d(TAG, "getComment: end");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onButtonClick(String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("lựa chọn thao tác");
        builder.setPositiveButton("Xóa", (dialog, which) -> {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(requireContext());
            builder1.setTitle("Xóa bình luận");
            builder1.setMessage("Bạn có chắc chắn muốn xóa bình luận này?");
            builder1.setPositiveButton("Xóa", (dialog1, which1) -> {
                newFeedsApi.deleteComment(id, res -> {
                    if (res != null) {
                        Toast.makeText(requireContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                        getComment();
                    }
                }, e -> {
                    Toast.makeText(requireContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                });
            }).setNegativeButton("Hủy", (dialog1, which1) -> {
                dialog1.dismiss();
            }).show();
        }).setNegativeButton("Sửa", (dialog, which) -> {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(requireContext());
            builder1.setTitle("Sửa bình luận");
            builder1.setMessage("Bạn có chắc chắn muốn sửa bình luận này?");
            EditText editText = new EditText(requireContext());
            editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            builder1.setView(editText);
            builder1.setPositiveButton("Sửa", (dialog1, which1) -> {
                String comment = editText.getText().toString();
                if (comment.isEmpty()) {
                    Toast.makeText(requireContext(), "Vui lòng nhập bình luận", Toast.LENGTH_SHORT).show();
                } else {
                    newFeedsApi.updateComment(id, comment, res -> {
                        if (res != null) {
                            Toast.makeText(requireContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                            getComment();
                        }
                    }, e -> {
                        Toast.makeText(requireContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                    });
                }
            }).setNegativeButton("Hủy", (dialog1, which1) -> {
                dialog1.dismiss();
            }).show();
        }).setNeutralButton("Hủy", (dialog, which) -> {
            dialog.dismiss();
        }).show();
    }
}