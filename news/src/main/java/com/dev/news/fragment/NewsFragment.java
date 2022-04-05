package com.dev.news.fragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dev.common.base.BaseFragment;
import com.dev.common.constant.ARouterConstant;
import com.dev.common.database.news.News;
import com.dev.common.utils.ActivityRouter;
import com.dev.news.adapter.NewsAdapter;
import com.dev.news.databinding.FragmentNewsBinding;
import com.dev.news.viewmodel.NewsViewModel;

/**
 * @author long.guo
 * @since 1/31/21
 */
@Route(path = ARouterConstant.News.newsFragment)
public class NewsFragment extends BaseFragment<NewsViewModel> {
    private FragmentNewsBinding binding;
    private final NewsAdapter adapter = new NewsAdapter();

    @Override
    protected View initBinding() {
        binding = FragmentNewsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {

    }

    @Override
    protected void setupRecyclerView() {
        String category = "全部";
        if (getArguments() != null) {
            category = getArguments().getString("category", "全部");
        }
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this.requireContext()));
        adapter.setOnItemClickListener((view, position) -> {
            News news = adapter.getData().get(position).news;
            ActivityRouter.gotoNewsDetailActivity(news.id);
        });
        binding.recyclerView.setAdapter(adapter);
        vm.loadNews(category);
    }

    @Override
    protected void initObserver() {
        vm.newsList.observe(this, adapter::setData);
    }
}
