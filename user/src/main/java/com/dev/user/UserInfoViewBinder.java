package com.dev.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.common.utils.LayoutInflaterUtils;

import me.drakeet.multitype.ItemViewBinder;

/**
 * @author long.guo
 * @since 1/26/21
 */
public class UserInfoViewBinder extends ItemViewBinder<String, UserInfoViewBinder.ViewHolder> {
    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = LayoutInflaterUtils.inflate(parent, R.layout.item_single);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull String s) {
        viewHolder.itemTv.setText(s);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView itemTv = itemView.findViewById(R.id.item_tv);

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
