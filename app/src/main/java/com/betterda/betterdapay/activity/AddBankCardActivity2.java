package com.betterda.betterdapay.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.util.CacheUtils;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.ImageTools;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.ShapeLoadingDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 上传照片
 * Created by Administrator on 2016/8/17.
 */
public class AddBankCardActivity2 extends BaseActivity implements View.OnClickListener {

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

        ImageTools.paizhao(getmActivity(), Constants.PHOTOHRAPH);
    }

    private void commit() {

        if (TextUtils.isEmpty(url_identity)) {
            showToast("请上传身份证正面照");
            return;
        }
        if (TextUtils.isEmpty(url_identity2)) {
            showToast("请上传身份证反面照");
            return;
        }
        if (TextUtils.isEmpty(url_bank)) {
            showToast("请上传银行卡正面照");
            return;
        }
        if (TextUtils.isEmpty(url_bank2)) {
            showToast("请上传银行卡反面照");
            return;
        }
        if (TextUtils.isEmpty(url_handidntity)) {
            showToast("请上传手持身份证照");
            return;
        }
        if (TextUtils.isEmpty(url_handbank)) {
            showToast("请上传手持银行卡照");
            return;
        }

        getData();
    }

    /**
     * 上传信息
     */
    private void getData() {

        NetworkUtils.isNetWork(getmActivity(), null, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                UtilMethod.showDialog(getmActivity(), dialog);
                mRxManager.add(
                        NetWork.getNetService()
                                .getAuth(UtilMethod.getAccout(getmActivity()), realName,
                                        identityCard, cardNum, bank, number, cardType, url_identity, url_identity2, url_handidntity, url_bank, url_bank2, url_handbank)
                                .compose(NetWork.handleResult(new BaseCallModel<String>()))
                                .subscribe(new MyObserver<String>() {
                                    @Override
                                    protected void onSuccess(String data, String resultMsg) {
                                        if (BuildConfig.LOG_DEBUG) {
                                            System.out.println("实名认证:"+data);
                                        }
                                        UtilMethod.dissmissDialog(getmActivity(), dialog);
                                        //修改认证状态
                                        CacheUtils.putBoolean(getmActivity(), UtilMethod.getAccout(getmActivity()) + Constants.Cache.AUTH, true);
                                        UtilMethod.startIntent(getmActivity(), HomeActivity.class);
                                        finish();
                                    }

                                    @Override
                                    public void onFail(String resultMsg) {
                                        if (BuildConfig.LOG_DEBUG) {
                                            System.out.println("实名认证fail:"+resultMsg);
                                        }
                                        showToast(resultMsg);
                                        UtilMethod.dissmissDialog(getmActivity(), dialog);
                                    }

                                    @Override
                                    public void onExit() {
                                        UtilMethod.dissmissDialog(getmActivity(), dialog);

                                    }
                                })
                );
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

        result(requestCode, resultCode, data);


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
        if (requestCode == 5) {
            if (requestCode == 5 && resultCode == -1) { //resultcode表示裁剪成功

                if (!ImageTools.checkSDCardAvailable()) {
                    UtilMethod.Toast(this, "内存卡错误,请检查您的内存卡");
                    return;
                }
                // 防止内存溢出,压缩图片
                Bitmap pic = ImageTools.scacleToBitmap(Constants.PHOTOPATHFORCROP, this);
                if (pic != null) {// 这个ImageView是拍照完成后显示图片
                    setPhoto(pic);
                }
            } else {
                UtilMethod.Toast(this, "图片选取失败");
            }
        }

    }


    private void resultSuccess(int requestCode, int resultCode, Intent data) {
        // 拍照
        if (requestCode == Constants.PHOTOHRAPH) {
            if (resultCode == RESULT_OK) {// 返回成功的时候
                //uri 不能放在常量类里面,要实时创建
                ImageTools.cropImg(ImageTools.getUri(this), this, 2, 1, 256, 128);
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

                    ImageTools.cropImg(uri, this, 2, 1, 256, 128);

                } else {
                    UtilMethod.Toast(this, "图片选取失败");
                }
            } else {
                UtilMethod.Toast(this, "图片选取失败");
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
        /*ImageTools.savePhotoToSDCard(pic, Constants.PHOTOPATH,
                name);*/
        // 上传图片 TODO 将图片转换为base64
        // encode(Constants.PHOTOPATH+name + ".png");
        upload(name, pic, imageView);


    }

    private void upload(final String name, final Bitmap bitmap, final ImageView imageView) {
        NetworkUtils.isNetWork(getmActivity(), null, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                UtilMethod.showDialog(getmActivity(), dialog);
                //封装普通的string字段
                RequestBody account = RequestBody.create(MediaType.parse("text/plain"), UtilMethod.getAccout(getmActivity()));
                //封装文件
                // RequestBody file = RequestBody.create(MediaType.parse("multipart/form-data"), new File(Constants.PHOTOPATH, name + ".png"));
                RequestBody file = RequestBody.create(MediaType.parse("multipart/form-data"), new File(Constants.PHOTOPATHFORCROP));
                //第一个参数是key,第二是文件名,如果没有文件名不会被当成文件
                MultipartBody.Part filePart = MultipartBody.Part.createFormData("images", name + ".png", file);
                mRxManager.add(
                        NetWork.getNetService()
                                .getImgUpload(account, filePart)
                                .compose(NetWork.handleResult(new BaseCallModel<String>()))
                                .subscribe(new MyObserver<String>() {
                                    @Override
                                    protected void onSuccess(String data, String resultMsg) {
                                        if (BuildConfig.LOG_DEBUG) {
                                            System.out.println("图片上传:" + data);
                                        }
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
                                        //将裁剪后的图片删除
                                        try {
                                            File fileImage = new File(Constants.PHOTOPATHFORCROP);
                                            if (fileImage != null) {
                                                fileImage.delete();
                                            }
                                        } catch (Exception e) {

                                        }

                                    }

                                    @Override
                                    public void onFail(String resultMsg) {
                                        showToast(resultMsg);
                                        UtilMethod.dissmissDialog(getmActivity(), dialog);
                                        //将裁剪后的图片删除
                                        try {
                                            File fileImage = new File(Constants.PHOTOPATHFORCROP);
                                            if (fileImage != null) {
                                                fileImage.delete();
                                            }
                                        } catch (Exception e) {

                                        }
                                    }

                                    @Override
                                    public void onExit() {
                                        UtilMethod.dissmissDialog(getmActivity(), dialog);
                                        //将裁剪后的图片删除
                                        try {
                                            File fileImage = new File(Constants.PHOTOPATHFORCROP);
                                            if (fileImage != null) {
                                                fileImage.delete();
                                            }
                                        } catch (Exception e) {

                                        }

                                    }
                                })
                );
            }
        });
    }

    /**
     * 将图片转换为base64
     * @param path
     */
    private void encode(String path) {
        //decode to bitmap
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        //convert to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();

        //base64 encode
        byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
        String encodeString = new String(encode);
        System.out.println("base64图片:" + encodeString);
    }


}
