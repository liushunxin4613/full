package com.ylink.fullgoal.controllerApi.surface;

import android.view.View;

import com.leo.core.iapi.api.IContentApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class ContentControllerApi<T extends ContentControllerApi, C> extends SurfaceControllerApi<T, C>
        implements IContentApi<T> {

    private final static int SHOW_CONTENT = 0x101;
    private final static int SHOW_NULL = 0x102;
    private final static int SHOW_ERROR = 0x103;
    private final static int SHOW_NONE = 0x104;

    private View nullView;
    private View errorView;
    private boolean isHideView;
    private int show = SHOW_NONE;

    public ContentControllerApi(C controller) {
        super(controller);
    }

    public void setShow(int show) {
        this.show = show;
    }

    public int getShow() {
        return show;
    }

    @Override
    public void showLoading(boolean hide) {
        super.showLoading(hide);
        if(hide){
            hideViews();
        }
    }

    @Override
    public T init() {
        showContentView();
        return getThis();
    }

    public void setNullView(View nullView) {
        this.nullView = nullView;
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

    @Override
    public T hideViews() {
        isHideView = true;
        initShow();
        setVisibility(View.INVISIBLE, getContentView(), getNullView(), getErrorView());
        return getThis();
    }

    private void initShow(){
        if(show == SHOW_NONE){
            if(getContentView() != null && getContentView().isShown()){
                show = SHOW_CONTENT;
            } else if(getNullView() != null && getNullView().isShown()){
                show = SHOW_NULL;
            } else if(getNullView() != null && getNullView().isShown()){
                show = SHOW_ERROR;
            }
        }
    }

    public synchronized void renewViews() {
        if(isHideView){
            switch (show) {
                case SHOW_CONTENT:
                    showContentView();
                    break;
                case SHOW_NULL:
                    showNullView(true);
                    break;
                case SHOW_ERROR:
                    showErrorView();
                    break;
            }
            isHideView = false;
        }
    }

    @Override
    public T hideContentView() {
        setVisibility(getContentView(), View.INVISIBLE);
        return getThis();
    }

    @Override
    public T showContentView() {
        setShow(SHOW_CONTENT);
        if (isNoDialogShowing()) {
            setVisibility(getContentView(), View.VISIBLE);
            setVisibility(View.INVISIBLE, getNullView(), getErrorView());
        }
        return getThis();
    }

    @Override
    public T showNullView(boolean refresh) {
        setShow(SHOW_NULL);
        if (isNoDialogShowing()) {
            setVisibility(getNullView(), View.VISIBLE);
            setVisibility(View.INVISIBLE, getContentView(), getErrorView());
        }
        return getThis();
    }

    @Override
    public T showErrorView() {
        setShow(SHOW_ERROR);
        if (isNoDialogShowing()) {
            setVisibility(getErrorView(), View.VISIBLE);
            setVisibility(View.INVISIBLE, getContentView(), getNullView());
        }
        return getThis();
    }

}