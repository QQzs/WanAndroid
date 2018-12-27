package com.zs.demo.wanandroid;

import android.app.Application;

import com.zs.demo.wanandroid.utils.SpUtil;

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
        SpUtil.init(this,SpUtil.APP_DATA);
    }

    public static MyApp getAppContext(){
        return mApplication;
    }


}
