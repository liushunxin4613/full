package com.ylink.fullgoal.db.table;

import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.net.UrlApi;
import com.leo.core.util.TextUtils;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.ylink.fullgoal.db.core.AppDatabase;
import com.ylink.fullgoal.db.core.DBModel;

import java.util.Date;
import java.util.Map;

import static com.ylink.fullgoal.config.Config.HTTP_CACHE_TIME;
import static com.ylink.fullgoal.config.UrlConfig.PATH_QUERY_DEPARTMENT_DATA;
import static com.ylink.fullgoal.config.UrlConfig.PATH_QUERY_DIMENSION_INFORMATION_DATA;
import static com.ylink.fullgoal.config.UrlConfig.PATH_QUERY_PROJECT_DATA;
import static com.ylink.fullgoal.config.UrlConfig.PATH_QUERY_TRANS_FILE;
import static com.ylink.fullgoal.config.UrlConfig.PATH_QUERY_USER_DATA;

/**
 * 存储repuest数据
 */
@Table(database = AppDatabase.class)
public class Request extends DBModel {

    private final static String[] ALLOW = {
            PATH_QUERY_USER_DATA,//获取人员信息
            PATH_QUERY_DEPARTMENT_DATA,//获取部门信息
            PATH_QUERY_PROJECT_DATA,//获取项目信息
            PATH_QUERY_DIMENSION_INFORMATION_DATA,//获取分摊维度信息
            PATH_QUERY_TRANS_FILE,//获取配置文件
    };

    private static boolean checkAllow(String path, String params) {
        if (TextUtils.check(path) && TextUtils.contains(ALLOW, path)) {
            Map<String, Object> reqinfo = null;
            Map<String, Object> map = TextUtils.toJSONMap(params);
            if (map != null) {
                reqinfo = TextUtils.toJSONMap(String.class, Object.class, map.get(UrlApi.REQINFO));
            }
            switch (path) {
                case PATH_QUERY_TRANS_FILE://获取配置文件
                    if (reqinfo != null) {
                        return TextUtils.equals(reqinfo.get("fileName"), "config.json");
                    }
                    break;
                default:
                    return true;
            }
        }
        return false;
    }

    public static void query(String baseUrl, String path, String params, IObjAction<String> action) {
        if (action != null) {
            if (checkAllow(path, params)) {
                SQLite.select().from(Request.class)
                        .where(Request_Table.baseUrl.eq(baseUrl),
                                Request_Table.path.eq(path),
                                Request_Table.params.eq(params))
                        .async()
                        .querySingleResultCallback((transaction, request) -> {
                            if (request != null && request.getDate() != null && System.currentTimeMillis()
                                    - request.getDate().getTime() < HTTP_CACHE_TIME) {
                                action.execute(request.getResponse());
                            } else {
                                action.execute(null);
                            }
                        })
                        .error((transaction, error) -> {
                            action.execute(null);
                            error.printStackTrace();
                        })
                        .execute();
            } else {
                action.execute(null);
            }
        }
    }

    public static void sav(String baseUrl, String path, String params, String response) {
        if (checkAllow(path, params)) {
            Request request = SQLite.select().from(Request.class)
                    .where(Request_Table.baseUrl.eq(baseUrl),
                            Request_Table.path.eq(path),
                            Request_Table.params.eq(params))
                    .querySingle();
            if (request == null) {
                request = new Request();
                request.setBaseUrl(baseUrl);
                request.setPath(path);
                request.setParams(params);
                request.setResponse(response);
                request.setDate(new Date());
                request.save();
            } else {
                if (!TextUtils.equals(response, request.getResponse())) {
                    request.setResponse(response);
                }
                request.setDate(new Date());
                request.update();
            }
        }
    }

    @PrimaryKey(autoincrement = true)
    private transient long id;
    @Column
    private String baseUrl;
    @Column
    private String path;//路径
    @Column
    private String params;//参数
    @Column
    private String response;//回传
    @Column
    private Date date;//时间

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}