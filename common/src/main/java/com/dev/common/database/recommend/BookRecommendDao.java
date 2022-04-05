package com.dev.common.database.recommend;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * @author long.guo
 * @since 1/30/21
 */
@Dao
public interface BookRecommendDao {
    @Query("select * from book_recommend")
    List<BookRecommend> findAll();

    @Query("select * from book_recommend where bookRootPath = :path limit 1")
    BookRecommend findByPath(String path);

    @Query("select * from book_recommend where bookRootPath = :path")
    long deleteByPath(String path);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(BookRecommend bookRecommend);
}
