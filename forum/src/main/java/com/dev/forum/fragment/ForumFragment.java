package com.dev.forum.fragment;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dev.common.base.BaseFragment;
import com.dev.common.constant.ARouterConstant;
import com.dev.forum.databinding.FragmentForumBinding;
import com.dev.forum.viewmodel.ForumViewModel;

/**
 * @author long.guo
 * @since 1/31/21
 */
@Route(path = ARouterConstant.Forum.forumFragment)
public class ForumFragment extends BaseFragment<ForumViewModel> {
    private FragmentForumBinding binding;

    @Override
    protected View initBinding() {
        binding = FragmentForumBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {

    }

    @Override
    protected void initObserver() {

    }
}
