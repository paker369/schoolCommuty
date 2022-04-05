package com.dev.news.activity;

import android.annotation.SuppressLint;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.dev.base.activity.BaseDetailActivity;
import com.dev.common.constant.ARouterConstant;
import com.dev.common.database.comment.Comment;
import com.dev.news.fragment.NewsCommentBottomSheetFragment;
import com.dev.news.viewmodel.NewsViewModel;

@Route(path = ARouterConstant.News.newsDetailActivity)
public class NewsDetailActivity extends BaseDetailActivity<NewsViewModel> {

    @Override
    protected void setupViews() {
        super.setupViews();
        binding.tvComment.setOnClickListener(v -> {
            NewsCommentBottomSheetFragment f = NewsCommentBottomSheetFragment.newInstance(ownerId, vm.news.getValue().title, vm.news.getValue().content);
            f.onDismiss = () -> {
                vm.commentCount(ownerId);
            };
            f.show(getSupportFragmentManager(), "CommentBottomSheetFragment");
        });
        binding.ivFavorite.setVisibility(View.GONE);
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void initObserver() {
        super.initObserver();
        vm.news.observe(this, news -> {
            if (news == null) return;
            setTitle(news.title);
            binding.content.setText(news.content);
            Glide.with(NewsDetailActivity.this).load(news.image).into(binding.image);
        });
    }

    @Override
    protected int commentType() {
        return Comment.DynamicType;
    }
}