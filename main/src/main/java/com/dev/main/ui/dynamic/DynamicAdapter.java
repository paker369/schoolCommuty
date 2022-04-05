package com.dev.main.ui.dynamic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dev.common.adapter.OnItemClickListener;
import com.dev.common.database.dynamic.Dynamic;
import com.dev.common.database.user.User;
import com.dev.common.utils.DateUtil;
import com.dev.main.R;
import com.dev.main.databinding.ItemDynamicBinding;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.dev.common.database.DaoProvider.userDao;

/**
 * @author long.guo
 * @since 1/24/21
 */
public class DynamicAdapter extends RecyclerView.Adapter<DynamicAdapter.DynamicVH> {

    OnItemClickListener onItemClickListener;
    private List<Dynamic> dynamics;
    private final static Map<Long, User> map = new TreeMap<>();

    public void setData(List<Dynamic> list) {
        this.dynamics = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DynamicVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DynamicVH vh = new DynamicVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dynamic, parent, false));
        vh.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(v, vh.getAdapterPosition());
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull DynamicVH holder, int position) {
        holder.bind(dynamics.get(position));
    }

    @Override
    public int getItemCount() {
        return dynamics == null ? 0 : dynamics.size();
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        map.clear();
    }

    static class DynamicVH extends RecyclerView.ViewHolder {

        ItemDynamicBinding binding = ItemDynamicBinding.bind(itemView);

        public DynamicVH(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Dynamic dynamic) {
            long userId = dynamic.userId;
            if (!map.containsKey(userId)) {
                User user = userDao().findById(userId);
                if (user == null) return;
                map.put(userId, user);
            }
            User bean = map.get(userId);
            if (bean == null) {
                return;
            }

            int holder = bean.gender == 0 ? R.drawable.ic_user_head_body : R.drawable.ic_user_head_girl;
            Glide.with(itemView.getContext()).load(bean.headImage).error(holder).into(binding.headImage);
            binding.userName.setText(bean.nickName);
            binding.title.setText(dynamic.title);
            binding.content.setText(dynamic.content);

            if (dynamic.attachment != null) {
                binding.attachment.setVisibility(View.VISIBLE);
                Glide.with(itemView.getContext()).load(dynamic.attachment).into(binding.attachment);
            } else {
                binding.attachment.setVisibility(View.GONE);
            }
            binding.time.setText(DateUtil.toTime(dynamic.createTime));
            binding.type.setText(dynamic.dynamicType);
        }
    }
}
