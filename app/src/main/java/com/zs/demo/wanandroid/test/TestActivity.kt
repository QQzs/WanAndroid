package com.zs.demo.wanandroid.test

import android.view.View
import com.zs.demo.wanandroid.R
import com.zs.demo.wanandroid.base.BaseActivity
import com.zs.demo.wanandroid.request.BaseResponse
import com.zs.demo.wanandroid.request.DefaultObserver
import com.zs.demo.wanandroid.request.RequestApi
import com.zs.demo.wanandroid.view.banner.BannerViewData
import com.zs.project.bean.android.ArticleBanner
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_text_layout.*
import java.util.*

class TestActivity : BaseActivity() {

    var data = ArrayList<BannerViewData>()

    override fun setLayoutId(): Int {
        return R.layout.activity_text_layout
    }

    override fun initView() {
    }

    override fun initData() {

        RequestApi.getInstance().service
                .articleBanner
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DefaultObserver<BaseResponse<MutableList<ArticleBanner>>>(this) {

                    override fun onSuccess(response: BaseResponse<MutableList<ArticleBanner>>?) {
                        for (i in 0..4) {
                            data.add(BannerViewData("11$i", "22$i", "11"))
                        }
                        banner_page?.initData(data)
                    }
                })

    }

    override fun onClick(p0: View?) {
    }


}
