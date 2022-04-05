package com.dev.admin.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dev.common.database.user.User;

import java.util.ArrayList;
import java.util.List;

import static com.dev.common.database.DaoProvider.userDao;

/**
 * @author long.guo
 * @since 1/23/21
 */
public class UserManageViewModel extends ViewModel {
    public MutableLiveData<List<User>> users = new MutableLiveData<>();
    public List<User> users_ = new ArrayList<>();

    {
        loadUsers();
    }

    public void loadUsers() {
        users_ = userDao().findAll();
        users.setValue(users_);
    }
}
