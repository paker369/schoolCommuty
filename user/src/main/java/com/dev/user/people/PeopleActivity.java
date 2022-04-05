package com.dev.user.people;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.dev.common.base.BaseActivity;
import com.dev.common.constant.ARouterConstant;
import com.dev.common.utils.FragmentRouter;
import com.dev.user.R;
import com.dev.user.databinding.ActivityPeopleBinding;

/**
 * @author long.guo
 * @since 2/7/21
 */

@Route(path = ARouterConstant.Users.peopleActivity)
public class PeopleActivity extends BaseActivity<PeopleViewModel> {
    private ActivityPeopleBinding binding;
    @Autowired(name = "userId")
    long userId;

    @Override
    protected View initBinding() {
        binding = ActivityPeopleBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {
        setupToolbar(binding.toolbar);
        binding.dynamic.setOnClickListener(v -> {
            // 他的动态
            Fragment f = FragmentRouter.dynamicFragment(userId);
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, f).addToBackStack(null).commitAllowingStateLoss();
        });

        binding.follow.setOnClickListener(v -> {
            if (vm.follow.getValue() != null) {
                vm.cancelFollow(userId);
            } else {
                vm.follow(userId);
            }
        });
    }

    @Override
    protected void initData() {
        vm.getUser(userId);
    }

    @Override
    protected void initObserver() {
        vm.user.observe(this, user -> {
            binding.gender.setText(user.gender == 0 ? toResString(R.string.male) : toResString(R.string.female));
            binding.userName.setText(user.nickName);
            int holder = user.gender == 0 ? R.drawable.ic_user_head_body : R.drawable.ic_user_head_girl;
            Glide.with(this).load(user.headImage).error(holder).into(binding.userHeadImage);
        });

        vm.follow.observe(this, follow -> {
            if (follow != null) {
                binding.follow.setText(toResString(R.string.cancel_follow));
            } else {
                binding.follow.setText(toResString(R.string.follow));
            }
        });
    }
}
