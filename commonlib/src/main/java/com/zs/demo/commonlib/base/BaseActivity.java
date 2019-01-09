package com.zs.demo.commonlib.base;

import android.app.Activity;
import android.os.Bundle;

import com.zs.demo.commonlib.app.Constant;
import com.zs.demo.commonlib.app.MyActivityManager;
import com.zs.demo.commonlib.utils.SpUtil;

/**
 * Created by zs
 * Date：2017年 09月 25日
 * Time：11:24
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public abstract class BaseActivity extends BaseRxActivity{

    protected Activity mActivity;
    protected String mUserId , mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (setLayoutId() != 0){
            initContentView(setLayoutId());
        }
    }

    protected void initContentView(int layoutResID){
        setContentView(layoutResID);
        mActivity = this;
        mUserId = SpUtil.getString(Constant.APP_USER_ID,"");
        mUserName = SpUtil.getString(Constant.APP_USER_NAME,"");
        MyActivityManager.getActivityManager().addActivity(this);
        /**
         * 初始化一些UI
         */
        initView();

        /**
         * 初始化一些数据
         */
        initData();
    }

    /**
     *
     * @return 返回布局
     */
    protected abstract int setLayoutId();

    public abstract void initView();

    public abstract void initData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyActivityManager.getActivityManager().finishActivity(this);
    }
    
}
