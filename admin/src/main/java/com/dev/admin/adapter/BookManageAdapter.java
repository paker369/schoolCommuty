package com.dev.admin.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dev.admin.R;
import com.dev.admin.databinding.ItemBookManageBinding;
import com.dev.common.adapter.OnItemClickListener;
import com.dev.common.database.recommend.BookRecommend;
import com.dev.common.entry.BookBean;
import com.dev.common.utils.LayoutInflaterUtils;

import java.util.List;

import static com.dev.common.database.DaoProvider.bookRecommendDao;

/**
 * @author long.guo
 * @since 1/24/21
 */
public class BookManageAdapter extends RecyclerView.Adapter<BookManageAdapter.BookVH> {

    @Nullable
    private final OnItemClickListener l;
    private List<BookBean> books;

    public void setBooks(List<BookBean> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    public BookManageAdapter(@Nullable OnItemClickListener l) {
        this.l = l;
    }

    @NonNull
    @Override
    public BookVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookVH(LayoutInflaterUtils.inflate(parent, R.layout.item_book_manage));
    }

    @Override
    public void onBindViewHolder(@NonNull BookVH holder, int position) {
        holder.bind(books.get(position));
    }

    @Override
    public int getItemCount() {
        return books == null ? 0 : books.size();
    }

    class BookVH extends RecyclerView.ViewHolder {

        ItemBookManageBinding binding = ItemBookManageBinding.bind(itemView);

        public BookVH(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                if (l != null)
                    l.onItemClick(v, getAdapterPosition());
                binding.checkbox.setChecked(!binding.checkbox.isChecked());
            });

            binding.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                BookBean bean = books.get(getAdapterPosition());
                if (isChecked) {
                    bookRecommendDao().insert(new BookRecommend(bean.getRootPath()));
                } else {
                    bookRecommendDao().deleteByPath(bean.getRootPath());
                }
            });
        }

        public void bind(BookBean bookBean) {
            Glide.with(itemView.getContext()).load(bookBean.getPoster()).into(binding.albumImage);
            binding.albumTitle.setText(bookBean.getBookName());
            binding.albumArtist.setText(bookBean.getBookDesc());
            BookRecommend recommend = bookRecommendDao().findByPath(bookBean.getRootPath());
            binding.checkbox.setChecked(recommend != null);
        }
    }
}
