package com.leo.core.iapi.main;

import com.leo.core.api.inter.MsgSubscriber;

import java.util.Map;

import rx.Observable;

public interface IHttpApi<T extends IHttpApi> {

    /**
     * 创建api
     *
     * @param url root url
     * @param clz clz
     * @param <B> <B>
     */
    <B> B create(String url, Class<B> clz);

    /**
     * 获取驱动器
     *
     * @param url url
     * @param <B> B
     * @return R
     */
    <B> B getApi(String url);

    /**
     * Observable.Transformer
     *
     * @param <B> B
     * @param <M> M
     * @return Observable.Transformer
     */
    <B, M> Observable.Transformer<B, M> transformer();

    /**
     * new Subscriber
     *
     * @param <B> B
     * @return Subscriber
     */
    <B> MsgSubscriber<T, B> newSubscriber();

    /**
     * 观察
     *
     * @param bean    bean
     * @param type    type
     * @param baseUrl baseUrl
     * @param path    path
     * @param map     map
     * @param what    what
     * @param tag     tag
     * @param <B>     <B>
     * @return 本身
     */
    <B> T observable(B bean, String type, String baseUrl, String path,
                     Map<String, String> map, int what, String tag);

    /**
     * 观察
     *
     * @param observable observable
     * @param type       type
     * @param baseUrl    baseUrl
     * @param path       path
     * @param map        map
     * @param what       what
     * @param tag        tag
     * @param <B>        <B>
     * @return 本身
     */
    <B> T observable(Observable<B> observable, String type, String baseUrl, String path,
                     Map<String, String> map, int what, String tag);

}
