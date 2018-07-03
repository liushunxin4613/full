package com.leo.core.net;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface Api {

    //Android通用

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

    /**
     * jsonPost
     */
    @Headers({"Accept: application/json", "Content-Type: application/json"})//需要添加头
    @POST("{path}")
    Observable<ResponseBody> jsonPost(@Path("path") String path, @Body RequestBody body);

    /**
     * bodyPost
     */
    @POST("{path}")
    <B> Observable<ResponseBody> bodyPost(@Path("path") String path, @Body RequestBody body);

}