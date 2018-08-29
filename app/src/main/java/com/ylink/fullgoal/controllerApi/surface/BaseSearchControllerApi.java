package com.ylink.fullgoal.controllerApi.surface;

import com.google.gson.reflect.TypeToken;
import com.leo.core.iapi.core.IMNApi;
import com.leo.core.iapi.core.IModel;
import com.leo.core.iapi.main.IApiBean;
import com.leo.core.other.Transformer;
import com.leo.core.search.SearchUtil;
import com.leo.core.util.LogUtil;
import com.leo.core.util.ResUtil;
import com.leo.core.util.SoftInputUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.config.Config;
import com.ylink.fullgoal.fg.DataFg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;

public class BaseSearchControllerApi<T extends BaseSearchControllerApi, C> extends RecycleControllerApi<T, C> {

    private String key;
    private String tag;
    private String value;
    private String search;
    private String keyword;
    private String searchTitle;
    private List<String> filterData;

    public BaseSearchControllerApi(C controller) {
        super(controller);
    }

    private String getKeyword() {
        return keyword;
    }

    private void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    protected String getSearchTitle() {
        return String.format("搜索%s", no(searchTitle));
    }

    private void setSearchTitle(String searchTitle) {
        this.searchTitle = searchTitle;
    }

    private List<String> getFilterData() {
        return filterData;
    }

    private void setFilterData(String text) {
        this.filterData = decode(text, TypeToken
                .getParameterized(List.class, String.class).getType());
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
            executeNon(bundle.getString(Config.SEARCH_TITLE), this::setSearchTitle);
            executeNon(bundle.getString(Config.SEARCH), this::setSearch);
            executeNon(bundle.getString(Config.KEY), this::setKey);
            executeNon(bundle.getString(Config.VALUE), this::setValue);
            executeNon(bundle.getString(Config.FILTERS), this::setFilterData);
            executeNon(bundle.getString(Config.TAG), this::setTag);
        });
    }

    @Override
    public void initData() {
        super.initData();
        query();
    }

    @Override
    protected List<? extends IModel> getOnDataFg(String type, String baseUrl, String path,
                                                 Map<String, String> map, int what, String msg,
                                                 String field, DataFg fg) {
        return fg.getModelData();
    }

    /**
     * 数据请求
     */
    protected void query() {
    }

    private boolean isFilter(IMNApi mnApi, String keyword) {
        String code = mnApi.getApiCode();
        mnApi.setSelectedApiCode(getValue());
        if (!TextUtils.isEmpty(getFilterData())) {
            for (String filter : getFilterData()) {
                if (!TextUtils.isEmpty(filter)
                        && !TextUtils.isEmpty(code)
                        && code.contains(filter)) {
                    return false;
                }
            }
        }
        if (TextUtils.isEmpty(keyword)) {
            return true;
        }
        mnApi.lazy();//懒加载处理
        return SearchUtil.searchString(mnApi.getSearchText(), keyword);
    }

    @Override
    protected void onInitActionData() {
        super.onInitActionData();
        search(getKeyword());
    }

    /**
     * 搜索
     *
     * @param keyword 关键字
     */
    protected void search(String keyword) {
        setKeyword(keyword);
        adapterDataApi().setHelper((adapter, api, list)
                -> Observable.create((Observable.
                OnSubscribe<List<IApiBean>>) subscriber -> {
            List<IMNApi> data = new ArrayList<>();
            execute(list, obj -> {
                if (isFilter(obj, keyword)) {
                    data.add(obj);
                }
            });
            api.setFilterData(data);
            subscriber.onNext(null);
            subscriber.onCompleted();
            LogUtil.ii(this, "\n" + LogUtil.getLog(data));
        }).compose(Transformer.getInstance())
                .subscribe(obj -> adapter.notifyDataSetChanged()))
                .notifyDataSetChanged();
    }

    private void showView(boolean enable) {
        if (enable) {
            showContentView();
        } else {
            showNullView(true);
        }
    }

}