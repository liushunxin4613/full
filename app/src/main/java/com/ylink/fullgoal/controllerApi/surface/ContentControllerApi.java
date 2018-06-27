package com.ylink.fullgoal.controllerApi.surface;

import android.view.View;

import com.leo.core.iapi.api.IContentApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class ContentControllerApi<T extends ContentControllerApi, C> extends SurfaceControllerApi<T, C>
        implements IContentApi<T> {

    private View nullView;
    private View errorView;

    public ContentControllerApi(C controller) {
        super(controller);
    }

    @Override
    public T init() {
        showContentView();
        return getThis();
    }

    public T setNullView(View nullView) {
        this.nullView = nullView;
        return getThis();
    }

    public T setErrorView(View errorView) {
        this.errorView = errorView;
        return getThis();
    }

    @Override
    public View getContentView() {
        return null;
    }

    @Override
    public View getNullView() {
        return nullView;
    }

    @Override
    public View getErrorView() {
        return errorView;
    }

    @Override
    public T startLoad(boolean refresh) {
        return getThis();
    }

    @Override
    public T stopLoad(boolean refresh) {
        return getThis();
    }

    protected boolean isContentViewShow() {
        return getContentView() != null && getContentView().isShown();
    }

    protected boolean isNullViewShow() {
        return getNullView() != null && getNullView().isShown();
    }

    protected boolean isErrorViewShow() {
        return getErrorView() != null && getErrorView().isShown();
    }

    protected T hideContentView() {
        setVisibility(getContentView(), View.INVISIBLE);
        return getThis();
    }

    @Override
    public T showContentView() {
        setVisibility(getContentView(), View.VISIBLE);
        setVisibility(View.GONE, getNullView(), getErrorView());
        return getThis();
    }

    @Override
    public T showNullView(boolean refresh) {
        setVisibility(getNullView(), View.VISIBLE);
        setVisibility(View.GONE, getContentView(), getErrorView());
        return getThis();
    }

    @Override
    public T showErrorView() {
        setVisibility(getErrorView(), View.VISIBLE);
        setVisibility(View.GONE, getContentView(), getNullView());
        return getThis();
    }

}
