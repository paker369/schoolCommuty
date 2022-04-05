package com.dev.main.ui.dynamic;

import com.dev.base.BaseCommentBottomSheetFragment;
import com.dev.base.viewmodel.CommentViewModel;
import com.dev.common.database.comment.Comment;

/**
 * @author long.guo
 * @since 2/6/21
 */
public class DynamicCommentBottomSheetFragment extends BaseCommentBottomSheetFragment<CommentViewModel> {

    private long ownerId;

    public static DynamicCommentBottomSheetFragment newInstance(long newsId, String title, String content) {
        DynamicCommentBottomSheetFragment fragment = new DynamicCommentBottomSheetFragment();
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
        return Comment.DynamicType;
    }
}
