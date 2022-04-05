package com.dev.common.utils;

import android.database.sqlite.SQLiteDatabase;

import com.dev.common.BaseApplication;
import com.dev.common.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 地址数据管理
 *
 * @author long.guo
 * @since 1/26/21
 */
public class AddressDBManage {

    public static final String DB_MANE = "city.s3db";
    private static String dbFilePath = BaseApplication.app.getFilesDir().getAbsolutePath() + File.separator + DB_MANE;

    private static AddressDBManage instance;

    private AddressDBManage() {
    }

    public static AddressDBManage getInstance() {
        if (instance == null) {
            instance = new AddressDBManage();
        }
        return instance;
    }

    /**
     * 打开地址数据库
     *
     * @return SQLiteDatabase?
     */
    public SQLiteDatabase openDatabase() {
        try {
            File dbFile = new File(dbFilePath);
            //文件复制
            if (!dbFile.exists()) {
                InputStream ip = BaseApplication.app.getResources().openRawResource(R.raw.city);
                FileOutputStream op = new FileOutputStream(dbFile);
                byte[] buf = new byte[1024];
                int count = 0;
                while ((count = ip.read(buf)) > 0) {
                    op.write(buf, 0, count);
                    op.flush();
                }
                op.close();
                ip.close();
            }
            return SQLiteDatabase.openOrCreateDatabase(dbFilePath, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
