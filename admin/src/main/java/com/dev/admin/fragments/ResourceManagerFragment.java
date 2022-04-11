package com.dev.admin.fragments;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.dev.admin.databinding.FragmentResourceManagerBinding;
import com.dev.admin.viewmodel.ResourceManagerViewModel;
import com.dev.common.adapter.TabAdapter2;
import com.dev.common.base.BaseFragment;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

/**
 * @author long.guo
 * @since 2/25/21
 */
public class ResourceManagerFragment extends BaseFragment<ResourceManagerViewModel> {
    private FragmentResourceManagerBinding binding;

    public static Fragment newInstance() {
        return new ResourceManagerFragment();
    }

    @Override
    protected View initBinding() {
        binding = FragmentResourceManagerBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {
    }

    @Override
    protected void setupViewPager() {
        String[] titles = new String[]{"分享管理", "实时推送"};
        Fragment[] fragments = new Fragment[1];
        fragments[0] = ResourceChildFragment.newInstance(0);
//        fragments[1] = ResourceChildFragment.newInstance(1);
        binding.viewPager.setAdapter(new TabAdapter2(titles, fragments, getChildFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
//        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    @Override
    protected void initObserver() {

    }
}
