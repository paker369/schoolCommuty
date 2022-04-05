package com.dev.common.database.dynamic;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @author long.guo
 * @since 1/30/21
 */
@Entity
public class Dynamic {
    @PrimaryKey
    public Long id = null;
    public long userId;
    public String title;
    public String content;
    public String attachment;
    public long createTime;

    public String dynamicType = "动态";

    public Dynamic(long userId, String title, String content, String attachment) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.attachment = attachment;
        createTime = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "Dynamic{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", attachment='" + attachment + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
