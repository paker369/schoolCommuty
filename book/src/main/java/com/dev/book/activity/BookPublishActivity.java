package com.dev.book.activity;

import android.Manifest;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.dev.base.activity.BasePublishActivity;
import com.dev.book.R;
import com.dev.book.databinding.ActivityBookPublishBinding;
import com.dev.book.fragments.BookPublishFragment;
import com.dev.book.viewmodel.BookManager;
import com.dev.book.viewmodel.BookPublishViewModel;
import com.dev.common.constant.ARouterConstant;
import com.dev.common.entry.BookBean;
import com.dev.common.utils.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

@Route(path = ARouterConstant.Book.bookPublishActivity)
public class BookPublishActivity extends BasePublishActivity<BookPublishViewModel> {

    private ActivityBookPublishBinding binding;
    private int category = 1;
    private String poster;

    private boolean isChoicePoster;

    @Override
    protected View initBinding() {
        binding = ActivityBookPublishBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {
        setupToolbar(binding.toolbar);

        PermissionUtil.getPermission(this, new String[]{Manifest.permission.RECORD_AUDIO}, true, false, () -> {

        });

        binding.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.first) {
                category = 1;
            } else if (checkedId == R.id.second) {
                category = 2;
            } else if (checkedId == R.id.third) {
                category = 3;
            }
        });

        binding.choicePosterTV.setOnClickListener(v -> {
            isChoicePoster = true;
            attachment();
        });

        binding.addTextView.setOnClickListener(v -> {
            fragments.add(BookPublishFragment.newInstance(fragments.size() + 1));
            adapter.notifyDataSetChanged();
        });

        binding.checkoutTextView.setOnClickListener(v -> {
            if (TextUtils.isEmpty(binding.etName.getText()) || TextUtils.isEmpty(binding.descEtName.getText())) {
                Toast.makeText(this, "请补全信息", Toast.LENGTH_SHORT).show();
                return;
            }
            if (this.poster == null) {
                Toast.makeText(this, "请选择封面", Toast.LENGTH_SHORT).show();
                return;
            }

            BookBean bookBean = new BookBean();
            bookBean.setBookName(binding.etName.getText().toString());
            bookBean.setPoster(this.poster);
            bookBean.setBookDesc(binding.descEtName.getText().toString());
            bookBean.setCategory(this.category);
            bookBean.setRootPath(String.valueOf(System.currentTimeMillis()));
            List<BookBean.PageBean> pages = new ArrayList<>();
            for (BookPublishFragment fragment : fragments) {
                pages.add(fragment.pageBean);
            }
            bookBean.setPages(pages);
            BookManager.saveBook(bookBean);
            Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private final List<BookPublishFragment> fragments = new ArrayList<>();
    private final FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    };

    @Override
    protected void setupViewPager() {
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setPageMargin(50);
    }

    @Override
    protected void initObserver() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 1025) {
            int index = requestCode - 1;
            if (index > 0 && index < fragments.size()) {
                BookPublishFragment fragment = (BookPublishFragment) adapter.getItem(index);
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public void onAttachmentSuccess(String imagePath) {
        if (isChoicePoster) {
            this.poster = imagePath;
            isChoicePoster = false;
            Glide.with(this).load(imagePath).into(binding.posterImageView);
        }
    }
}