package com.dev.question.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.common.base.BaseAdapter;
import com.dev.common.database.question.Question;
import com.dev.common.utils.LayoutInflaterUtils;
import com.dev.question.R;
import com.dev.question.databinding.ItemCommentListBinding;

/**
 * @author long.guo
 * @since 2/20/21
 */
public class QuestionAdapter extends BaseAdapter<Question, QuestionAdapter.QuestionVH> {

    @NonNull
    @Override
    public QuestionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QuestionVH vh = new QuestionVH(LayoutInflaterUtils.inflate(parent, R.layout.item_question_list));
        vh.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(v, vh.getAdapterPosition());
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionVH holder, int position) {
        holder.bind(data.get(position));
    }

    static class QuestionVH extends RecyclerView.ViewHolder {
        ItemCommentListBinding binding = ItemCommentListBinding.bind(itemView);

        public QuestionVH(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Question question) {
            binding.title.setText(question.title);
            binding.subtitle.setText(question.content);
        }
    }
}
