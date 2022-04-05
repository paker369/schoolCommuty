package com.dev.user.profile;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.dev.common.constant.ARouterConstant;
import com.dev.common.database.user.User;
import com.dev.common.utils.ActivityRouter;
import com.dev.user.R;
import com.dev.user.UserSession;
import com.dev.user.base.UserSettingBaseActivity;
import com.dev.user.databinding.ActivityUserProfileBinding;

import static com.dev.common.database.DaoProvider.userDao;

@Route(path = ARouterConstant.Users.userProfileActivity)
public class UserProfileActivity extends UserSettingBaseActivity<UserProfileViewModel> {

    private ActivityUserProfileBinding binding;
    private User user = UserSession.getInstance().user();
    @Autowired(name = "userId")
    long userId = -1;

    @Override
    protected void initViewModel() {
        if (userId != -1) {
            user = userDao().findById(userId);
        }
        vm = new ViewModelProvider(this).get(UserProfileViewModel.class);
    }

    @Override
    protected View initBinding() {
        binding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {
        setupToolbar(binding.toolbar);
        setupUserProfile();
        binding.userHeadContainer.setOnClickListener(v -> {
            showChooseUserHeadDialog();
        });
        binding.userNameContainer.setOnClickListener(v -> {

        });
        binding.userNicknameContainer.setOnClickListener(v -> {

        });
        binding.userGenderContainer.setOnClickListener(v -> {
            showChooseSexDialog();
        });
        binding.userBirthdayContainer.setOnClickListener(v -> {
            showChooseBirthDialog();
        });
        binding.userAddressContainer.setOnClickListener(v -> {
            showChooseAddressDialog();
        });
        binding.userPhoneContainer.setOnClickListener(v -> {
        });
        binding.logoutBtn.setOnClickListener(v -> {
            vm.logout(this);
            onBackPressed();
        });
        binding.userResetPswContainer.setOnClickListener(v -> {
            ActivityRouter.gotoResetPasswordActivity();
        });
    }

    @Override
    protected void initObserver() {

    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void onBirthdayChanged(int year, int month, int day) {
        user.birthday = String.format("%d-%d-%d", year, month, day);
        vm.updateUser(user);
        binding.birthdayText.setText(user.birthday == null ? toResString(R.string.no_settle) : user.birthday);
    }

    @Override
    protected void onAddressChange(String province, String city) {
        user.address = province + city + city;
        vm.updateUser(user);
        binding.addressText.setText(user.address == null ? toResString(R.string.no_settle) : user.address);
    }

    @Override
    protected void onHeadChange(String filePath) {
        user.headImage = filePath;
        vm.updateUser(user);
        int holder = user.gender == 0 ? R.drawable.ic_user_head_body : R.drawable.ic_user_head_girl;
        Glide.with(this).load(user.headImage).error(holder).into(binding.userHeadImage);
    }

    @Override
    protected void onGenderChange(int position, String name) {
        user.gender = position == 0 ? 0 : 1;
        vm.updateUser(user);
        binding.genderText.setText(user.gender == 0 ? toResString(R.string.male) : toResString(R.string.female));
    }

    private void setupUserProfile() {
        int holder = user.gender == 0 ? R.drawable.ic_user_head_body : R.drawable.ic_user_head_girl;
        Glide.with(this).load(user.headImage).error(holder).into(binding.userHeadImage);
        binding.userNameText.setText(user.name);
        binding.nicknameText.setText(user.nickName);
        binding.genderText.setText(user.gender == 0 ? toResString(R.string.male) : toResString(R.string.female));
        binding.birthdayText.setText(user.birthday == null ? toResString(R.string.no_settle) : user.birthday);
        binding.addressText.setText(user.address == null ? toResString(R.string.no_settle) : user.address);
        binding.phoneText.setText(user.phone);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        user.name = binding.userNameText.getText() == null ? "" : binding.userNameText.getText().toString();
        user.nickName = binding.nicknameText.getText() == null ? "" : binding.nicknameText.getText().toString();
        user.school = binding.schoolText.getText() == null ? "" : binding.schoolText.getText().toString();
        userDao().update(user);
        if (userId == -1) {
            UserSession.refreshLogin();
        }
    }
}