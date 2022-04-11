package com.dev.main.insert;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.dev.common.base.BaseActivity;
import com.dev.common.constant.ARouterConstant;
import com.dev.common.database.dynamic.Dynamic;
import com.dev.common.database.question.Question;
import com.dev.common.utils.SpUtil;
import com.dev.main.R;
import com.dev.main.databinding.ActivityPublishBinding;
import com.dev.user.UserSession;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;
import com.suke.widget.SwitchButton;

import static com.dev.common.database.DaoProvider.dynamicDao;
import static com.dev.common.database.DaoProvider.questionDao;

@Route(path = ARouterConstant.Main.publishActivity)
public class PublishActivity extends BaseActivity<InsertViewModel> {

    private ActivityPublishBinding binding;
    private String attachment;
    String permission = Manifest.permission.READ_EXTERNAL_STORAGE;

    @Autowired(name = "dynamicType")
    String dynamicType = "动态";
    private boolean show;

    @Override
    protected void initWindow() {
        super.initWindow();
        // Set up shared element transition
        findViewById(android.R.id.content).setTransitionName("shared_element_end_root");
        setEnterSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
    }

    @Override
    protected void initViewModel() {
        vm = new ViewModelProvider(this).get(InsertViewModel.class);
    }

    @Override
    protected View initBinding() {
        binding = ActivityPublishBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
    private boolean isTop=false;
    @Override
    protected void setupViews() {
        setupTypes();
        setupToolbar(binding.toolbar);
        if( UserSession.getInstance().isAdmin()){
            binding.switch1.setVisibility(View.VISIBLE);
            binding.shifou.setVisibility(View.VISIBLE);
            binding.switch2.setVisibility(View.VISIBLE);
            binding.gonggao.setVisibility(View.VISIBLE);
        }else {
            binding.switch1.setVisibility(View.GONE);
            binding.shifou.setVisibility(View.GONE);
            binding.switch2.setVisibility(View.GONE);
            binding.gonggao.setVisibility(View.GONE);
        }
        binding.switch1.setChecked(false);
        binding. switch1.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
              isTop=isChecked;
            }
        });
        binding.switch2.setChecked(false);
        binding. switch2.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                show=isChecked;
            }
        });
        binding.btnPublish.setOnClickListener(v -> {
            if (binding.title.getText() == null || binding.title.getText().length() == 0) {
                Toast.makeText(this, "标题不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (binding.content.getText() == null || binding.content.getText().length() == 0) {
                Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dynamicType.equals("提问")) {
                Question q = new Question(binding.title.getText().toString(), binding.content.getText().toString(), UserSession.getInstance().id());
                questionDao().insert(q);
            } else {
                Dynamic bean = new Dynamic(
                        UserSession.getInstance().id(),
                        binding.title.getText().toString(),
                        binding.content.getText().toString(),
                        attachment,isTop?0:1,UserSession.getInstance().isAdmin()
                );
                bean.dynamicType = this.dynamicType;
                dynamicDao().insert(bean);
            }
            Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            if(show){
                SpUtil.getInstance().save(SpUtil.GONGGAO, binding.content.getText().toString());
            }
            finish();
        });

        binding.contentContainer.setOnClickListener(v -> {
            showSoftKeyboard(binding.content);
        });

        binding.attachment.setOnClickListener(v -> {
            // 打开文件，选择，申请权限
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                // 5.0无需权限
                openFile();
            } else {
                // 申请权限
                if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{permission}, 1024);
                } else {
                    openFile();
                }
            }
        });

        binding.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio0) {
                dynamicType = "动态";
            } else if (checkedId == R.id.radio1) {
                dynamicType = "书籍";
            } else if (checkedId == R.id.radio2) {
                dynamicType = "期刊";
            } else if (checkedId == R.id.radio3) {
                dynamicType = "电影资讯";
            }
        });
    }

    private void setupTypes() {
        if (dynamicType.equals("提问")) {
            binding.radioGroup.setVisibility(View.GONE);
            binding.attachment.setVisibility(View.GONE);
            binding.title.setHint("请输入问题标题");
            binding.content.setHint("请输入问题描述");
        } else if (dynamicType.equals("书籍分享")) {
            binding.radioGroup.setVisibility(View.GONE);
            binding.title.setHint("请输入书籍名称");
            binding.content.setHint("请输入书籍分享介绍");
        }else if (dynamicType.equals("闲置")) {
            binding.radioGroup.setVisibility(View.GONE);
            binding.title.setHint("请输入闲置名称");
            binding.content.setHint("请输入闲置详情介绍");
        }else if (dynamicType.equals("其他")) {
            binding.radioGroup.setVisibility(View.GONE);
            binding.title.setHint("请输入你想要的标题");
            binding.content.setHint("请输入你想要的介绍");
        }
    }

    @Override
    protected void initObserver() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(199);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1024) {
            if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                openFile();
            } else {
                Toast.makeText(this, "应用缺少必要的权限，请在设置中打开", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1025) {
            handleImageBeforeKitKat(data);
        }
    }

    private void openFile() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1025);
    }

    private void handleImageBeforeKitKat(@Nullable Intent data) {
        if (data == null) return;
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
        Log.i(mTag, "imagePath:" + imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            attachment = imagePath;
            binding.image.setVisibility(View.VISIBLE);
            Glide.with(this).load(imagePath).into(binding.image);
        } else {
            Toast.makeText(this, "获取图片失败", Toast.LENGTH_SHORT).show();
        }
    }
}