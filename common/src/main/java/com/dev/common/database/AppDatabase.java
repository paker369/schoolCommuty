package com.dev.common.database;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dev.common.BaseApplication;
import com.dev.common.database.comment.Comment;
import com.dev.common.database.comment.CommentDao;
import com.dev.common.database.dynamic.Dynamic;
import com.dev.common.database.dynamic.DynamicDao;
import com.dev.common.database.favorite.Favorite;
import com.dev.common.database.favorite.FavoriteDao;
import com.dev.common.database.like.Like;
import com.dev.common.database.like.LikeDao;
import com.dev.common.database.news.News;
import com.dev.common.database.news.NewsDao;
import com.dev.common.database.question.Question;
import com.dev.common.database.question.QuestionDao;
import com.dev.common.database.recommend.BookRecommend;
import com.dev.common.database.recommend.BookRecommendDao;
import com.dev.common.database.recommend.PublishBook;
import com.dev.common.database.recommend.PublishBookDao;
import com.dev.common.database.user.User;
import com.dev.common.database.user.UserDao;
import com.dev.common.database.user.follow.Follow;
import com.dev.common.database.user.follow.FollowDao;

/**
 * @author long.guo
 * @since 1/29/21
 */
@Database(
        entities = {User.class, Dynamic.class, BookRecommend.class, Comment.class, Favorite.class, News.class, Like.class, PublishBook.class, Follow.class, Question.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract DynamicDao dynamicDao();

    public abstract BookRecommendDao bookRecommendDao();

    public abstract LikeDao likeDao();

    public abstract NewsDao newsDao();

    public abstract CommentDao commentDao();

    public abstract FavoriteDao favoriteDao();

    public abstract PublishBookDao publishBookDao();

    public abstract FollowDao followDao();

    public abstract QuestionDao questionDao();

    private static AppDatabase instance;

    @NonNull
    public static AppDatabase getInstance() {
        if (instance == null) {
            instance = Room.databaseBuilder(BaseApplication.app, AppDatabase.class, "AppDatabase.db")
                    .allowMainThreadQueries().build();
        }
        return instance;
    }

}
