package com.ylink.fullgoal.config;

import com.leo.core.api.api.VsApi;
import com.leo.core.api.inter.ClzAction;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.util.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonHelper extends VsApi<JsonHelper> {

    private Node root;
    private List<Node> data;
    private List<IObjAction> actionData;

    public static JsonHelper newBuilder() {
        return new JsonHelper();
    }

    private JsonHelper() {
        data = new ArrayList<>();
        actionData = new ArrayList<>();
    }

    private Node getRoot() {
        return root;
    }

    private List<Node> getData() {
        return data;
    }

    private List<IObjAction> getActionData() {
        return actionData;
    }

    public JsonHelper setRoot(Node... args) {
        root = node(args);
        if (root != null) {
            getData().add(root);
        }
        return this;
    }

    public <A> JsonHelper addChild(Class<A> clz, IObjAction<A> action, Node... args) {
        if (getRoot() == null) {
            throw new NullPointerException("setRoot()不能为空");
        }
        Node node = node(clz, action, args);
        if (node != null) {
            getRoot().addEndChild(node);
        }
        return this;
    }

    public JsonHelper addChild(IObjAction<Object> action, Node... args) {
        addChild(Object.class, action, args);
        return this;
    }

    public <A> JsonHelper add(Class<A> clz, IObjAction<A> action, Node... args) {
        if (action != null) {
            if (!TextUtils.isEmpty(args)) {
                Node node = node(clz, action, args);
                if (node != null) {
                    getData().add(node);
                }
            } else {
                getActionData().add(action);
            }
        }
        return this;
    }

    public JsonHelper add(IObjAction<Object> action, Node... args) {
        add(Object.class, action, args);
        return this;
    }

    public JsonHelper execute(String text) {
        executeJson(TextUtils.toJSONMap(text));
        return this;
    }

    private void executeJson(Object obj) {
        execute(getActionData(), a -> a.execute(obj));
        if (obj instanceof Map) {
            execute(getData(), node -> {
                try {
                    onNode(node, (Map<String, Object>) obj);
                } catch (Exception ignored) {
                }
            });
        }
    }

    private void onNode(Node node, Map<String, Object> map) {
        if (node != null && map != null && ((node.getReturnAction() != null
                && node.getReturnAction().execute(map))
                || node.getReturnAction() == null)) {
            if (!TextUtils.isEmpty(node.getChildData())) {
                Object obj = map.get(node.getName());
                if (obj instanceof Map) {
                    try {
                        Map<String, Object> childMap = (Map<String, Object>) obj;
                        execute(node.getChildData(), child -> onNode(child, childMap));
                    } catch (Exception ignored) {
                    }
                } else if (obj instanceof List) {
                    execute((List) obj, item -> {
                        if (item instanceof Map) {
                            try {
                                Map<String, Object> childMap = (Map<String, Object>) item;
                                execute(node.getChildData(), child -> onNode(child, childMap));
                            } catch (Exception ignored) {
                            }
                        }
                    });
                }
            } else if (node.getAction() != null) {
                node.getAction().execute(map.get(node.getName()));
            }
        }
    }

    private <A> Node node(Class<A> clz, IObjAction<A> action, Node... args) {
        if (action != null && !TextUtils.isEmpty(args)) {
            Node end = args[args.length - 1];
            if (end != null) {
                end.setAction(new ClzAction(clz, action));
                return node(args);
            }
        }
        return null;
    }

    private Node node(Node... args) {
        Node tNode = null;
        if (!TextUtils.isEmpty(args)) {
            for (int i = args.length - 1; i >= 0; i--) {
                Node node = args[i];
                if (node == null || TextUtils.isEmpty(node.getName())) {
                    return null;
                } else {
                    if (tNode != null) {
                        node.addChild(tNode);
                    }
                    tNode = node;
                }
            }
        }
        return tNode;
    }

}