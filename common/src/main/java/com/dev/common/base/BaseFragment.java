package com.dev.common.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.launcher.ARouter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author long.guo
 * @since 1/23/21
 */
public abstract class BaseFragment<T extends ViewModel> extends Fragment {
    protected String mTag = getClass().getSimpleName();
    protected T vm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
        ARouter.getInstance().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initBinding();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupViews();
        setupViewPager();
        setupRecyclerView();
        initData();
        initObserver();
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

    protected String toResString(int resId) {
        return getString(resId);
    }

    protected int toResColor(int resId) {
        return ContextCompat.getColor(requireContext(), resId);
    }
}
