package com.dev.common.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dev.common.base.BaseFragment;

import java.util.List;

/**
 * @author long.guo
 * @since 1/24/21
 */
public class TabAdapter2 extends FragmentPagerAdapter {
    private final String[] titles;
    private final Fragment[] fragments;

    public TabAdapter2(String[] titles, Fragment[] fragments, @NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.titles = titles;
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
