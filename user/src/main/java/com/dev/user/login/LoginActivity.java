package com.dev.user.login;

import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.dev.common.base.BaseActivity;
import com.dev.common.constant.ARouterConstant;
import com.dev.common.database.user.User;
import com.dev.common.utils.ActivityRouter;
import com.dev.common.utils.SpUtil;
import com.dev.user.UserSession;
import com.dev.user.databinding.ActivityLoginBinding;

@Route(path = ARouterConstant.Users.loginActivity)
public class LoginActivity extends BaseActivity<LoginViewModel> {

    private ActivityLoginBinding binding;

    @Autowired(name = ActivityRouter.KEY_LOGIN_IS_ADMIN)
    boolean isAdmin;

    @Override
    protected View initBinding() {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void initViewModel() {
        vm = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
    }

    @Override
    protected void setupViews() {
        setupToolbar(binding.toolbar);
        setupIsAdmin();
        binding.btnLogin.setOnClickListener(v -> {
            if (binding.nameContainer.getVisibility() == View.VISIBLE) {
                loginByName();
            } else {
                loginByPhone();
            }
        });

        binding.registerTextView.setOnClickListener(v -> {
            ActivityRouter.gotoRegisterActivity();
        });
    }

    @Override
    protected void initObserver() {
        vm.login.observe(this, userBeanResource -> {
            User bean = userBeanResource.getData();
            switch (userBeanResource.getCode()) {
                case LoginViewModel.NO_USER:
                    binding.phoneContainer.setError(userBeanResource.getMsg());
                    binding.nameContainer.setError(userBeanResource.getMsg());
                    break;
                case LoginViewModel.PASSWORD_ERROR:
                    binding.passwordContainer.setError(userBeanResource.getMsg());
                    break;
                default:
                    binding.phoneContainer.setError(null);
                    binding.passwordContainer.setError(null);
                    whenLoginSuccess(bean);

                    UserSession.login(bean);
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                    ActivityRouter.gotoMainActivity();
            }
        });
    }

    private void loginByPhone() {
        if (binding.etPhone.getText() != null && binding.etPhone.getText().length() != 11) {
            binding.phoneContainer.setError("输入的手机号码有误");
            return;
        }
        binding.phoneContainer.setError(null);

        if (binding.etPassword.getText() != null && binding.etPassword.getText().length() < 5) {
            binding.passwordContainer.setError("密码必须大于5位");
            return;
        }

        binding.passwordContainer.setError(null);
        vm.login(binding.etPhone.getText().toString(), binding.etPassword.getText().toString(), isAdmin);
    }

    private void loginByName() {
        if (binding.etName.getText() == null || binding.etName.getText().length() == 0) {
            binding.nameContainer.setError("输入的用户名有误");
            return;
        }
        binding.nameContainer.setError(null);

        if (binding.etPassword.getText() != null && binding.etPassword.getText().length() < 5) {
            binding.passwordContainer.setError("密码必须大于5位");
            return;
        }
        binding.passwordContainer.setError(null);
        vm.loginByName(binding.etName.getText().toString(), binding.etPassword.getText().toString(), isAdmin);
    }

    private void setupIsAdmin() {
        if (isAdmin) {
            binding.registerTextView.setVisibility(View.GONE);
            binding.nameContainer.setVisibility(View.VISIBLE);
            binding.phoneContainer.setVisibility(View.GONE);

            binding.checkbox.setChecked(SpUtil.getInstance().getBoolean(SpUtil.KEY_CHECKED_ADMIN));
            binding.etName.setText(SpUtil.getInstance().getString(SpUtil.KEY_USER_NAME_ADMIN));
            binding.etPassword.setText(SpUtil.getInstance().getString(SpUtil.KEY_PASSWORD_ADMIN));
        } else {
            binding.registerTextView.setVisibility(View.VISIBLE);
            binding.nameContainer.setVisibility(View.GONE);
            binding.phoneContainer.setVisibility(View.VISIBLE);

            binding.checkbox.setChecked(SpUtil.getInstance().getBoolean(SpUtil.KEY_CHECKED));
            binding.etPhone.setText(SpUtil.getInstance().getString(SpUtil.KEY_PHONE));
            binding.etPassword.setText(SpUtil.getInstance().getString(SpUtil.KEY_PASSWORD));
        }
    }

    private void whenLoginSuccess(User bean) {
        if (isAdmin) {
            SpUtil.getInstance().save(SpUtil.KEY_USER_NAME_ADMIN, bean.nickName);
            if (binding.checkbox.isChecked()) {
                SpUtil.getInstance().save(SpUtil.KEY_PASSWORD_ADMIN, bean.password);
            } else {
                SpUtil.getInstance().save(SpUtil.KEY_PASSWORD_ADMIN, "");
            }
            SpUtil.getInstance().save(SpUtil.KEY_CHECKED_ADMIN, binding.checkbox.isChecked());
        } else {
            SpUtil.getInstance().save(SpUtil.KEY_PHONE, bean.phone);
            if (binding.checkbox.isChecked()) {
                SpUtil.getInstance().save(SpUtil.KEY_PASSWORD, bean.password);
            } else {
                SpUtil.getInstance().save(SpUtil.KEY_PASSWORD, "");
            }
            SpUtil.getInstance().save(SpUtil.KEY_CHECKED, binding.checkbox.isChecked());
        }
    }
}