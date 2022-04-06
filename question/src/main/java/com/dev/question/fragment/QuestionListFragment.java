package com.dev.question.fragment;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dev.common.Constant;
import com.dev.common.base.BaseFragment;
import com.dev.common.constant.ARouterConstant;
import com.dev.common.utils.ActivityRouter;
import com.dev.question.adapter.QuestionAdapter;
import com.dev.question.databinding.FragmentQuestionListBinding;
import com.dev.question.viewmodel.QuestionViewModel;

/**
 * @author long.guo
 * @since 2/20/21
 */
@Route(path = ARouterConstant.Question.questionListFragment)
public class QuestionListFragment extends BaseFragment<QuestionViewModel> {
    private FragmentQuestionListBinding binding;
    private final QuestionAdapter adapter = new QuestionAdapter();

    public static QuestionListFragment newInstance(long userId) {
        QuestionListFragment f = new QuestionListFragment();
        Bundle b = new Bundle();
        b.putLong("userId", userId);
        f.setArguments(b);
        return f;
    }

    @Override
    protected View initBinding() {
        binding = FragmentQuestionListBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((v, pos) -> {
            ActivityRouter.gotoQuestionDetailActivity(vm.questions.getValue().get(pos).id);
        });
    }

    @Override
    protected void initData() {
        long userId = requireArguments().getLong("userId", -1);
        if (userId == -1) {
            vm.loadAllQuestions();
        } else {
            vm.loadQuestions(userId);
        }
    }

    @Override
    protected void initObserver() {
        vm.questions.observe(this,  question -> {
            if (question == null || question.size() == 0) {
                binding.empty.setVisibility(View.VISIBLE);
            } else {
                binding.empty.setVisibility(View.GONE);
            }
            adapter.setData(question);
        });
        Constant.stateChanged.observe(this, o -> {
            long userId = requireArguments().getLong("userId", -1);
            if (userId == -1) {
                vm.loadAllQuestions();
            } else {
                vm.loadQuestions(userId);
            }
        });
    }
}
