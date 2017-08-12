package com.betterda.betterdapay.http;

import com.betterda.betterdapay.javabean.BankCard;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.CreateOrderEntity;
import com.betterda.betterdapay.javabean.Income;
import com.betterda.betterdapay.javabean.Information;
import com.betterda.betterdapay.javabean.MemberCounts;
import com.betterda.betterdapay.javabean.Messages;
import com.betterda.betterdapay.javabean.Rating;
import com.betterda.betterdapay.javabean.RatingCalculateEntity;
import com.betterda.betterdapay.javabean.ShareInfo;
import com.betterda.betterdapay.javabean.TransactionRecord;
import com.betterda.betterdapay.javabean.TuiGuang;
import com.betterda.betterdapay.javabean.UpdateCondition;
import com.betterda.betterdapay.javabean.UserInfo;
import com.betterda.betterdapay.javabean.Wallet;
import com.betterda.betterdapay.javabean.WithDraw;
import com.betterda.betterdapay.javabean.WithDrawStatus;
import com.betterda.betterdapay.util.Constants;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * 封装retrofit请求需要的接口
 * Created by Administrator on 2016/7/29.
 */
public interface NetService {

    /*使用全路径复写baseUrl，适用于非统一baseUrl的场景
    @Headers("Cache-Control: public, max-age=3600") //标注缓存的时间,单位秒,一些显示的不常改动的可以设置
    @GET
    Call<ResponseBody> v3(@Url String url);

    */

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
                                                  @Field("inviteCode") String inviteCode,
                                                  @Field("appCode") String appCode);

    /**
     * 短信验证
     *
     * @param phone
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_SENDMSG)
    Observable<BaseCallModel<String>> getSendMsg(@Field("account") String phone,
                                                 @Field("appCode") String appCode
                                                 );

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
                                                 @Field("password") String password,
                                                 @Field("appCode") String appCode);

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
                                                   @Field("password") String password,
                                                   @Field("appCode") String appCode);


    /**
     * 生成手机控件付款订单
     * @param account
     * @param txnAmt
     * @param accNo
     * @param channelId
     * @param paymentType 10收款  20付款
     * @param appCode
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_ORDER_CREATE)
    Observable<BaseCallModel<String>> getOrder(@Field("account") String account,
                                                          @Field("txnAmt") String txnAmt,
                                                          @Field("accNo") String accNo,
                                                          @Field("channelId") String channelId,
                                                          @Field("rankId") String rankId,
                                                          @Field("paymentType") String paymentType,
                                                          @Field("appCode") String appCode);

    /**
     * 扫描收款
     *
     * @param account
     * @param amount
     * @param body
     * @param payType 1为支付宝 2为微信
     * @return
     */

    @FormUrlEncoded
    @POST(Constants.Url.URL_GET_ORDERFORSCAN)
    Observable<BaseCallModel<String>> getOrderForScan(@Field("account") String account,
                                                      @Field("txnAmt") String amount,
                                                      @Field("body") String body,
                                                      @Field("walletType") String payType,
                                                      @Field("longitude") String longitude,
                                                      @Field("latitude") String latitude,
                                                      @Field("province") String province,
                                                      @Field("city") String city,
                                                      @Field("area") String area,
                                                      @Field("street") String street,
                                                      @Field("channelId") String channelId,
                                                      @Field("appCode") String appCode);

    /**
     * 获取各等级费率
     *
     * @param
     * @return
     */
    @Headers("Cache-Control: public, max-age=1800")
    @FormUrlEncoded
    @POST(Constants.Url.URL_RATING)
    Observable<BaseCallModel<List<Rating>>> getRating(@Field("appCode") String appCode);


    /**
     * 获取我的费率
     *
     * @param account
     * @param
     * @return
     */
    @Headers("Cache-Control: public, max-age=1800")
    @FormUrlEncoded
    @POST(Constants.Url.URL_MY_RATING)
    Observable<BaseCallModel<List<Rating.RateDetail>>> getRatingForMe(@Field("account") String account,
                                                                      @Field("appCode") String appCode);

    /**
     * 获取我的费率用于计算(暂时不用)
     *
     * @param account
     * @param
     * @return
     */
    @Headers("Cache-Control: public, max-age=1800")
    @FormUrlEncoded
    @POST(Constants.Url.URL_MY_RATINGS)
    Observable<BaseCallModel<List<RatingCalculateEntity>>> getRatingForCalculate(@Field("account") String account,
                                                                                 @Field("appCode") String appCode);


    /**
     * 获取升级条件接口
     *
     * @param account
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_UPDATE_CONDITION)
    Observable<BaseCallModel<List<UpdateCondition>>> getUpdateCondition(@Field("account") String account,
                                                                        @Field("appCode") String appCode
    );


    /**
     * 结算
     *
     * @param account
     * @param money
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_JIESUAN)
    Observable<BaseCallModel<WithDraw>> getJiesuan(@Field("drawAccount") String account,
                                                   @Field("drawCash") String money,
                                                   @Field("appCode") String appCode
    );


    /**
     * 银行卡添加
     *
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_BANK_ADD)
    Observable<BaseCallModel<String>> getBandAdd(@Field("account") String account,
                                                 @Field("realName") String realname,
                                                 @Field("idCard") String idcard,
                                                 @Field("bankName") String bankname,
                                                 @Field("bankCard") String bankcard,
                                                 @Field("mobile") String mobile,
                                                 @Field("appCode") String appCode
    );

    /**
     * 银行卡查询
     *
     * @param account
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_BANK_GET)
    Observable<BaseCallModel<List<BankCard>>> getBandGet(@Field("account") String account,
                                                         @Field("appCode") String appCode
    );

    /**
     * 银行卡删除
     *
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_BANK_DELETE)
    Observable<BaseCallModel<List<BankCard>>> getBandDelete(@Field("cardId") String cardId,
                                                            @Field("appCode") String appCode
    );

    /**
     * 获取分享链接
     *
     * @param account
     * @return
     */
    @Headers("Cache-Control: public, max-age=3600")
    @FormUrlEncoded
    @POST(Constants.Url.URL_CDOE_GET)
    Observable<BaseCallModel<List<ShareInfo>>> getCode(@Field("account") String account,
                                                       @Field("appCode") String appCode

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
     * @param identityPositive 身份证正面照
     * @param identityNegative 身份证反面照
     * @param handIdentity     手持身份证
     * @param cardPositive     银行卡正面照
     * @param cardNegative     银行卡反面照
     * @param handCard         手持银行卡
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_AUTH_ADD)
    Observable<BaseCallModel<String>> getAuth(@Field("account") String account,
                                              @Field("realname") String realName,
                                              @Field("idcard") String identityCard,
                                              @Field("bankcard") String cardNum,
                                              @Field("bankname") String bank,
                                              @Field("mobile") String number,
                                              @Field("idcardfront") String identityPositive,
                                              @Field("idcardback") String identityNegative,
                                              @Field("handidcard") String handIdentity,
                                              @Field("bankcardfront") String cardPositive,
                                              @Field("bankcardback") String cardNegative,
                                              @Field("handbankcard") String handCard,
                                              @Field("province") String province,
                                              @Field("city") String city,
                                              @Field("bankCode") String bankCode,
                                              @Field("appCode") String appCode

    );


    /**
     * 我的推广
     *
     * @param account
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_SUB_GET)
    Observable<BaseCallModel<List<TuiGuang>>> getSub(@Field("account") String account,
                                                     @Field("start") String pageNo,
                                                     @Field("length") String pageSize,
                                                     @Field("appCode") String appCode

    );

    /**
     * 我的钱包
     *
     * @param account
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_WALLET_GET)
    Observable<BaseCallModel<Wallet>> getWallet(@Field("account") String account,
                                                @Field("appCode") String appCode

    );

    /**
     * 意见反馈
     *
     * @param account
     * @param content 反馈的内容
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_FEEDBACK_ADD)
    Observable<BaseCallModel<String>> getFeedBack(@Field("account") String account,
                                                  @Field("feedback") String content,
                                                  @Field("appCode") String appCode
    );

    /**
     * 我的资料获取
     *
     * @param account
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_GET_INFORMATION)
    Observable<BaseCallModel<Information>> getInformation(@Field("account") String account

    );


    /**
     * @author : lyf
     * @创建日期： 2017/6/16
     * @功能说明：获取会员个数
     */
    @FormUrlEncoded
    @POST(Constants.Url.URL_GET_MEMBERS)
    Observable<BaseCallModel<MemberCounts>> getMemberCounts(@Field("account") String account,
                                                            @Field("appCode") String appCode);

    /**
     * 图片上传
     *
     * @param account
     * @param file
     * @return
     */
    @Multipart
    @POST(Constants.Url.URL_IMG_UPLOAD)
    Observable<BaseCallModel<String>> getImgUpload(@Part("account") RequestBody account,
                                                   @Part MultipartBody.Part file,
                                                   @Part("appCode") RequestBody appCode
    );


    /**
     * 版本更新
     *
     * @return
     */

    @FormUrlEncoded
    @POST(Constants.Url.URL_UPDATE_VERSION)
    Observable<BaseCallModel<String>> getUpdate(@Field("versionNo") String version,
                                                @Field("appCode") String appCode);


    /**
     * 获取消息列表
     *
     * @return
     */

    @FormUrlEncoded
    @POST(Constants.Url.URL_GET_MESSAGE_LIST)
    Observable<BaseCallModel<List<Messages>>> getMessageList(@Field("account") String account,
                                                             @Field("start") String start,
                                                             @Field("length") String length,
                                                             @Field("appCode") String appCode
    );

    /**
     * 获取分润明细
     *
     * @return
     */

    @FormUrlEncoded
    @POST(Constants.Url.URL_GET_INCOME)
    Observable<BaseCallModel<List<Income>>> getIncomeList(@Field("account") String account,
                                                          @Field("start") String pageNo,
                                                          @Field("length") String pageSize,
                                                          @Field("appCode") String appCode
    );

    /**
     * 获取结算明细
     *
     * @return
     */

    @FormUrlEncoded
    @POST(Constants.Url.URL_GET_JIESUAN)
    Observable<BaseCallModel<List<WithDraw>>> getAmountList(@Field("account") String account,
                                                            @Field("start") String start,
                                                            @Field("length") String length,
                                                            @Field("appCode") String appCode
    );

    /**
     * 查询结算状态
     *
     * @return
     */

    @FormUrlEncoded
    @POST(Constants.Url.URL_CHECK_WITHDRAW)
    Observable<BaseCallModel<WithDrawStatus>> getCheckWithdraw(@Field("account") String account,
                                                               @Field("appCode") String appCode
    );


    /**
     * 获取账单记录
     *
     * @return
     */

    @FormUrlEncoded
    @POST(Constants.Url.URL_ORDER_GET)
    Observable<BaseCallModel<List<TransactionRecord>>> getOrders(@Field("account") String account,
                                                                 @Field("startTime") String startTime,
                                                                 @Field("endTime") String endTime,
                                                                 @Field("start") String pageNo,
                                                                 @Field("length") String pageSize,
                                                                 @Field("appCode") String appCode

    );

    /**
     * 错误日志
     *
     * @return
     */

    @FormUrlEncoded
    @POST(Constants.Url.URL_ERRORLOG)
    Observable<BaseCallModel<String>> getErrorlog(@Field("type") String type,
                                                  @Field("content") String content,
                                                  @Field("appCode") String appCode

    );

    /**
     * 获取最新分润列表
     *
     * @return
     */

    @FormUrlEncoded
    @POST(Constants.Url.URL_SUBRUN)
    Observable<BaseCallModel<List<String>>> getSubRun(@Field("start") String start,
                                                  @Field("length") String length,
                                                  @Field("appCode") String appCode

    );


}
