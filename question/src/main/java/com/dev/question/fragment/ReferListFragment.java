package com.dev.question.fragment;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.dev.common.Constant;
import com.dev.common.base.BaseFragment;
import com.dev.question.adapter.ReferAdapter;
import com.dev.question.databinding.FragmentReferListBinding;
import com.dev.question.viewmodel.ReferViewModel;

/**
 * @author long.guo
 * @since 2/21/21
 */
public class ReferListFragment extends BaseFragment<ReferViewModel> {
    private FragmentReferListBinding binding;
    private final ReferAdapter adapter = new ReferAdapter();

    public static Fragment newInstance(int i) {
        return new ReferListFragment();
    }

    @Override
    protected View initBinding() {
        binding = FragmentReferListBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {

    }

    @Override
    protected void initData() {
        vm.loadRefer();
    }

    @Override
    protected void setupRecyclerView() {
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initObserver() {
        vm.refers.observe(this, adapter::setData);
        Constant.stateChanged.observe(this, o -> {
            vm.loadRefer();
        });
    }
}
