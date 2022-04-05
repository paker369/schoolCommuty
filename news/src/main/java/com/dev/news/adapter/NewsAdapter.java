package com.dev.news.adapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dev.common.base.BaseAdapter;
import com.dev.common.database.comment.Comment;
import com.dev.common.database.like.Like;
import com.dev.common.database.news.News;
import com.dev.common.database.news.NewsWithCommentAndLike;
import com.dev.common.utils.DateUtil;
import com.dev.common.utils.LayoutInflaterUtils;
import com.dev.news.R;
import com.dev.news.databinding.ItemNewsBinding;

import java.util.List;

/**
 * @author long.guo
 * @since 1/31/21
 */
public class NewsAdapter extends BaseAdapter<NewsWithCommentAndLike, NewsAdapter.NewsVH> {

    @NonNull
    @Override
    public NewsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewsVH vh = new NewsVH(LayoutInflaterUtils.inflate(parent, R.layout.item_news));
        vh.itemView.setOnClickListener(v -> {
            onItemClickListener.onItemClick(v, vh.getAdapterPosition());
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsVH holder, int position) {
        holder.bind(data.get(position));
    }

    static class NewsVH extends RecyclerView.ViewHolder {

        private final ItemNewsBinding binding = ItemNewsBinding.bind(itemView);

        public NewsVH(@NonNull View itemView) {
            super(itemView);
        }

        @SuppressLint("DefaultLocale")
        public void bind(NewsWithCommentAndLike newsWithCommentAndLike) {
            News news = newsWithCommentAndLike.news;
            List<Comment> comments = newsWithCommentAndLike.comments;
            List<Like> likes = newsWithCommentAndLike.likes;
            Glide.with(itemView.getContext()).load(news.image).into(binding.imageView);
            binding.title.setText(news.title);
            binding.tool.setText(String.format("%d条评论，%d条点赞   %s", comments.size(), likes.size(), DateUtil.toTime(news.createTime)));
        }
    }
}
