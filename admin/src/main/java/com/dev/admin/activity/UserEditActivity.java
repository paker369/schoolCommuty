package com.dev.admin.activity;

import android.view.View;
import android.widget.Toast;

import com.dev.admin.databinding.ActivityUserEditBinding;
import com.dev.admin.viewmodel.UserManageViewModel;
import com.dev.common.base.BaseActivity;
import com.dev.common.database.user.User;
import com.google.gson.Gson;

import static com.dev.common.database.DaoProvider.userDao;

public class UserEditActivity extends BaseActivity<UserManageViewModel> {

    private ActivityUserEditBinding binding;
    private final Gson gson = new Gson();

    @Override
    protected View initBinding() {
        binding = ActivityUserEditBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {
        setupToolbar(binding.toolbar);

        long userId = getIntent().getLongExtra("userId", -1);
        User user = userDao().findById(userId);
        binding.editText.setText(gson.toJson(user));

        binding.follow.setOnClickListener(v -> onBackPressed());
    }

    @Override
    protected void initObserver() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            User user = gson.fromJson(binding.editText.getText().toString(), User.class);
            userDao().update(user);
            setResult(RESULT_OK);
        } catch (Exception e) {
            Toast.makeText(this, "编辑错误", Toast.LENGTH_SHORT).show();
        }
    }
}