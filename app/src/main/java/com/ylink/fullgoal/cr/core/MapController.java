package com.ylink.fullgoal.cr.core;

import java.util.HashMap;
import java.util.Map;

import static com.leo.core.util.TextUtils.check;

public abstract class MapController<T extends MapController, K, DB, UB> extends BaseController<T, DB, UB> {

    private Map<K, DB> map;

    public MapController() {
        map = new HashMap<>();
    }

    @Deprecated
    @Override
    public T initDB(DB db) {
        throw new RuntimeException("initDB(db)方法被禁用,推荐使用initDB(key, db)");
    }

    public T initDB(K key, DB db) {
        put(key, db);
        return super.initDB(db);
    }

    public DB getValue(K key){
        if(check(key)){
            return getMap().get(key);
        }
        return null;
    }

    public Map<K, DB> getMap() {
        return map;
    }

    public T clear(){
        getMap().clear();
        return getThis();
    }

    public T put(K key, DB db){
        if(check(key, db)){
            getMap().put(key, db);
        }
        return getThis();
    }

    public T remove(K key){
        if(check(key)){
            getMap().remove(key);
        }
        return getThis();
    }

    @Override
    public Map<K, DB> getViewBean() {
        return getMap();
    }

}
