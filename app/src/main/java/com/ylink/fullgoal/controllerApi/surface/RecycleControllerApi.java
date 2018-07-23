package com.ylink.fullgoal.controllerApi.surface;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leo.core.api.main.DataApi;
import com.leo.core.iapi.api.IRecycleApi;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.api.IShowDataApi;
import com.leo.core.iapi.main.IApiBean;
import com.leo.core.util.SoftInputUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.LineBean;
import com.ylink.fullgoal.bean.VgBean;
import com.ylink.fullgoal.bean.VgBeanD1;
import com.ylink.fullgoal.controllerApi.core.RecycleControllerApiAdapter;
import com.ylink.fullgoal.core.BaseBiBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

public class RecycleControllerApi<T extends RecycleControllerApi, C> extends ContentControllerApi<T, C>
        implements IRecycleApi<T, RecycleControllerApiAdapter>, IShowDataApi {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private RecycleControllerApiAdapter adapter;
    private RecyclerView.LayoutManager manager;
    private DataApi<DataApi, IApiBean> dataApi;

    public RecycleControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
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
        return new RecycleControllerApiAdapter(getThis());
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
        adapterDataApi().notifyDataSetChanged();
        return getThis();
    }

    @Override
    public View getContentView() {
        return getRecyclerView();
    }

    @Override
    public void initView() {
        super.initView();
        getRecyclerView().setLayoutManager(getLayoutManager());
        getRecyclerView().setAdapter(getRecycleAdapter());
        getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case SCROLL_STATE_DRAGGING://手指滑动
                        SoftInputUtil.hidSoftInput(recyclerView);
                        break;
                    case SCROLL_STATE_IDLE://结束滑动
                        break;
                    case SCROLL_STATE_SETTLING://惯性滑动
                        break;
                }
            }
        });
    }

    protected void initDataAction(IObjAction<List<IApiBean>> action) {
        clear();
        if (action != null) {
            List<IApiBean> data;
            action.execute(data = new ArrayList<>());
            execute(getLineData(data), this::add);
        }
        notifyDataSetChanged();
    }

    protected List<IApiBean> getLineData(List<? extends IApiBean> data) {
        if (!TextUtils.isEmpty(data)) {
            List<IApiBean> dat = new ArrayList<>();
            int count = TextUtils.count(data);
            for (int i = 0; i < count; i++) {
                dat.add(data.get(i));
                dat.add(new LineBean());
            }
            return dat;
        }
        return null;
    }

    public T clear() {
        adapterDataApi().removeAll();
        return getThis();
    }

    public boolean check(IApiBean bean) {
        return adapterDataApi().check(bean);
    }

    public int getCount() {
        return adapterDataApi().getCount();
    }

    public IApiBean getItem(int position) {
        return adapterDataApi().getItem(position);
    }

    public T add(IApiBean bean) {
        adapterDataApi().add(bean);
        return getThis();
    }

    public T addAll(List<IApiBean> data) {
        adapterDataApi().addAll(data);
        return getThis();
    }

    public T replaceAll(List<IApiBean> data) {
        clear().addAll(data);
        return getThis();
    }

    public <B extends IApiBean> T addLineAll(List<B> data, boolean end, IObjAction<B> api) {
        if (!TextUtils.isEmpty(data)) {
            for (B bean : data) {
                if (!check(bean)) {
                    if (!end && getCount() > 0) {
                        add(new LineBean());
                    }
                    if (api != null) {
                        api.execute(bean);
                    }
                    add(bean);
                    if (end) {
                        add(new LineBean());
                    }
                }
            }
        }
        return getThis();
    }

    public <B extends IApiBean> T replaceLineAll(List<B> data, boolean end) {
        replaceApiAll(data, end, null);
        return getThis();
    }

    public <B extends IApiBean> T replaceApiAll(List<B> data, boolean end, IObjAction<B> api) {
        clear().addLineAll(data, end, api);
        return getThis();
    }

    //私有的

    protected T addSmallVgBean(BaseBiBean... args) {
        if (!TextUtils.isEmpty(args)) {
            add(new VgBean(TextUtils.getListData(args), LineBean.SMALL));
        }
        return getThis();
    }

    protected T addSmallVgBeanD1(BaseBiBean... args) {
        if (!TextUtils.isEmpty(args)) {
            add(new VgBeanD1(TextUtils.getListData(args), LineBean.SMALL));
        }
        return getThis();
    }

    protected void addVgBean(BaseBiBean... args) {
        if (!TextUtils.isEmpty(args)) {
            add(new VgBean(TextUtils.getListData(args)));
        }
    }

    public VgBean addVgBean(IObjAction<List<BaseBiBean>> api) {
        if (api != null) {
            List<BaseBiBean> data = new ArrayList<>();
            api.execute(data);
            if (!TextUtils.isEmpty(data)) {
                VgBean vb = new VgBean(data);
                add(vb);
                return vb;
            }
        }
        return null;
    }

}