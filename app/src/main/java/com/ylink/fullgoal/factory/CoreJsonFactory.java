package com.ylink.fullgoal.factory;

import com.leo.core.api.api.VsApi;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.controllerApi.core.AppControllerApi;
import com.ylink.fullgoal.json.CoreJson;
import com.ylink.fullgoal.json.ReportJson;

import java.util.ArrayList;
import java.util.List;

public class CoreJsonFactory extends VsApi<CoreJsonFactory> {

    private final static String CORE_JSON = "core.json";

    private static CoreJsonFactory instance;

    public static CoreJsonFactory getInstance() {
        if (instance == null) {
            synchronized (CoreJsonFactory.class) {
                if (instance == null) {
                    instance = new CoreJsonFactory();
                }
            }
        }
        return instance;
    }

    //预配置出的数据
    private final List<String> REPORT_ONE = new ArrayList<>();
    private final List<String> REPORT_ALL = new ArrayList<>();

    //对外访问接口

    /**
     * 初始化数据,必先处理
     *
     * @param api api
     */
    public final void init(AppControllerApi api) {
        this.api = api;
        initCore();
    }

    /**
     * 是否含有投研报告
     *
     * @param report 投研报告
     * @return 是否
     */
    public final boolean reportContains(String report) {
        return TextUtils.check(report) && (REPORT_ONE.contains(report) || REPORT_ALL.contains(report));
    }

    /**
     * 投研报告是否是使用全布局
     *
     * @param report 投研报告
     * @return 是否
     */
    public final boolean reportAllContains(String report) {
        return TextUtils.check(report) && REPORT_ALL.contains(report);
    }

    //内部属性和方法

    private AppControllerApi api;

    private AppControllerApi getApi() {
        return api;
    }

    private void initCore() {
        CoreJson coreJson = getJsonBean(CORE_JSON, CoreJson.class);
        List<CoreJson.InitBean> initData = coreJson.getInit();
        execute(initData, item -> initInitJson(item.getName(), item.getFileName()));
    }

    private void initInitJson(String name, String fileName) {
        if (TextUtils.check(name, fileName)) {
            switch (name) {
                case "report"://投研报告
                    initReportJson(getJsonBean(fileName, ReportJson.class));
                    break;
            }
        }
    }

    private void initReportJson(ReportJson json) {
        ReportJson.FilterBean filter = json.getFilter();
        initList(REPORT_ONE, filter.getOne());
        initList(REPORT_ALL, filter.getAll());
    }

    private <A> void initList(List<A> data, List<A> insertData) {
        if (TextUtils.checkNull(data, insertData)) {
            data.clear();
            execute(insertData, data::add);
        }
    }

    private <A> A getJsonBean(String fileName, Class<A> clz) {
        if (TextUtils.check(fileName, clz)) {
            return getApi().decode(getApi().getAssetsString(fileName), clz);
        }
        return null;
    }

}