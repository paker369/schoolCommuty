package com.dev.user.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dev.common.database.user.User;

/**
 * @author long.guo
 * @since 1/21/21
 */
public interface IUser {
    long id();

    @NonNull
    String nickName();

    @Nullable
    String userName();

    @NonNull
    String phone();

    @NonNull
    String password();

    @Nullable
    String headImage();

    int age();

    int gender();

    @Nullable
    String address();

    boolean isAdmin();

    @Nullable
    User user();
}
