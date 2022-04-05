package com.dev.base.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.dev.base.databinding.ActivityBaseDeailBinding;
import com.dev.base.viewmodel.BaseDetailViewModel;
import com.dev.common.base.BaseActivity;
import com.dev.common.database.favorite.Favorite;
import com.dev.common.database.like.Like;
import com.dev.user.UserSession;

/**
 * @author long.guo
 * @since 2/6/21
 */
public abstract class BaseDetailActivity<vm extends BaseDetailViewModel> extends BaseActivity<vm> {

    protected ActivityBaseDeailBinding binding;
    @Autowired(name = "ownerId")
    public long ownerId;

    @Override
    protected View initBinding() {
        binding = ActivityBaseDeailBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {
        setupToolbar(binding.toolbar);
        vm.loadById(ownerId);

        binding.tvLike.setOnClickListener(v -> {
            if (vm.myLike.getValue() == null) {
                vm.like(new Like(UserSession.getInstance().id(), ownerId, commentType()));
            } else {
                vm.unLike(ownerId);
            }
        });

        binding.ivFavorite.setOnClickListener(v -> {
            if (vm.myFavorite.getValue() == null) {
                vm.favorite(new Favorite(UserSession.getInstance().id(), ownerId, commentType()));
            } else {
                vm.unFavorite();
            }
        });

        binding.share.setOnClickListener(v -> {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            // 比如发送文本形式的数据内容
            // 指定发送的内容
            shareIntent.putExtra(Intent.EXTRA_TEXT, binding.content.getText());
            // 指定发送内容的类型
            shareIntent.setType("text/plain");
            // 比如发送二进制文件数据流内容（比如图片、视频、音频文件等等）
            // 指定发送的内容 (EXTRA_STREAM 对于文件 Uri )
//                putExtra(Intent.EXTRA_STREAM, uriToImage)
            // 指定发送内容的类型 (MIME type)
//                type = "image/jpeg"
            startActivity(Intent.createChooser(shareIntent, binding.toolbar.getTitle()));
        });
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void initObserver() {
        vm.likeCount.observe(this, likeCount -> {
            if (likeCount == null) {
                return;
            }
            binding.tvLike.setText(String.format("%d条", likeCount));
        });

        vm.commentCount.observe(this, commentCount -> {
            if (commentCount == null) {
                return;
            }
            binding.tvComment.setText(String.format("%d条", commentCount));
        });

        vm.myLike.observe(this, myLike -> {
            binding.tvLike.setSelected(myLike != null);
        });

        vm.myFavorite.observe(this, myFavorite -> {
            binding.ivFavorite.setSelected(myFavorite != null);
        });
    }

    protected abstract int commentType();
}
