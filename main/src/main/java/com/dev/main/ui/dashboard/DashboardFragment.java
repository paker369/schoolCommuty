package com.dev.main.ui.dashboard;

import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.dev.common.base.BaseFragment;
import com.dev.main.databinding.FragmentDashboardBinding;

public class DashboardFragment extends BaseFragment<DashboardViewModel> {
    private FragmentDashboardBinding binding;

    @Override
    protected void initViewModel() {
        vm = new ViewModelProvider(this).get(DashboardViewModel.class);
    }

    @Override
    protected View initBinding() {
        binding = FragmentDashboardBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {
        vm.getText().observe(getViewLifecycleOwner(), s -> binding.textDashboard.setText("暂无动态"));
    }

    @Override
    protected void initObserver() {

    }
}