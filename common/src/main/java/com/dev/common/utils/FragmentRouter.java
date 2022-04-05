package com.dev.common.utils;

import androidx.fragment.app.Fragment;

import com.dev.common.constant.ARouterConstant;

/**
 * @author long.guo
 * @since 1/24/21
 */
public class FragmentRouter {

    public static Fragment bookListFragment(int position) {
        return (Fragment) ARouterConstant.postcard(ARouterConstant.Book.bookList).withInt("position", position).navigation();
//        return ARouterConstant.loadFragment(ARouterConstant.Book.bookList);
    }

    public static Fragment dynamicFragment(long userId) {
        return (Fragment) ARouterConstant.postcard(ARouterConstant.Main.dynamicFragment).withLong("userId", userId).navigation();
    }

    public static Fragment dynamicFragment(long userId, boolean flower) {
        return (Fragment) ARouterConstant.postcard(ARouterConstant.Main.dynamicFragment).withLong("userId", userId).withBoolean("flower", flower).navigation();
    }

    public static Fragment newsFragment(String category) {
        return (Fragment) ARouterConstant.postcard(ARouterConstant.News.newsFragment).withString("category", category).navigation();
    }

    public static Fragment forumFragment(String category) {
        return (Fragment) ARouterConstant.postcard(ARouterConstant.Forum.forumFragment).withString("category", category).navigation();
    }

    public static Fragment questionFragment(long userId) {
        return (Fragment) ARouterConstant.postcard(ARouterConstant.Question.questionListFragment).withLong("userId", userId).navigation();
    }
}
