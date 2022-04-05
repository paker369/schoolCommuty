package com.dev.common.database.recommend;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * @author long.guo
 * @since 2/6/21
 */
@Dao
public interface PublishBookDao {
    @Query("select * from book_publish")
    List<PublishBook> findAll();

    @Insert
    void insert(PublishBook publishBook);
}
