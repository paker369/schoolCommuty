package com.dev.main.ui.home;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.dev.common.base.BaseFragment;
import com.dev.main.databinding.FragmentTestBinding;

/**
 * @author long.guo
 * @since 1/24/21
 */
public class TestFragment extends BaseFragment<ViewModel> {
    private FragmentTestBinding binding;

    @Override
    protected void initViewModel() {

    }

    @Override
    protected View initBinding() {
        binding = FragmentTestBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {

    }

    @Override
    protected void initObserver() {

    }
}
