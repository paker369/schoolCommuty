package com.dev.common.database.recommend;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @author long.guo
 * @since 2/6/21
 */
@Entity(tableName = "book_publish")
public class PublishBook {
    @PrimaryKey
    public Long id;
    public String json;

    public PublishBook() {

    }

    @Ignore
    public PublishBook(String json) {
        this.json = json;
    }
}
