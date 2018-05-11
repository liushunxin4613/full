package com.ylink.fullgoal.controllerApi.surface;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.leo.core.api.main.DataApi;
import com.leo.core.bean.BaseApiBean;
import com.leo.core.iapi.IRecycleApi;
import com.leo.core.iapi.IRunApi;
import com.leo.core.iapi.IShowDataApi;
import com.leo.core.iapi.main.CreateControllerApiCallback;
import com.leo.core.iapi.main.IApiBean;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.api.surface.ItemControllerApi;
import com.ylink.fullgoal.bean.GridBean;
import com.ylink.fullgoal.bean.LineBean;
import com.ylink.fullgoal.bean.VgBean;
import com.ylink.fullgoal.controllerApi.core.RecycleControllerApiAdapter;

import java.util.List;

import butterknife.Bind;

public class RecycleControllerApi<T extends RecycleControllerApi, C> extends ContentControllerApi<T, C>
        implements IRecycleApi<T, RecycleControllerApiAdapter>, IShowDataApi, CreateControllerApiCallback {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private RecycleControllerApiAdapter adapter;
    private RecyclerView.LayoutManager manager;
    private DataApi<DataApi, IApiBean> dataApi;

    public RecycleControllerApi(C controller) {
        super(controller);
    }

    @Override
    public IControllerApi createControllerApi(ViewGroup container, int resId) {
        return new ItemControllerApi(null);
    }

    @Override
    public Integer getRootViewResId() {
        return R.layout.l_recycle;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (manager == null) {
            manager = newLayoutManager();
            if (manager == null) {
                throw new NullPointerException("newLayoutManager不能为空");
            }
        }
        return manager;
    }

    @Override
    public RecyclerView.LayoutManager newLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    public RecycleControllerApiAdapter getRecycleAdapter() {
        if (adapter == null) {
            adapter = newRecycleAdapter();
            if (adapter == null) {
                throw new NullPointerException("newRecycleAdapter不能为空");
            }
        }
        return adapter;
    }

    @Override
    public RecycleControllerApiAdapter newRecycleAdapter() {
        return new RecycleControllerApiAdapter();
    }

    @Override
    public DataApi<DataApi, IApiBean> adapterDataApi() {
        if (dataApi == null) {
            dataApi = newAdapterDataApi();
            if (dataApi == null) {
                throw new NullPointerException("newAdapterDataApi不能为空");
            }
        }
        return dataApi;
    }

    @Override
    public DataApi<DataApi, IApiBean> newAdapterDataApi() {
        return getRecycleAdapter() == null ? null : getRecycleAdapter().dataApi();
    }

    @Override
    public T notifyDataSetChanged() {
        getRecycleAdapter().notifyDataSetChanged();
        return getThis();
    }

    @Override
    public RecyclerView getContentView() {
        return getRecyclerView();
    }

    @Override
    public void initView() {
        super.initView();
        getRecyclerView().setLayoutManager(getLayoutManager());
        getRecyclerView().setAdapter(getRecycleAdapter());
        getRecycleAdapter().setCallback(this);
    }

    protected T clear() {
        adapterDataApi().removeAll();
        return getThis();
    }

    protected boolean check(IApiBean bean){
        return adapterDataApi().check(bean);
    }

    protected int getCount() {
        return adapterDataApi().getCount();
    }

    protected IApiBean getItem(int position){
        return adapterDataApi().getItem(position);
    }

    protected T add(IApiBean bean) {
        adapterDataApi().add(bean);
        return getThis();
    }

    protected T addAll(List<IApiBean> data) {
        adapterDataApi().addAll(data);
        return getThis();
    }

    protected T replaceAll(List<IApiBean> data) {
        clear().addAll(data);
        return getThis();
    }

    protected <B extends IApiBean> T addLineAll(List<B> data, boolean end, IRunApi<B> api) {
        if (!TextUtils.isEmpty(data)) {
            for (B bean : data) {
                if(!check(bean)){
                    if(!end && getCount() > 0){
                        add(new LineBean());
                    }
                    if(api != null){
                        api.execute(bean);
                    }
                    add(bean);
                    if(end){
                        add(new LineBean());
                    }
                }
            }
        }
        return getThis();
    }

    protected <B extends IApiBean> T replaceLineAll(List<B> data, boolean end) {
        replaceApiAll(data, end, null);
        return getThis();
    }

    protected <B extends IApiBean> T replaceApiAll(List<B> data, boolean end, IRunApi<B> api) {
        clear().addLineAll(data, end, api);
        return getThis();
    }

    //私有的

    public T addVgBean(BaseApiBean... args){
        if(!TextUtils.isEmpty(args)){
            add(new VgBean(TextUtils.getListData(args)));
        }
        return getThis();
    }

    public T addGridBean(BaseApiBean... args){
        if(!TextUtils.isEmpty(args)){
            add(new GridBean(TextUtils.getListData(args)));
        }
        return getThis();
    }

}
