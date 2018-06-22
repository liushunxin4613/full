package com.ylink.fullgoal.cr.surface;

import com.leo.core.util.LogUtil;
import com.ylink.fullgoal.cr.core.StringController;

import static com.leo.core.util.DateUtil.getDDate;
import static com.leo.core.util.DateUtil.getDateString;
import static com.leo.core.util.DateUtil.getMDate;
import static com.leo.core.util.DateUtil.getNowDate;
import static com.leo.core.util.DateUtil.getYDate;
import static com.ylink.fullgoal.config.Config.D1;
import static com.ylink.fullgoal.config.Config.D2;
import static com.ylink.fullgoal.config.Config.D3;
import static com.ylink.fullgoal.config.Config.D4;
import static com.ylink.fullgoal.config.Config.D_DATE1;
import static com.ylink.fullgoal.config.Config.D_DATE2;
import static com.ylink.fullgoal.config.Config.D_DATE3;
import static com.ylink.fullgoal.config.Config.D_DATE4;
import static com.ylink.fullgoal.config.Config.D_DATE5;
import static com.ylink.fullgoal.config.Config.D_DATE6;

public class DDateController<T extends DDateController> extends StringController<T> {

    @Override
    protected String getOnUBKey(String key) {
        switch (key) {
            case D1:
            case D2:
            case D3:
            case D4:
                return "time";
        }
        return super.getOnUBKey(key);
    }

    @Override
    protected String getOnUB(String key) {
        return no(toUB(db -> {
            switch (db) {
                case D_DATE1:
                    return getDateString(getNowDate());
                case D_DATE2:
                    return getDateString(getDDate(-7));
                case D_DATE3:
                    return getDateString(getMDate(-1));
                case D_DATE4:
                    return getDateString(getMDate(-3));
                case D_DATE5:
                    return getDateString(getMDate(-6));
                case D_DATE6:
                    return getDateString(getYDate(-1));
            }
            return null;
        }), "");
    }

}
