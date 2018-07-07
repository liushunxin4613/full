package com.ylink.fullgoal.cr.surface;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.cr.core.BaseController;
import com.ylink.fullgoal.fg.ApplyDataFgV1;
import com.ylink.fullgoal.fg.ApplyFgV1;

import java.util.List;

/**
 * 费用指标值控制器
 */
public class CostIndexValueController<T extends CostIndexValueController> extends BaseController<T,
        List<ApplyDataFgV1>, String> {

    @Override
    public T initDB(List<ApplyDataFgV1> applyDataFgV1s) {
        return super.initDB(applyDataFgV1s);
    }

    @Override
    public List<ApplyDataFgV1> getDB() {
        return super.getDB();
    }

    @Override
    protected Class<List<ApplyDataFgV1>> getClz() {
        return null;
    }

    @Override
    protected Class<String> getUBClz() {
        return null;
    }

    @Override
    public String getViewBean() {
        StringBuilder builder = new StringBuilder();
        execute(getDB(), obj -> {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            if (TextUtils.check(obj.getKey(), obj.getValue())) {
                builder.append(String.format("%s已选", vr(obj.getKey(), ApplyFgV1::getName)));
            }
        });
        return builder.toString();
    }

}