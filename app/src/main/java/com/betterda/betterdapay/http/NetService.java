package com.betterda.betterdapay.http;

import com.betterda.betterdapay.javabean.BankCard;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.EWeiMa;
import com.betterda.betterdapay.javabean.FenRun;
import com.betterda.betterdapay.javabean.Information;
import com.betterda.betterdapay.javabean.MemberCounts;
import com.betterda.betterdapay.javabean.Messages;
import com.betterda.betterdapay.javabean.MyShangHu;
import com.betterda.betterdapay.javabean.Order;
import com.betterda.betterdapay.javabean.OrderALL;
import com.betterda.betterdapay.javabean.Rating;
import com.betterda.betterdapay.javabean.RatingCalculateEntity;
import com.betterda.betterdapay.javabean.TuiGuang;
import com.betterda.betterdapay.javabean.UpdateCondition;
import com.betterda.betterdapay.javabean.UserInfo;
import com.betterda.betterdapay.javabean.Wallet;
import com.betterda.betterdapay.util.Constants;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * 封装retrofit请求需要的接口
 * Created by Administrator on 2016/7/29.
 */
public interface NetService {
    /**
     * 注册
     *
     * @param account
     * @param password
     * @param inviteCode
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_REGISTER)
    Observable<BaseCallModel<String>> getRegister(@Field("account") String account,
                                                  @Field("password") String password,
                                                  @Field("inviteCode") String inviteCode);

    /**
     * 短信验证
     *
     * @param phone
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_SENDMSG)
    Observable<BaseCallModel<String>> getSendMsg(@Field("phone") String phone);

    /**
     * 登录
     *
     * @param account
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_LOGIN)
    Observable<BaseCallModel<UserInfo>> getLogin(@Field("account") String account,
                                                 @Field("password") String password);

    /**
     * 忘记密码
     *
     * @param account
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_PWD_UPDATE)
    Observable<BaseCallModel<String>> getPwdUpdate(@Field("account") String account,
                                                     @Field("password") String password);


    /**
     * 生成订单
     *
     * @param account   帐号
     * @param type      交易类型
     * @param money
     * @param orderType 订单类型
     * @param channel   支付通道
     * @return
     */

    @FormUrlEncoded
    @POST(Constants.Url.URL_ORDER_CREATE)
    Observable<BaseCallModel<String>> getOrder(@Field("account") String account,
                                               @Field("type") String type,
                                               @Field("money") String money,
                                               @Field("orderType") String orderType,
                                               @Field("channel") String channel);

    /**
     * 账单接口
     *
     * @param account
     * @param token
     * @param orderType
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_ORDER_GET)
    Observable<BaseCallModel<OrderALL>> getOrderGet(@Field("account") String account,
                                                    @Field("token") String token,
                                                    @Field("orderType") String orderType,
                                                    @Field("pageNo") String pageNo,
                                                    @Field("pageSize") String pageSize);

    /**
     * 获取各等级费率
     *
     * @param
     * @return
     */
    @GET(Constants.Url.URL_RATING)
    Observable<BaseCallModel<List<Rating>>> getRating();


    /**
     * 获取我的费率
     *
     * @param account
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_MY_RATING)
    Observable<BaseCallModel<Rating>> getRatingForMe(@Field("account") String account);

    /**
     * 获取我的费率用于计算
     *
     * @param account
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_MY_RATINGS)
    Observable<BaseCallModel<List<RatingCalculateEntity>>> getRatingForCalculate(@Field("account") String account);


    /**
     * 获取升级条件接口
     *
     * @param account

     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_UPDATE_CONDITION)
    Observable<BaseCallModel<List<UpdateCondition>>> getUpdateCondition(@Field("account") String account
                                                    );


    /**
     * 升级到指定接口
     *
     * @param account

     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_UPDATE_TO_RATE)
    Observable<BaseCallModel<String>> getUpdateToRate(@Field("account") String account,
                                                      @Field("rankId") String rateId
    );


    /**
     * 结算
     *
     * @param account
     * @param token
     * @param money
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_JIESUAN)
    Observable<BaseCallModel<String>> getJiesuan(@Field("account") String account,
                                                 @Field("token") String token,
                                                 @Field("money") String money
    );

    /**
     * 银行卡添加
     *
     * @param account
     * @param token
     * @param truename     持卡人姓名
     * @param identitycard 身份证
     * @param bank         所属银行
     * @param cardnum      卡号
     * @param number       预留手机号
     * @param cardType     银行卡类型
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_BANK_ADD)
    Observable<BaseCallModel<String>> getBandAdd(@Field("account") String account,
                                                 @Field("token") String token,
                                                 @Field("cardName") String truename,
                                                 @Field("identityCard") String identitycard,
                                                 @Field("bank") String bank,
                                                 @Field("cardNum") String cardnum,
                                                 @Field("number") String number,
                                                 @Field("cardType") String cardType
    );

    /**
     * 银行卡查询
     *
     * @param account
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_BANK_GET)
    Observable<BaseCallModel<List<BankCard>>> getBandGet(@Field("account") String account,
                                                 @Field("token") String token
    );

    /**
     * 银行卡删除
     *
     * @param account
     * @param token
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_BANK_DELETE)
    Observable<BaseCallModel<String>> getBandDelete(@Field("account") String account,
                                                    @Field("token") String token,
                                                    @Field("id") String id
    );

    /**
     * 我的二维码获取
     *
     * @param account
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_CDOE_GET)
    Observable<BaseCallModel<EWeiMa>> getCode(@Field("account") String account,
                                              @Field("token") String token

    );

    /**
     * 实名认证
     *
     * @param account
     * @param realName         认证人姓名
     * @param identityCard     身份证号
     * @param cardNum          银行卡号
     * @param bank             所属银行
     * @param number           预留手机号
     * @param cardType         银行卡类型
     * @param identityPositive 身份证正面照
     * @param identityNegative 身份证反面照
     * @param handIdentity     手持身份证
     * @param cardPositive     银行卡正面照
     * @param cardNegative     银行卡反面照
     * @param token
     * @param handCard         手持银行卡
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_AUTH_ADD)
    Observable<BaseCallModel<String>> getAuth(@Field("account") String account,
                                              @Field("realName") String realName,
                                              @Field("identityCard") String identityCard,
                                              @Field("cardNum") String cardNum,
                                              @Field("bank") String bank,
                                              @Field("number") String number,
                                              @Field("cardType") String cardType,
                                              @Field("identityPositive") String identityPositive,
                                              @Field("identityNegative") String identityNegative,
                                              @Field("handIdentity") String handIdentity,
                                              @Field("cardPositive") String cardPositive,
                                              @Field("cardNegative") String cardNegative,
                                              @Field("token") String token,
                                              @Field("handCard") String handCard

    );



    /**
     * 我的推广
     * @param account
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_SUB_GET)
    Observable<BaseCallModel<List<TuiGuang>>> getSub(@Field("account") String account,
                                                @Field("pageNo") String pageNo,
                                                @Field("pageSize") String pageSize

    );

    /**
     * 我的钱包
     * @param account
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_WALLET_GET)
    Observable<BaseCallModel<Wallet>> getWallet(@Field("account") String account

    );

    /**
     * 意见反馈
     * @param account
     * @param content 反馈的内容
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_FEEDBACK_ADD)
    Observable<BaseCallModel<String>> getFeedBack(@Field("account") String account,
                                                @Field("content") String content
    );

    /**
     * 我的资料获取
     * @param account
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_GET_INFORMATION)
    Observable<BaseCallModel<Information>> getInformation(@Field("account") String account,
                                                @Field("token") String token

    );

    /**
     * 搜索接口
     * @param account
     * @param token
     * @param startDate
     * @param endDate
     * @param orderType 收款 1 分润2
     * @param profitType 0 返还分润 1 推广分润
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_SEARCH_POST)
    Observable<BaseCallModel<OrderALL>> getSearch(@Field("account") String account,
                                                @Field("token") String token,
                                                @Field("startDate") String startDate,
                                                @Field("endDate") String endDate,
                                                @Field("orderType") String orderType,
                                                @Field("profitType") String profitType,
                                                @Field("pageNo") String pageNo,
                                                @Field("pageSize") String pageSize
    );

    @FormUrlEncoded
    @POST(Constants.Url.URL_SEARCH_POST)
    Observable<BaseCallModel<List<FenRun>>> getSearch2(@Field("account") String account,
                                                @Field("token") String token,
                                                @Field("startDate") String startDate,
                                                @Field("endDate") String endDate,
                                                @Field("orderType") String orderType,
                                                @Field("profitType") String profitType,
                                                @Field("pageNo") String pageNo,
                                                @Field("pageSize") String pageSize
    );
   /**
    *@author : lyf
    *@创建日期： 2017/6/16
    *@功能说明：获取会员个数
    */
    @FormUrlEncoded
    @POST(Constants.Url.URL_GET_MEMBERS)
    Observable<BaseCallModel<MemberCounts>> getMemberCounts(@Field("account") String account);

    /**
     * 图片上传
     * @param account
     * @param token
     * @param file
     * @return
     */
    @Multipart
    @POST(Constants.Url.URL_IMG_UPLOAD)
    Observable<BaseCallModel<String>> getImgUpload(@Part("account") RequestBody account,
                                                @Part("token") RequestBody token,
                                                @Part MultipartBody.Part file
    );


    /**
     * 版本更新

     * @return
     */

    @FormUrlEncoded
    @POST(Constants.Url.URL_UPDATE_VERSION)
    Observable<BaseCallModel<String>> getUpdate(@Field("versionNo") String version);


    /**
     *获取消息列表

     * @return
     */

    @FormUrlEncoded
    @POST(Constants.Url.URL_GET_MESSAGE_LIST)
    Observable<BaseCallModel<List<Messages>>> getMessageList(@Field("account") String account,
                                                       @Field("pageNo") String pageNo,
                                                       @Field("pageSize") String pageSize
                                                     );

}
