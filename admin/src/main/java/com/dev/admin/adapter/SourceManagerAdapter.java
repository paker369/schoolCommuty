package com.dev.admin.adapter;

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
import com.dev.main.databinding.ItemDynamic1Binding;
import com.dev.main.databinding.ItemDynamicBinding;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.dev.common.database.DaoProvider.userDao;

/**
 * @author long.guo
 * @since 1/24/21
 */
public class SourceManagerAdapter extends RecyclerView.Adapter<SourceManagerAdapter.DynamicVH> {

    public OnItemClickListener onItemClickListener;
    private List<Dynamic> dynamics;
    private final static Map<Long, User> map = new TreeMap<>();
    public DynamicClickListener l;

    public void setData(List<Dynamic> list) {
        this.dynamics = list;
        notifyDataSetChanged();
    }
    public void setListener(DynamicClickListener l) {
        this.l = l;
    }
    @NonNull
    @Override
    public DynamicVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DynamicVH vh = new DynamicVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dynamic1, parent, false));
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

     class DynamicVH extends RecyclerView.ViewHolder {

        ItemDynamic1Binding binding = ItemDynamic1Binding.bind(itemView);

        public DynamicVH(@NonNull View itemView) {
            super(itemView);
            binding.tvTop.setOnClickListener(v -> l.updateClick(dynamics.get(getAdapterPosition())));
            binding.tvDelete.setOnClickListener(v -> l.deleteClick(dynamics.get(getAdapterPosition())));
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
            binding.tvTop.setText(dynamic.top==0?"????????????":"??????");
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

    public interface DynamicClickListener {


        void updateClick(Dynamic  dynamic);

        void deleteClick(Dynamic  dynamic);
    }
}
