package com.dev.user.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dev.common.base.Resource;
import com.dev.common.database.user.User;

import static com.dev.common.database.DaoProvider.userDao;

public class LoginViewModel extends ViewModel {

    public static final int NO_USER = 1;
    public static final int PASSWORD_ERROR = 2;

    public MutableLiveData<Resource<User>> login = new MutableLiveData<>();

    public void login(String phone, String password, boolean isAdmin) {
        User user = userDao().findByPhone(phone);
        if (user == null) {
            login.setValue(Resource.newInstance(NO_USER, "用户不存在"));
        } else {
            if (user.isAdmin && !isAdmin) {
                login.setValue(Resource.newInstance(NO_USER, "用户不存在"));
                return;
            }
            if (user.password.equals(password)) {
                login.setValue(Resource.newInstance(user));
            } else {
                login.setValue(Resource.newInstance(PASSWORD_ERROR, "密码输入错误"));
            }
        }
    }

    public void loginByName(String nickName, String password, boolean isAdmin) {
        User userBean = userDao().findByNickname(nickName);
        if (userBean == null) {
            login.setValue(Resource.newInstance(NO_USER, "用户不存在"));
        } else {
            if (userBean.isAdmin && !isAdmin) {
                login.setValue(Resource.newInstance(NO_USER, "用户不存在"));
                return;
            }
            if (userBean.password.equals(password)) {
                login.setValue(Resource.newInstance(userBean));
            } else {
                login.setValue(Resource.newInstance(PASSWORD_ERROR, "密码输入错误"));
            }
        }
    }

    public void loginDataChanged(String username, String password) {
    }
}