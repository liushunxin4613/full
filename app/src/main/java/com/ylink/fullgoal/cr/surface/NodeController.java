package com.ylink.fullgoal.cr.surface;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.cr.core.BaseController;
import com.ylink.fullgoal.fg.NodeFg;

import java.util.ArrayList;
import java.util.List;

/**
 * 审核节点控制器
 */
public class NodeController<T extends NodeController> extends BaseController<T, List<NodeFg>, List<NodeFg>> {

    @Override
    public T initDB(List<NodeFg> nodeFgs) {
        return super.initDB(nodeFgs);
    }

    public T addHeadAll(List<NodeFg> nodeFgs) {
        if (getDB() == null) {
            initDB(nodeFgs);
        } else if (TextUtils.check(nodeFgs)) {
            execute(nodeFgs, item -> getDB().add(0, item));
        }
        return getThis();
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