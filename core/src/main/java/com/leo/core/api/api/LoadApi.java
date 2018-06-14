package com.leo.core.api.api;

import com.leo.core.api.core.ThisApi;
import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.iapi.inter.IAction;
import com.leo.core.iapi.api.IContentApi;
import com.leo.core.iapi.api.ILoadApi;
import com.leo.core.iapi.inter.OnAddListener;
import com.leo.core.util.TextUtils;

import java.util.List;

public class LoadApi<T extends LoadApi> extends ThisApi<T> implements ILoadApi<T> {

    private int count;
    private int loadCode;
    private boolean start;
    private boolean refresh;
    private long pageNum = 20;
    private IContentApi contentApi;
    private long socketOutTime = 10000;
    private CoreControllerApi controllerApi;

    public LoadApi(CoreControllerApi controllerApi) {
        this(controllerApi, null);
    }

    public LoadApi(CoreControllerApi controllerApi, IContentApi contentApi) {
        this.controllerApi = controllerApi;
        this.contentApi = contentApi;
        init();
    }

    @Override
    public T init() {
        controllerApi().addOnAddListener(new OnAddListener() {
            @Override
            public <R> void onAdd(R obj) {}
            @Override
            public <R> void onListAdd(List<R> data) {
                count = TextUtils.count(data);
            }
        });
        executeNon(contentApi(), IContentApi::init);
//        add(HttpError.class, e -> onLoad(isRefresh(), State.ERROR, e.getMsg()));
//        add(Completed.class, c -> onCompleted());
        return getThis();
    }

    @Override
    public CoreControllerApi controllerApi() {
        if(controllerApi == null){
            throw new NullPointerException("controllerApi不能为空");
        }
        return controllerApi;
    }

    @Override
    public IContentApi contentApi() {
        return contentApi;
    }

    @Override
    public boolean isStart() {
        return start;
    }

    @Override
    public boolean isRefresh() {
        return refresh;
    }

    @Override
    public int getDataCount() {
        return count;
    }

    public T setSocketOutTime(long socketOutTime) {
        if(socketOutTime > 0){
            this.socketOutTime = socketOutTime;
        }
        return getThis();
    }

    public T setPageNum(long pageNum) {
        if(pageNum > 0) {
            this.pageNum = pageNum;
        }
        return getThis();
    }

    @Override
    public void load(boolean refresh) {
        start = true;
        this.refresh = refresh;
        loadCode = controllerApi().addUI(socketOutTime, (IAction) () -> onLoad(refresh, State.ERROR, controllerApi().formatString("%s数据失败", refresh ? "刷新" : "加载")));
    }

    @Override
    public T onLoad(boolean refresh, State state, String msg) {
        start = false;
        controllerApi().cancel(loadCode);
        if(isStart()){
            switch (state){
                case SUCCESS://成功
                    executeNon(contentApi(), IContentApi::showContentView);
                    break;
                case NULL://空数据
                    executeNon(contentApi(), api -> api.showNullView(refresh));
                    break;
                case ERROR://失败
                    controllerApi().show(msg);
                    executeNon(contentApi(), IContentApi::showErrorView);
                    break;
            }
        }
        executeNon(contentApi(), api -> api.stopLoad(refresh));
        return getThis();
    }

    @Override
    public void onCompleted() {
        if(!isRefresh() && getDataCount() < pageNum){
            executeNon(contentApi(), api -> api.showNullView(false));
        }
        if(isRefresh() && getDataCount() == 0){
            onLoad(isRefresh(), State.NULL, null);
        } else {
            onLoad(isRefresh(), State.SUCCESS, null);
        }
    }

}
