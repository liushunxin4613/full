package com.ylink.fullgoal.cr.surface;

import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.cr.core.MapController;
import com.ylink.fullgoal.vo.CostIndexVo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.leo.core.util.TextUtils.check;
import static com.ylink.fullgoal.config.ComConfig.QR;

public class CostPageController<T extends CostPageController> extends MapController<T, IControllerApi,
        CostIndexVo, List<Map<String, Object>>> {

    private double otherSum;

    @Override
    public T initDB(IControllerApi key, CostIndexVo vo) {
        return super.initDB(key, vo);
    }

    public T update(IControllerApi key, double money) {
        if (check(getValue(key))) {
            getValue(key).getMoney().initDB(money);
        }
        return getThis();
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
    public Map<IControllerApi, CostIndexVo> getViewBean() {
        return super.getViewBean();
    }

    /**
     * 计算已过虑项目金额
     */
    public double getFilterMoney(IControllerApi... args) {
        otherSum = 0;
        List<IControllerApi> data = Arrays.asList(args);
        execute(getMap(), (api, value) -> {
            if (!data.contains(api)) {
                otherSum += value.getMoney().getdouble();
            }
        });
        return otherSum;
    }

    public List<IControllerApi> getFilterApi(IControllerApi... args) {
        List<IControllerApi> data = new ArrayList<>();
        List<IControllerApi> filter = Arrays.asList(args);
        execute(getMap(), (api, value) -> {
            if (!filter.contains(api)) {
                data.add(api);
            }
        });
        return data;
    }

    @Override
    protected String getOnUBKey(String key) {
        switch (key) {
            case QR:
                return "share";
        }
        return super.getOnUBKey(key);
    }

    @Override
    protected List<Map<String, Object>> getOnUB(String key) {
        switch (key) {
            case QR:
                List<Map<String, Object>> data = new ArrayList<>();
                execute(getMap(), (kk, vv) -> {
                    if (check(kk, vv)) {
                        data.add(vv.getCheckMap(key));
                    }
                });
                return data;
        }
        return super.getOnUB(key);
    }

}