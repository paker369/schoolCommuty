package com.dev.common.database.comment;

import com.dev.common.database.user.User;

/**
 * @author long.guo
 * @since 1/31/21
 */
public class CommentWithUser {
    public Comment comment;
    public User user;

    public CommentWithUser(){}
    public CommentWithUser(Comment comment, User user){
        this.comment = comment;
        this.user = user;
    }
}
