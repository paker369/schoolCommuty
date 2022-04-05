package com.dev.user.setting;

import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dev.common.base.BaseActivity;
import com.dev.common.constant.ARouterConstant;
import com.dev.user.databinding.ActivityPasswordSettingBinding;

@Route(path = ARouterConstant.Users.resetPasswordActivity)
public class PasswordSettingActivity extends BaseActivity<PasswordSettingViewModel> {

    private ActivityPasswordSettingBinding binding;

    @Override
    protected View initBinding() {
        binding = ActivityPasswordSettingBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {
        setupToolbar(binding.toolbar);
        binding.checkoutBtn.setOnClickListener(v -> {
            if (binding.oldEtPassword.getText() == null || binding.oldEtPassword.getText().length() < 5) {
                binding.oldPasswordContainer.setError("密码必须大于5位");
                return;
            }
            binding.oldPasswordContainer.setError(null);

            if (binding.etPassword.getText() == null || binding.etPassword.getText().length() < 5) {
                binding.passwordContainer.setError("密码必须大于5位");
                return;
            }
            binding.passwordContainer.setError(null);

            String rePasswordStr = null;
            if (binding.rePasswordEditText.getText() != null) {
                rePasswordStr = binding.rePasswordEditText.getText().toString();
            }
            if (!binding.etPassword.getText().toString().equals(rePasswordStr)) {
                binding.rePasswordContainer.setError("两次密码不一致");
                return;
            }
            binding.rePasswordContainer.setError(null);

            String oldPassword = binding.oldEtPassword.getText().toString();
            String newPassword = binding.etPassword.getText().toString();
            vm.modifyPassword(oldPassword, newPassword);
        });
    }

    @Override
    protected void initObserver() {
        vm.modifyMessage.observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            if (TextUtils.equals("success", message)) {
                finish();
            }
        });
    }
}