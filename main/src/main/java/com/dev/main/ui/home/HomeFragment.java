package com.dev.main.ui.home;

import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dev.common.adapter.TabAdapter;
import com.dev.common.adapter.TabAdapter2;
import com.dev.common.base.BaseFragment;
import com.dev.common.utils.ActivityRouter;
import com.dev.common.utils.FragmentRouter;
import com.dev.common.utils.SpUtil;
import com.dev.common.utils.data.ArrayList;
import com.dev.main.R;
import com.dev.main.databinding.FragmentHomeBinding;
import com.dev.main.search.SearchFragment;
import com.dev.user.UserSession;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.kongzue.dialog.listener.OnMenuItemClickListener;
import com.kongzue.dialog.util.TextInfo;
import com.kongzue.dialog.v2.BottomMenu;
import com.kongzue.dialog.v2.DialogSettings;

import java.util.List;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;
import static com.dev.common.Constant.stateChanged;
import static com.dev.common.constant.KeyConstant.REQUEST_CODE_PUBLISH_NEWS;
import static com.dev.common.database.DaoProvider.newsDao;
import static com.kongzue.dialog.v2.DialogSettings.STYLE_KONGZUE;
import static com.kongzue.dialog.v2.DialogSettings.THEME_LIGHT;

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
//        DialogSettings.style = STYLE_KONGZUE;
//        DialogSettings.tip_theme = THEME_LIGHT;
//        DialogSettings.use_blur=false;
//        DialogSettings.dialog_cancelable_default=true;
        binding.publish.setOnClickListener(v -> {
//            ActivityRouter.gotoNewsPublishActivity(requireActivity(), REQUEST_CODE_PUBLISH_NEWS);
            if (UserSession.isLogin()) {
//                List<String> list = new ArrayList<>();
//                list.add("????????????");
//                list.add("??????");
//                list.add("??????");
//                list.add("??????");
                BottomMenu.build((AppCompatActivity) getActivity(), new String[]{"????????????", "??????", "??????", "??????"}, new OnMenuItemClickListener() {
                    @Override
                    public void onClick(String text, int index) {
                        switch (index) {
                            case 0:
                                ActivityRouter.gotoPublishActivity(requireActivity(), 1000, "????????????");
                                break;
                            case 1:
                                ActivityRouter.gotoPublishActivity(requireActivity(), 1000, "??????");

                                break;
                            case 2:
                                ActivityRouter.gotoPublishActivity(requireActivity(), 1000, "??????");

                                break;
                            case 3:
                                ActivityRouter.gotoPublishActivity(requireActivity(), 1000, "??????");

                                break;
                        }
                    }
                }).setMenuTextInfo(new TextInfo().setGravity(Gravity.CENTER)).showDialog();
//                new MaterialAlertDialogBuilder(getContext())
//                        .setTitle("????????????")
//                        .setPositiveButton("               ????????????", (dialog, which) -> {
//                            ActivityRouter.gotoPublishActivity(requireActivity(), 1000, "????????????");
//                        })
//                        .setNegativeButton("          ??????", (dialog, which) -> {
//                            ActivityRouter.gotoPublishActivity(requireActivity(), 1000, "??????");
//                        })
//                        .setNeutralButton("??????", (dialog, which) -> {
//                            ActivityRouter.gotoPublishActivity(requireActivity(), 1000, "??????");
//                        })
//                        .setNeutralButton("??????", (dialog, which) -> {
//                            ActivityRouter.gotoPublishActivity(requireActivity(), 1000, "??????");
//                        }).show();
            } else {
                Toast.makeText(requireContext(), "????????????", Toast.LENGTH_SHORT).show();
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
        String[] titles = new String[]{"??????", "??????", "??????"};
        Fragment[] fragments = new Fragment[3];
        fragments[0] = FragmentRouter.questionFragment(-1);
        fragments[1] = FragmentRouter.dynamicFragment(-1);
        fragments[2] = FragmentRouter.dynamicFragment(-1, true);
        binding.viewPager.setAdapter(new TabAdapter2(titles, fragments, getChildFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.gg.setText(SpUtil.getInstance().getString(SpUtil.GONGGAO));
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