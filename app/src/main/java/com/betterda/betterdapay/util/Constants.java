package com.betterda.betterdapay.util;

import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.net.URL;

public class Constants {

    public static final String CACHE_FILE_NAME = "betterdapay"; //缓存目录
    public static final String PHOTOPATH = Environment.getExternalStorageDirectory().getPath() + "/betterdapay/photo/";//存图片的路径
    public static final Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.png")); //保存拍照照片的uri
    public static final int PHOTOZOOM = 1;// 相机选取
    public static final int PHOTOHRAPH = 2;// 相机拍照

    public static final String IMAGE_UNSPECIFIED = "image/*";
    public static String PHOTONAME = "photo"; //存放照片的名字
    public static final int TITLE_COUNT = 3;//APP底部的title个数
    public static final int PAGE_SIZE = 1;//一页加载的个数
    //判断输入金额的正则
    public static final String str = "^(([0-9]|([1-9][0-9]{0,9}))((\\.[0-9]{1,2})?))$";

    public final static String ZHIFUBAO = "支付宝支付";
    public final static String WEIXIN = "微信支付";
    public final static String SHOU_KUAN = "0"; //收款
    public final static String FEN_RUN = "1"; //分润
    public final static String TI_XIAN = "2";//提现
    public final static String FEN_RUN_HUI = "0"; //返还分润
    public final static String FEN_RUN_TUI = "1";//推广分润
     public final static String FEN_RUN_HUI2 = "返回分润"; //返还分润
    public final static String FEN_RUN_TUI2 = "推广分润";//推广分润


    public static class Cache {
        public static final String TOKEN = "token";
        public static final String PWD = "pwd";
        public static final String ACCOUNT = "account";
        public static final String AUTH = "auth";//是否认证
        public static final String RANK = "rank";//等级
        public static final String REMEMBER = "remember";//是否记住密码
        public static final String GUIDE = "guide";//是否进入过引导界面

    }


    public static class Url {
        public static final String URL = "http://69b2f3c8.ngrok.io/wallet/";
        public static final String URL_LOGIN = URL + "api/loginInController.do?loginIn";
        public static final String URL_REGISTER = URL + "api/registerController.do?register";
        public static final String URL_SENDMSG = URL + "api/msgController.do?sendMsg";//短信验证
        public static final String URL_PWD_UPDATE = URL + "api/resetPwdController.do?reset";//忘记密码
        public static final String URL_ORDER_CREATE = URL + "appAPI.do?api/account/order/add";//订单生成
        public static final String URL_ORDER_GET = URL + "appAPI.do?api/account/order/get";//账单获取
        public static final String URL_ORDER_FENRUN = URL + "appAPI.do?api/account/tProfit/get";//分润获取
        public static final String URL_RATING = URL + "appAPI.do?api/account/rateDetail/get";//获取各等级费率
        public static final String URL_JIESUAN = URL + "appAPI.do?api/account/tSettlement/add";//结算接口
        public static final String URL_BANK_ADD = URL + "appAPI.do?api/account/bank/add";//银行卡添加接口
        public static final String URL_BANK_GET = URL + "appAPI.do?api/account/bank/get";//银行卡查询接口
        public static final String URL_BANK_DELETE = URL + "appAPI.do?api/account/bank/delete";//银行卡删除接口
        public static final String URL_CDOE_GET = URL + "appAPI.do?api/account/code/get";//我的二维码接口
        public static final String URL_AUTH_ADD = URL + "appAPI.do?api/account/auth/add";//实名认证接口
        public static final String URL_SUBNUM_GET = URL + "appAPI.do?api/account/subnum/get";//我的商户接口
        public static final String URL_SUB_GET = URL + "appAPI.do?api/account/sub/get";//我的推广接口
        public static final String URL_WALLET_GET = URL + "appAPI.do?api/account/wallet/get";//钱包获取接口
        public static final String URL_FEEDBACK_ADD = URL + "appAPI.do?api/account/feedback/add";//意见反馈
        public static final String URL_SEARCH_POST = URL + "appAPI.do?api/account/search/post";//搜索接口
        public static final String URL_IMG_UPLOAD = URL + "appAPI.do?api/android/img/upload";//图片上传
        public static final String URL_GET_INFORMATION = "appAPI.do?api/account/auth/get";//我的资料获取
        public static final String URL_GET_MEMBERS = "api/memberNumController.do?memberNum";//获取会员个数



    }


}
