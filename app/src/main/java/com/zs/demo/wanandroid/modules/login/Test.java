package com.zs.demo.wanandroid.modules.login;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * Created by zs
 * Date：2018年 08月 08日
 * Time：14:05
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class Test extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.name == Manifest.permission.CAMERA){
                            if (permission.granted){

                            }else{

                            }
                        }else if (permission.name == Manifest.permission.RECORD_AUDIO){

                        }
                    }
                });

        rxPermissions.request(Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {

                    }
                });



    }
}
