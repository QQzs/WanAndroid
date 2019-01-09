package com.zs.demo.commonlib.request;

import io.reactivex.disposables.Disposable;

/**
 * Created by zhpan on 2017/4/22.
 * 添加订阅者
 */

public interface BaseImpl {

    boolean addRxStop(Disposable disposable);

    boolean addRxDestroy(Disposable disposable);

    void remove(Disposable disposable);

    void showLoading();

    void dismissLoading();
}
