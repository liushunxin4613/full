package com.ylink.fullgoal.controllerApi.core;

import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.leo.core.core.BaseControllerApi;
import com.leo.core.core.BaseControllerApiView;
import com.leo.core.iapi.IObjAction;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.api.config.ParseApi;
import com.ylink.fullgoal.api.config.UrlApi;
import com.ylink.fullgoal.api.func.UserApi;
import com.ylink.fullgoal.bean.UserBean;
import com.ylink.fullgoal.config.Api;
import com.ylink.fullgoal.hb.DataHb;
import com.ylink.fullgoal.vo.SearchVo;

import java.util.List;

import butterknife.ButterKnife;

import static com.ylink.fullgoal.config.Config.ROOT_URL;

public class ControllerApi<T extends ControllerApi, C> extends BaseControllerApi<T, C> {

    private String rootUrl = ROOT_URL;

    public ControllerApi(C controller) {
        super(controller);
    }

    @Override
    public UserApi userApi() {
        return (UserApi) super.userApi();
    }

    @Override
    public UserApi newUserApi() {
        return new UserApi(getThis());
    }

    @Override
    public Api getApi() {
        return getApi(rootUrl);
    }

    @Override
    public Api getApi(String url) {
        return create(url, Api.class);
    }

    @Override
    public UrlApi api() {
        return (UrlApi) super.api();
    }

    @Override
    public UrlApi newApi() {
        return new UrlApi(getThis());
    }

    @Override
    public ParseApi parseApi() {
        return (ParseApi) super.parseApi();
    }

    @Override
    public ParseApi newParseApi() {
        return new ParseApi();
    }

    @Override
    public boolean isLogin() {
        return super.isLogin();
    }

    @Override
    public UserBean getUser() {
        return super.getUser();
    }

    @Override
    public Class<? extends View> getRootViewClz() {
        return BaseControllerApiView.class;
    }

    @Override
    public void initView() {
        if (getRootView() != null)
            ButterKnife.bind(this, getRootView());
        addRootType(DataHb.class);
    }

    public void post(String path, String keyword) {
        if (!TextUtils.isEmpty(path)) {
            switch (path) {
                case "reimbursementCompensation"://员工列表
                    post(path, map -> map.put("keyword", keyword));
                    break;
                case "BudgetDepartmentCompensation"://部门列表
                    post(path, map -> map.put("keyword", keyword));
                    break;
                case "ProjectCompensation"://项目列表
                    post(path, map -> {
                        map.put("departmentCode", getDepartmentCode());
                        map.put("keyword", keyword);
                    });
                    break;
                case "PaymentRequestCompensation"://合同付款申请单列表
                    post(path, map -> {
                        map.put("reimbursement", getUserName());
                        map.put("keyword", keyword);
                    });
                    break;
                case "TraveFormCompensation"://出差申请单列表
                    post(path, map -> {
                        map.put("reimbursement", getUserName());
                        map.put("departmentCode", getDepartmentCode());
                        map.put("keyword", keyword);
                    });
                    break;
                case "CtripCompensation"://携程机票列表
                    post(path, map -> {
                        map.put("reimbursement", getUserName());
                        map.put("keyword", keyword);
                    });
                    break;
                case "ResearchReportCompensation"://调研报告列表
                    post(path, map -> {
                        map.put("reimbursement", getUserName());
                        map.put("departmentCode", getDepartmentCode());
                        map.put("keyword", keyword);
                    });
                    break;
            }
        }
    }

    protected <A> void executeSearch(Class<A> clz, IObjAction<A> action) {
        if (clz != null && action != null) {
            TypeToken<SearchVo<A>> token = (TypeToken<SearchVo<A>>) TypeToken
                    .getParameterized(SearchVo.class, clz);
            execute(getFinish(), token, vo -> action.execute(vo.getObj()));
        }
    }

    protected void addListRootType(Class... args) {
        if (!TextUtils.isEmpty(args)) {
            execute(args, item -> addRootType(TypeToken.getParameterized(List.class, item)));
        }
    }

}
