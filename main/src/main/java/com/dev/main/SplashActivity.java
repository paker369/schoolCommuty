package com.dev.main;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.dev.common.base.BaseActivity;
import com.dev.common.constant.ARouterConstant;
import com.dev.common.utils.ActivityRouter;
import com.dev.main.databinding.ActivitySplashBinding;

@Route(path = ARouterConstant.Main.SplashActivity)
public class SplashActivity extends BaseActivity<SplashViewModel> {

    private ActivitySplashBinding binding;
    private CountDownTimer timer;

    @Override
    protected View initBinding() {
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {
        if (!isTaskRoot() && getIntent() != null) {
            String action = getIntent().getAction();
            if (getIntent().hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                finish();
                return;
            }
        }
        timer = new CountDownTimer(3850, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
//                tvCount.setText(millisUntilFinished / 1000 + " 跳過");
            }

            @Override
            public void onFinish() {
                ActivityRouter.gotoMainActivity();
                finish();
            }
        }.start();
        setupToolbar(binding.toolbar);
//        setupToolbar(binding.toolbar);
//        Fragment fragment = FragmentRouter.dynamicFragment(UserSession.getInstance().id());
//        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, fragment).commitAllowingStateLoss();
        String url = "file:///android_asset/animation.gif";
        Glide.with(this)
                .asGif()
                .load(url)
                .into(binding.image);
    }

    @Override
    protected void initObserver() {

    }
}