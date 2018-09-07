package com.ylink.fullgoal.factory;

import com.leo.core.api.api.VsApi;
import com.leo.core.util.JavaTypeUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.bean.UserBean;
import com.ylink.fullgoal.db.table.JsonFile;
import com.ylink.fullgoal.fg.DepartmentFg;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 缓存数据
 */
public class CacheFactory extends VsApi<CacheFactory> {

    private static CacheFactory instance;

    public static CacheFactory getInstance() {
        if (instance == null) {
            synchronized (CacheFactory.class) {
                if (instance == null) {
                    instance = new CacheFactory();
                }
            }
        }
        return instance;
    }

    private final static String CACHE_SIZE = "size.cacheSize";

    private int cacheSize = 10;
    private Map<Object, Object> cacheMap;

    public void init() {
        JsonFile jf = JsonFile.query(CACHE_SIZE);
        if (jf != null) {
            int size = JavaTypeUtil.getInt(jf.getValue(), 0);
            if (size > 0) {
                this.cacheSize = size;
            }
        }
        this.cacheMap = new LinkedHashMap<Object, Object>((int) Math.ceil(cacheSize / 0.75f) + 1, 0.75f, true) {
            private static final long serialVersionUID = -9193199736698387017L;
            @Override
            protected boolean removeEldestEntry(Map.Entry<Object, Object> eldest) {
                return size() > cacheSize;
            }
        };
        this.initMap();
    }

    public boolean reportAllContains(String text) {
        return TextUtils.check(text) && REPORT_FILTER_ALL.contains(text);
    }

    public boolean reportContains(String text) {
        return TextUtils.check(text) && (REPORT_FILTER_ALL.contains(text)
                || REPORT_FILTER_ONE.contains(text));
    }

    //对内方法

    private final static Set<String> REPORT_FILTER_ONE = new HashSet<>();
    private final static Set<String> REPORT_FILTER_ALL = new HashSet<>();

    private void initMap() {
        execute(JsonFile.queryList(JsonFile.REPORT_JSON, "filter.one"), item
                -> REPORT_FILTER_ONE.add(item.getValue()));
        execute(JsonFile.queryList(JsonFile.REPORT_JSON, "filter.all"), item
                -> REPORT_FILTER_ALL.add(item.getValue()));
    }

    //缓存数据
    private UserBean userBean;
    private DepartmentFg departmentFg;

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public DepartmentFg getDepartmentFg() {
        return departmentFg;
    }

    public void setDepartmentFg(DepartmentFg departmentFg) {
        this.departmentFg = departmentFg;
    }

}