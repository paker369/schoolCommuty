package com.dev.main.ui.mine;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.dev.common.base.BaseFragment;
import com.dev.common.utils.ActivityRouter;
import com.dev.main.R;
import com.dev.main.databinding.FragmentMineBinding;
import com.dev.user.UserSession;
import com.dev.user.api.LogChangeListener;
import com.dev.user.api.LoginUser;

/**
 * @author long.guo
 * @since 1/23/21
 */
public class MineFragment extends BaseFragment<MineViewModel> {
    private FragmentMineBinding binding;

    private final LogChangeListener l = new LogChangeListener() {
        @Override
        public void onLogin(LoginUser loginUser) {
            setupUserProfile();
        }

        @Override
        public void onLogout() {
            binding.userProfileGroup.setVisibility(View.GONE);
            binding.adminGroup.setVisibility(View.GONE);
            Glide.with(MineFragment.this).load(R.drawable.ic_user_head_default).into(binding.ivUserHead);
            binding.tvLoginHint.setText(R.string.immediately_sign_in);
            binding.tvLoginHintDes.setText(R.string.sign_in_hint_des);
        }
    };

    @Override
    protected void initViewModel() {
        vm = new ViewModelProvider(this).get(MineViewModel.class);
    }

    @Override
    protected View initBinding() {
        binding = FragmentMineBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {
        binding.loginGroup.setOnClickListener(v -> {
            if (!UserSession.isLogin()) {
                ActivityRouter.gotoLoginSelectActivity();
            } else {
                ActivityRouter.gotoUserProfileActivity();
            }
        });

        if (UserSession.isLogin()) {
            setupUserProfile();
        }

        binding.adminTextView.setOnClickListener(v -> ActivityRouter.gotoAdminActivity());


        binding.dynamicContainer.setOnClickListener(v -> {
            ActivityRouter.gotoDynamicActivity();
        });

        binding.settingContainer.setOnClickListener(v -> {
            ActivityRouter.gotoSettingActivity();
        });

        binding.favoriteContainer.setOnClickListener(v -> {
            ActivityRouter.gotoMyFavoriteActivity();
        });
    }

    @Override
    protected void initObserver() {
        UserSession.addLogListener(l);
    }

    @SuppressLint("SetTextI18n")
    private void setupUserProfile() {
        UserSession session = UserSession.getInstance();
        if (session.isAdmin()) {
            binding.adminGroup.setVisibility(View.VISIBLE);
        } else {
            binding.adminGroup.setVisibility(View.GONE);
        }
        binding.userProfileGroup.setVisibility(View.VISIBLE);

        int holder = session.gender() == 0 ? R.drawable.ic_user_head_body : R.drawable.ic_user_head_girl;
        Glide.with(MineFragment.this).load(session.headImage()).error(holder).into(binding.ivUserHead);
        binding.tvLoginHint.setText(session.userName());
        binding.phoneValueTextView.setText(session.phone());
        binding.tvLoginHintDes.setText("用户昵称:" + session.nickName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UserSession.removeLogListener(l);
    }
}
