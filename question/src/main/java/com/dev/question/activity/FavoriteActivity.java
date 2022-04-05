package com.dev.question.activity;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dev.common.base.BaseActivity;
import com.dev.common.base.BaseAdapter;
import com.dev.common.constant.ARouterConstant;
import com.dev.common.utils.LayoutInflaterUtils;
import com.dev.question.R;
import com.dev.question.databinding.ActivityFavoriteBinding;
import com.dev.question.databinding.ItemFavoriteBinding;
import com.dev.question.viewmodel.FavoriteViewModel;

@Route(path = ARouterConstant.Question.myFavoriteActivity)
public class FavoriteActivity extends BaseActivity<FavoriteViewModel> {

    private ActivityFavoriteBinding binding;
    private final FavoriteAdapter adapter = new FavoriteAdapter();

    @Override
    protected View initBinding() {
        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {
        setupToolbar(binding.toolbar);
    }

    @Override
    protected void setupRecyclerView() {
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        vm.loadFavorite();
    }

    @Override
    protected void initObserver() {
        vm.favorite.observe(this, adapter::setData);
    }

    static class FavoriteAdapter extends BaseAdapter<FavoriteBean, FavoriteAdapter.FavoriteVH> {

        @NonNull
        @Override
        public FavoriteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new FavoriteVH(LayoutInflaterUtils.inflate(parent, R.layout.item_favorite));
        }

        @Override
        public void onBindViewHolder(@NonNull FavoriteVH holder, int position) {
            holder.bind(data.get(position));
        }

        static class FavoriteVH extends RecyclerView.ViewHolder {

            private ItemFavoriteBinding binding = ItemFavoriteBinding.bind(itemView);

            public FavoriteVH(@NonNull View itemView) {
                super(itemView);
            }

            public void bind(FavoriteBean favoriteBean) {
                binding.title.setText(favoriteBean.title);
                if (favoriteBean.type == 0) {
                    binding.type.setText("已收藏");
                } else {
                    binding.type.setText("已喜欢");
                }
            }
        }

    }

    public static class FavoriteBean {
        public String title;
        public int type;

        public FavoriteBean(String title, int type) {
            this.title = title;
            this.type = type;
        }
    }
}