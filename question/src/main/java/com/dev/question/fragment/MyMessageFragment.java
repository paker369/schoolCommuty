package com.dev.question.fragment;

import android.view.View;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.dev.common.adapter.TabAdapter2;
import com.dev.common.base.BaseFragment;
import com.dev.common.utils.ActivityRouter;
import com.dev.question.R;
import com.dev.question.databinding.FragmentQuestionBinding;
import com.dev.question.viewmodel.QuestionViewModel;
import com.dev.user.UserSession;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;
import static com.dev.common.constant.KeyConstant.REQUEST_CODE_PUBLISH_NEWS;

/**
 * @author long.guo
 * @since 2/8/21
 */
public class MyMessageFragment extends BaseFragment<QuestionViewModel> {

    public static final String SEARCH_FRAGMENT_TAG = "questionSearchFragment";

    private FragmentQuestionBinding binding;
    private QuestionSearchFragment searchFragment;

    @Override
    protected View initBinding() {
        binding = FragmentQuestionBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {
        binding.publish.setOnClickListener(v -> {
            ActivityRouter.gotoNewsPublishActivity(requireActivity(), REQUEST_CODE_PUBLISH_NEWS);
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
    protected void initObserver() {

    }

    @Override
    public void onResume() {
        super.onResume();
        String[] titles = new String[]{"我的提问", "我的回答", "与我相关"};
        Fragment[] fragments = new Fragment[3];
        fragments[0] = QuestionListFragment.newInstance(UserSession.getInstance().id());
        fragments[1] = CommentListFragment.newInstance(1);
        fragments[2] = ReferListFragment.newInstance(2);
        binding.viewPager.setAdapter(new TabAdapter2(titles, fragments, getChildFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    private boolean isSearchAdded() {
        if (searchFragment == null) return false;
        return getChildFragmentManager().findFragmentByTag(SEARCH_FRAGMENT_TAG) != null;
    }

    private void addSearchFragment() {
        if (searchFragment == null) searchFragment = new QuestionSearchFragment();
        getChildFragmentManager().beginTransaction().add(R.id.fragmentContainer, searchFragment, SEARCH_FRAGMENT_TAG).commitAllowingStateLoss();
    }

    private void removeSearchFragment() {
        if (searchFragment != null)
            getChildFragmentManager().beginTransaction().remove(searchFragment).commitAllowingStateLoss();
    }
}
