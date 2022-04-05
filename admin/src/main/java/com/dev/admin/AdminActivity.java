package com.dev.admin;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dev.admin.databinding.ActivityAdminBinding;
import com.dev.admin.fragments.BookManageFragment;
import com.dev.admin.fragments.ResourceManagerFragment;
import com.dev.admin.fragments.UserManageFragment;
import com.dev.common.adapter.TabAdapter;
import com.dev.common.base.BaseActivity;
import com.dev.common.constant.ARouterConstant;

import java.util.ArrayList;
import java.util.List;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

@Route(path = ARouterConstant.Admin.adminActivity)
public class AdminActivity extends BaseActivity<AdminViewModel> {

    private ActivityAdminBinding binding;

    @Override
    protected void initViewModel() {
        vm = new ViewModelProvider(this).get(AdminViewModel.class);
    }

    @Override
    protected View initBinding() {
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {
        setupToolbar(binding.toolbar);
        List<String> titles = new ArrayList<>(3);
        titles.add("用户管理");
        titles.add("资源管理");

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(UserManageFragment.newInstance());
        fragments.add(ResourceManagerFragment.newInstance());
        binding.viewPager.setAdapter(new TabAdapter(titles, fragments, getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    @Override
    protected void initObserver() {

    }
}