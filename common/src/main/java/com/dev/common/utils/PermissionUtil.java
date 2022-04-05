package com.dev.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.lang.reflect.Field;

/**
 * @author long.guo
 * @since 1/25/21
 */
public class PermissionUtil {
    //权限被拒绝标记
    public static final int type_denid = 0;
    //权限点击了再也不问的标记
    public static final int type_denid_never_ask = 1;

    /**
     * activity中使用，可以关闭授权界面的权限申请
     *
     * @param activity           RxAppCompatActivity 当前的activity
     * @param permissions        Array<String> 权限数据数组
     * @param cancle             Boolean   是否可以人为关闭授权界面。默认可以关闭
     * @param finishSelf         Boolean 当app前往设置界面时，是否需要关闭当前的界面。默认可以关闭
     * @param permissionCallback Permission.GrantedSuccess 授权成功的回调
     */
    @SuppressLint("CheckResult")
    public static void getPermission(FragmentActivity activity, String[] permissions, Boolean cancle, Boolean finishSelf, GrantedSuccess permissionCallback) {
        RxPermissions p = new RxPermissions(activity);
        p.requestEachCombined(permissions).subscribe(permission -> {
            //已获得所有申请权限
            if (permission.granted) {
                permissionCallback.onGranted();
                Class<?> c = p.getClass();
                Field f = c.getDeclaredField("mRxPermissionsFragment");
                f.setAccessible(true);
                f.set(p, null);
            } else if (permission.shouldShowRequestPermissionRationale) {
                //至少一个权限未授予
                alert(activity);
            } else {
                //至少一个权限点击了再也不询问
                alert(activity);
            }
        }, error -> Log.i("guolong", error.getMessage(), error));
    }

    /**
     * fragment的权限请求
     *
     * @param fragment           RxFragment
     * @param permissions        Array<String>
     * @param cancle             Boolean
     * @param finishSelf         Boolean
     * @param permissionCallback Permission.GrantedSuccess
     */
    @SuppressLint("CheckResult")
    public static void getPermission(Fragment fragment, String[] permissions, Boolean cancle, Boolean finishSelf, GrantedSuccess permissionCallback) {
        RxPermissions p = new RxPermissions(fragment);
        p.requestEachCombined(permissions).subscribe(permission -> {
            //已获得所有申请权限
            if (permission.granted) {
                permissionCallback.onGranted();
                Class<?> c = p.getClass();
                Field f = c.getDeclaredField("mRxPermissionsFragment");
                f.setAccessible(true);
                f.set(p, null);
            } else if (permission.shouldShowRequestPermissionRationale) {
                //至少一个权限未授予
                alert(fragment.getContext());
            } else {
                //至少一个权限点击了再也不询问
                alert(fragment.getContext());
            }
        });
    }


    /**
     * activity中使用，可以关闭授权界面的权限申请
     */
    public static void checkPermission(FragmentActivity activity, String[] permissions, Callback2 permissionCallback) {
        RxPermissions p = new RxPermissions(activity);
        for (String permission : permissions) {
            //存在未授权情况,则应用退出
            if (!p.isGranted(permission)) {
                permissionCallback.onFail();
                return;
            }
        }
        permissionCallback.onGranted();
    }

    /**
     * 去app的权限设置界面
     */
    private static void goAppPermissionSetting(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        localIntent.setData(Uri.fromParts("package", "com.dev.gradation", null));
        context.startActivity(localIntent);
    }

    /**
     * 创建activity的权限alert
     *
     * @param type               Int 0代表重新拉取权限的alert字符串资源， 1代表去app设置界面的alert字符串资源
     * @param activity           RxAppCompatActivity
     * @param permissions        Array<String>
     * @param cancle             Boolean
     * @param finishSelf         Boolean
     * @param permissionCallback Permission.GrantedSuccess
     */
    private static void alert(int type, FragmentActivity activity,
                              String[] permissions,
                              Boolean cancle,
                              Boolean finishSelf,
                              GrantedSuccess permissionCallback,
                              AlertCallback callback) {
        Toast.makeText(activity, "应用缺少相应的权限，请在设置中打开", Toast.LENGTH_SHORT).show();
    }

    private static void alert(Context context) {
        Toast.makeText(context, "应用缺少相应的权限，请在设置中打开", Toast.LENGTH_SHORT).show();
    }

    /**
     * 创建fragment的权限alert
     *
     * @param type               Int
     * @param fragment           RxFragment
     * @param permissions        Array<String>
     * @param cancle             Boolean
     * @param finishSelf         Boolean
     * @param permissionCallback Permission.GrantedSuccess
     */
    private static void alert(int type, Fragment fragment,
                              String[] permissions,
                              boolean cancle,
                              boolean finishSelf,
                              GrantedSuccess permissionCallback,
                              AlertCallback callback) {
        Toast.makeText(fragment.getContext(), "应用缺少相应的权限，请在设置中打开", Toast.LENGTH_SHORT).show();
    }

    public interface Callback extends GrantedSuccess, GrantedFail {
    }

    public interface Callback2 extends GrantedSuccess, GrantedAllFail {
    }

    public interface GrantedSuccess {
        void onGranted();
    }

    public interface GrantedAllFail {
        void onFail();
    }

    public interface GrantedFail {
        void onDenid(String[] array);

        void onDenidNeverAsk(String[] array);
    }

    public interface AlertCallback {
        void callback(FragmentActivity activity, String[] permissions, boolean cancel, boolean finishSelf, GrantedSuccess success);
    }
}
