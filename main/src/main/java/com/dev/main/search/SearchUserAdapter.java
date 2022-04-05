package com.dev.main.search;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dev.common.base.BaseAdapter;
import com.dev.common.database.user.User;
import com.dev.common.utils.LayoutInflaterUtils;
import com.dev.main.R;
import com.dev.main.databinding.ItemSearchUserHeadBinding;

/**
 * @author long.guo
 * @since 1/27/21
 */
public class SearchUserAdapter extends BaseAdapter<User, SearchUserAdapter.UserHeadVH> {

    @NonNull
    @Override
    public UserHeadVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserHeadVH vh = new UserHeadVH(LayoutInflaterUtils.inflate(parent, R.layout.item_search_user_head));
        vh.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, vh.getAdapterPosition());
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull UserHeadVH holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class UserHeadVH extends RecyclerView.ViewHolder {

        private final ItemSearchUserHeadBinding binding = ItemSearchUserHeadBinding.bind(itemView);

        public UserHeadVH(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(User userBean) {
            int holder = userBean.gender == 0 ? R.drawable.ic_user_head_body : R.drawable.ic_user_head_girl;
            Glide.with(itemView.getContext()).load(userBean.headImage).error(holder).into(binding.headImage);
            binding.name.setText(userBean.nickName);
        }
    }
}
