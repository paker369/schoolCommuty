package com.dev.common.database.recommend;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @author long.guo
 * @since 1/30/21
 */
@Entity(tableName = "book_recommend")
public class BookRecommend {
    @PrimaryKey
    public Long id = null;
    public String bookRootPath;

    public BookRecommend() {
    }

    @Ignore
    public BookRecommend(String bookRootPath) {
        this.bookRootPath = bookRootPath;
    }

    @Override
    public String toString() {
        return "BookRecommend{" +
                "id=" + id +
                ", bookRootPath='" + bookRootPath + '\'' +
                '}';
    }
}
