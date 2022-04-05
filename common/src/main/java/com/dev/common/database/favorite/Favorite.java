package com.dev.common.database.favorite;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * 收藏
 *
 * @author long.guo
 * @since 1/21/21
 */
@Entity
public class Favorite {
    @PrimaryKey
    public Long id = null;
    public long createTime;
    public long userCreatorId;
    public long ownerId;
    public int ownerType;

    public Favorite() {
    }

    @Ignore
    public Favorite(long userCreatorId, long ownerId, int ownerType) {
        this.userCreatorId = userCreatorId;
        this.ownerId = ownerId;
        this.ownerType = ownerType;
        this.createTime = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", userCreatorId=" + userCreatorId +
                ", ownerId=" + ownerId +
                ", ownerType=" + ownerType +
                '}';
    }
}
