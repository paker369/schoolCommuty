package com.dev.common.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author long.guo
 * @since 1/24/21
 */
public class PageAdapter<T extends Fragment> extends FragmentPagerAdapter {
    private final List<T> fragments;

    public PageAdapter(List<T> tables, @NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        fragments = tables;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
