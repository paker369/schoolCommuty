package com.dev.question.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.common.base.BaseAdapter;
import com.dev.common.database.comment.Comment;
import com.dev.common.utils.LayoutInflaterUtils;
import com.dev.question.R;
import com.dev.question.databinding.ItemQuestionListBinding;

/**
 * @author long.guo
 * @since 2/21/21
 */
public class CommentListAdapter extends BaseAdapter<Comment, CommentListAdapter.CommentVH> {
    @NonNull
    @Override
    public CommentListAdapter.CommentVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentListAdapter.CommentVH(LayoutInflaterUtils.inflate(parent, R.layout.item_question_list));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentListAdapter.CommentVH holder, int position) {
        holder.bind(data.get(position));
    }

    static class CommentVH extends RecyclerView.ViewHolder {
        ItemQuestionListBinding binding = ItemQuestionListBinding.bind(itemView);

        public CommentVH(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Comment comment) {
            binding.title.setText(comment.targetTitle);
            binding.subtitle.setText(comment.content);
        }
    }
}
