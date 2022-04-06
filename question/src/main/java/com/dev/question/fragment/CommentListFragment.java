package com.dev.question.fragment;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.dev.common.Constant;
import com.dev.common.base.BaseFragment;
import com.dev.question.adapter.CommentListAdapter;
import com.dev.question.databinding.FragmentCommentListBinding;
import com.dev.question.viewmodel.QuestionViewModel;

/**
 * @author long.guo
 * @since 2/21/21
 */
public class CommentListFragment extends BaseFragment<QuestionViewModel> {
    private FragmentCommentListBinding binding;
    private final CommentListAdapter adapter = new CommentListAdapter();

    public static Fragment newInstance(int i) {
        return new CommentListFragment();
    }

    @Override
    protected View initBinding() {
        binding = FragmentCommentListBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {

    }

    @Override
    public void onResume() {
        super.onResume();
        vm.loadAnswer();
    }

    @Override
    protected void initData() {
        vm.loadAnswer();
    }

    @Override
    protected void setupRecyclerView() {
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initObserver() {
        vm.myAnswer.observe(this,   comment -> {
            if (comment == null || comment.size() == 0) {
                binding.empty.setVisibility(View.VISIBLE);
            } else {
                binding.empty.setVisibility(View.GONE);
            }
            adapter.setData(comment);
        });
        Constant.stateChanged.observe(this,o->{
            vm.loadAnswer();
        });
    }
}
