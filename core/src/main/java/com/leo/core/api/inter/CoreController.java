package com.leo.core.api.inter;

import android.support.annotation.NonNull;

import com.leo.core.api.api.VesApi;
import com.leo.core.iapi.inter.IBolAction;
import com.leo.core.iapi.inter.IController;
import com.leo.core.iapi.inter.IReturnAction;
import com.leo.core.util.TextUtils;

public abstract class CoreController<T extends CoreController, DB, UB> extends VesApi<T> implements
        IController<T, DB, UB> {

    private String field;
    private DB db;

    @Override
    public DB getVo() {
        return super.getVo();
    }

    @Override
    public DB newVo() {
        return getDB();
    }

    @Override
    public T initField(String field) {
        this.field = field;
        return getThis();
    }

    @Override
    public T initDB(DB db) {
        this.db = db;
        return getThis();
    }

    @Override
    public T clear() {
        this.db = null;
        return getThis();
    }

    protected <A> A toUB(IReturnAction<DB, A> action) {
        return getExecute(getDB(), action);
    }

    @Override
    public <A> A toField(IReturnAction<String, A> action) {
        return getExecute(field, action);
    }

    @Override
    public DB getDB() {
        return db;
    }

    @Override
    public DB getFilterDB(IBolAction<DB>... args) {
        if (!TextUtils.isEmpty(args)) {
            for (IBolAction<DB> action : args) {
                if (!action.execute(getDB())) {
                    return null;
                }
            }
        }
        return getDB();
    }

    @Override
    public String getUBKey(String... args) {
        return getValue(args, getDefUBKey(), this::getOnUBKey);
    }

    @Override
    public UB getUB(String... args) {
        return getValue(args, getDefUB(), this::getOnUB);
    }

    @NonNull
    @Override
    public UB getSafeUB(String... args) {
        UB ub = getUB(args);
        if (ub == null){
            ub = getNoneUB();
        }
        return ub;
    }

    /**
     * 当UB为空时,safeUB的处理
     */
    @NonNull
    protected UB getNoneUB(){
        Class<UB> clz = getUBClz();
        if(clz != null){
            try {
                return clz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        throw new NullPointerException("getNoneUB()返回值不能为空");
    }

    @Override
    public String getApiCode() {
        return null;
    }

    /**
     * 获取UB类型
     */
    protected abstract Class<UB> getUBClz();

    /**
     * 默认上传参数
     *
     * @return 参数
     */
    protected String getDefUBKey(){
        return null;
    }

    /**
     * 默认上传值
     *
     * @return 值
     */
    protected UB getDefUB(){
        return null;
    }

    /**
     * 不同调用可用
     *
     * @param key key
     * @return 值
     */
    protected String getOnUBKey(String key) {
        return null;
    }

    /**
     * 不同调用可用
     *
     * @param key key
     * @return 值
     */
    protected UB getOnUB(String key) {
        return null;
    }

    private <A> A getValue(String[] args, A def, IReturnAction<String, A> action) {
        if (!TextUtils.isEmpty(args) && action != null) {
            for (String key : args) {
                if (!TextUtils.isEmpty(key)) {
                    A value = action.execute(key);
                    if (value != null) {
                        return value;
                    }
                }
            }
        }
        return def;
    }

}