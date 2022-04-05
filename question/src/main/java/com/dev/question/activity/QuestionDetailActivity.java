package com.dev.question.activity;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dev.base.activity.BaseDetailActivity;
import com.dev.common.constant.ARouterConstant;
import com.dev.common.database.comment.Comment;
import com.dev.question.fragment.QuestionCommentBottomSheetFragment;
import com.dev.question.viewmodel.QuestionViewModel;

@Route(path = ARouterConstant.Question.questionListActivity)
public class QuestionDetailActivity extends BaseDetailActivity<QuestionViewModel> {

    @Override
    protected void initViewModel() {
        super.initViewModel();
        vm.commentType = commentType();
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        binding.tvComment.setOnClickListener(v -> {
            QuestionCommentBottomSheetFragment f = QuestionCommentBottomSheetFragment.newInstance(ownerId, vm.question.getValue().title, vm.question.getValue().content);
            f.onDismiss = () -> {
                vm.commentCount(ownerId);
            };
            f.show(getSupportFragmentManager(), "CommentBottomSheetFragment");
        });
//        binding.ivFavorite.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        vm.loadQuestionById(ownerId);
    }

    @Override
    protected int commentType() {
        return Comment.QuestionType;
    }

    @Override
    protected void initObserver() {
        super.initObserver();
        vm.question.observe(this, q -> {
            if (q == null) return;
            setTitle(q.title);
            binding.content.setText(q.content);
        });
    }
}