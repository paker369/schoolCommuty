package com.dev.user.profile;

import android.app.Activity;
import android.widget.Toast;

import com.dev.common.database.user.User;
import com.dev.user.UserSession;
import com.dev.user.base.UserSettingViewModel;

import static com.dev.common.database.DaoProvider.userDao;

/**
 * @author long.guo
 * @since 1/25/21
 */
public class UserProfileViewModel extends UserSettingViewModel {
    public void logout(Activity activity) {
        UserSession.logout();
        Toast.makeText(activity, "登出成功", Toast.LENGTH_SHORT).show();
    }

    public void updateUser(User user) {
        userDao().update(user);
        UserSession.login(user);
    }
}
