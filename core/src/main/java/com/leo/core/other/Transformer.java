package com.leo.core.other;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Transformer<T> implements Observable.Transformer<T, T> {

    private Observable.Transformer<T, T> transformer;

    public Transformer() {
    }

    public Transformer(Observable.Transformer<T, T> transformer) {
        this.transformer = transformer;
    }

    @Override
    public Observable<T> call(Observable<T> source) {
        if (transformer != null)
            return transformer.call(source).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        else
            return source.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
    }

}
