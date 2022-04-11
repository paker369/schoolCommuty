package com.dev.base.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.dev.common.base.BaseViewModel;
import com.dev.common.database.comment.Comment;
import com.dev.common.database.comment.CommentWithUser;
import com.dev.common.database.user.User;

import java.util.ArrayList;
import java.util.List;

import static com.dev.common.database.DaoProvider.commentDao;
import static com.dev.common.database.DaoProvider.userDao;

/**
 * @author long.guo
 * @since 2/6/21
 */
public class CommentViewModel extends BaseViewModel {
    public MutableLiveData<List<CommentWithUser>> comments = new MutableLiveData<>();

    public void addComment(Comment comment) {
        long id = commentDao().insert(comment);
    }

    public void loadAllComment(long ownerId, int commentType) {
        List<Comment> comments = commentDao().getCommentByOwnerIdAndType(ownerId);
        List<CommentWithUser> result = new ArrayList<>();
        for (Comment comment : comments) {
            User user = userDao().findById(comment.userCreatorId);
            result.add(new CommentWithUser(comment, user));
        }
        this.comments.setValue(result);
    }
}
