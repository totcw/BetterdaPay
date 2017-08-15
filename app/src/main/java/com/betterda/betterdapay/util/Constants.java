package com.betterda.betterdapay.util;

import android.os.Environment;

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


    public final static String ZHIFUBAO = "85"; //支付宝的code值
    public final static String WEIXIN = "86";//微信的code值
    public final static String UNION_T1 = "87";//快捷T+1的code值
    public final static String UNION_D0 = "88";//快捷D+0的code值
    public final static String UNION_CONTROL_T1 = "89";//快捷控件T1的code值
    public final static String UNION_CONTROL_D0 = "90";//快捷控件D0的code值

    public final static String WITHDRAW = "提现";
    public final static String NUMBER_REGULAR = "^1(3[0-9]|4[0-9]|5[0-9]|7[0-9]|8[0-9])\\d{8}$";//判断手机的正则


    public static class Cache {
        public static final String TOKEN = "token";
        public static final String PWD = "pwd";
        public static final String ACCOUNT = "account";
        public static final String AUTH = "auth";//是否认证
        public static final String RANK = "rank";//等级code
        public static final String RANKNAME = "rankname";//等级名字
        public static final String REMEMBER = "remember";//是否记住密码
        public static final String GUIDE = "guide";//是否进入过引导界面
        public static final String ALIAS = "alias";//极光设置别名
        public final static String MESSAGE = "message";//是否是新消息

    }


    public static class Url {
        public static final String URL = "http://192.168.0.104:8080/paycloud-walletapi/";
      //  public static final String URL = "http://119.23.227.230:7080/wallet/";
        public static final String URL_LOGIN = URL + "api/user/login";
        public static final String URL_REGISTER = URL + "api/user/register";
        public static final String URL_SENDMSG = URL + "api/user/verifcode/get";//短信验证
        public static final String URL_PWD_UPDATE = URL + "api/user/password/update";//忘记密码
        public static final String URL_ORDER_CREATE = URL + "api/channel/sdk/consume";//订单生成(手机支付控件升级付款)
        public static final String URL_ORDER_GET = URL + "api/order/get";//账单获取
        public static final String URL_RATING = URL + "api/rate/list/get";//获取各等级费率
        public static final String URL_MY_RATING = URL + "api/rate/my/get";//获取我的等级费率
        public static final String URL_UPDATE_CONDITION = URL + "api/rate/rank/list/get";//获取升级条件
        public static final String URL_JIESUAN = URL + "api/wallet/draw";//结算接口
        public static final String URL_BANK_ADD = URL + "api/bankcard/add";//银行卡添加接口
        public static final String URL_BANK_GET = URL + "api/bankcard/get";//银行卡查询接口
        public static final String URL_BANK_DELETE = URL + "api/bankcard/delete";//银行卡删除接口
        public static final String URL_CDOE_GET = URL + "api/member/invite/get";//获取分享链接
        public static final String URL_AUTH_ADD = URL + "api/certification/auth";//实名认证接口
        public static final String URL_GET_INCOME = URL + "api/wallet/profitRecord/get";//分润明细
        public static final String URL_GET_JIESUAN = URL + "api/wallet/drawRecord/get";//结算明细
        public static final String URL_SUB_GET = URL + "api/member/myspread/get";//我的推广接口
        public static final String URL_WALLET_GET = URL + "api/wallet/get";//钱包获取接口
        public static final String URL_FEEDBACK_ADD = URL + "api/feedback/submit";//意见反馈
        public static final String URL_IMG_UPLOAD = URL + "api/certification/img/upload";//图片上传
        public static final String URL_UPDATE_VERSION = URL + "api/version/newest";//版本更新
        public static final String URL_GET_INFORMATION = "api/memberController.do?getInfo";//我的资料获取
        public static final String URL_GET_MEMBERS = "api/member/spread/count";//获取会员个数
        public static final String URL_GET_MESSAGE_LIST = "api/message/get";//获取消息列表
        public static final String URL_CHECK_WITHDRAW = "api/wallet/drawStatus/get";//查询结算状态(已经不需要)
        public static final String URL_ERRORLOG = "api/errorLogController.do?errorLog";//错误日志
        public static final String URL_SUBRUN = "api/wallet/profit/get";//最新分润列表
        public static final String URL_GET_ORDERFORSCAN = "api/channel/oneyardpay/consume";//扫码收款
        public static final String URL_CHANNEL_D0 = "api/channel/getdoform";//银联快捷D0
        public static final String URL_CHANNEL_T1 = "api/channel/getform";//银联快捷T1


    }


}
