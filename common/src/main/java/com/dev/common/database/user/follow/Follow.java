package com.dev.common.database.user.follow;

import androidx.room.Entity;
import androidx.room.Ignore;

/**
 * @author long.guo
 * @since 2/7/21
 */
@Entity(primaryKeys = {"owner", "follower"})
public class Follow {
    public long owner;//被关注id
    public long follower;//关注者id

    public Follow() {

    }

    @Ignore
    public Follow(long owner, long follower) {
        this.owner = owner;
        this.follower = follower;
    }
}
