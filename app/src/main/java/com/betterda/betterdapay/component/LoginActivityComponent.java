package com.betterda.betterdapay.component;

import com.betterda.betterdapay.Score.LoginActivityScore;
import com.betterda.betterdapay.activity.LoginActivity;
import com.betterda.betterdapay.modules.LoginActivityModules;

import dagger.Component;

/**
 * Created by Administrator on 2016/8/16.
 */
@LoginActivityScore
@Component(dependencies = AppComponent.class,modules = LoginActivityModules.class)
public interface LoginActivityComponent {

    void inject(LoginActivity loginActivity);


}
