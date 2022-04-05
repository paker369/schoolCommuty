package com.dev.common.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.core.app.ActivityOptionsCompat;

import com.dev.common.constant.ARouterConstant;
import com.dev.common.entry.BookBean;

/**
 * @author long.guo
 * @since 1/22/21
 */
public class ActivityRouter {

    public static final String KEY_LOGIN_IS_ADMIN = "key_login_type";

    public static void gotoLoginActivity(boolean isAdmin) {
        ARouterConstant.postcard(ARouterConstant.Users.loginActivity).withBoolean(KEY_LOGIN_IS_ADMIN, isAdmin).navigation();
    }

    public static void gotoRegisterActivity() {
        ARouterConstant.startActivity(ARouterConstant.Users.registerActivity);
    }

    public static void gotoMainActivity() {
        ARouterConstant.startActivity(ARouterConstant.Main.mainActivity);
    }

    public static void gotoLoginSelectActivity() {
        ARouterConstant.startActivity(ARouterConstant.Users.selectorActivity);
    }

//    public static void gotoLoginSelectActivity() {
//        ARouterConstant.startActivity(ARouterConstant.Users.selectorActivity);
//    }

    public static void gotoLoginSelectActivity(Activity context, int requestCode) {
        ARouterConstant.postcard(ARouterConstant.Users.selectorActivity).navigation(context, requestCode);
    }

    public static void gotoAdminActivity() {
        ARouterConstant.startActivity(ARouterConstant.Admin.adminActivity);
    }

    public static void gotoPublishActivity() {
        ARouterConstant.startActivity(ARouterConstant.Main.publishActivity);
    }

    public static void gotoPublishActivity(Activity activity, int requestCode, String dynamicType) {
        ARouterConstant.postcard(ARouterConstant.Main.publishActivity).withString("dynamicType", dynamicType).navigation(activity, requestCode);
    }

    public static void gotoPublishActivity(Activity activity, int requestCode, View sharedElement) {
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity, sharedElement, "shared_element_end_root");
        ARouterConstant.postcard(ARouterConstant.Main.publishActivity).withOptionsCompat(options).navigation(activity, requestCode);
    }

    public static void gotoReadingActivity(BookBean bookBean) {
        ARouterConstant.postcard(ARouterConstant.Book.bookReadingActivity).withSerializable("bookBean", bookBean).navigation();
    }

    public static void gotoUserProfileActivity() {
        ARouterConstant.startActivity(ARouterConstant.Users.userProfileActivity);
    }

    public static void gotoUserProfileActivity(long userId) {
        ARouterConstant.postcard(ARouterConstant.Users.userProfileActivity).withLong("userId", userId).navigation();
    }

    public static void gotoSettingActivity() {
        ARouterConstant.startActivity(ARouterConstant.Main.settingActivity);
    }

    public static void gotoDynamicActivity() {
        ARouterConstant.startActivity(ARouterConstant.Main.dynamicActivity);
    }

    public static void gotoNewsDetailActivity(Long id) {
        ARouterConstant.postcard(ARouterConstant.News.newsDetailActivity).withSerializable("ownerId", id).navigation();
    }

    public static void gotoNewsPublishActivity(Activity activity, int requestCode) {
        ARouterConstant.postcard(ARouterConstant.Publish.publishNewsActivity).navigation(activity, requestCode);
    }

    public static void gotoResetPasswordActivity() {
        ARouterConstant.startActivity(ARouterConstant.Users.resetPasswordActivity);
    }

    public static void gotoDynamicDetailActivity(long dynamicId) {
        ARouterConstant.postcard(ARouterConstant.Main.dynamicDetailActivity).withLong("ownerId", dynamicId).navigation();
    }

    public static void gotoBookPublishActivity() {
        ARouterConstant.startActivity(ARouterConstant.Book.bookPublishActivity);
    }

    public static void gotoPeopleActivity(long userId) {
        ARouterConstant.postcard(ARouterConstant.Users.peopleActivity).withLong("userId", userId).navigation();
    }

    public static void gotoQuestionDetailActivity(long questionId) {
        ARouterConstant.postcard(ARouterConstant.Question.questionListActivity).withLong("ownerId", questionId).navigation();
    }

    public static void gotoMyFavoriteActivity() {
        ARouterConstant.startActivity(ARouterConstant.Question.myFavoriteActivity);
    }
}
