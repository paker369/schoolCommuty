package com.dev.admin.fragments;

import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.dev.admin.adapter.BookManageAdapter;
import com.dev.admin.databinding.FragmentBookManageBinding;
import com.dev.admin.viewmodel.BookManageViewModel;
import com.dev.common.adapter.OnItemClickListener;
import com.dev.common.base.BaseFragment;

/**
 * @author long.guo
 * @since 1/23/21
 */
public class BookManageFragment extends BaseFragment<BookManageViewModel> {
    private FragmentBookManageBinding binding;
    private final BookManageAdapter adapter = new BookManageAdapter(null);

    public static BookManageFragment newInstance() {
        return new BookManageFragment();
    }

    @Override
    protected void initViewModel() {
        vm = new ViewModelProvider(this).get(BookManageViewModel.class);
    }

    @Override
    protected View initBinding() {
        binding = FragmentBookManageBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {
        binding.recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initObserver() {
        vm.allBooks.observe(this, adapter::setBooks);
    }
}
