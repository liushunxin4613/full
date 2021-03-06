package com.ylink.fullgoal.cr.core;

import android.support.annotation.NonNull;

import com.leo.core.iapi.inter.IReturnAction;
import com.leo.core.iapi.main.IOnCom;
import com.leo.core.util.TextUtils;

import java.util.ArrayList;
import java.util.List;

import static com.ylink.fullgoal.config.ComConfig.UPDATE;

/**
 * 添加更多控制器
 */
public abstract class AddController<T extends AddController, DB> extends BaseController<T, DB, List<DB>> {

    private List<DB> data;

    public AddController() {
        data = new ArrayList<>();
    }

    @Override
    public T initDB(DB db) {
        add(db);
        notifyDataChanged();
        return super.initDB(db);
    }

    /**
     * 初始化data
     *
     * @param data data
     * @return 本身
     */
    public T initDB(List<DB> data) {
        clear();
        addAll(data);
        notifyDataChanged();
        return getThis();
    }

    protected DB onAddDB(DB db) {
        return db;
    }

    @Override
    public T clear() {
        getData().clear();
        return super.clear();
    }

    protected void add(DB db) {
        executeNon(onAddDB(db), obj -> getData().add(obj));
    }

    protected void addAll(List<DB> data) {
        execute(data, this::add);
    }

    protected void notifyDataChanged(){
    }

    @NonNull
    @Override
    protected List<DB> getNoneUB() {
        return new ArrayList<>();
    }

    public List<DB> getData() {
        return data;
    }

    public T remove(DB db, IOnCom action) {
        if (db != null && action != null && !TextUtils.isEmpty(getData())) {
            if (getData().contains(db)) {
                getData().remove(db);
                action.onCom(0, UPDATE, null);
            }
        }
        return getThis();
    }

    /**
     * 数据获取action
     *
     * @param <A> A
     * @return action
     */
    protected <A> IReturnAction<DB, A> getDBAction() {
        return null;
    }

    /**
     * 获得过滤数据
     *
     * @param args args
     * @return 过滤数据
     */
    public <A> List<A> getFilterDBData(IReturnAction<DB, A> action, String... args) {
        if (action != null && !TextUtils.isEmpty(getData()) && !TextUtils.isEmpty(args)) {
            List<A> filterData = new ArrayList<>();
            execute(args, key -> execute(getData(), item -> {
                DB db = getFilterDB(key, item);
                if (db != null) {
                    A a = action.execute(db);
                    if (a != null) {
                        filterData.add(a);
                    }
                }
            }));
            return filterData;
        }
        return null;
    }

    /**
     * 获得过滤数据
     *
     * @param args args
     * @return 过滤数据
     */
    public List<DB> getFilterDBData(String... args) {
        if (TextUtils.isEmpty(args)) {
            return getData();
        }
        return getFilterDBData(db -> db, args);
    }

    /**
     * 过滤参数获得db
     *
     * @param key 过滤参数
     * @param db  db
     * @return db
     */
    protected abstract DB getFilterDB(String key, DB db);

    @Override
    public List<DB> getViewBean() {
        return getFilterDBData();
    }

    @Override
    protected List<DB> getOnUB(String key) {
        return getData();
    }

    public boolean isEmpty(){
        return TextUtils.isEmpty(getData());
    }

}