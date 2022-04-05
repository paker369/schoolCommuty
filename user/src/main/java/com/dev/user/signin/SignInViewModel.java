package com.dev.user.signin;

import androidx.lifecycle.MutableLiveData;

import com.dev.common.base.Resource;
import com.dev.common.database.user.User;
import com.dev.user.base.UserSettingViewModel;

import static com.dev.common.base.Resource.CODE_EXIST;
import static com.dev.common.database.DaoProvider.userDao;

/**
 * @author long.guo
 * @since 1/22/21
 */
public class SignInViewModel extends UserSettingViewModel {

    public MutableLiveData<Resource<User>> register = new MutableLiveData<>();

    public void register(User user) {
        User existUser = userDao().findByPhone(user.phone);
        if (existUser != null) {
            register.setValue(Resource.newInstance(CODE_EXIST, "该账号已被注册"));
            return;
        }
        user.id = userDao().insert(user);
        register.setValue(Resource.newInstance(user));
    }
}
