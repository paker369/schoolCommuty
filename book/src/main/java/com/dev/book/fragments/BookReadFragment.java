package com.dev.book.fragments;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.dev.book.databinding.FragmentReadingBinding;
import com.dev.common.base.BaseFragment;
import com.dev.common.entry.BookBean;
import com.dev.player.GlobalPlayer;

/**
 * @author long.guo
 * @since 1/24/21
 */
public class BookReadFragment extends BaseFragment<ViewModel> {
    private FragmentReadingBinding binding;

    private final BookBean.PageBean pageBean;
    private final GlobalPlayer globalPlayer = GlobalPlayer.getInstance();

    public BookReadFragment(BookBean.PageBean pageBean) {
        this.pageBean = pageBean;
    }

    @Override
    protected void initViewModel() {

    }

    @Override
    protected View initBinding() {
        binding = FragmentReadingBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {
        Glide.with(this).load(pageBean.getImage()).into(binding.poster);
        binding.text.setText(pageBean.getText());
        binding.playButton.setOnClickListener(v -> {
            // 播放
            if (globalPlayer.isPlaying()) {
                binding.playButton.setSelected(false);
                globalPlayer.pause();
            } else {
                globalPlayer.start(pageBean.getMp3());
                binding.playButton.setSelected(true);
            }
        });
    }

    @Override
    protected void initObserver() {

    }
}
