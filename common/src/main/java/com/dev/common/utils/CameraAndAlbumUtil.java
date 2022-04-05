package com.dev.common.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.dev.common.BaseApplication;
import com.dev.common.R;

import java.io.File;
import java.io.IOException;

/**
 * @author long.guo
 * @since 1/25/21
 */
public class CameraAndAlbumUtil {
    public static final int OPEN_ALBUM = 0;
    public static final int OPEN_CAMERA = 1;
    public static final int OPEN_CROP = 2;

    @Nullable
    public static Uri openCamera(Activity activity, String name) {
        Uri uri = null;
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = null;
        try {
            f = createPictureCacheFile(name);
            uri = getUri(f);
            if (cameraIntent.resolveActivity(activity.getBaseContext().getPackageManager()) != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                activity.startActivityForResult(cameraIntent, OPEN_CAMERA);
            } else {
                uri = null;
                Toast.makeText(activity, R.string.common_camera_not_found, Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uri;
    }

    public static void openAlbum(Activity activity) {
        Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(albumIntent, OPEN_ALBUM);
    }

    private static Uri getUri(File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(BaseApplication.app, "com.dev.gradation.fileprovider", file);
        } else {
            return Uri.fromFile(file);
        }
    }

    private static File createPictureCacheFile(String fileName) throws IOException {
        File f = new File(BaseApplication.app.getCacheDir().getAbsolutePath(), "cache_pic");
        if (!f.exists()) {
            f.mkdirs();
        }
        File fp = new File(f, fileName);
        if (!fp.exists()) {
            fp.createNewFile();
        }
        return fp;
    }


    /**
     * 开启裁剪相片
     */
    public static File startPhotoCrop(Activity activity, Uri imgUri, String cropName) {
        //创建file文件，用于存储剪裁后的照片
        File cropImageFile = null;
        try {
            cropImageFile = createPictureCacheFile(cropName);
            Uri cropImgUri = Uri.fromFile(cropImageFile);
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.setDataAndType(imgUri, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 50);
            intent.putExtra("outputY", 50);
            intent.putExtra("scale", true);
            intent.putExtra("scaleUpIfNeeded", true);
            //设置目的地址uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImgUri);
            //设置图片格式
            intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
            intent.putExtra("return-data", true);
            intent.putExtra("noFaceDetection", false);
            activity.startActivityForResult(intent, OPEN_CROP);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cropImageFile;
    }
}
