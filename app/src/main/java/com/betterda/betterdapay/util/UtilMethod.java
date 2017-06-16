package com.betterda.betterdapay.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.activity.HomeActivity;
import com.betterda.betterdapay.activity.RealNameAuthActivity;
import com.betterda.mylibrary.LoadingPager;
import com.betterda.mylibrary.ShapeLoadingDialog;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rx.Subscription;


/**
 * Created by lyf
 */
public class UtilMethod {

    /**
     * get mobile device id
     *
     * @return
     */
    public static String getUdId(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) {
            return null;
        }
        return tm.getDeviceId();
    }

    /**
     * get app version name and version code
     */
    public static String getAppVersion(Context context) {
        String versionName = "0.0.0";
        int versionCode = 1;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versionCode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName + "-" + versionCode;
    }

    /**
     * get app version name
     */
    public static String getAppVersionName(Context context) {
        String versionName = "0.0.0";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * get app version code
     */
    public static int getAppVersionCode(Context context) {
        int versionCode = 1;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = pi.versionCode;
            return versionCode;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionCode;
    }

    /**
     * get mobile model
     */
    public static String getDevice() {
        return Build.MODEL;
    }

    /**
     * get mobile phone number and replace china number
     */
    public static String getPhoneNumber(Context context) {
        TelephonyManager phoneMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String tel = phoneMgr.getLine1Number();
        if (tel != null) {
            tel = tel.replace("+86", "").trim();
        }
        return tel;
    }
    /**
     * 获取apk程序信息[packageName,versionName...]
     *
     * @param context Context
     * @param path    apk path
     */
    public static PackageInfo getApkInfo(Context context, String path) {

        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            return info;
        }
        return null;
    }
    /**
     * 下载的apk和当前程序版本比较
     *
     * @param apkInfo apk file's packageInfo
     * @param context Context
     * @return 如果当前应用版本小于apk的版本则返回true
     */
    public static boolean compare(PackageInfo apkInfo, Context context) {
        if (apkInfo == null) {
            return false;
        }
        String localPackage = context.getPackageName();
        if (apkInfo.packageName.equals(localPackage)) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(localPackage, 0);
                System.out.println("apk:"+apkInfo.versionCode);
                System.out.println("packageInfo:"+packageInfo.versionCode);
                if (apkInfo.versionCode > packageInfo.versionCode) {
                    return true;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 安装apk
     * @param context
     * @param uri
     */
    public static void startInstall(Context context, Uri uri) {
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.setDataAndType(uri, "application/vnd.android.package-archive");
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(install);
    }

    /**
     * close the soft keyboard
     *
     * @param context
     */
    public static void closeKeyBox(Context context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        final View v = ((Activity) context).getWindow().peekDecorView();
        imm.hideSoftInputFromWindow(v.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
    /**
     * 获取状态栏高度
     *
     * @param activity
     * @return
     */
    public static int statusHeight(Activity activity) {
        //获取状态栏高度
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getWeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        //窗口的宽度
        int screenWidth = dm.widthPixels;
        return screenWidth;

    }

    /**
     * 获取屏幕的高度
     *
     * @param activity
     * @return
     */
    public static int getHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        //窗口的宽度
        int screenWidth = dm.heightPixels;
        return screenWidth;

    }

    /**
     * 跳转activity
     *
     * @param context
     * @param cla
     */
    public static <T> void startIntent(Context context, Class<T> cla) {
        Intent intent = new Intent(context, cla);
        context.startActivity(intent);
    }


    public static <T> void startIntent(Context context, Class<T> cla, String key, String value) {
        Intent intent = new Intent(context, cla);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }


    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * * 设置添加popupwindow屏幕的背景透明度
     * * @param bgAlpha
     */
    public static void backgroundAlpha(float bgAlpha, Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 打印土司
     *
     * @param context
     * @param message
     */
    public static void Toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    /**
     * 获取当前的时间格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrdentTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        return time;
    }

    /**
     * 获取当前的时间格式 yyyy-MM-dd
     */
    public static String getCurrdentTime2() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(date);
        return time;
    }

    /**
     * 得到自定义的一个加载对话框
     *
     * @param mContext
     * @param content
     * @return
     */
    public static ShapeLoadingDialog createDialog(Context mContext, String content) {
        ShapeLoadingDialog shapeLoadingDialog = new ShapeLoadingDialog(mContext);
        shapeLoadingDialog.setLoadingText(content);
        shapeLoadingDialog.setCanceledOnTouchOutside(false);
        // shapeLoadingDialog.show();
        return shapeLoadingDialog;
    }


    /**
     * 裁剪
     *
     * @param uri 图片的uri
     */
    public static void cropImg(Uri uri, Activity activity, int width, int height, int width2, int height2) {


        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", width); //X方向上的比例
        intent.putExtra("aspectY", height);
        intent.putExtra("outputX", width2); //裁剪区的宽
        intent.putExtra("outputY", height2);
        intent.putExtra("scale", true); //是否保留比例
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Constants.imageUri);
        activity.startActivityForResult(intent, 5);
    }

    /**
     * 拍照
     */
    public static void paizhao(Activity activity, int requestcode) {
        try {
            if (!ImageTools.checkSDCardAvailable()) {
                UtilMethod.Toast(activity, "内存卡错误,请检查您的内存卡");
                return;
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // 这样就将文件的存储方式和uri指定到了Camera应用中 指定了这个 data一般为空了
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Constants.imageUri);

            activity.startActivityForResult(intent, requestcode);


        } catch (Exception e) {
            UtilMethod.Toast(activity, "拍照失败");
        }

    }


    /**
     * 选取图片
     */
    public static void choosePicture(Activity activity, int requestcode) {
        try {
            if (!ImageTools.checkSDCardAvailable()) {
                UtilMethod.Toast(activity, "内存卡错误,请检查您的内存卡");
                return;
            }
            Intent intentphoto = new Intent(Intent.ACTION_PICK);
            intentphoto.setDataAndType(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    Constants.IMAGE_UNSPECIFIED);
            activity.startActivityForResult(intentphoto, requestcode);

        } catch (Exception e) {
            UtilMethod.Toast(activity, "选取照片失败");
        }

    }









    /**
     * 去掉反斜杠
     *
     * @param s
     */
    public static String deleteString(String s) {
        if (null != s) {

            String replace = s.replace("\\", "");
            return replace;
        }
        return null;
    }

    /**
     * 去除服务器返回的json含有的\和开头结尾的""
     *
     * @param s
     * @return
     */
    public static String getString(String s) {
        String substring = null;
        if (!TextUtils.isEmpty(s)) {
            String s1 = UtilMethod.deleteString(s);
            if (!TextUtils.isEmpty(s1)) {
                if (s1.startsWith("\"") && s1.endsWith("\"")) {
                    substring = s1.substring(1, s1.length() - 1);
                }

            }
        }


        return substring;
    }


    /**
     * float格式化保留2位
     *
     * @param money
     */
    public static String FloatFormat(float money) {
        DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(money);//format 返回的是字符串
        return p;
    }

    /**
     * 显示对话框
     */
    public static void showDialog(Activity activity, ShapeLoadingDialog dialog) {
        if (!activity.isFinishing()) {
            if (dialog != null) {
                dialog.show();
            }
        }
    }

    public static void showDialog(Activity activity, AlertDialog dialog) {
        if (!activity.isFinishing()) {
            if (dialog != null) {
                dialog.show();
            }
        }
    }


    /**
     * 关闭对话框
     */
    public static void dissmissDialog(Activity activity, ShapeLoadingDialog dialog) {
        if (!activity.isFinishing()) {
            if (dialog != null) {

                dialog.dismiss();
            }
        }
    }

    public static void dissmissDialog(Activity activity, AlertDialog dialog) {
        if (!activity.isFinishing()) {
            if (dialog != null) {

                dialog.dismiss();
            }
        }
    }

    /**
     * 判断服务器返回的数据是否为空
     */
    public static void isDataEmpty(LoadingPager loadingPager, List list) {
        if (loadingPager != null) {
            if (list != null) {
                if (list.size() == 0) {
                    loadingPager.setEmptyVisable();
                } else {
                    loadingPager.hide();
                }
            } else {
                loadingPager.setEmptyVisable();
            }
        }
    }

    /**
     * 解除rxbus的绑定
     * @param subscribe
     */
    public static void unSubscribe(Subscription subscribe) {
        if (subscribe != null) {
            if (!subscribe.isUnsubscribed()) {
                subscribe.unsubscribe();
            }
            subscribe = null;
        }
    }


    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }


    public static String getAccout(Context context) {
        return  CacheUtils.getString(context, Constants.Cache.ACCOUNT, "");
    }

    public static String getToken(Context context) {

       return CacheUtils.getString(context, getAccout(context) + Constants.Cache.TOKEN, "");
    }


    /**
     * 判断数据不是空
     */
    public  static void judgeData(List list,LoadingPager loadingPager) {

        if (list != null) {
            if (list.size() == 0) {
                loadingPager.setEmptyVisable();
                return;
            }
        }
        loadingPager.hide();
    }



    /**
     * 显示温馨提示对话框
     */
    public  static boolean showNotice(final Context context) {
        String accout = UtilMethod.getAccout(context);
        boolean auth = CacheUtils.getBoolean(context, accout+Constants.Cache.AUTH, false);
        if (!auth) {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_notice, null);
            TextView mTvCancel = (TextView) view.findViewById(R.id.tv_update_cancel);
            TextView mTvComfirm = (TextView) view.findViewById(R.id.tv_update_comfirm);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final AlertDialog alertDialog = builder.setCancelable(false).setView(view).create();
            UtilMethod.showDialog((Activity) context, alertDialog);

            mTvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UtilMethod.dissmissDialog((Activity) context, alertDialog);
                }
            });

            mTvComfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UtilMethod.dissmissDialog((Activity) context, alertDialog);
                    UtilMethod.startIntent(context, RealNameAuthActivity.class);
                }
            });
            return false;
        } else {
            return true;
        }
    }



}
