package com.dev.common.database.question;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.dev.common.database.comment.Comment;

import java.util.List;

/**
 * @author long.guo
 * @since 2/8/21
 */
public class QuestionWithComment {
    @Embedded
    public Question question;
    @Relation(
            parentColumn = "id",
            entityColumn = "ownerId"
    )
    public List<Comment> comments;
}
