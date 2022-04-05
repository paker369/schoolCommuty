package com.dev.user.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dev.common.database.user.User;

/**
 * @author long.guo
 * @since 1/21/21
 */
public class LogoutUser implements IUser {
    @Override
    public long id() {
        return 0;
    }

    @Override
    @NonNull
    public String nickName() {
        return "";
    }

    @Override
    @Nullable
    public String address() {
        return null;
    }

    @Override
    @Nullable
    public String userName() {
        return null;
    }

    @Override
    @NonNull
    public String phone() {
        return "";
    }

    @Override
    @NonNull
    public String password() {
        return "";
    }

    @Override
    @Nullable
    public String headImage() {
        return null;
    }

    @Override
    public int age() {
        return 0;
    }

    @Override
    public int gender() {
        return 0;
    }

    @Override
    public boolean isAdmin() {
        return false;
    }

    @Nullable
    @Override
    public User user() {
        return null;
    }
}
