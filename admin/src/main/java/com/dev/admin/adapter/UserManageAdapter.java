package com.dev.admin.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dev.admin.R;
import com.dev.admin.databinding.ItemUserManageBinding;
import com.dev.common.database.user.User;

import java.util.List;

/**
 * @author long.guo
 * @since 1/23/21
 */
public class UserManageAdapter extends RecyclerView.Adapter<UserManageAdapter.UserManageVH> {

    private List<User> users;

    private DynamicClickListener l;

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    public void setListener(DynamicClickListener l) {
        this.l = l;
    }

    @NonNull
    @Override
    public UserManageVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserManageVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_manage, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserManageVH holder, int position) {
        User bean = users.get(position);

        holder.bind(bean);
    }

    @Override
    public int getItemCount() {
        return users == null ? 0 : users.size();
    }

    public class UserManageVH extends RecyclerView.ViewHolder {

        ItemUserManageBinding binding = ItemUserManageBinding.bind(itemView);

        public UserManageVH(@NonNull View itemView) {
            super(itemView);
            binding.viewDynamic.setOnClickListener(v -> l.onClick(users.get(getAdapterPosition()).id));
            binding.viewEdit.setOnClickListener(v -> l.editClick(users.get(getAdapterPosition()).id, getAdapterPosition()));
            binding.deleteEdit.setOnClickListener(v -> l.deleteClick(users.get(getAdapterPosition())));
        }

        @SuppressLint("DefaultLocale")
        public void bind(User user) {
            int holder = user.gender == 0 ? R.drawable.ic_user_head_body : R.drawable.ic_user_head_girl;
            Glide.with(itemView.getContext()).load(user.headImage).error(holder).into(binding.headImage);

            binding.userId.setText(String.format("用户id:%d", user.id));
            String gender = user.gender == 0 ? "男" : "女";
            binding.userName.setText(String.format("%s  %s  %d岁", user.nickName, gender, user.age));
            binding.account.setText(String.format("手机:%s, 密码:%s", user.phone, user.password));
        }
    }

    public interface DynamicClickListener {
        void onClick(long userId);

        void editClick(long userId, int position);

        void deleteClick(User user);
    }
}
