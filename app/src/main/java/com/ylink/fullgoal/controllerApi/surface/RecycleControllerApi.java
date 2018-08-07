package com.ylink.fullgoal.controllerApi.surface;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leo.core.api.main.DataApi;
import com.leo.core.bean.Completed;
import com.leo.core.core.BaseRecycleControllerApiAdapter;
import com.leo.core.iapi.api.IRecycleApi;
import com.leo.core.iapi.core.IMNApi;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.api.IShowDataApi;
import com.leo.core.util.SoftInputUtil;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.controllerApi.core.RecycleControllerApiAdapter;

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

    private RecyclerView.LayoutManager manager;
    private DataApi<DataApi, IMNApi> dataApi;
    private BaseRecycleControllerApiAdapter adapter;

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
        return (RecycleControllerApiAdapter) adapter;
    }

    @Override
    public RecycleControllerApiAdapter newRecycleAdapter() {
        return new RecycleControllerApiAdapter(getThis());
    }

    @Override
    public DataApi<DataApi, IMNApi> adapterDataApi() {
        if (dataApi == null) {
            dataApi = newAdapterDataApi();
            if (dataApi == null) {
                throw new NullPointerException("newAdapterDataApi不能为空");
            }
        }
        return dataApi;
    }

    @Override
    public DataApi<DataApi, IMNApi> newAdapterDataApi() {
        return getRecycleAdapter() == null ? null : getRecycleAdapter().dataApi();
    }

    @Override
    public T notifyDataSetChanged() {
        super.notifyDataChanged();
        adapterDataApi().notifyDataSetChanged();
        return getThis();
    }

    @Override
    public View getContentView() {
        return getRecyclerView();
    }

    @Override
    public void initAddAction() {
        super.initAddAction();
        add(Completed.class, (fieldName, path, what, msg, bean)
                -> vs(adapterDataApi(), DataApi::openEmptyListen));
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

    protected void initDataAction(IObjAction<List<IMNApi>> action) {
        clear();
        if (action != null) {
            List<IMNApi> data;
            action.execute(data = new ArrayList<>());
            execute(data, this::add);
        }
        notifyDataSetChanged();
        dismissLoading();
    }

    public T clear() {
        adapterDataApi().removeAll();
        return getThis();
    }

    public boolean check(IMNApi bean) {
        return adapterDataApi().check(bean);
    }

    public int getCount() {
        return adapterDataApi().getCount();
    }

    public IMNApi getItem(int position) {
        return adapterDataApi().getItem(position);
    }

    public T add(IMNApi bean) {
        adapterDataApi().add(bean);
        return getThis();
    }

    public T addAll(List<IMNApi> data) {
        adapterDataApi().addAll(data);
        return getThis();
    }

    public T replaceAll(List<IMNApi> data) {
        clear().addAll(data);
        return getThis();
    }

    //私有的

    /*protected T addSmallVgBean(BaseBiBean... args) {
        if (!TextUtils.isEmpty(args)) {
            add(new VgBean(TextUtils.getListData(args), LineBean.SMALL));
        }
        return getThis();
    }*/

    /*protected T addSmallVgBeanD1(BaseBiBean... args) {
        if (!TextUtils.isEmpty(args)) {
            add(new VgBeanD1(TextUtils.getListData(args), LineBean.SMALL));
        }
        return getThis();
    }*/

    /*protected void addVgBean(BaseBiBean... args) {
        if (!TextUtils.isEmpty(args)) {
            add(new VgBean(TextUtils.getListData(args)));
        }
    }*/

    /*public VgBean addVgBean(IObjAction<List<BaseBiBean>> api){
        return addVgBean(api, false);
    }

    public VgBean addVgBean(IObjAction<List<BaseBiBean>> api, boolean noLine) {
        if (api != null) {
            List<BaseBiBean> data = new ArrayList<>();
            api.execute(data);
            if (!TextUtils.isEmpty(data)) {
                VgBean vb = new VgBean(data);
                vb.setNoLine(noLine);
                add(vb);
                return vb;
            }
        }
        return null;
    }*/

}