package com.dev.common.database.question;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @author long.guo
 * @since 2/8/21
 */
@Entity
public class Question {
    @PrimaryKey
    public Long id;
    public String title;
    public String content;
    public long creatorUserId;

    public Question() {

    }

    @Ignore
    public Question(String title, String content, long creatorUserId) {
        this.title = title;
        this.content = content;
        this.creatorUserId = creatorUserId;
    }
}
