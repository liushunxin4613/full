package com.leo.core.iapi.api;

import com.leo.core.iapi.core.IApi;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.other.MMap;

import java.util.Map;

/**
 * 路由
 */
public interface IRouteApi extends IApi {

    /**
     * 转向路由
     * @param url url地址
     */
    void route(String url);

    /**
     * 转向路由
     *
     * @param path    转向路径 eg(activity/view/api)
     * @param command 路由命令 eg({"finish":"0"})
     * @param params  传出参数 eg({"state":"name"})
     */
    void route(String path, String command, String params);

    /**
     * 转向路由
     *
     * @param path          转向路径 eg(activity/view/api)
     * @param commandAction 路由命令 eg({"finish":"0"})
     * @param paramsAction  传出参数 eg({"state":"name"})
     */
    void route(String path, IObjAction<MMap<String, String>> commandAction,
               IObjAction<MMap<String, String>> paramsAction);

    /**
     * 转向路由
     *
     * @param path          转向路径 eg(activity/view/api)
     * @param commandAction 路由命令 eg({"finish":"0"})
     * @param paramsAction  传出参数 eg({"state":"name"})
     */
    void route(String module, String path, IObjAction<MMap<String, String>> commandAction,
               IObjAction<MMap<String, String>> paramsAction);

    /**
     * 转向路由
     *
     * @param path       转向路径 eg(activity/view/api)
     * @param commandMap 路由命令 eg({"finish":"0"})
     * @param paramsMap  传出参数 eg({"state":"name"})
     */
    void route(String path, Map<String, String> commandMap, Map<String, String> paramsMap);

    /**
     * 转向路由
     *
     * @param module     版本
     * @param activity   activity级
     * @param api        api级
     * @param viewApi    视图api级
     * @param commandMap 路由命令 eg({"finish":"0"})
     * @param paramsMap  传出参数 eg({"state":"name"})
     */
    void route(String module, String activity, String api, String viewApi, Map<String, String>
            commandMap, Map<String, String> paramsMap);

}
