package com.dev.base.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dev.base.R;
import com.dev.common.base.BaseAdapter;
import com.dev.common.database.comment.Comment;
import com.dev.common.database.comment.CommentWithUser;
import com.dev.common.database.user.User;
import com.dev.common.databinding.ItemCommentBinding;
import com.dev.common.utils.LayoutInflaterUtils;

/**
 * @author long.guo
 * @since 1/31/21
 */
public class CommentAdapter extends BaseAdapter<CommentWithUser, CommentAdapter.CommentVH> {

    @NonNull
    @Override
    public CommentVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CommentVH vh = new CommentVH(LayoutInflaterUtils.inflate(parent, R.layout.item_comment));
        vh.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(v, vh.getAdapterPosition());
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentVH holder, int position) {
        holder.bind(data.get(position));
    }

    static class CommentVH extends RecyclerView.ViewHolder {

        private final ItemCommentBinding binding = ItemCommentBinding.bind(itemView);

        public CommentVH(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(CommentWithUser commentWithUser) {
            Comment comment = commentWithUser.comment;
            User user = commentWithUser.user;

            int holder = user.gender == 0 ? R.drawable.ic_user_head_body : R.drawable.ic_user_head_girl;
            Glide.with(itemView.getContext()).load(user.headImage).error(holder).into(binding.head);
            binding.nickname.setText(user.nickName);
            binding.tvComment.setText(comment.content);
        }
    }
}
