package com.leo.core.iapi.main;

import com.leo.core.iapi.IClassAddApi;

import rx.Observable;
import rx.Subscriber;

public interface IHttpApi<T extends IHttpApi> extends IClassAddApi<T> {

    /**
     * 创建api
     * @param url root url
     * @param clz clz
     * @param <R> <R>
     * @return
     */
    <R> R create(String url, Class<R> clz);

    /**
     * 获取驱动器
     * @param url url
     * @param <R> R
     * @return R
     */
    <R> R getApi(String url);

    /**
     * 获取驱动器
     * @param <R> R
     * @return R
     */
    <R> R getApi();

    /**
     * Observable.Transformer
     * @param <R> R
     * @param <M> M
     * @return Observable.Transformer
     */
    <R, M> Observable.Transformer<R, M> transformer();

    /**
     * new Observable.Transformer
     * @param <R> R
     * @param <M> M
     * @return Observable.Transformer
     */
    <R, M> Observable.Transformer<R, M> newTransformer();

    /**
     * Subscriber
     * @param <R> R
     * @return Subscriber
     */
    <R> Subscriber<R> subscriber();

    /**
     * new Subscriber
     * @param <R> R
     * @return Subscriber
     */
    <R> Subscriber<R> newSubscriber();

    /**
     * 设置setSubscriber
     * @param newSubscriber newSubscriber
     * @param <R> <R>
     * @return 本身
     */
    <R> T setNewSubscriber(Subscriber<R> newSubscriber);

    /**
     * 观察
     * @param observable observable
     * @param <R> R
     * @return 本身
     */
    <R> T observable(Observable<R> observable);

}
