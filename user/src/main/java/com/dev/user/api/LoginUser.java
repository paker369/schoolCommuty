package com.dev.user.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dev.common.database.user.User;

import org.jetbrains.annotations.NotNull;

/**
 * @author long.guo
 * @since 1/21/21
 */
public class LoginUser implements IUser {
    private final User user;

    public LoginUser(User user) {
        this.user = user;
    }

    @Override
    public long id() {
        return user.id;
    }

    @Override
    public @androidx.annotation.NonNull
    String nickName() {
        return user.nickName;
    }


    @Override
    public @androidx.annotation.NonNull
    String userName() {
        return user.name;
    }

    @Override
    public @NonNull
    @NotNull
    String phone() {
        return user.phone;
    }

    @Override
    public @NonNull
    @NotNull
    String password() {
        return user.password;
    }

    @Override
    public String headImage() {
        return user.headImage;
    }

    @Override
    public int age() {
        return user.age;
    }

    @Override
    public int gender() {
        return user.gender;
    }

    @Override
    public String address() {
        return user.address;
    }

    @Override
    public boolean isAdmin() {
        return user.isAdmin;
    }

    @Nullable
    @Override
    public User user() {
        return user;
    }
}
