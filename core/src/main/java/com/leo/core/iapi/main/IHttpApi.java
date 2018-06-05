package com.leo.core.iapi.main;

import com.leo.core.api.MsgSubscriber;

import rx.Observable;

public interface IHttpApi<T extends IHttpApi> {

    /**
     * 创建api
     * @param url root url
     * @param clz clz
     * @param <B> <B>
     */
    <B> B create(String url, Class<B> clz);

    /**
     * 获取驱动器
     * @param url url
     * @param <B> B
     * @return R
     */
    <B> B getApi(String url);

    /**
     * 获取驱动器
     * @param <B> B
     * @return R
     */
    <B> B getApi();

    /**
     * Observable.Transformer
     * @param <B> B
     * @param <M> M
     * @return Observable.Transformer
     */
    <B, M> Observable.Transformer<B, M> transformer();

    /**
     * new Observable.Transformer
     * @param <B> B
     * @param <M> M
     * @return Observable.Transformer
     */
    <B, M> Observable.Transformer<B, M> newTransformer();

    /**
     * Subscriber
     * @param <B> B
     * @return Subscriber
     */
    <B> MsgSubscriber<T, B> subscriber();

    /**
     * new Subscriber
     * @param <B> B
     * @return Subscriber
     */
    <B> MsgSubscriber<T, B> newSubscriber();

    /**
     * 设置setSubscriber
     * @param newSubscriber newSubscriber
     * @param <B> <B>
     * @return 本身
     */
    <B> T setNewSubscriber(MsgSubscriber<T, B> newSubscriber);

    /**
     * 观察
     * @param observable observable
     * @param <B> B
     * @return 本身
     */
    <B> T observable(Observable<B> observable);

    /**
     * 观察
     * @param observable observable
     * @param <B> B
     * @return 本身
     */
    <B> T observable(Observable<B> observable, String path, int what, String tag);

}
