package com.dev.main.ui.home;

import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dev.common.adapter.TabAdapter;
import com.dev.common.adapter.TabAdapter2;
import com.dev.common.base.BaseFragment;
import com.dev.common.utils.ActivityRouter;
import com.dev.common.utils.FragmentRouter;
import com.dev.common.utils.data.ArrayList;
import com.dev.main.R;
import com.dev.main.databinding.FragmentHomeBinding;
import com.dev.main.search.SearchFragment;
import com.dev.user.UserSession;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;
import static com.dev.common.Constant.stateChanged;
import static com.dev.common.constant.KeyConstant.REQUEST_CODE_PUBLISH_NEWS;
import static com.dev.common.database.DaoProvider.newsDao;

public class HomeFragment extends BaseFragment<HomeViewModel> {

    private FragmentHomeBinding binding;

    public static final String SEARCH_FRAGMENT_TAG = "searchFragment";
    private SearchFragment searchFragment;

    @Override
    protected void initViewModel() {
        vm = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    protected View initBinding() {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {
        binding.publish.setOnClickListener(v -> {
//            ActivityRouter.gotoNewsPublishActivity(requireActivity(), REQUEST_CODE_PUBLISH_NEWS);
            if (UserSession.isLogin()) {
                new MaterialAlertDialogBuilder(getContext())
                        .setTitle("动态类型")
                        .setPositiveButton("电影资讯，期刊，书籍等", (dialog, which) -> {
                            ActivityRouter.gotoPublishActivity(requireActivity(), 1000, "其他");
                        })
                        .setNegativeButton("提问", (dialog, which) -> {
                            ActivityRouter.gotoPublishActivity(requireActivity(), 1000, "提问");
                        })
                        .setNeutralButton("其他网站", (dialog, which) -> {
                            ActivityRouter.gotoPublishActivity(requireActivity(), 1000, "其他网站");
                        }).show();
            } else {
                Toast.makeText(requireContext(), "请先登录", Toast.LENGTH_SHORT).show();
            }
        });

        binding.searchView.setOnCloseListener(() -> {
            removeSearchFragment();
            return false;
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!isSearchAdded()) {
                    addSearchFragment();
                }
                searchFragment.search(newText);
                return false;
            }
        });
    }

    @Override
    protected void setupViewPager() {
    }

    @Override
    public void onResume() {
        super.onResume();
        String[] titles = new String[]{"提问", "推荐", "关注"};
        Fragment[] fragments = new Fragment[3];
        fragments[0] = FragmentRouter.questionFragment(-1);
        fragments[1] = FragmentRouter.dynamicFragment(-1);
        fragments[2] = FragmentRouter.dynamicFragment(-1, true);
        binding.viewPager.setAdapter(new TabAdapter2(titles, fragments, getChildFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        stateChanged.setValue(true);
    }

    @Override
    protected void initObserver() {

    }

    private boolean isSearchAdded() {
        if (searchFragment == null) return false;
        return getChildFragmentManager().findFragmentByTag(SEARCH_FRAGMENT_TAG) != null;
    }

    private void addSearchFragment() {
        if (searchFragment == null) searchFragment = new SearchFragment();
        getChildFragmentManager().beginTransaction().add(R.id.fragmentContainer, searchFragment, SEARCH_FRAGMENT_TAG).commitAllowingStateLoss();
    }

    private void removeSearchFragment() {
        if (searchFragment != null)
            getChildFragmentManager().beginTransaction().remove(searchFragment).commitAllowingStateLoss();
    }
}