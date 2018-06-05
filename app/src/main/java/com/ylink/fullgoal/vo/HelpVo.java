package com.ylink.fullgoal.vo;

import com.leo.core.iapi.IObjAction;
import com.leo.core.util.RunUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 方法帮助
 */
public class HelpVo {

    protected <B> void execute(List<B> data, IObjAction<B> action) {
        RunUtil.execute(data, action);
    }

    protected <A> List<A> getExecute(IObjAction<List<A>> action) {
        if (action != null) {
            List<A> data = new ArrayList<>();
            action.execute(data);
            return data;
        }
        return null;
    }

}
