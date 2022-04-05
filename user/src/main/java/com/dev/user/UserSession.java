package com.dev.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dev.common.database.user.User;
import com.dev.user.api.IUser;
import com.dev.user.api.LogChangeListener;
import com.dev.user.api.LoginUser;
import com.dev.user.api.LogoutUser;

import java.util.HashSet;
import java.util.Set;

import static com.dev.common.database.DaoProvider.userDao;

/**
 * @author long.guo
 * @since 1/21/21
 */
public class UserSession implements IUser {
    private static UserSession userSession = new UserSession();
    private IUser iUser = new LogoutUser();
    private static final Set<LogChangeListener> observers = new HashSet<>();

    public static UserSession getInstance() {
        return userSession;
    }

    private UserSession() {
    }

    public static void login(LoginUser iUser) {
        userSession.iUser = iUser;
        for (LogChangeListener observer : observers) {
            observer.onLogin((LoginUser) userSession.iUser);
        }
    }

    public static void login(User user) {
        userSession.iUser = new LoginUser(user);
        for (LogChangeListener observer : observers) {
            observer.onLogin((LoginUser) userSession.iUser);
        }
    }

    public static void logout() {
        userSession.iUser = new LogoutUser();
        for (LogChangeListener observer : observers) {
            observer.onLogout();
        }
    }

    public static void refreshLogin() {
        User user = userDao().findById(userSession.id());
        if (user == null) return;
        login(user);
    }

    public static boolean isLogin() {
        return userSession.iUser instanceof LoginUser;
    }

    public static void addLogListener(LogChangeListener l) {
        observers.add(l);
    }

    public static void removeLogListener(LogChangeListener l) {
        observers.remove(l);
    }

    @Override
    public long id() {
        return iUser.id();
    }

    @NonNull
    @Override
    public String nickName() {
        return iUser.nickName();
    }

    @Override
    @Nullable
    public String userName() {
        return iUser.userName();
    }

    @Override
    @NonNull
    public String phone() {
        return iUser.phone();
    }

    @Override
    @NonNull
    public String password() {
        return iUser.password();
    }

    @Override
    public String headImage() {
        return iUser.headImage();
    }

    @Override
    public int age() {
        return iUser.age();
    }

    @Override
    public int gender() {
        return iUser.gender();
    }

    @Nullable
    @Override
    public String address() {
        return iUser.address();
    }

    @Override
    public boolean isAdmin() {
        return iUser.isAdmin();
    }

    @Nullable
    @Override
    public User user() {
        return iUser.user();
    }
}
