package com.zs.demo.wanandroid.request;


import com.zs.demo.wanandroid.modules.login.bean.LoginBean;
import com.zs.demo.wanandroid.modules.login.bean.RegisterBean;
import com.zs.project.bean.android.ArticleBanner;
import com.zs.project.bean.android.ArticleList;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by zs
 * Date：2017年 09月 22日
 * Time：10:34
 * —————————————————————————————————————
 * About: 请求接口
 * —————————————————————————————————————
 */

public interface RequestService {

    /**
     * 玩android 登录
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseResponse<LoginBean>> loginAndroid(@Field("username") String username,
                                                     @Field("password") String password);

    /**
     * 玩android 注册
     * @param username
     * @param password
     * @param repassword
     * @return
     */
    @FormUrlEncoded
    @POST("user/register")
    Observable<BaseResponse<RegisterBean>> registerAndroid(@Field("username") String username,
                                                           @Field("password") String password,
                                                           @Field("repassword") String repassword);

    /**
     * 玩android 首页banner
     */
    @GET("/banner/json")
    Observable<BaseResponse<List<ArticleBanner>>> getArticleBanner();

    /**
     * 玩android 首页数据
     * @param page page
     */
    @GET("article/list/{page}/json")
    Observable<BaseResponse<ArticleList>> getArticleList(@Path("page") int page);

    /**
     * 玩android 收藏列表
     * @param page page
     */
    @GET("lg/collect/list/{page}/json")
    Observable<BaseResponse<ArticleList>> getCollectList(@Path("page") int page);

    /**
     * 玩android 收藏文章
     * @param id id
     */
    @POST("lg/collect/{id}/json")
    Observable<BaseResponse<Object>> collectArticle(@Path("id") int id);

    /**
     * 玩android 取消收藏文章
     * @param id id
     */
    @POST("lg/uncollect_originId/{id}/json")
    Observable<BaseResponse<Object>> unCollectArticle(@Path("id") int id);

    /**
     * 玩android 取消收藏文章
     * @param id id
     * @param originId originId
     */
    @FormUrlEncoded
    @POST("lg/uncollect/{id}/json")
    Observable<BaseResponse<Object>> unCollectArticleList(@Path("id") int id, @Field("originId") int originId);


    /**
     *
     * 测试请求
     *
     */
    @FormUrlEncoded
    @POST("user/login")
    Call<ResponseBody> login(@Field("username") String username,
                             @Field("password") String password);

    @GET("article/list/{page}/json")
    Call<ResponseBody> getArticleListTest(@Path("page") int page);

    @GET("lg/collect/list/{page}/json")
    Call<ResponseBody> getColllectListTest(@Path("page") int page);


    @GET("v2/movie/{path}")
    Call<ResponseBody> getTestData(@Path("path") String path, @Query("start") int start, @Query("count") int count);

    @POST("lg/collect/{id}/json")
    Call<ResponseBody> collectArticleTest(@Path("id") int id);

    @GET("v2/movie/subject/{movieId}")
    Call<ResponseBody> getMovieDetailData(@Path("movieId") String movieId);



    @POST("api/uaa/oauth/token")
    Call<ResponseBody> getToken(@Query("username") String username,
                                @Query("password") String password,
                                @Query("grant_type") String grant_type,
                                @Query("prod") String prod);

    @POST("api/uaa/leave")
    Call<ResponseBody> leaveApp();

    @GET("api/uas/open/personandusers/getInfo")
    Call<ResponseBody> getUser(@Query("loginName") String loginName);

    @GET("api/uas/open/resources/findByLoginNameAndProductCode")
    Call<ResponseBody> findProduct(@Query("hasSuperResource") String hasSuperResource,
                                   @Query("loginName") String loginName,
                                   @Query("productCode") String productCode);

}
