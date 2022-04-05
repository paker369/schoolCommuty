package com.dev.main.ui.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dev.common.base.BaseFragment;
import com.dev.common.constant.ARouterConstant;
import com.dev.common.database.dynamic.Dynamic;
import com.dev.common.utils.ActivityRouter;
import com.dev.main.databinding.FragmentNotificationsBinding;
import com.dev.user.UserSession;
import com.dev.user.api.LogChangeListener;
import com.dev.user.api.LoginUser;

import static android.app.Activity.RESULT_OK;

@Route(path = ARouterConstant.Main.dynamicFragment)
public class DynamicFragment extends BaseFragment<DynamicViewModel> {
    private FragmentNotificationsBinding binding;
    private final DynamicAdapter adapter = new DynamicAdapter();
    private long userId = -1;
    private boolean flower;//是否是关注的动态

    private final LogChangeListener l = new LogChangeListener() {
        @Override
        public void onLogin(LoginUser loginUser) {
            logChanged(true);
        }

        @Override
        public void onLogout() {
            logChanged(false);
        }
    };

    @Override
    protected void initViewModel() {
        vm = new ViewModelProvider(this).get(DynamicViewModel.class);
    }

    @Override
    protected View initBinding() {
        binding = FragmentNotificationsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            userId = bundle.getLong("userId", -1);
            flower = bundle.getBoolean("flower", false);
        }

        UserSession.addLogListener(l);
        logChanged(UserSession.isLogin());

        if (userId != -1) {
            binding.addButton.setVisibility(View.GONE);
        }

        binding.goLoginGroup.setOnClickListener(v -> ActivityRouter.gotoLoginSelectActivity());
        binding.addButton.setOnClickListener(v -> {
            if (UserSession.isLogin()) {
                ActivityRouter.gotoPublishActivity(requireActivity(), 1000, "动态");
            } else {
                Toast.makeText(requireContext(), "请先登录", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(adapter);

        adapter.onItemClickListener = (view, position) -> {
            Dynamic dynamic = vm.dynamicList.getValue().get(position);
            ActivityRouter.gotoDynamicDetailActivity(dynamic.id);
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1000) {
            // refresh
            vm.loadDynamic(userId);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (flower) {
            vm.loadDynamicFlower(userId);
        } else {
            vm.loadDynamic(userId);
        }
    }

    @Override
    protected void initObserver() {
        vm.dynamicList.observe(this, dynamics -> {
            if (dynamics == null || dynamics.size() == 0) {
                binding.empty.setVisibility(View.VISIBLE);
            } else {
                binding.empty.setVisibility(View.GONE);
            }
            adapter.setData(dynamics);
        });
        vm.question.observe(this, questions -> {

        });
    }

    private void logChanged(boolean isLogin) {
        binding.goLoginGroup.setVisibility(View.GONE);
        if (isLogin) {
            if (userId == -1) {
//                binding.addButton.setVisibility(View.VISIBLE);
            }
//            binding.recyclerView.setVisibility(View.VISIBLE);
        } else {
//            binding.goLoginGroup.setVisibility(View.VISIBLE);
            binding.addButton.setVisibility(View.GONE);
//            binding.recyclerView.setVisibility(View.GONE);
        }
        vm.loadDynamic(userId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UserSession.removeLogListener(l);
        binding.recyclerView.setAdapter(null);
    }
}