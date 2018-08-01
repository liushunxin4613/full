package com.ylink.fullgoal.cr.surface;

import com.ylink.fullgoal.cr.core.BaseController;
import com.ylink.fullgoal.fg.NodeFg;

import java.util.List;

/**
 * 审核节点控制器
 */
public class NodeController<T extends NodeController> extends BaseController<T, List<NodeFg>, List<NodeFg>> {

    @Override
    public T initDB(List<NodeFg> nodeFgs) {
        return super.initDB(nodeFgs);
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