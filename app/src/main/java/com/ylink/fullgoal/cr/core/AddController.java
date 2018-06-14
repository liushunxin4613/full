package com.ylink.fullgoal.cr.core;

import com.leo.core.iapi.inter.IReturnAction;
import com.leo.core.util.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加更多控制器
 */
public abstract class AddController<T extends AddController, DB> extends BaseController<T, DB> {

    private List<DB> data;

    public AddController() {
        data = new ArrayList<>();
    }

    @Override
    public T initDB(DB db) {
        if(db != null){
            data.add(db);
        }
        return super.initDB(db);
    }

    /**
     * 数据获取action
     * @param <A> A
     * @return action
     */
    protected <A> IReturnAction<DB, A> getDBAction(){
        return null;
    }

    /**
     * 获得过滤数据
     * @param args args
     * @return 过滤数据
     */
    public <A> List<A> getFilterDBData(IReturnAction<DB, A> action, String... args) {
        if (action != null && !TextUtils.isEmpty(data) && !TextUtils.isEmpty(args)) {
            List<A> filterData = new ArrayList<>();
            execute(args, key -> execute(data, item -> {
                DB db = getFilterDB(key, item);
                if(db != null){
                    A a = action.execute(db);
                    if(a != null){
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
     * @param args args
     * @return 过滤数据
     */
    public List<DB> getFilterDBData(String... args) {
        if(TextUtils.isEmpty(args)){
            return data;
        }
        return getFilterDBData(db -> db, args);
    }

    /**
     * 过滤参数获得db
     * @param key 过滤参数
     * @param db db
     * @return db
     */
    protected abstract DB getFilterDB(String key, DB db);

    @Override
    public List<DB> getViewBean() {
        return getFilterDBData();
    }

}
