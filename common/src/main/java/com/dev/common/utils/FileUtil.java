package com.dev.common.utils;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.dev.common.BaseApplication;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件操作工具类
 *
 * @author guolong
 * @since 2019/9/10
 */
public class FileUtil {
    /**
     * 读取文件内容为二进制数组
     */
    public static byte[] read(String filePath) throws IOException {
        InputStream in = new FileInputStream(filePath);
        byte[] data = inputStream2ByteArray(in);
        in.close();

        return data;
    }

    /**
     * 流转二进制数组
     */
    private static byte[] inputStream2ByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }

    public static String getRealPathFromURI(Uri uri) {
        String result;
        Cursor cursor = BaseApplication.app.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
        if (cursor == null) {
            result = uri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }
        return result;
    }
}

