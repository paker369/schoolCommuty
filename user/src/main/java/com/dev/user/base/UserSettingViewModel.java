package com.dev.user.base;

import android.Manifest;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import com.dev.common.utils.CameraAndAlbumUtil;
import com.dev.common.utils.PermissionUtil;

/**
 * @author long.guo
 * @since 1/25/21
 */
public class UserSettingViewModel extends ViewModel {
    public <T extends UserSettingViewModel> Uri openCamera(AppCompatActivity activity, String tempFileName) {
        final Uri[] uri = {null};
        PermissionUtil.getPermission(
                activity,
                new String[]{Manifest.permission.CAMERA},
                true,
                false,
                () -> uri[0] = CameraAndAlbumUtil.openCamera(activity, tempFileName)
        );
        return uri[0];
    }

    public <T extends UserSettingViewModel> void openAlbum(AppCompatActivity activity) {
        CameraAndAlbumUtil.openAlbum(activity);
    }

}
