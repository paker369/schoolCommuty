package com.dev.common.base;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dev.common.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author long.guo
 * @since 1/31/21
 */
public abstract class AutoHeightBottomSheetDialogFragment<T extends ViewModel> extends BottomSheetDialogFragment {
    protected T vm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
        ARouter.getInstance().inject(this);
    }

    // 默认不开启自适应
    private boolean autoHeightEnable = false;

    // 最大高度
    private int maxHeight = 0;

    // 可以修改高度的View
    private View autoHeightView = null;
    private int originalHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    private boolean useOriginalLp = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initBinding();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
        setupViewPager();
        setupRecyclerView();
        initObserver();
        if (autoHeightEnable) {
            autoHeightView.post(this::autoHeight);
        }
    }

    protected void initViewModel() {
        ParameterizedType superType = (ParameterizedType) getClass().getGenericSuperclass();
        Type[] types = superType.getActualTypeArguments();
        Type type = types[0];
        vm = new ViewModelProvider(this).get((Class<T>) type);
    }

    protected abstract View initBinding();

    protected abstract void setupViews();

    protected void setupRecyclerView() {

    }

    protected void setupViewPager() {

    }

    private BottomSheetBehavior<View> bottomSheetBehavior;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            View bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
            bottomSheet.post(() -> {
                ViewGroup.LayoutParams lp = bottomSheet.getLayoutParams();
                if (lp instanceof CoordinatorLayout.LayoutParams) {
                    bottomSheetBehavior = (BottomSheetBehavior<View>) ((CoordinatorLayout.LayoutParams) lp).getBehavior();
                    bottomSheetBehavior.setPeekHeight(bottomSheet.getHeight());
                }
            });
        }
        final View view = getView();
        view.post(() -> {
        });
    }

    protected abstract void initObserver();


    protected String toResString(int resId) {
        return getString(resId);
    }

    protected int toResColor(int resId) {
        return ContextCompat.getColor(requireContext(), resId);
    }

    /**
     * 调用该方法已，开启自动使用高度
     *
     * @param autoHeightView 可以调整高度的View，往往是RecyclerView
     * @param maxHeight      最大高度
     */
    public void openAutoHeight(View autoHeightView, int maxHeight) {
        this.autoHeightView = autoHeightView;
        this.maxHeight = maxHeight;

        if (autoHeightView.getLayoutParams() != null) {
            this.originalHeight = autoHeightView.getLayoutParams().height;
        } else {
            throw new NullPointerException("you should set LayoutParams first.");
        }
        this.autoHeightEnable = true;
        this.autoHeightView.post(this::autoHeight);
    }

    private void autoHeight() {
        if (maxHeight <= 0 || autoHeightView == null) return;
        autoHeightView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom - top == oldBottom - oldTop) return;
                if (useOriginalLp) {
                    adjustHeight();
                } else {
                    setViewHeight(autoHeightView, originalHeight);
                    useOriginalLp = true;
                }
            }
        });
    }

    private void adjustHeight() {
        if (autoHeightView.getHeight() > maxHeight) {
            setViewHeight(autoHeightView, maxHeight);
            useOriginalLp = false;
        }
    }

    private void setViewHeight(View view, int height) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.height = height;
        view.setLayoutParams(lp);
    }
}
