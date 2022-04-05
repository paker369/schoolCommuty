package com.dev.news.activity;

import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.dev.common.constant.ARouterConstant;
import com.dev.common.database.news.News;
import com.dev.news.databinding.ActivityNewsPublishBinding;
import com.dev.news.viewmodel.NewsPublishViewModel;
import com.dev.base.activity.BasePublishActivity;

import static com.dev.common.constant.KeyConstant.RESULT_CODE_REFRESH;
import static com.dev.common.database.DaoProvider.newsDao;

@Route(path = ARouterConstant.Publish.publishNewsActivity)
public class NewsPublishActivity extends BasePublishActivity<NewsPublishViewModel> {

    private ActivityNewsPublishBinding binding;

    @Override
    protected View initBinding() {
        binding = ActivityNewsPublishBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {
        setupToolbar(binding.toolbar);
        binding.btnPublish.setOnClickListener(v -> {
            if (binding.title.getText() == null || binding.title.getText().length() == 0) {
                Toast.makeText(this, "标题不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            if (binding.category.getText() == null || binding.category.getText().length() == 0) {
                Toast.makeText(this, "分类不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (binding.content.getText() == null || binding.content.getText().length() == 0) {
                Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!binding.checkbox.isChecked()) {
                Toast.makeText(this, "请勾选协议", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = binding.title.getText().toString();
            String content = binding.content.getText().toString();
            String category = binding.category.getText().toString();
            News news = new News(title, content, attachment, category);
            newsDao().insert(news);

            setResult(RESULT_CODE_REFRESH);
            finish();
        });

        binding.attachment.setOnClickListener(v -> {
            attachment();
        });
        binding.image.setOnClickListener(v -> {
            attachment();
        });
    }

    @Override
    protected void initObserver() {

    }

    @Override
    public void onAttachmentSuccess(String imagePath) {
        Glide.with(this).load(imagePath).into(binding.image);
    }
}