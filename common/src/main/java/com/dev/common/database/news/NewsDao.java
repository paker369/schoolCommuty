package com.dev.common.database.news;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

/**
 * @author long.guo
 * @since 1/31/21
 */
@Dao
public interface NewsDao {

    /**
     * 所有新闻及其对应的所有评论
     */
    @Transaction
    @Query("select * from news")
    List<NewsWithComment> getNewsWithComments();

    @Transaction
    @Query("select * from news order by createTime desc")
    List<NewsWithCommentAndLike> getNewsWithCommentsAndLike();

    /**
     * 分类获取新闻及其对应的所有评论
     *
     * @param category 分类
     */
    @Transaction
    @Query("select * from news where category = :category")
    List<NewsWithComment> getNewsWithCommentsByCategory(String category);

    @Transaction
    @Query("select * from news where category = :category order by createTime desc")
    List<NewsWithCommentAndLike> getNewsWithCommentsAndLikeByCategory(String category);

    @Query("select * from news where id = :id")
    News getNewsById(long id);

    @Query("select distinct category from news")
    List<String> getNewsCategory();

    @Insert
    long insert(News news);
}
