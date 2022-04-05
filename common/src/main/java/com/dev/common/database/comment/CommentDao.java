package com.dev.common.database.comment;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * @author long.guo
 * @since 1/31/21
 */
@Dao
public interface CommentDao {
    @Query("select * from comment")
    List<Comment> findAll();

    @Query("select count(*) from comment where ownerId = :ownerId and ownerType = :ownerType")
    int getCountByOwnerIdAndType(long ownerId, int ownerType);

    @Insert
    long insert(Comment comment);

    @Query("select * from comment where ownerId = :ownerId and ownerType = :ownerType order by createTime")
    List<Comment> getCommentByOwnerIdAndType(long ownerId, int ownerType);

    /**
     * 去重查询user 的所有评论对应的ownerId和ownerType
     *
     * @return ownerId, ownerType
     */
    @Query("select distinct ownerId,ownerType from comment where userCreatorId = :userCreatorId")
    List<OwnerIdAndType> getCommentByUserCreatorId(long userCreatorId);
}
