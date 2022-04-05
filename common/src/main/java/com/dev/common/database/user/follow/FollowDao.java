package com.dev.common.database.user.follow;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * @author long.guo
 * @since 2/7/21
 */
@Dao
public interface FollowDao {
    @Query("select * from follow where owner = :ownerId")
    List<Follow> findByOwnerId(long ownerId);

    @Query("select * from follow where follower = :followerId")
    List<Follow> findByFollowerId(long followerId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Follow follow);

    @Delete
    void delete(Follow follow);

    @Query("select * from follow where owner = :ownerId and follower = :followerId")
    Follow findById(long ownerId, long followerId);
}
