package com.android.ql.lf.carappclient.application;

import android.support.multidex.MultiDexApplication;

import com.android.ql.lf.carappclient.component.AppComponent;
import com.android.ql.lf.carappclient.component.AppModule;
import com.android.ql.lf.carappclient.component.DaggerAppComponent;
import com.android.ql.lf.carappclient.utils.Constants;
import com.tencent.bugly.Bugly;

/**
 * Created by lf on 18.4.10.
 *
 * @author lf on 18.4.10
 */

public class CarAppClientApplication extends MultiDexApplication{
    private AppComponent appComponent;

    public static CarAppClientApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        Bugly.init(this, Constants.BUGLY_APP_ID, false);
        application = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
//        initHX();
    }

//    private void initHX() {
//        EaseUI.getInstance().init(this,null);
//    }

    public static CarAppClientApplication getInstance() {
        return application;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
