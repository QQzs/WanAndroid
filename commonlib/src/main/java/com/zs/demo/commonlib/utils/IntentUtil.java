package com.zs.demo.commonlib.utils;

import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zs.demo.commonlib.app.Constant;
import com.zs.demo.commonlib.app.RouterPath;

/**
 * Created by zs
 * Date：2019年 01月 09日
 * Time：17:37
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class IntentUtil {

    public static boolean isLogin(String ... flag){
        if (TextUtils.isEmpty(SpUtil.getString(Constant.APP_USER_ID,null))){
            turnToLogin(flag);
            return false;
        } else{
            return true;
        }
    }

    public static void turnToLogin(String ... flag){
        String type = "";
        if (flag != null && flag.length > 0){
            type = flag[0];
        }
        ARouter.getInstance().build(RouterPath.LOGIN_ACTIVITY)
                .withString(FieldUtil.FLAG , type)
                .navigation();
    }

}
