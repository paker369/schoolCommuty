package com.dev.main.ui.dynamic;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.dev.base.activity.BaseDetailActivity;
import com.dev.common.constant.ARouterConstant;
import com.dev.common.database.comment.Comment;

@Route(path = ARouterConstant.Main.dynamicDetailActivity)
public class DynamicDetailActivity extends BaseDetailActivity<DynamicViewModel> {

    @Override
    protected void initViewModel() {
        super.initViewModel();
        vm.commentType = commentType();
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        binding.tvComment.setOnClickListener(v -> {
            DynamicCommentBottomSheetFragment f = DynamicCommentBottomSheetFragment.newInstance(ownerId,vm.dynamic.getValue().title,vm.dynamic.getValue().content);
            f.onDismiss = () -> vm.commentCount(ownerId);
            f.show(getSupportFragmentManager(), "CommentBottomSheetFragment");
        });
//        binding.ivFavorite.setVisibility(View.GONE);
    }

    @Override
    protected void initObserver() {
        super.initObserver();
        vm.dynamic.observe(this, dynamic -> {
            if (dynamic == null) return;
            setTitle(dynamic.title);
            binding.content.setText(dynamic.content);
            Glide.with(DynamicDetailActivity.this).load(dynamic.attachment).into(binding.image);
        });
    }

    @Override
    protected int commentType() {
        return Comment.DynamicType;
    }
}