package com.zs.demo.commonlib.request;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zs
 * Date：2018年 01月 17日
 * Time：11:26
 * —————————————————————————————————————
 * About: 请求简化帮助类
 * —————————————————————————————————————
 */
public class RequestHelper {

    public static Observable getObservable(Observable request){
        return request.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

//    public <T> void create(final BaseResponse<T> response){
//
//        RequestApi.getInstance().getService()
//                .loginAndroid("", "")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new DefaultObserver<response>() {
//
//                });
//
//    }
}
