package com.dev.common.constant;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author long.guo
 * @since 1/21/21
 */
public interface ARouterConstant {

    static void startActivity(String path) {
        ARouter.getInstance().build(path).navigation();
    }

    static Postcard postcard(String path) {
        return ARouter.getInstance().build(path).with(new Bundle());
    }

    static Fragment loadFragment(String path) {
        return (Fragment) ARouter.getInstance().build(path).navigation();
    }

    interface Users {
        String selectorActivity = "/user/selector";
        String loginActivity = "/user/login";
        String registerActivity = "/user/register";
        String userProfileActivity = "/user/profile";
        String resetPasswordActivity = "/user/resetPasswordActivity";
        String peopleActivity = "/user/peopleActivity";
    }

    interface Main {
        String mainActivity = "/main/main";
        String SplashActivity = "/main/main";
        String publishActivity = "/main/publish";
        String dynamicFragment = "/main/dynamicFragment";
        String settingActivity = "/main/settingsActivity";
        String dynamicActivity = "/main/dynamicActivity";
        String dynamicDetailActivity = "/main/dynamicDetailActivity";
    }

    interface Player {

    }

    interface Splash {
        String splashActivity = "/splash/splashActivity";
    }

    interface Admin {
        String adminActivity = "/admin/adminActivity";
    }

    interface Book {
        String bookList = "/book/list";
        String bookReadingActivity = "/book/reading";
        String bookPublishActivity = "/book/bookPublishActivity";
    }

    public interface News {
        String newsFragment = "/news/newsFragment";
        String newsDetailActivity = "/news/newsDetailActivity";
    }

    public interface Publish {
        String publishNewsActivity = "/publish/publishNewsActivity";
    }

    public interface Forum {
        String forumFragment = "/forum/forumFragment";
    }

    public interface Question {
        String questionListFragment = "/question/questionListFragment";
        String questionListActivity = "/question/questionListActivity";
        String myFavoriteActivity = "/question/myFavoriteActivity";
    }
}
