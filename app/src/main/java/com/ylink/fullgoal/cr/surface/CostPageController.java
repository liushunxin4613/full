package com.ylink.fullgoal.cr.surface;

import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.cr.core.MapController;
import com.ylink.fullgoal.vo.CostIndexVo;

import java.util.Map;

public class CostPageController<T extends CostPageController> extends MapController<T, IControllerApi, CostIndexVo>{

    private double otherSum;

    @Override
    public T initDB(IControllerApi key, CostIndexVo dimenListFg) {
        return super.initDB(key, dimenListFg);
    }

    @Override
    public T put(IControllerApi key, CostIndexVo dimenListFg) {
        return super.put(key, dimenListFg);
    }

    @Override
    public T remove(IControllerApi key) {
        return super.remove(key);
    }

    @Override
    public CostIndexVo getValue(IControllerApi key) {
        return super.getValue(key);
    }

    @Override
    public CostIndexVo getDB() {
        return super.getDB();
    }

    @Override
    protected Class<CostIndexVo> getClz() {
        return CostIndexVo.class;
    }

    @Override
    protected Class<CostIndexVo> getUBClz() {
        return null;
    }

    @Override
    public Map<IControllerApi, CostIndexVo> getViewBean() {
        return super.getViewBean();
    }

    /**
     * 计算其他项目金额
     */
    public double getOtherMoney(IControllerApi api){
        if(api != null){
            otherSum = 0;
            execute(getMap(), (key, value) -> {
                if(!TextUtils.equals(key, api)){
                    otherSum += value.getMoney().getdouble();
                }
            });
            return otherSum;
        }
        return 0;
    }

}
