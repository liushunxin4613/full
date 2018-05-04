package com.leo.core.factory;

import android.app.Activity;

import com.leo.core.api.ActionApi;
import com.leo.core.iapi.core.IApi;
import com.leo.core.util.LogUtil;

public class ActionApiFactory<T extends ActionApiFactory, A extends IApi> extends ActionApi<T, A> {

    private static ActionApiFactory instance;

    public static ActionApiFactory getInstance(){
        if(instance == null){
            synchronized (ActionApiFactory.class){
                if(instance == null){
                    instance = new ActionApiFactory();
                }
            }
        }
        return instance;
    }

}
