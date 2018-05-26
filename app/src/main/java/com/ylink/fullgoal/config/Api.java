package com.ylink.fullgoal.config;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

public interface Api {

    //Android通用

    /**
     * 下载文件
     */
    @GET
    Observable<ResponseBody> downloadFile(@Url String url);

    /**
     * 上传图片
     */
    @POST
    <B> Observable<ResponseBody> upload(@Url String url, @Body RequestBody body);

    /**
     * GET
     */
    @GET("{path}")
    Observable<ResponseBody> get(@Path("path") String path, @QueryMap Map<String, String> map);

    /**
     * POST
     */
    @FormUrlEncoded
    @POST("{path}")
    Observable<ResponseBody> post(@Path("path") String path, @FieldMap Map<String, String> map);

    //自有

    /**
     * 以BASE64上传图片
     */
    @FormUrlEncoded
    @POST("uploadImage")
    <B> Observable<ResponseBody> uploadBase64Image(@Field("image") String image);

}
