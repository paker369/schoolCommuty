package com.dev.book.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dev.book.adapter.BookListAdapter;
import com.dev.book.databinding.FragmentBookListBinding;
import com.dev.book.viewmodel.BookListViewModel;
import com.dev.common.base.BaseFragment;
import com.dev.common.constant.ARouterConstant;
import com.dev.common.entry.BookBean;
import com.dev.common.utils.ActivityRouter;

import java.util.List;

/**
 * @author long.guo
 * @since 1/23/21
 */
@Route(path = ARouterConstant.Book.bookList)
public class BookListFragment extends BaseFragment<BookListViewModel> {

    private FragmentBookListBinding binding;
    private int position = -1;
    private final BookListAdapter adapter = new BookListAdapter((view, position) -> {
        List<BookBean> books = vm.books.getValue();
        if (books != null)
            ActivityRouter.gotoReadingActivity(vm.books.getValue().get(position));
    });

    @Override
    protected void initViewModel() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt("position");
        }
        vm = new ViewModelProvider(this).get(BookListViewModel.class);
    }

    @Override
    protected View initBinding() {
        binding = FragmentBookListBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {
    }

    @Override
    protected void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initObserver() {
        vm.loadBooks(position);
        vm.books.observe(this, bookBeans -> {
            int visible = (bookBeans == null || bookBeans.size() == 0) ? View.VISIBLE : View.GONE;
            binding.empty.setVisibility(visible);
            adapter.setBooks(bookBeans);
        });
    }
}
