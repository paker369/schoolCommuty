package com.dev.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.dev.common.BaseApplication;

import org.jetbrains.annotations.Contract;

import java.security.Key;

/**
 * @author long.guo
 * @since 1/23/21
 */
public class SpUtil {

    private static SpUtil spUtil;

    public static final String KEY_PHONE_ADMIN = "key_phone_admin";
    public static final String KEY_USER_NAME_ADMIN = "key_user_name_admin";
    public static final String KEY_PASSWORD_ADMIN = "key_password_admin";
    public static final String KEY_CHECKED_ADMIN = "key_checked_admin";

    public static final String KEY_PHONE = "key_phone";
    public static final String KEY_PASSWORD = "key_password";
    public static final String KEY_CHECKED = "key_checked";

    public static final String KEY_FIRST_COMMAND = "key_first_command";

    @Contract(pure = true)
    private SpUtil() {

    }

    public static SpUtil getInstance() {
        if (spUtil == null) {
            spUtil = new SpUtil();
        }
        return spUtil;
    }

    SharedPreferences sp = BaseApplication.app.getSharedPreferences("sp_common", Context.MODE_PRIVATE);

    public void save(String key, String value) {
        sp.edit().putString(key, value).apply();
    }

    public String getString(String key) {
        return sp.getString(key, "");
    }

    public void save(String key, boolean value) {
        sp.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key) {
        return sp.getBoolean(key, false);
    }
}
