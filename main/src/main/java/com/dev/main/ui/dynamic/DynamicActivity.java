package com.dev.main.ui.dynamic;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dev.common.base.BaseActivity;
import com.dev.common.constant.ARouterConstant;
import com.dev.common.utils.FragmentRouter;
import com.dev.main.R;
import com.dev.main.databinding.ActivityDynamicBinding;
import com.dev.user.UserSession;

@Route(path = ARouterConstant.Main.dynamicActivity)
public class DynamicActivity extends BaseActivity<DynamicViewModel> {

    private ActivityDynamicBinding binding;

    @Override
    protected View initBinding() {
        binding = ActivityDynamicBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {
        setupToolbar(binding.toolbar);
        Fragment fragment = FragmentRouter.dynamicFragment(UserSession.getInstance().id());
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, fragment).commitAllowingStateLoss();
    }

    @Override
    protected void initObserver() {

    }
}