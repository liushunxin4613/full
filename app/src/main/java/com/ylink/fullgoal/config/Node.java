package com.ylink.fullgoal.config;

import com.leo.core.api.inter.ClzAction;
import com.leo.core.iapi.inter.IMapAction;
import com.leo.core.iapi.inter.IReturnAction;
import com.leo.core.util.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Node {

    private String name;
    private Node end;
    private List<Node> childData;
    private transient ClzAction action;
    private transient IReturnAction<Map<String, Object>, Boolean> returnAction;

    public Node(String name) {
        this.name = name;
    }

    public Node(String name, IReturnAction<Map<String, Object>, Boolean> action) {
        this.name = name;
        this.returnAction = action;
    }

    public Node(String name, String key, String value) {
        this.name = name;
        this.returnAction = parent -> TextUtils.equals(parent.get(key), value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClzAction getAction() {
        return action;
    }

    public void setAction(ClzAction action) {
        this.action = action;
    }

    public IReturnAction<Map<String, Object>, Boolean> getReturnAction() {
        return returnAction;
    }

    public void setReturnAction(IReturnAction<Map<String, Object>, Boolean> returnAction) {
        this.returnAction = returnAction;
    }

    public List<Node> getChildData() {
        return childData;
    }

    public void setEnd(Node end) {
        this.end = end;
    }

    public void addChild(Node node) {
        if (node != null) {
            if (childData == null) {
                childData = new ArrayList<>();
            }
            this.end = node.end == null ? node : node.end;
            childData.add(node);
        }
    }

    public void addEndChild(Node node) {
        if (node != null && end != null) {
            end.addChild(node);
        }
    }

    public <A> void setEndAction(Class<A> clz, IMapAction<Map, A> action) {
        if (TextUtils.check(clz, end, action)) {
            end.setAction(new ClzAction(clz, action));
        }
    }

}