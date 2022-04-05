package com.dev.admin.fragments;

import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dev.admin.R;
import com.dev.admin.activity.UserEditActivity;
import com.dev.admin.adapter.UserManageAdapter;
import com.dev.admin.databinding.FragmentUserManageBinding;
import com.dev.admin.viewmodel.UserManageViewModel;
import com.dev.common.base.BaseFragment;
import com.dev.common.database.user.User;
import com.dev.common.utils.ActivityRouter;
import com.dev.common.utils.FragmentRouter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.dev.common.database.DaoProvider.userDao;

/**
 * @author long.guo
 * @since 1/23/21
 */
public class UserManageFragment extends BaseFragment<UserManageViewModel> {

    private FragmentUserManageBinding binding;
    private final UserManageAdapter adapter = new UserManageAdapter();

    public static UserManageFragment newInstance() {
        return new UserManageFragment();
    }

    @Override
    protected void initViewModel() {
        vm = new ViewModelProvider(this).get(UserManageViewModel.class);
    }

    @Override
    protected View initBinding() {
        binding = FragmentUserManageBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {
        adapter.setListener(new UserManageAdapter.DynamicClickListener() {
            @Override
            public void onClick(long userId) {
                Fragment f = FragmentRouter.dynamicFragment(userId);
                binding.fragmentParent.setVisibility(View.VISIBLE);
                getChildFragmentManager().beginTransaction().addToBackStack(null).add(R.id.fragmentContainer, f, "FRAGMENT_TAG").commitAllowingStateLoss();
            }

            @Override
            public void editClick(long userId, int position) {
//                Intent intent = new Intent(getActivity(), UserEditActivity.class);
//                intent.putExtra("userId", userId);
//                startActivityForResult(intent, 100);
                ActivityRouter.gotoUserProfileActivity(userId);
                getActivity().overridePendingTransition(0, 0);
            }

            @Override
            public void deleteClick(User user) {
                userDao().deleteUser(user);
                vm.loadUsers();
            }
        });

        binding.close.setOnClickListener(v -> {
            Fragment f = getChildFragmentManager().findFragmentByTag("FRAGMENT_TAG");
            binding.fragmentParent.setVisibility(View.GONE);
            if (f != null) {
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.remove(f).commitAllowingStateLoss();
            }
        });

        binding.searchView.setOnCloseListener(() -> {
            vm.users.setValue(vm.users_);
            return false;
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Gson gson = new Gson();
                List<User> list = new ArrayList<>();
                for (User user : vm.users_) {
                    if (gson.toJson(user).contains(newText)) {
                        list.add(user);
                    }
                }

                vm.users.setValue(list);
                return false;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        vm.loadUsers();
    }

    @Override
    protected void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initObserver() {
        vm.users.observe(this, adapter::setUsers);
    }
}
