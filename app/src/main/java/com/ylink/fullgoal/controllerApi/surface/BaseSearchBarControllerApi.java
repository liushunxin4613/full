package com.ylink.fullgoal.controllerApi.surface;

import com.google.gson.reflect.TypeToken;
import com.leo.core.util.ResUtil;
import com.leo.core.util.SoftInputUtil;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.config.Config;
import java.util.List;

public class BaseSearchBarControllerApi<T extends BaseSearchBarControllerApi, C> extends RecycleBarControllerApi<T, C> {

    private String key;
    private String value;
    private String search;
    private String keyword;
    private List<String> filterData;

    public BaseSearchBarControllerApi(C controller) {
        super(controller);
    }

    private String getKeyword() {
        return keyword;
    }

    private void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    private List<String> getFilterData() {
        return filterData;
    }

    private void setFilterData(String filterData) {
        this.filterData = decode(filterData, TypeToken
                .getParameterized(List.class, String.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getActivity().finish();
    }

    @Override
    public T finishActivity(Object obj) {
        SoftInputUtil.hidSoftInput(getRootView());
        return super.finishActivity(obj);
    }

    @Override
    public void initView() {
        super.initView();
        getRecyclerView().setBackgroundColor(ResUtil.getColor(R.color.white));
        adapterDataApi().setEmptyAction(this::showView);
        executeBundle(bundle -> {
            executeNon(bundle.getString(Config.SEARCH), this::setSearch);
            executeNon(bundle.getString(Config.KEY), this::setKey);
            executeNon(bundle.getString(Config.VALUE), this::setValue);
            executeNon(bundle.getString(Config.FILTERS), this::setFilterData);
        });
    }

    @Override
    public void initData() {
        super.initData();
        query();
    }

    /**
     * 数据请求
     */
    protected void query() {
    }

    private void showView(boolean enable) {
        if (enable) {
            showContentView();
        } else {
            showNullView(true);
        }
    }

}