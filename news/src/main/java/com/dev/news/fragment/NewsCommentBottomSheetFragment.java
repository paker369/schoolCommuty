package com.dev.news.fragment;

import com.dev.base.BaseCommentBottomSheetFragment;
import com.dev.base.viewmodel.CommentViewModel;
import com.dev.common.database.comment.Comment;
import com.dev.user.UserSession;

/**
 * @author long.guo
 * @since 1/31/21
 */
public class NewsCommentBottomSheetFragment extends BaseCommentBottomSheetFragment<CommentViewModel> {

    private long ownerId;

    public static NewsCommentBottomSheetFragment newInstance(long newsId, String title, String content) {
        NewsCommentBottomSheetFragment fragment = new NewsCommentBottomSheetFragment();
        fragment.ownerId = newsId;
        fragment.targetTitle = title;
        fragment.targetContent = content;
        return fragment;
    }

    @Override
    protected long ownerId() {
        return ownerId;
    }

    @Override
    protected int commentType() {
        return Comment.NEWS_TYPE;
    }
}
