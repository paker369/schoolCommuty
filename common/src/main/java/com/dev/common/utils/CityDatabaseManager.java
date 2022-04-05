package com.dev.common.utils;

import android.database.sqlite.SQLiteDatabase;

import com.dev.common.entry.CityBean;

import org.jetbrains.anko.db.DatabaseKt;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import kotlin.Pair;

/**
 * @author long.guo
 * @since 1/26/21
 */
public class CityDatabaseManager {
    /**
     * 查询省信息
     */
    public static List<CityBean> queryProvince() {
        SQLiteDatabase db = AddressDBManage.getInstance().openDatabase();
        ArrayList<CityBean> data = new ArrayList<>();
        if (db != null) {
            DatabaseKt.select(db, "province", "code", "name").exec(cursor -> {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    //名称
                    String name = null;
                    name = new String(cursor.getBlob(1), Charset.forName("GBK"));
                    //编码
                    String code = cursor.getString(0);
                    data.add(new CityBean(code, name));
                    cursor.moveToNext();
                }
                cursor.close();
                return data;
            });
        }
        return data;
    }

    /**
     * 查询市信息
     */
    public static List<CityBean> queryCity(String proviceCode) {
        SQLiteDatabase db = AddressDBManage.getInstance().openDatabase();
        ArrayList<CityBean> data = new ArrayList<>();
        if (db != null) {
            DatabaseKt.select(db, "city", "code", "name").whereArgs("pcode = {pcode}", new Pair<>("pcode", proviceCode)).exec(cursor -> {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    //名称
                    String name = new String(cursor.getBlob(1), Charset.forName("GBK"));
                    //编码
                    String code = cursor.getString(0);
                    data.add(new CityBean(code, name));
                    cursor.moveToNext();
                }
                cursor.close();
                return data;
            });
        }
        return data;
    }
}
