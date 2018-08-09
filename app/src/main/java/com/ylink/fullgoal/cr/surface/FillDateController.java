package com.ylink.fullgoal.cr.surface;

import com.leo.core.util.DateUtil;
import com.ylink.fullgoal.cr.core.StringController;

import static com.ylink.fullgoal.config.ComConfig.CC;
import static com.ylink.fullgoal.config.ComConfig.YB;

/**
 * 时间处理
 */
public class FillDateController<T extends FillDateController> extends StringController<T>{

    @Override
    protected String getOnUBKey(String key) {
        switch (key){
            case YB:
            case CC:
                return "fillDate";
        }
        return super.getOnUBKey(key);
    }

    @Override
    protected String getDefUB() {
        return DateUtil.getFormatString(DateUtil.getNowDate(), "yyyyMMdd");
    }

}
