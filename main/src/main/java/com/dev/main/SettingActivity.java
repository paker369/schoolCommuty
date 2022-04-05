package com.dev.main;

import android.annotation.SuppressLint;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dev.common.base.BaseActivity;
import com.dev.common.constant.ARouterConstant;
import com.dev.common.utils.AppUtils;
import com.dev.main.databinding.ActivitySettingBinding;

@Route(path = ARouterConstant.Main.settingActivity)
public class SettingActivity extends BaseActivity<SettingViewModel> {

    private ActivitySettingBinding binding;

    @Override
    protected View initBinding() {
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void setupViews() {
        setupToolbar(binding.toolbar);
        binding.versionName.setText(String.format("版本：V%s", AppUtils.versionName()));
    }

    @Override
    protected void initObserver() {

    }
}