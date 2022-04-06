package com.dev.main;


import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.dev.common.adapter.TabAdapter2;
import com.dev.common.base.BaseActivity;
import com.dev.common.constant.ARouterConstant;
import com.dev.common.utils.ActivityRouter;
import com.dev.main.databinding.ActivityShowBinding;
import com.dev.main.databinding.ActivitySplashBinding;
import com.dev.question.fragment.CommentListFragment;
import com.dev.question.fragment.QuestionListFragment;
import com.dev.question.fragment.ReferListFragment;
import com.dev.user.UserSession;

@Route(path = ARouterConstant.Main.ShowActivity)
public class ShowActivity extends BaseActivity<ShowViewModel> {

    private ActivityShowBinding binding;
    private int type;

    @Override
    protected View initBinding() {
        binding = ActivityShowBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {
        type = getIntent().getIntExtra("type", 0);

        String[] titles = new String[]{"我的提问"};
        Fragment[] fragments = new Fragment[1];
        switch (type) {
            case 1:
                setupToolbar(binding.toolbar,"我的提问");
                fragments[0] = QuestionListFragment.newInstance(UserSession.getInstance().id());
                break;
            case 2:
                setupToolbar(binding.toolbar,"我的回答");
                fragments[0] =CommentListFragment.newInstance(1);
                break;
            case 3:
                setupToolbar(binding.toolbar,"与我相关");
                fragments[0] = ReferListFragment.newInstance(2);
                break;

        }
        binding.viewPager.setAdapter(new TabAdapter2(titles, fragments, getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
    }

    @Override
    protected void initObserver() {

    }
}
