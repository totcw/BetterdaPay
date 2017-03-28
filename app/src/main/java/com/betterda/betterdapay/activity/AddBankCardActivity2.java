package com.betterda.betterdapay.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.util.CacheUtils;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.ImageTools;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.PermissionUtil;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.ShapeLoadingDialog;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 上传照片
 * Created by Administrator on 2016/8/17.
 */
public class AddBankCardActivity2 extends BaseActivity implements View.OnClickListener {
    private String[] REQUEST_PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
    private static final int REQUEST_PERMISSION_CODE_TAKE_PIC = 9; //权限的请求码
    private static final int REQUEST_PERMISSION_SEETING = 8; //去设置界面的请求码

    @BindView(R.id.topbar_addbankcard2)
    NormalTopBar topbarAddbankcard2;
    @BindView(R.id.iv_addbankcard2_identity)
    ImageView ivAddbankcard2Identity;
    @BindView(R.id.linear_addbankcard2_identity)
    LinearLayout linearAddbankcard2Identity;
    @BindView(R.id.iv_addbankcard2_identity2)
    ImageView ivAddbankcard2Identity2;
    @BindView(R.id.linear_addbankcard2_identity2)
    LinearLayout linearAddbankcard2Identity2;
    @BindView(R.id.iv_addbankcard2_handidentity)
    ImageView ivAddbankcard2Handidentity;
    @BindView(R.id.linear_addbankcard2_handidentity)
    LinearLayout linearAddbankcard2Handidentity;
    @BindView(R.id.iv_addbankcard2_bankcard)
    ImageView ivAddbankcard2Bankcard;
    @BindView(R.id.iv_addbankcard2_bankcard2)
    ImageView ivAddbankcard2Bankcard2;
    @BindView(R.id.linear_addbankcard2_bankcard2)
    LinearLayout linearAddbankcard2Bankcard2;
    @BindView(R.id.iv_addbankcard2_handbank)
    ImageView ivAddbankcard2Handbank;
    @BindView(R.id.linear_addbankcard2_handbank)
    LinearLayout linearAddbankcard2Handbank;
    @BindView(R.id.linear_addbankcard2_bankcard)
    LinearLayout linearAddbankcard2Bankcard;
    @BindView(R.id.btn_addbankcard_commit)
    Button btnAddbankcardCommit;


    private String realName;//认证姓名
    private String identityCard;//身份证号
    private String cardNum;//银行卡号
    private String bank;//所属银行
    private String number;//预留手机号码
    private String cardType;//银行卡类型
    private String url_identity, url_bank, url_identity2, url_bank2, url_handbank, url_handidntity;

    private ShapeLoadingDialog dialog;
    private int isLogo;//用来区分6种图片

    @Override
    public void initView() {
        setContentView(R.layout.activity_addbankcard2);
    }

    @Override
    public void init() {
        super.init();
        setTopBar();
        getIntentData();
        dialog = UtilMethod.createDialog(getmActivity(), "正在上传...");

    }


    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            realName = intent.getStringExtra("realName");
            identityCard = intent.getStringExtra("identityCard");
            cardNum = intent.getStringExtra("cardNum");
            bank = intent.getStringExtra("bank");
            number = intent.getStringExtra("number");
            cardType = intent.getStringExtra("cardType");
        }
    }

    private void setTopBar() {
        topbarAddbankcard2.setTitle("上传照片");
    }

    @Override
    public void initListener() {
        super.initListener();
        topbarAddbankcard2.setOnBackListener(this);
    }

    @OnClick({R.id.linear_addbankcard2_identity, R.id.linear_addbankcard2_identity2, R.id.linear_addbankcard2_handidentity, R.id.linear_addbankcard2_bankcard2, R.id.linear_addbankcard2_handbank, R.id.linear_addbankcard2_bankcard, R.id.btn_addbankcard_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_addbankcard2_identity://身份证正面照
                isLogo = 0;
                showPopupWindowPhoto();
                break;
            case R.id.linear_addbankcard2_identity2://身份证反面照
                isLogo = 1;
                showPopupWindowPhoto();
                break;
            case R.id.linear_addbankcard2_handidentity://手持身份证照
                isLogo = 2;
                showPopupWindowPhoto();
                break;
            case R.id.linear_addbankcard2_bankcard2://银行卡反面照
                isLogo = 3;
                showPopupWindowPhoto();
                break;
            case R.id.linear_addbankcard2_handbank://手持银行卡照
                isLogo = 4;
                showPopupWindowPhoto();
                break;
            case R.id.linear_addbankcard2_bankcard://银行卡正面照
                isLogo = 5;
                showPopupWindowPhoto();
                break;
            case R.id.btn_addbankcard_commit:
                commit();
                break;
            case R.id.bar_back:
                back();
                break;
            case R.id.tv_photo_cameral://拍照
                requestPermiss();
                closePopupWindow();
                break;
            case R.id.tv_photo_photo://图片库
                ImageTools.choosePicture(getmActivity(), Constants.PHOTOZOOM);
                closePopupWindow();
                break;
            case R.id.tv_photo_cancell://取消
                closePopupWindow();
                break;
        }
    }

    /**
     * 请求拍照的权限
     */
    private void requestPermiss() {
        PermissionUtil.checkPermission(getmActivity(), linearAddbankcard2Bankcard, REQUEST_PERMISSIONS, REQUEST_PERMISSION_CODE_TAKE_PIC, new PermissionUtil.permissionInterface() {
            @Override
            public void success() {
                UtilMethod.paizhao(getmActivity(), Constants.PHOTOHRAPH);
            }
        });
    }

    private void commit() {
        if (TextUtils.isEmpty(url_identity)) {
            showToast("身份证正面照不能为空");
            return;
        }
        if (TextUtils.isEmpty(url_identity2)) {
            showToast("身份证反面照不能为空");
            return;
        }
        if (TextUtils.isEmpty(url_bank)) {
            showToast("银行卡正面照不能为空");
            return;
        }
        if (TextUtils.isEmpty(url_bank2)) {
            showToast("银行卡反面照不能为空");
            return;
        }
        if (TextUtils.isEmpty(url_handidntity)) {
            showToast("手持身份证照不能为空");
            return;
        }
        if (TextUtils.isEmpty(url_handbank)) {
            showToast("手持银行卡照不能为空");
            return;
        }

        getData();
    }

    /**
     * 上传信息
     */
    private void getData() {

        NetworkUtils.isNetWork(getmActivity(), ivAddbankcard2Bankcard, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                UtilMethod.showDialog(getmActivity(),dialog);
               subscription= NetWork.getNetService(subscription)
                       .getAuth(UtilMethod.getAccout(getmActivity()),realName,
                       identityCard,cardNum,bank,number,cardType,url_identity,url_identity2,url_handidntity,url_bank,url_bank2,UtilMethod.getToken(getmActivity()),url_handbank)
                       .compose(NetWork.handleResult(new BaseCallModel<String>()))
                       .subscribe(new MyObserver<String>() {
                           @Override
                           protected void onSuccess(String data, String resultMsg) {

                               UtilMethod.dissmissDialog(getmActivity(), dialog);
                               showToast(resultMsg);

                               //修改认证状态
                               CacheUtils.putBoolean(getmActivity(),UtilMethod.getAccout(getmActivity())+ Constants.Cache.AUTH, true);
                               UtilMethod.startIntent(getmActivity(), HomeActivity.class);
                               finish();
                           }

                           @Override
                           public void onFail(String resultMsg) {
                               showToast(resultMsg);
                            UtilMethod.dissmissDialog(getmActivity(),dialog);
                           }

                           @Override
                           public void onExit() {
                               UtilMethod.dissmissDialog(getmActivity(),dialog);
                                ExitToLogin();
                           }
                       });
            }
        });
    }

    /**
     * 选择照片
     */
    private void showPopupWindowPhoto() {

        View view = LayoutInflater.from(this).inflate(R.layout.pp_choose_photo,
                null);
        TextView tv_cameral = (TextView) view.findViewById(R.id.tv_photo_cameral);
        TextView tv_photo = (TextView) view.findViewById(R.id.tv_photo_photo);
        TextView tv_cancell = (TextView) view.findViewById(R.id.tv_photo_cancell);
        tv_cameral.setOnClickListener(this);
        tv_photo.setOnClickListener(this);
        tv_cancell.setOnClickListener(this);
        setUpPopupWindow(view, null);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //如果是从设置界面返回,就继续判断权限
        if (requestCode == REQUEST_PERMISSION_SEETING) {
            requestPermiss();
        } else {
            result(requestCode, resultCode, data);
        }

    }

    /**
     * 解析拍照或者图片库的图片
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void result(int requestCode, int resultCode, Intent data) {
        resultSuccess(requestCode, resultCode, data);

        if (requestCode == 5 && resultCode == -1) { //resulrcode表示裁剪成功
            if (!ImageTools.checkSDCardAvailable()) {
                UtilMethod.Toast(this, "内存卡错误,请检查您的内存卡");
                return;
            }
            // 防止内存溢出
            String path = Environment.getExternalStorageDirectory()
                    + "/image.png";
            Bitmap pic = ImageTools.scacleToBitmap(path, this);
            if (pic != null) {// 这个ImageView是拍照完成后显示图片

                setPhoto(pic);
            }
        }
    }

    private void resultSuccess(int requestCode, int resultCode, Intent data) {
        // 拍照
        if (requestCode == Constants.PHOTOHRAPH) {
            if (resultCode == RESULT_OK) {// 返回成功的时候
                UtilMethod.cropImg(Constants.imageUri, this, 2, 1, 256, 128);
            } else if (resultCode == RESULT_CANCELED) {// 取消的时候
                UtilMethod.Toast(this, "取消拍照");
            } else {
                // 失败的时候
                UtilMethod.Toast(this, "拍照失败");
            }

        }
        // 读取相册缩放图片
        if (requestCode == Constants.PHOTOZOOM) {
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {

                    UtilMethod.cropImg(uri, this, 2, 1, 256, 128);

                } else {
                    UtilMethod.Toast(this, "图片选取失败");
                }
            }

        }
    }

    /**
     * 设置照片
     *
     * @param pic
     */
    private void setPhoto(Bitmap pic) {
        String accout = UtilMethod.getAccout(getmActivity());
        switch (isLogo) {
            case 0:
                savePhoto(pic, accout + "identity", ivAddbankcard2Identity);

                break;
            case 1:
                savePhoto(pic, accout + "identity2", ivAddbankcard2Identity2);

                break;
            case 2://手持身份证
                savePhoto(pic, accout + "handidentity", ivAddbankcard2Handidentity);

                break;
            case 3:
                savePhoto(pic, accout + "bankcard2", ivAddbankcard2Bankcard2);

                break;
            case 4://手持银行卡
                savePhoto(pic, accout + "handbank", ivAddbankcard2Handbank);

                break;
            case 5:
                savePhoto(pic, accout + "bankcard", ivAddbankcard2Bankcard);

                break;
        }


    }


    public void savePhoto(Bitmap pic, final String name, ImageView imageView) {
        // 将bitmap保存到本地
        ImageTools.savePhotoToSDCard(pic, Constants.PHOTOPATH,
                name);
        // 上传图片
        upload(name,pic,imageView);


    }

    private void upload(final String name, final Bitmap bitmap, final ImageView imageView) {
        NetworkUtils.isNetWork(getmActivity(), linearAddbankcard2Bankcard, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                UtilMethod.showDialog(getmActivity(), dialog);
                //封装普通的string字段
                RequestBody account = RequestBody.create(MediaType.parse("text/plain"), UtilMethod.getAccout(getmActivity()));
                RequestBody token = RequestBody.create(MediaType.parse("text/plain"), UtilMethod.getToken(getmActivity()));
                //封装文件
                RequestBody file = RequestBody.create(MediaType.parse("multipart/form-data"), new File(Constants.PHOTOPATH, name + ".png"));
                //第一个参数是key,第二是文件名,如果没有文件名不会被当成文件
                MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", name + ".png", file);
                subscription = NetWork.getNetService(subscription)
                        .getImgUpload(account, token, filePart)
                        .compose(NetWork.handleResult(new BaseCallModel<String>()))
                        .subscribe(new MyObserver<String>() {
                            @Override
                            protected void onSuccess(String data, String resultMsg) {
                                showToast(resultMsg);
                                switch (isLogo) {
                                    case 0:
                                        url_identity = data;
                                        break;
                                    case 1:
                                        url_identity2 = data;
                                        break;
                                    case 2://手持身份证
                                        url_handidntity = data;
                                        break;
                                    case 3:
                                        url_bank2 = data;
                                        break;
                                    case 4://手持银行卡
                                        url_handbank = data;
                                        break;
                                    case 5:
                                        url_bank = data;
                                        break;
                                }
                                imageView.setImageBitmap(bitmap);
                                UtilMethod.dissmissDialog(getmActivity(), dialog);
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                showToast(resultMsg);
                                UtilMethod.dissmissDialog(getmActivity(), dialog);
                            }

                            @Override
                            public void onExit() {
                                UtilMethod.dissmissDialog(getmActivity(), dialog);
                                ExitToLogin();
                            }
                        });
            }
        });
    }


    /**
     * 检测权限的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[] grantResults) {

        if (requestCode == REQUEST_PERMISSION_CODE_TAKE_PIC) {
            if (PermissionUtil.verifyPermissions(grantResults)) {//有权限
                ImageTools.paizhao(getmActivity(), Constants.PHOTOHRAPH);
            } else {
                //没有权限
                if (!PermissionUtil.shouldShowPermissions(this,permissions)) {//这个返回false 表示勾选了不再提示
                    showSnackBar(linearAddbankcard2Bankcard, "请去设置界面设置权限","去设置");
                } else {
                    //表示没有权限 ,但是没勾选不再提示
                    showSnackBar(linearAddbankcard2Bankcard, "请允许权限请求!");
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void doSnack() {
        super.doSnack();
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_PERMISSION_SEETING);
    }
}
