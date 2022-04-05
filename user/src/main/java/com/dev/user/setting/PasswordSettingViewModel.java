package com.dev.user.setting;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.dev.common.base.BaseViewModel;
import com.dev.common.database.user.User;
import com.dev.user.UserSession;

import static com.dev.common.database.DaoProvider.userDao;

/**
 * @author long.guo
 * @since 2/6/21
 */
public class PasswordSettingViewModel extends BaseViewModel {
    MutableLiveData<String> modifyMessage = new MutableLiveData<>();

    public void modifyPassword(String oldPassword, String newPassword) {
        User user = UserSession.getInstance().user();
        if (!TextUtils.equals(user.password, oldPassword)) {
            modifyMessage.setValue("密码不正确");
            return;
        }
        user.password = newPassword;
        userDao().update(user);
        modifyMessage.setValue("success");
    }
}
