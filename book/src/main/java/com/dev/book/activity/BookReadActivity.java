package com.dev.book.activity;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModel;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dev.book.databinding.ActivityBookReadBinding;
import com.dev.book.fragments.BookReadFragment;
import com.dev.common.base.BaseActivity;
import com.dev.common.constant.ARouterConstant;
import com.dev.common.entry.BookBean;
import com.dev.player.GlobalPlayer;

import java.util.List;

@Route(path = ARouterConstant.Book.bookReadingActivity)
public class BookReadActivity extends BaseActivity<ViewModel> {
    private ActivityBookReadBinding binding;
    private final GlobalPlayer globalPlayer = GlobalPlayer.getInstance();

    @Override
    protected void initViewModel() {

    }

    @Override
    protected View initBinding() {
        binding = ActivityBookReadBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {
        setupToolbar(binding.toolbar);
        BookBean bookBean = (BookBean) getIntent().getSerializableExtra("bookBean");
        if (bookBean == null) return;
        List<BookBean.PageBean> pages = bookBean.getPages();
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return pages == null ? 0 : pages.size();
            }

            @NonNull
            @Override
            public Fragment getItem(int position) {
                return new BookReadFragment(pages.get(position));
            }
        };
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                globalPlayer.stop();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        binding.viewPager.setAdapter(adapter);
    }

    @Override
    protected void initObserver() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        globalPlayer.destroy();
    }
}