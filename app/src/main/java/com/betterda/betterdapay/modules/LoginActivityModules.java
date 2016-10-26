package com.betterda.betterdapay.modules;

import com.betterda.betterdapay.Score.LoginActivityScore;
import com.betterda.betterdapay.activity.LoginActivity;
import com.betterda.betterdapay.javabean.FenRun;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.mylibrary.ShapeLoadingDialog;

import javax.inject.Scope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/8/16.
 */

@Module
public class LoginActivityModules {
    private LoginActivity loginActivity;


    public LoginActivityModules(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }


    @LoginActivityScore
    @Provides
    public  LoginActivity provideloginActivity() {
        return this.loginActivity;
    }



    @Provides
    public ShapeLoadingDialog providedialog() {
        return UtilMethod.createDialog(loginActivity, "正在登录...");
    }
}
