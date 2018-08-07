package com.ylink.fullgoal.controllerApi.surface;

import com.google.gson.reflect.TypeToken;
import com.leo.core.bean.Completed;
import com.leo.core.iapi.api.IKeywordApi;
import com.leo.core.iapi.main.IApiBean;
import com.leo.core.other.Transformer;
import com.leo.core.search.SearchUtil;
import com.leo.core.util.ResUtil;
import com.leo.core.util.SoftInputUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.LineBean;
import com.ylink.fullgoal.config.Config;
import com.ylink.fullgoal.config.ViewBean;
import com.ylink.fullgoal.fg.DataFg;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import rx.Observable;

import static com.ylink.fullgoal.vo.SearchVo.SEARCHS;

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
        //消息标志
        add(DataFg.class, (fieldName, path, what, msg, bean) -> showView(bean.isSuccess()));
        //完成
        add(Completed.class, (fieldName, path, what, msg, bean) -> search(getKeyword()));
        query();
    }

    /**
     * 数据请求
     */
    protected void query() {
    }

    private boolean isFilter(IApiBean bean, IApiBean old, String keyword) {
        if (bean instanceof LineBean) {
            return !(old instanceof LineBean);
        } else if (bean instanceof IKeywordApi) {
            String code = ((IKeywordApi) bean).getApiCode();
            String fk = ((IKeywordApi) bean).getFilter();
            if (!TextUtils.isEmpty(getFilterData())) {
                for (String filter : getFilterData()) {
                    if (!TextUtils.isEmpty(filter) && !TextUtils.isEmpty(fk)
                            && fk.contains(filter)) {
                        return false;
                    }
                }
            }
            if (TextUtils.isEmpty(keyword)) {
                return true;
            }
            if (bean instanceof ViewBean) {
                return SearchUtil.search(((ViewBean) bean).getMap(), keyword);
            }
            return SearchUtil.search(bean, keyword);
        }
        return true;
    }

    /**
     * 搜索
     *
     * @param keyword 关键字
     */
    protected void search(String keyword) {
        setKeyword(keyword);
        adapterDataApi().setHelper((adapter, api, list)
                -> Observable.create((Observable.OnSubscribe<List<IApiBean>>) subscriber -> {
            List<IApiBean> data = new ArrayList<>();
            AtomicReference<IApiBean> old = new AtomicReference<>();
            execute(list, obj -> {
                /*if (isFilter(obj, old.get(), keyword)) {
                    data.add(obj);
                    old.set(obj);
                }*/ //TODO
            });
//            api.setFilterData(data); //TODO
            subscriber.onNext(null);
            subscriber.onCompleted();
        }).compose(Transformer.getInstance())
                .subscribe(obj -> adapter.notifyDataSetChanged()))
                .notifyDataSetChanged();
    }

    private String getSearchValue(String key) {
        if (!TextUtils.isEmpty(SEARCHS) && !TextUtils.isEmpty(key)) {
            for (String[] args : SEARCHS) {
                if (!TextUtils.isEmpty(args)) {
                    if (TextUtils.count(args) == 2 &&
                            TextUtils.equals(args[0], key)) {
                        return args[1];
                    }
                }
            }
        }
        return null;
    }

    private void showView(boolean enable) {
        if (enable) {
            showContentView();
        } else {
            showNullView(true);
        }
    }

}