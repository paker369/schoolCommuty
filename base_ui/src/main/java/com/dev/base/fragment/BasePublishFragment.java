package com.dev.base.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModel;

import com.dev.common.base.BaseFragment;
import com.dev.common.utils.PermissionUtil;

/**
 * @author long.guo
 * @since 2/6/21
 */
public abstract class BasePublishFragment<T extends ViewModel> extends BaseFragment<T> {
    protected String attachment;
    String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
    protected int requestCode;

    protected void attachment() {
        // 打开文件，选择，申请权限
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // 5.0无需权限
            openFile();
        } else {
            // 申请权限
            if (ActivityCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                PermissionUtil.checkPermission(requireActivity(), new String[]{permission}, new PermissionUtil.Callback2() {
                    @Override
                    public void onFail() {
                        Toast.makeText(requireContext(), "应用缺少必要的权限，请在设置中打开", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onGranted() {
                        openFile();
                    }
                });
            } else {
                openFile();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCode) {
            handleImageBeforeKitKat(data, requestCode);
        }
    }

    private void openFile() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, requestCode);
    }

    private void handleImageBeforeKitKat(@Nullable Intent data, int requestCode) {
        if (data == null) return;
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath, requestCode);
        Log.i(mTag, "imagePath:" + imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实图片路径
        Cursor cursor = requireActivity().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath, int requestCode) {
        if (imagePath != null) {
            attachment = imagePath;
            onAttachmentSuccess(imagePath, requestCode);
        } else {
            Toast.makeText(requireActivity(), "获取图片失败", Toast.LENGTH_SHORT).show();
        }
    }

    public abstract void onAttachmentSuccess(String imagePath, int requestCode);
}
