package com.dev.common.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.launcher.ARouter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author long.guo
 * @since 1/22/21
 */
public abstract class BaseActivity<T extends ViewModel> extends AppCompatActivity {

    protected String mTag = getClass().getSimpleName();
    protected T vm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        initWindow();
        initViewModel();
        setContentView(initBinding());
        setupViews();
        setupViewPager();
        setupRecyclerView();
        initObserver();
        initData();
    }

    protected void initViewModel() {
        ParameterizedType superType = (ParameterizedType) getClass().getGenericSuperclass();
        Type[] types = superType.getActualTypeArguments();
        Type type = types[0];
        vm = new ViewModelProvider(this).get((Class<T>) type);
    }

    protected abstract View initBinding();

    protected abstract void setupViews();

    protected void setupRecyclerView() {

    }

    protected void setupViewPager() {

    }

    protected abstract void initObserver();

    protected void initData() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    protected void initWindow() {
        int flag;
        if (Build.VERSION.SDK_INT >= 23) {
            flag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        } else {
            flag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        }
        getWindow().getDecorView().setSystemUiVisibility(flag);
        initStatusBar();
    }

    protected void initStatusBar() {
        try {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setupToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 隐藏软键盘。
     */
    protected void hideSoftKeyboard() {
        try {
            View view = getCurrentFocus();
            if (view == null) return;
            IBinder binder = view.getWindowToken();
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(binder, InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            Log.e(mTag, e.getMessage(), e);
        }

    }

    /**
     * 显示软键盘。
     */
    protected void showSoftKeyboard(EditText editText) {
        try {
            editText.requestFocus();
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.showSoftInput(editText, 0);
        } catch (Exception e) {
            Log.e(mTag, e.getMessage(), e);
        }
    }

    protected String toResString(int resId) {
        return getString(resId);
    }

    protected int toResColor(int resId) {
        return ContextCompat.getColor(this, resId);
    }
}
