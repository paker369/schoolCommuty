package com.dev.user.selector;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dev.common.base.BaseActivity;
import com.dev.common.constant.ARouterConstant;
import com.dev.common.utils.ActivityRouter;
import com.dev.user.databinding.ActivityLoginSelectBinding;

@Route(path = ARouterConstant.Users.selectorActivity)
public class LoginSelectActivity extends BaseActivity<ViewModel> {

    private ActivityLoginSelectBinding binding;

    @Override
    protected void initViewModel() {
    }

    @Override
    protected View initBinding() {
        binding = ActivityLoginSelectBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {
        setupToolbar(binding.toolbar);
        binding.adminButton.setOnClickListener(v -> {
            ActivityRouter.gotoLoginActivity(true);
            finish();
        });

        binding.normalButton.setOnClickListener(v -> {
            ActivityRouter.gotoLoginActivity(false);
            finish();
        });
    }

    @Override
    protected void initObserver() {

    }
}