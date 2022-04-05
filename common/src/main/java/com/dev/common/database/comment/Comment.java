package com.dev.common.database.comment;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @author long.guo
 * @since 1/31/21
 */
@Entity
public class Comment {

    @Ignore
    public static final int NEWS_TYPE = 1;//新闻
    public static final int DynamicType = 2;//动态
    public static final int QuestionType = 3;//提问

    @PrimaryKey
    public Long id = null;
    public long userCreatorId;
    public long createTime;
    public String content;
    public long ownerId;//新闻 or 动态 等的id
    public int ownerType;//新闻 or 动态的类型

    public String targetTitle;
    public String targetContent;

    public Comment() {
    }

    @Ignore
    public Comment(long userCreatorId, String content, long ownerId, int ownerType, String targetTitle, String targetContent) {
        this.userCreatorId = userCreatorId;
        this.createTime = System.currentTimeMillis();
        this.content = content;
        this.ownerId = ownerId;
        this.ownerType = ownerType;
        this.targetTitle = targetTitle;
        this.targetContent = targetContent;
    }
}
