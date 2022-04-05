package com.dev.user.signin;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * @author long.guo
 * @since 1/22/21
 */
public class SignInViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SignInViewModel.class)) {
            return (T) new SignInViewModel();
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
