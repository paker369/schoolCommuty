package com.dev.base;

import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.dev.base.adapter.CommentAdapter;
import com.dev.base.databinding.FragmentCommentBottomSheetBinding;
import com.dev.base.viewmodel.CommentViewModel;
import com.dev.common.base.AutoHeightBottomSheetDialogFragment;
import com.dev.common.database.comment.Comment;
import com.dev.common.database.comment.CommentWithUser;
import com.dev.user.UserSession;

/**
 * @author long.guo
 * @since 2/6/21
 */
public abstract class BaseCommentBottomSheetFragment<vm extends CommentViewModel> extends AutoHeightBottomSheetDialogFragment<vm> {

    protected FragmentCommentBottomSheetBinding binding;

    private final CommentAdapter adapter = new CommentAdapter();
    public OnDismiss onDismiss;

    protected String targetTitle;
    protected String targetContent;

    @Override
    protected View initBinding() {
        binding = FragmentCommentBottomSheetBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupViews() {
        vm.loadAllComment(ownerId(), commentType());
        binding.send.setOnClickListener(v -> {
            if (binding.editText.getText() == null || binding.editText.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "请输入内容", Toast.LENGTH_SHORT).show();
                return;
            }
            String content = binding.editText.getText().toString();
            Comment comment = new Comment(UserSession.getInstance().id(), content, ownerId(), commentType(),targetTitle,targetContent);
            vm.addComment(comment);
            binding.editText.setText("");
            adapter.getData().add(new CommentWithUser(comment, UserSession.getInstance().user()));
            adapter.notifyItemInserted(adapter.getData().size() - 1);
        });
        int holder = UserSession.getInstance().gender() == 0 ? R.drawable.ic_user_head_body : R.drawable.ic_user_head_girl;
        Glide.with(requireActivity()).load(UserSession.getInstance().headImage()).error(holder).into(binding.head);
    }

    @Override
    protected void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(adapter);
    }

    protected abstract long ownerId();

    protected abstract int commentType();

    @Override
    protected void initObserver() {
        vm.comments.observe(this, adapter::setData);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismiss != null) {
            onDismiss.onDismiss();
        }
    }

    public interface OnDismiss {
        void onDismiss();
    }
}
