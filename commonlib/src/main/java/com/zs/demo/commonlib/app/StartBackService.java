package com.zs.demo.commonlib.app;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.zs.demo.commonlib.utils.LogUtil;

/**
 * Created by zs
 * Date：2019年 01月 17日
 * Time：16:09
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class StartBackService extends IntentService {

    private static final String ACTION_INIT_WHEN_APP_CREATE = "com.zs.demo.app.service.action.INIT";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public StartBackService() {
        super("StartBackService");
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, StartBackService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        LogUtil.Companion.logShow("onHandleIntent");

    }


}
