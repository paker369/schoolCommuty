package com.dev.common.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dev.common.R;

/**
 * @author long.guo
 * @since 1/25/21
 */
public class CustomDialog extends Dialog {
    public static final int wrapContent = ViewGroup.LayoutParams.WRAP_CONTENT;
    public static final int matchParent = ViewGroup.LayoutParams.MATCH_PARENT;

    private CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Build {
        private int style = R.style.dialog;
        private final int layout;
        private int width = wrapContent;
        private int height = wrapContent;
        private int gravity = Gravity.CENTER;
        private int anim = 0;
        @Nullable
        private UpdateUIListener listener;

        public Build(int layout) {
            this.layout = layout;
        }

        public Build style(int style) {
            this.style = style;
            return this;
        }

        public Build width(int width) {
            this.width = width;
            return this;
        }

        public Build height(int height) {
            this.height = height;
            return this;
        }

        public Build gravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Build anim(int anim) {
            this.anim = anim;
            return this;
        }

        public Build updateUIListener(UpdateUIListener listener) {
            this.listener = listener;
            return this;
        }

        public CustomDialog create(Context context) {
            CustomDialog dialog = new CustomDialog(context, style);
            dialog.setContentView(layout);
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.width = width;
            lp.height = height;
            lp.gravity = gravity;
            dialog.getWindow().setAttributes(lp);
            dialog.getWindow().setWindowAnimations(anim);
            if (listener != null) {
                listener.setupViews(dialog,dialog.getWindow().getDecorView());
            }
            return dialog;
        }
    }

    public interface UpdateUIListener {
        void setupViews(CustomDialog dialog, View view);
    }
}
