package com.dev.common.database.favorite;

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
public interface FavoriteDao {
    @Query("select * from favorite where userCreatorId = :userCreatorId and ownerId = :ownerId and ownerType = :ownerType")
    Favorite getFavorite(long userCreatorId, long ownerId, int ownerType);

    @Query("select * from favorite where userCreatorId = :userCreatorId and ownerType = :ownerType")
    List<Favorite> getFavorite(long userCreatorId, long ownerType);

    @Delete
    void delete(Favorite value);

    @Insert
    long insert(Favorite favorite);
}
