package com.dev.common.base;

import androidx.recyclerview.widget.RecyclerView;

import com.dev.common.adapter.OnItemClickListener;

import java.util.List;

/**
 * @author long.guo
 * @since 1/27/21
 */
abstract public class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected OnItemClickListener onItemClickListener;
    protected List<T> data;

    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return data;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void addItem(T item) {
        if (data == null) return;
        int size = data.size();
        data.add(item);
        notifyItemChanged(size);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
