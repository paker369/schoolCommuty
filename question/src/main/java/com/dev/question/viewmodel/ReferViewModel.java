package com.dev.question.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.dev.common.base.BaseViewModel;
import com.dev.common.database.comment.Comment;
import com.dev.common.database.dynamic.Dynamic;
import com.dev.common.database.like.Like;
import com.dev.common.database.user.User;
import com.dev.common.utils.DateUtil;
import com.dev.user.UserSession;

import java.util.ArrayList;
import java.util.List;

import static com.dev.common.database.DaoProvider.commentDao;
import static com.dev.common.database.DaoProvider.dynamicDao;
import static com.dev.common.database.DaoProvider.likeDao;
import static com.dev.common.database.DaoProvider.userDao;

/**
 * @author long.guo
 * @since 2/21/21
 */
public class ReferViewModel extends BaseViewModel {
    public MutableLiveData<List<String>> refers = new MutableLiveData<>();

    public void loadRefer() {
        List<String> str = new ArrayList<>();
        List<Dynamic> dynamics = dynamicDao().findAllByUserId(UserSession.getInstance().id());
        for (Dynamic dynamic : dynamics) {
            List<Comment> comments = commentDao().getCommentByOwnerIdAndType(dynamic.id);
            List<Like> likes = likeDao().getLikeByOwnerIdAndType(dynamic.id, Comment.DynamicType);
            for (Comment comment : comments) {
                User user = userDao().findById(comment.userCreatorId);
                str.add("用户" + user.nickName + " 在 " + DateUtil.toTime(comment.createTime) + "时评论了  \"" + comment.content + "\"");
            }
            for (Like like : likes) {
                User user = userDao().findById(like.userCreatorId);
                str.add("用户" + user.nickName + " 在 " + DateUtil.toTime(like.createTime) + "时喜欢了  \"" + dynamic.content + "\"");
            }
        }
        refers.setValue(str);
    }
}
