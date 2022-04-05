package com.dev.common.database.news;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @author long.guo
 * @since 1/31/21
 */
@Entity
public class News {
    @PrimaryKey
    public Long id = null;
    public String title;
    public String content;
    public String image;
    public String category;
    public Long createTime;

//    @Ignore
//    public boolean isLike;
//    @Ignore
//    public int likeCount;
//    @Ignore
//    public int commentCount;

    public News() {

    }

    @Ignore
    public News(String title, String content, String image, String category) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.category = category;
        this.createTime = System.currentTimeMillis();
    }
}
