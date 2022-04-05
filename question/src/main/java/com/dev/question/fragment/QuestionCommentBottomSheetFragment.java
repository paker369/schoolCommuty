package com.dev.question.fragment;

import com.dev.base.BaseCommentBottomSheetFragment;
import com.dev.common.database.comment.Comment;
import com.dev.question.viewmodel.QuestionCommentViewModel;

/**
 * @author long.guo
 * @since 2/21/21
 */
public class QuestionCommentBottomSheetFragment extends BaseCommentBottomSheetFragment<QuestionCommentViewModel> {

    private long ownerId;

    public static QuestionCommentBottomSheetFragment newInstance(long newsId, String title, String content) {
        QuestionCommentBottomSheetFragment fragment = new QuestionCommentBottomSheetFragment();
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
        return Comment.QuestionType;
    }
}
