package com.dev.main;

import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dev.common.base.BaseActivity;
import com.dev.common.constant.ARouterConstant;
import com.dev.common.utils.ActivityRouter;
import com.dev.main.databinding.ActivityMainBinding;
import com.dev.user.UserSession;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import static com.dev.common.database.DaoProvider.userDao;

@Route(path = ARouterConstant.Main.mainActivity)
public class MainActivity extends BaseActivity<MainViewModel> {

    private ActivityMainBinding binding;

    @Override
    protected void initViewModel() {

    }

    @Override
    protected View initBinding() {
//        UserSession.login(userDao().findAdmin());
        if (!UserSession.isLogin()) {
            ActivityRouter.gotoLoginSelectActivity(this,100);
            overridePendingTransition(0, 0);
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {
        binding.navView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_notifications, R.id.navigation_mine)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    protected void initObserver() {

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(!UserSession.isLogin()){
            finish();
        }
    }
}