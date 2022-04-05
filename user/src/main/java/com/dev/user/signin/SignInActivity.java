package com.dev.user.signin;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dev.common.base.BaseActivity;
import com.dev.common.constant.ARouterConstant;
import com.dev.common.database.user.User;
import com.dev.common.utils.ActivityRouter;
import com.dev.common.utils.SpUtil;
import com.dev.user.R;
import com.dev.user.UserSession;
import com.dev.user.base.UserSettingBaseActivity;
import com.dev.user.databinding.ActivitySignInBinding;

@Route(path = ARouterConstant.Users.registerActivity)
public class SignInActivity extends UserSettingBaseActivity<SignInViewModel> {

    private ActivitySignInBinding binding;
    private String birthday;
    private String address;

    @Override
    protected void initViewModel() {
        vm = new ViewModelProvider(this, new SignInViewModelFactory())
                .get(SignInViewModel.class);
    }

    @Override
    protected View initBinding() {
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {
        setupToolbar(binding.toolbar);

        binding.registerButton.setOnClickListener(v -> {
            signIn();
        });

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, R.layout.item_gender, getResources().getStringArray(R.array.gender_array));
        binding.genderTextView.setAdapter(adapter);
        binding.userAddressContainer.setOnClickListener(v -> {
            showChooseAddressDialog();
        });
        binding.userBirthdayContainer.setOnClickListener(v -> {
            showChooseBirthDialog();
        });
    }

    @Override
    protected void initObserver() {
        vm.register.observe(this, userResource -> {
            User user = userResource.getData();
            if (user != null) {
                SpUtil.getInstance().save(SpUtil.KEY_PHONE, user.phone);
                UserSession.login(user);
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                ActivityRouter.gotoMainActivity();
            } else {
                Toast.makeText(this, userResource.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onBirthdayChanged(int year, int month, int day) {
        birthday = year + "-" + month + "-" + day;
        binding.birthdayText.setText(this.birthday);
    }

    @Override
    protected void onAddressChange(String province, String city) {
        this.address = province + city;
        binding.addressText.setText(this.address);
    }

    private void signIn() {
        if (binding.etPhone.getText() == null || binding.etPhone.getText().length() != 11) {
            binding.phoneContainer.setError("输入的手机号码有误");
            return;
        }
        binding.phoneContainer.setError(null);

        if (binding.ageEdit.getText() == null || binding.ageEdit.getText().length() == 0) {
            binding.ageContainer.setError("请输入年龄");
            return;
        }
        binding.ageContainer.setError(null);
        int ageInt = Integer.parseInt(binding.ageEdit.getText().toString());

        if (binding.genderTextView.getText() == null) {
            binding.genderContainer.setError("请选择性别");
            return;
        }

        String gender = binding.genderTextView.getText() == null ? "" : binding.genderTextView.getText().toString();
        if (!"男".equals(gender) && !"女".equals(gender)) {
            binding.genderContainer.setError("请输入正确的性别");
            return;
        }
        int genderInt = gender.equals("男") ? 0 : 1;
        binding.genderContainer.setError(null);

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

        String phone = binding.etPhone.getText().toString();
        String password = binding.etPassword.getText().toString();
        String headImage = null;
        String name = binding.userNameTextView.getText() == null ? null : binding.userNameTextView.getText().toString();
        String nickName = binding.etNickname.getText() == null ? null : binding.etNickname.getText().toString();
        User user = new User(phone, password, headImage, name, nickName, birthday, address, ageInt, genderInt);
        user.school = binding.etNickname.getText() == null ? null : binding.etNickname.getText().toString();
        vm.register(user);
    }
}