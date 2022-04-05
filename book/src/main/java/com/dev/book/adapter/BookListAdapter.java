package com.dev.book.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dev.book.R;
import com.dev.book.databinding.ItemBookListBinding;
import com.dev.common.adapter.OnItemClickListener;
import com.dev.common.entry.BookBean;
import com.dev.common.utils.LayoutInflaterUtils;

import java.util.List;

/**
 * @author long.guo
 * @since 1/24/21
 */
public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookVH> {
    @Nullable
    private OnItemClickListener l;
    private List<BookBean> books;

    public void setBooks(List<BookBean> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    public BookListAdapter(@Nullable OnItemClickListener l) {
        this.l = l;
    }

    @NonNull
    @Override
    public BookVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookVH(LayoutInflaterUtils.inflate(parent, R.layout.item_book_list));
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

        ItemBookListBinding binding = ItemBookListBinding.bind(itemView);

        public BookVH(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                if (l != null)
                    l.onItemClick(v, getAdapterPosition());
            });
        }

        public void bind(BookBean bookBean) {
            Glide.with(itemView.getContext()).load(bookBean.getPoster()).into(binding.albumImage);
            binding.albumTitle.setText(bookBean.getBookName());
            binding.albumArtist.setText(bookBean.getBookDesc());
        }
    }
}
