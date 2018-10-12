package com.ylink.fullgoal.cr.surface;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.cr.core.BaseController;
import com.ylink.fullgoal.fg.NodeFg;

import java.util.Collections;
import java.util.List;

/**
 * 审核节点控制器
 */
public class NodeController<T extends NodeController> extends BaseController<T, List<NodeFg>, List<NodeFg>> {

    @Override
    public T initDB(List<NodeFg> nodeFgs) {
        super.initDB(nodeFgs);
        sort();
        return getThis();
    }

    public T addHeadAll(List<NodeFg> nodeFgs) {
        if (getDB() == null) {
            initDB(nodeFgs);
        } else if (TextUtils.check(nodeFgs)) {
            execute(nodeFgs, item -> getDB().add(0, item));
            sort();
        }
        return getThis();
    }

    private void sort() {
        Collections.sort(getDB(), (dt, dt1) -> TextUtils.isEmpty(dt.getProcessTime())
                || TextUtils.isEmpty(dt1.getProcessTime()) ? 1
                : -dt.getProcessTime().compareTo(dt1.getProcessTime()));
    }

    @Override
    public List<NodeFg> getDB() {
        return super.getDB();
    }

    @Override
    public List<NodeFg> getViewBean() {
        return getDB();
    }

}