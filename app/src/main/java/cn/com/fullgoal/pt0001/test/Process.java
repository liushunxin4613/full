package cn.com.fullgoal.pt0001.test;

import com.leo.core.api.api.VsApi;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.util.TextUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * 流程
 */
public class Process extends VsApi<Process> implements IProcess<Process> {

    private final Stack<IProcessAction> stack = new Stack<>();
    private final Map<String, Object> map = new LinkedHashMap<>();
    private final List<IObjAction<Map<String, Object>>> data = new ArrayList<>();

    @Override
    public Process put(String key, Object obj) {
        if (TextUtils.check(key)) {
            map.put(key, obj);
        }
        return getThis();
    }

    @Override
    public Object get(String key) {
        if (TextUtils.check(key)) {
            return map.get(key);
        }
        return null;
    }

    @Override
    public <V> V get(String key, Class<V> clz) {
        return TextUtils.conversion(get(key), clz);
    }

    @Override
    public <V> Process execute(String key, Class<V> clz, IObjAction<V> action) {
        if (TextUtils.check(key, clz, action)) {
            action.execute(get(key, clz));
        }
        return getThis();
    }

    @Override
    public Process addCompleteAction(IObjAction<Map<String, Object>> action) {
        if (action != null) {
            data.add(action);
        }
        return getThis();
    }

    @Override
    public Process push(IProcessAction action) {
        if (action != null) {
            stack.push(action);
        }
        return getThis();
    }

    @Override
    public Process pushFooter(IProcessAction action) {
        if (action != null) {
            stack.add(0, action);
        }
        return getThis();
    }

    @Override
    public Process next() {
        if (!stack.empty()) {
            IProcessAction action = stack.pop();
            if (action != null) {
                action.execute(map, this);
            }
        }
        return getThis();
    }

    @Override
    public Process complete() {
        execute(data, action -> action.execute(map));
        stack.clear();
        map.clear();
        return getThis();
    }

}