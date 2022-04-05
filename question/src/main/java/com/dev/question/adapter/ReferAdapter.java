package com.dev.question.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.common.base.BaseAdapter;
import com.dev.common.utils.LayoutInflaterUtils;
import com.dev.question.R;
import com.dev.question.databinding.ItemReferListBinding;

/**
 * @author long.guo
 * @since 2/21/21
 */
public class ReferAdapter extends BaseAdapter<String, ReferAdapter.ReferVH> {

    @NonNull
    @Override
    public ReferVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReferVH(LayoutInflaterUtils.inflate(parent, R.layout.item_refer_list));
    }

    @Override
    public void onBindViewHolder(@NonNull ReferVH holder, int position) {
        holder.bind(data.get(position));
    }

    static class ReferVH extends RecyclerView.ViewHolder {

        private final ItemReferListBinding binding = ItemReferListBinding.bind(itemView);

        public ReferVH(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(String s) {
            binding.subtitle.setText(s);
        }
    }
}
