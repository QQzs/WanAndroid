package com.zs.demo.wanandroid;

import android.app.Application;

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
    }

    public static MyApp getAppContext(){
        return mApplication;
    }


}
