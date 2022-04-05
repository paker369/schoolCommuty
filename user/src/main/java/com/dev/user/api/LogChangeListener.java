package com.dev.user.api;

/**
 * @author long.guo
 * @since 1/21/21
 */
public interface LogChangeListener {
    void onLogin(LoginUser loginUser);

    void onLogout();
}
