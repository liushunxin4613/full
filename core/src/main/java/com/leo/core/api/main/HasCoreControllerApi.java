package com.leo.core.api.main;

import com.leo.core.iapi.api.IDataApi;
import com.leo.core.util.TextUtils;

import java.util.List;

public class HasCoreControllerApi<T extends HasCoreControllerApi> extends HasControllerApi<T> {

    public HasCoreControllerApi(CoreControllerApi controllerApi) {
        super(controllerApi);
    }

    @Override
    public CoreControllerApi controllerApi() {
        return (CoreControllerApi) super.controllerApi();
    }

    protected String getDefTable(){
        throw new NullPointerException("getDefTable不能为空");
    }

    protected IDataApi dataApi(){
        return controllerApi().switchTable(getDefTable());
    }

    protected T remove(String key){
        executeNon(dataApi(), obj -> obj.remove(key));
        return getThis();
    }

    protected T remove(Class clz){
        executeNon(dataApi(), obj -> obj.remove(clz));
        return getThis();
    }

    protected T removeData(String key){
        executeNon(dataApi(), obj -> obj.removeData(key));
        return getThis();
    }

    protected T removeData(Class clz){
        executeNon(dataApi(), obj -> obj.removeData(clz));
        return getThis();
    }

    protected  <V> T saveData(V value){
        executeNon(dataApi(), obj -> obj.saveData(value));
        return getThis();
    }

    protected  <V> T saveData(List<V> value){
        executeNon(dataApi(), obj -> obj.saveData(value));
        return getThis();
    }

    protected  <V> T saveData(String key, V value){
        executeNon(dataApi(), obj -> obj.saveData(key, value));
        return getThis();
    }

    protected  <V> T saveData(String key, List<V> value){
        executeNon(dataApi(), obj -> obj.saveData(key, value));
        return getThis();
    }

    protected <R> R getBean(String key) {
        return getBean(key, null);
    }

    protected <R> R getBean(Class<R> clz) {
        return getBean(null, clz);
    }

    protected <R> R getBean(String key, Class<R> clz) {
        if(TextUtils.isEmpty(key)){
            if(clz == null){
                return null;
            } else {
                return (R) getExecute(dataApi(), null, obj -> obj.getBean(clz));
            }
        } else {
            if(clz == null){
                return (R) getExecute(dataApi(), null, obj -> obj.getString(key));
            } else {
                return (R) getExecute(dataApi(), null, obj -> obj.getBean(key, clz));
            }
        }
    }

    protected <R> List<R> getBeanData(String key) {
        return getBeanData(key, null);
    }

    protected <R> List<R> getBeanData(Class<R> clz) {
        return getBeanData(null, clz);
    }

    protected <R> List<R> getBeanData(String key, Class<R> clz) {
        if(TextUtils.isEmpty(key)){
            if(clz == null){
                return null;
            } else {
                return (List<R>) getExecute(dataApi(), null, obj -> obj.getBeanData(clz));
            }
        } else {
            if(clz == null){
                return (List<R>) getExecute(dataApi(), null, obj -> obj.getStringData(key));
            } else {
                return (List<R>) getExecute(dataApi(), null, obj -> obj.getBeanData(key, clz));
            }
        }
    }

    protected void ee(CharSequence text, Object obj){
        controllerApi().ee(text, obj);
    }

    protected void show(CharSequence text){
        controllerApi().show(text);
    }

}