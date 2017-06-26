package com.betterda.betterdapay.util;

import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.net.URL;

public class Constants {

    public static final String CACHE_FILE_NAME = "betterdapay"; //缓存目录
   // public static final String PHOTOPATH = Environment.getExternalStorageDirectory().getPath() + "/betterdapay/photo/";//存图片的路径
   // public static  Uri imageUri =Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"image.png"));
    public static final String PHOTOPATHFORCROP =Environment.getExternalStorageDirectory() + "/image.png";//存图片的路径


    public static final int PHOTOZOOM = 1;// 相机选取
    public static final int PHOTOHRAPH = 2;// 拍照
    public static final String IMAGE_UNSPECIFIED = "image/*";

    public static final int PAGE_SIZE = 10;//一页加载的个数
    //判断输入金额的正则
    public static final String str = "^(([0-9]|([1-9][0-9]{0,9}))((\\.[0-9]{1,2})?))$";

    public final static String ZHIFUBAO = "支付宝";
    public final static String WEIXIN = "微信";
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
        public static final String ALIAS = "alias";//极光设置别名
        public final static String MESSAGE = "message";//是否是新消息

    }


    public static class Url {
        public static final String URL = "http://192.168.0.121:8080/wallet/";
       // public static final String URL = "http://192.168.0.113:8080/wallet/";
        public static final String URL_LOGIN = URL + "api/loginInController.do?loginIn";
        public static final String URL_REGISTER = URL + "api/registerController.do?register";
        public static final String URL_SENDMSG = URL + "api/msgController.do?sendMsg";//短信验证
        public static final String URL_PWD_UPDATE = URL + "api/resetPwdController.do?reset";//忘记密码
        public static final String URL_ORDER_CREATE = URL + "appAPI.do?api/account/order/add";//订单生成
        public static final String URL_ORDER_GET = URL + "appAPI.do?api/account/order/get";//账单获取
        public static final String URL_RATING = URL + "api/memberRankController.do?getRanks";//获取各等级费率
        public static final String URL_MY_RATING = URL + "api/memberRankController.do?getRank";//获取我的等级费率
        public static final String URL_MY_RATINGS = URL + "api/memberRankController.do?getRates";//获取当前等级费率用与计算
        public static final String URL_UPDATE_CONDITION = URL + "api/memberRankController.do?queryUpgrade";//获取升级条件
        public static final String URL_UPDATE_TO_RATE = URL + "api/upgradeOrderController.do?upgrade";//升级到指定接口
        public static final String URL_JIESUAN = URL + "api/disburseController.do?withdraw";//结算接口
        public static final String URL_BANK_ADD = URL + "appAPI.do?api/account/bank/add";//银行卡添加接口
        public static final String URL_BANK_GET = URL + "appAPI.do?api/account/bank/get";//银行卡查询接口
        public static final String URL_BANK_DELETE = URL + "appAPI.do?api/account/bank/delete";//银行卡删除接口
        public static final String URL_CDOE_GET = URL + "api/shareController.do?share";//获取分享链接
        public static final String URL_AUTH_ADD = URL + "appAPI.do?api/account/auth/add";//实名认证接口
        public static final String URL_GET_INCOME = URL + "api/incomeController.do?getIncomes";//分润明细
        public static final String URL_GET_JIESUAN = URL + "api/disburseController.do?getDisburse";//结算明细
        public static final String URL_SUB_GET = URL + "api/mySpreadController.do?mySpread";//我的推广接口
        public static final String URL_WALLET_GET = URL + "api/balanceController.do?getBalance";//钱包获取接口
        public static final String URL_FEEDBACK_ADD = URL + "api/feedbackController.do?feedback";//意见反馈
        public static final String URL_SEARCH_POST = URL + "appAPI.do?api/account/search/post";//搜索接口
        public static final String URL_IMG_UPLOAD = URL + "fileUpload";//图片上传
        public static final String URL_UPDATE_VERSION = URL + "api/versionController.do?version";//版本更新
        public static final String URL_GET_INFORMATION = "api/memberController.do?getInfo";//我的资料获取
        public static final String URL_GET_MEMBERS = "api/memberNumController.do?memberNum";//获取会员个数
        public static final String URL_GET_MESSAGE_LIST = "api/infoListController.do?infoList";//获取消息列表
        public static final String URL_CHECK_WITHDRAW = "api/disburseController.do?isWithdraw";//查询结算状态




    }


}
