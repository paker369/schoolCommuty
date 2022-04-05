package com.dev.common.database.like;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @author long.guo
 * @since 1/31/21
 */
@Entity
public class Like {
    @PrimaryKey
    public Long id = null;
    public long userCreatorId;
    public long createTime;
    public long ownerId;//新闻 or 动态 等的id
    public int ownerType;//新闻 or 动态的类型

    public Like() {
    }

    @Ignore
    public Like(long userCreatorId, long ownerId, int ownerType) {
        this.userCreatorId = userCreatorId;
        this.ownerId = ownerId;
        this.ownerType = ownerType;
        this.createTime = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "Like{" +
                "id=" + id +
                ", userCreatorId=" + userCreatorId +
                ", createTime=" + createTime +
                ", ownerId=" + ownerId +
                ", ownerType=" + ownerType +
                '}';
    }
}
