package com.leo.core.helper;

import com.leo.core.api.api.ActionApi;
import com.leo.core.iapi.inter.IAction;

/**
 * 时间处理时间
 */
public class TimeFactory {

    private static TimeFactory instance;
    public static TimeFactory getInstance(){
        if(instance == null){
            synchronized (TimeFactory.class){
                if(instance == null){
                    instance = new TimeFactory();
                }
            }
        }
        return instance;
    }

    private long time;
    private ActionApi actionApi;

    private TimeFactory() {
        actionApi = new ActionApi();
    }

    private long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    private void initTime() {
        this.time = currentTimeMillis();
    }

    private long interval() {
        return time == 0 ? 0 : currentTimeMillis() - time;
    }

    public void start() {
        initTime();
    }

    public void check(long interval, IAction action) {
        if (interval >= 0 && action != null) {
            long offset = interval - interval();
            actionApi.addUI(offset > 0 ? offset : 0, action);
            actionApi.start();
        }
    }

}
