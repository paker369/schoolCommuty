package com.dev.common.database.news;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.dev.common.database.comment.Comment;
import com.dev.common.database.like.Like;

import java.util.List;

/**
 * @author long.guo
 * @since 1/31/21
 */
public class NewsWithCommentAndLike {
    @Embedded
    public News news;
    @Relation(
            parentColumn = "id",
            entityColumn = "ownerId"
    )
    public List<Comment> comments;

    @Relation(
            parentColumn = "id",
            entityColumn = "ownerId"
    )
    public List<Like> likes;
}
