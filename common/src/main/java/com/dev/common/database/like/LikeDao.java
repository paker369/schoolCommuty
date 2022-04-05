package com.dev.common.database.like;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * @author long.guo
 * @since 1/31/21
 */
@Dao
public interface LikeDao {
    @Query("select * from `like` where ownerId = :ownerId and ownerType = :ownerType")
    List<Like> like(long ownerId, int ownerType);

    @Insert
    long insert(Like like);

    @Delete
    void delete(Like like);

    @Query("select count(*) from `like` where ownerId = :ownerId and ownerType = :ownerType")
    int getCountByOwnerIdAndType(long ownerId, int ownerType);

    @Query("select * from `like` where userCreatorId = :userId and ownerId = :ownerId and ownerType = :ownerType")
    Like getMyLike(long userId, long ownerId, int ownerType);

    @Query("select * from `like` where ownerId = :ownerId and ownerType = :ownerType")
    List<Like> getLikeByOwnerIdAndType(long ownerId, int ownerType);

    @Query("select * from `like` where userCreatorId = :userId and ownerType = :ownerType")
    List<Like> getMyLike(long userId, long ownerType);
}
