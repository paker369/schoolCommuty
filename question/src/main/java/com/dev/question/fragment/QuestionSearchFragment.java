package com.dev.question.fragment;

import android.view.View;

import com.dev.common.base.BaseFragment;
import com.dev.question.databinding.FragmentQuestionSearchBinding;
import com.dev.question.viewmodel.QuestionViewModel;

/**
 * @author long.guo
 * @since 2/20/21
 */
public class QuestionSearchFragment extends BaseFragment<QuestionViewModel> {
    private FragmentQuestionSearchBinding binding;
    private String keyword;

    @Override
    protected View initBinding() {
        binding = FragmentQuestionSearchBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {

    }

    @Override
    protected void initObserver() {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (keyword != null) {
            search(this.keyword);
        }
    }

    public void search(String keyword) {
        if (vm != null) {
            vm.searchQuestion(keyword);
        }
        this.keyword = keyword;
    }
}
