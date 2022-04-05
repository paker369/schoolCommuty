package com.dev.base.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModel;

import com.dev.common.base.BaseActivity;

/**
 * @author long.guo
 * @since 1/31/21
 */
public abstract class BasePublishActivity<T extends ViewModel> extends BaseActivity<T> {
    protected String attachment;
    String permission = Manifest.permission.READ_EXTERNAL_STORAGE;

    protected void attachment() {
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
            onAttachmentSuccess(imagePath);
        } else {
            Toast.makeText(this, "获取图片失败", Toast.LENGTH_SHORT).show();
        }
    }

    public abstract void onAttachmentSuccess(String imagePath);
}
