package com.leo.core.core;

import com.leo.core.iapi.core.IMNApi;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.search.SearchUtil;
import com.leo.core.util.TextUtils;

public class MNApi<T extends MNApi, P extends IControllerApi> extends SelectedApiCodeApi<T> implements IMNApi<P> {

    private transient P api;
    private transient Object apiId;
    private transient Object controller;
    private transient String searchText;

    @Override
    public Integer getViewType() {
        throw new RuntimeException("getViewType没有初始化");
    }

    @Override
    public Object getApiId() {
        if (apiId == null) {
            apiId = newApiId();
        }
        return apiId;
    }

    private Object newApiId() {
        return null;
    }

    @Override
    public void setParentControllerApi(P api) {
        this.api = api;
    }

    @Override
    public P parentControllerApi() {
        return api;
    }

    @Override
    public void setController(Object controller) {
        this.controller = controller;
    }

    @Override
    public Object getController() {
        return controller;
    }

    @Override
    public String getSearchText() {
        return searchText;
    }

    @Override
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    @Override
    public void createSearchText() {
        String[] args = getSearchFields();
        if (!TextUtils.isEmpty(args)) {
            setSearchText(SearchUtil.getArgsSearch(args));
        }
    }

    @Override
    public void lazy() {
        if (getSearchFields() != null && getSearchText() == null) {
            createSearchText();
        }
    }

    @Override
    public String getApiCode() {
        return null;
    }

    protected String[] getSearchFields() {
        return null;
    }

}