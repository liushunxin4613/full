package com.leo.core.api;

import android.os.Handler;
import android.os.Looper;

import com.leo.core.api.core.ThisApi;
import com.leo.core.iapi.IAction;
import com.leo.core.iapi.IActionApi;
import com.leo.core.iapi.core.IApi;
import com.leo.core.util.LogUtil;
import com.leo.core.util.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ActionApi<T extends ActionApi, A extends IApi> extends ThisApi<T> implements IActionApi<T, A> {

    private Thread thread;
    private Handler handler;
    private boolean start;
    private int interval = 100;
    private Map<Long, List<A>> map;
    private Map<Long, List<A>> uiMap;
    private Set<Integer> codeData;
    private Set<Integer> cancelData;

    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    @Override
    public Map<Long, List<A>> getActionMap() {
        if (map == null) {
            map = newActionMap();
            if (map == null) {
                throw new NullPointerException("newActionMap 不能为空");
            }
        }
        return map;
    }

    @Override
    public Map<Long, List<A>> newActionMap() {
        return new HashMap<>();
    }

    @Override
    public Map<Long, List<A>> getUIActionMap() {
        if (uiMap == null) {
            uiMap = newUIActionMap();
            if (uiMap == null) {
                throw new NullPointerException("newUIActionMap 不能为空");
            }
        }
        return uiMap;
    }

    @Override
    public Map<Long, List<A>> newUIActionMap() {
        return new HashMap<>();
    }

    @Override
    public Set<Integer> getCancelData() {
        if (cancelData == null) {
            cancelData = newCancelData();
            if (cancelData == null) {
                throw new NullPointerException("newCancelData 不能为空");
            }
        }
        return cancelData;
    }

    @Override
    public Set<Integer> newCancelData() {
        return new HashSet<>();
    }

    @Override
    public T setInterval(int interval) {
        if (interval > 0) {
            this.interval = interval;
        }
        return getThis();
    }

    @Override
    public int getInterval() {
        return interval;
    }

    @Override
    public int add(long time, A action) {
        if (time >= 0 && action != null) {
            return put(currentTimeMillis() + time, action);
        }
        return 0;
    }

    @Override
    public int add(long time, A action, int interval, int num) {
        if (time >= 0 && interval > 0 && num > 0 && action != null) {
            return put(currentTimeMillis() + time, action, interval, num);
        }
        return 0;
    }

    @Override
    public int addUI(long time, A action) {
        if (time >= 0 && action != null) {
            return putUI(currentTimeMillis() + time, action);
        }
        return 0;
    }

    @Override
    public int addUI(long time, A action, int interval, int num) {
        if (time >= 0 && interval > 0 && num + 1 > 0 && action != null) {
            return putUI(currentTimeMillis() + time, action, interval, num);
        }
        return 0;
    }

    @Override
    public int put(long time, A action) {
        return put(getActionMap(), time, action);
    }

    @Override
    public int put(long time, A action, int interval, int num) {
        return put(getActionMap(), time, action, interval, num);
    }

    @Override
    public int putUI(long time, A action) {
        return put(getUIActionMap(), time, action);
    }

    @Override
    public int putUI(long time, A action, int interval, int num) {
        return put(getUIActionMap(), time, action, interval, num);
    }

    @Override
    public T cancel(int actionCode) {
        if (actionCode > 0) {
            getCancelData().add(actionCode);
        }
        return getThis();
    }

    @Override
    public T action(A action) {
        if (action instanceof IAction) {
            ((IAction) action).execute();
        }
        return getThis();
    }

    @Override
    public T start() {
        start = true;
        handler = new Handler(Looper.getMainLooper());
        thread = new Thread(() -> {
            synchronized (this) {
                while (start) {
                    codeData = new HashSet<>();
                    start(getUIActionMap(), true);
                    start(getActionMap(), false);
                    for (Integer code : codeData) {
                        if(getCancelData().contains(code)){
                            getCancelData().remove(code);
                        }
                    }
                    try {
                        Thread.sleep(getInterval());
                    } catch (InterruptedException e) {
                        LogUtil.ee(this, e);
                    }
                }
            }
        });
        thread.start();
        return getThis();
    }

    @Override
    public T stop() {
        start = false;
        thread = null;
        return getThis();
    }

    //私有的

    protected T start(Map<Long, List<A>> map, boolean main) {
        if (!TextUtils.isEmpty(map)) {
            long ct = currentTimeMillis();
            List<Long> data = new ArrayList<>();
            for (Map.Entry<Long, List<A>> entry : map.entrySet()) {
                if (entry != null) {
                    Long key = entry.getKey();
                    List<A> value = entry.getValue();
                    if (key > 0 && !TextUtils.isEmpty(value)) {
                        List<A> list = new ArrayList<>();
                        for (A action : value) {
                            Integer code = action.hashCode();
                            if (getCancelData().contains(code)) {
                                codeData.add(code);
                                list.add(action);
                            } else if (key <= ct) {
                                if (main) {
                                    handler.post(() -> action(action));
                                } else {
                                    action(action);
                                }
                                data.add(key);
                            }
                        }
                        for (A a : list) {
                            value.remove(a);
                        }
                    }
                }
            }
            for (Long key : data) {
                map.remove(key);
            }
        }
        return getThis();
    }

    private int put(Map<Long, List<A>> map, long time, A action) {
        if (map != null && time >= 0 && action != null) {
            if (!map.containsKey(time)) {
                map.put(time, TextUtils.getListData(action));
            } else {
                map.get(time).add(action);
            }
            return action.hashCode();
        }
        return 0;
    }

    private int put(Map<Long, List<A>> map, long time, A action, int interval, int num) {
        if (map != null && time >= 0 && action != null && interval > 0 && num + 1> 0) {
            for (int i = 0; i < num + 1; i++) {
                long index = time + i * interval;
                if (!map.containsKey(index)) {
                    map.put(index, TextUtils.getListData(action));
                } else {
                    map.get(index).add(action);
                }
            }
            return action.hashCode();
        }
        return 0;
    }

}
