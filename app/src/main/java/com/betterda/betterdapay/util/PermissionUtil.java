package com.betterda.betterdapay.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.betterda.betterdapay.activity.AddBankCardActivity2;

/**
 * Created by Administrator on 2016/8/26.
 */
public class PermissionUtil {

    /**
     * 请求权限
     */
    public static void requestContactsPermissions(final Activity activity,View view, final String[] permissions, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        Manifest.permission.CAMERA)) {
            Snackbar.make(view, "需要一些权限",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat
                                    .requestPermissions(activity, permissions,
                                            requestCode);
                        }
                    })
                    .show();
        } else {
            // 无需向用户界面提示，直接请求权限
            ActivityCompat.requestPermissions(activity,
                    permissions,
                    requestCode);
        }
    }

    /**
     * 判断请求权限是否成功
     * @param grantResults
     * @return
     */
    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if(grantResults.length < 1){
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    /**
     * 检测权限
     * @param activity
     * @param view //随便一个view 来用现象snackbar
     * @param permissions  //请求的权限组
     * @param requestCode  //请求码
     */
    public  static void checkPermission(final Activity activity,View view, final String[] permissions, final int requestCode,permissionInterface permissionInterface) {
        boolean isPermission = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                //没有权限
            } else {
                //一个有权限,就结束
                isPermission = false;
                break;
            }
        }

        if (isPermission) {
            //请求权限
            PermissionUtil.requestContactsPermissions(activity, view, permissions, requestCode);
        } else {
            //拥有权限
            permissionInterface.success();
        }
    }


    public interface permissionInterface{
        void success();
    }

}
