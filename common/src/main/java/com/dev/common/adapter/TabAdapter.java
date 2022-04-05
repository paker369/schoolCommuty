package com.dev.common.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author long.guo
 * @since 1/23/21
 */
public class TabAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragments;
    private final List<String> titles;

    public TabAdapter(List<String> titles, List<Fragment> tables, @NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        fragments = tables;
        this.titles = titles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
