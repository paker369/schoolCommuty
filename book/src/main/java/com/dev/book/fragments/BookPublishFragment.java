package com.dev.book.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.czt.mp3recorder.MP3Recorder;
import com.dev.base.fragment.BasePublishFragment;
import com.dev.book.databinding.FragmentBookPublishBinding;
import com.dev.book.viewmodel.BookPublishViewModel;
import com.dev.common.BaseApplication;
import com.dev.common.entry.BookBean;
import com.dev.common.utils.PermissionUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author long.guo
 * @since 2/6/21
 */
public class BookPublishFragment extends BasePublishFragment<BookPublishViewModel> {
    private FragmentBookPublishBinding binding;
    public BookBean.PageBean pageBean = new BookBean.PageBean();

    private final String fileName = System.currentTimeMillis() + ".mp3";
    private final String path = BaseApplication.app.getExternalCacheDir() + File.separator + "record";
    private MP3Recorder mp3Recorder;

    public static BookPublishFragment newInstance(int number) {
        BookPublishFragment f = new BookPublishFragment();
        f.requestCode = number;
        return f;
    }

    @Override
    protected View initBinding() {
        binding = FragmentBookPublishBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void setupViews() {
        binding.recordTextView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                startRecord();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                stopRecord();
            }
            return false;
        });

        binding.etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                pageBean.setText(s.toString());
            }
        });

        binding.imageView.setOnClickListener(v -> attachment());

        if (mp3Recorder == null) {
            mp3Recorder = new MP3Recorder(new File(path, fileName));
        }
    }

    @Override
    protected void initObserver() {

    }

    private void startRecord() {
        PermissionUtil.checkPermission(requireActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, new PermissionUtil.Callback2() {
            @Override
            public void onFail() {
                Toast.makeText(requireActivity(), "需要录音权限", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onGranted() {
                Toast.makeText(requireContext(), "开始录音", Toast.LENGTH_SHORT).show();
                try {
                    mp3Recorder.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void stopRecord() {
        Toast.makeText(requireContext(), "停止录音", Toast.LENGTH_SHORT).show();
        mp3Recorder.stop();
        pageBean.setMp3(path + File.separator + fileName);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!TextUtils.isEmpty(pageBean.getImage()))
            Glide.with(this).load(pageBean.getImage()).into(binding.imageView);
    }

    @Override
    public void onAttachmentSuccess(String imagePath, int requestCode) {
        if (requestCode == this.requestCode) {
            pageBean.setImage(imagePath);
            Glide.with(this).load(imagePath).into(binding.imageView);
        }
    }
}
