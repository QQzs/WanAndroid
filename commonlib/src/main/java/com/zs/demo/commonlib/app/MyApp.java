package com.zs.demo.commonlib.app;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zs.demo.commonlib.utils.SpUtil;

/**
 * Created by zs
 * Date：2018年 01月 02日
 * Time：17:52
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class MyApp extends Application {

    public static MyApp mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        initArouter();
        SpUtil.init(this,Constant.APP_DATA);
    }

    public static MyApp getAppContext(){
        return mApplication;
    }

    private void initArouter() {
        //组件化ARouter初始化
        if (Constant.DEBUG) {
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
    }

}
