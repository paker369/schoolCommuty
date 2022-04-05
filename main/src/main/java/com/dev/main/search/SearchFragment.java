package com.dev.main.search;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.book.adapter.BookListAdapter;
import com.dev.common.base.BaseFragment;
import com.dev.common.entry.BookBean;
import com.dev.common.utils.ActivityRouter;
import com.dev.main.databinding.FragmentSearchBinding;

import java.util.List;
import java.util.Objects;

/**
 * @author long.guo
 * @since 1/27/21
 */
public class SearchFragment extends BaseFragment<SearchViewModel> {
    private FragmentSearchBinding binding;
    private final SearchUserAdapter userAdapter = new SearchUserAdapter();
    private String keyword;
    private final BookListAdapter contentAdapter = new BookListAdapter((view, position) -> {
        List<BookBean> books = vm.books.getValue();
        if (books != null)
            ActivityRouter.gotoReadingActivity(vm.books.getValue().get(position));
    });

    @Override
    protected View initBinding() {
        binding = FragmentSearchBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (keyword != null) {
            search(this.keyword);
        }
    }

    @Override
    protected void setupViews() {
        binding.userRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        binding.userRecyclerView.setAdapter(userAdapter);
        userAdapter.setOnItemClickListener((view, position) -> {
            ActivityRouter.gotoPeopleActivity(Objects.requireNonNull(vm.users.getValue()).get(position).id);
        });
        if (keyword != null) {
            vm.searchUsers(keyword);
            vm.searchBooks(keyword);
        }

        binding.contentRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.contentRecyclerView.setAdapter(contentAdapter);
    }

    @Override
    protected void initObserver() {
        vm.users.observe(this, userAdapter::setData);
        vm.books.observe(this, contentAdapter::setBooks);
    }

    public void search(String keyword) {
        if (vm != null) {
            vm.searchUsers(keyword);
            vm.searchBooks(keyword);
        }
        this.keyword = keyword;
    }
}
