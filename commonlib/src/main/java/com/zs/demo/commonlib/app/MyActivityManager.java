package com.zs.demo.commonlib.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;

import java.util.List;
import java.util.Stack;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 *
 * @author
 * @version 1.0
 * @created 2012-3-21
 */
public class MyActivityManager {

    private static Stack<Activity> activityStack;
    private static MyActivityManager instance;

    private MyActivityManager() {
    }

    /**
     * 单一实例
     */
    public static MyActivityManager getActivityManager() {
        if (instance == null) {
            instance = new MyActivityManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        if (activityStack != null) {
            Activity activity = activityStack.lastElement();
            return activity;
        } else {
            return null;
        }
    }

    /**
     * 获取倒数第二个activity
     *
     * @return
     */
    public Activity getLastSecondActivity() {
        if (activityStack == null) return null;
        int size = activityStack.size();
        if (size > 1) {
            return activityStack.get(size - 2);
        } else {
            return null;
        }
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        if (activityStack != null) {
            Activity activity = activityStack.lastElement();
            finishActivity(activity);
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null && activityStack != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        if (activityStack != null) {
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    finishActivity(activity);
                }
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        if (activityStack != null) {
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                if (null != activityStack.get(i)) {
                    activityStack.get(i).finish();
                }
            }
            activityStack.clear();
        }
    }

    public static void closeBackgroundProcess(Context context) {
//        MyActivityManager activityManager = (MyActivityManager) context
//                .getSystemService(Context.ACTIVITY_SERVICE);
//        List<MyActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager
//                .getRunningAppProcesses();
//        String currentProcess = context.getApplicationInfo().processName;
//        for (MyActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfos) {
//            String processName = appProcessInfo.processName;
//            if (processName.equals(currentProcess)) {
//                System.out.println("ApplicationInfo-->" + processName);
//                activityManager.killBackgroundProcesses(processName);
//                Process.killProcess(appProcessInfo.pid);
//                System.out.println("Killed -->PID:" + appProcessInfo.pid + "--ProcessName:" + processName);
//            }
//        }
    }

    /**
     * 退出应用程序
     */
    public static void AppExit(Context context) {
        try {
            finishAllActivity();
            android.app.ActivityManager activityMgr = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }

    /**
     * 判断当前的应用程序是否在后台运行
     *
     * @param context Context
     * @return true表示当前应用程序在后台运行。false为在前台运行
     */
    public static boolean isApplicationBroughtToBackground(final Context context) {
        android.app.ActivityManager am = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<android.app.ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
}