package com.dev.common.database;

import com.dev.common.database.comment.CommentDao;
import com.dev.common.database.dynamic.DynamicDao;
import com.dev.common.database.favorite.FavoriteDao;
import com.dev.common.database.like.LikeDao;
import com.dev.common.database.news.NewsDao;
import com.dev.common.database.question.QuestionDao;
import com.dev.common.database.recommend.BookRecommendDao;
import com.dev.common.database.recommend.PublishBookDao;
import com.dev.common.database.user.UserDao;
import com.dev.common.database.user.follow.FollowDao;

/**
 * @author long.guo
 * @since 1/30/21
 */
public class DaoProvider {

    public static UserDao userDao() {
        return AppDatabase.getInstance().userDao();
    }

    public static DynamicDao dynamicDao() {
        return AppDatabase.getInstance().dynamicDao();
    }

    public static BookRecommendDao bookRecommendDao() {
        return AppDatabase.getInstance().bookRecommendDao();
    }

    public static LikeDao likeDao() {
        return AppDatabase.getInstance().likeDao();
    }

    public static NewsDao newsDao() {
        return AppDatabase.getInstance().newsDao();
    }

    public static CommentDao commentDao() {
        return AppDatabase.getInstance().commentDao();
    }

    public static FavoriteDao favoriteDao() {
        return AppDatabase.getInstance().favoriteDao();
    }

    public static PublishBookDao publishBookDao() {
        return AppDatabase.getInstance().publishBookDao();
    }

    public static FollowDao followDao() {
        return AppDatabase.getInstance().followDao();
    }

    public static QuestionDao questionDao() {
        return AppDatabase.getInstance().questionDao();
    }
}
