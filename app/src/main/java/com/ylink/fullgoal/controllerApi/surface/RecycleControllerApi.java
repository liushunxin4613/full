package com.ylink.fullgoal.controllerApi.surface;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leo.core.api.main.DataApi;
import com.leo.core.bean.Completed;
import com.leo.core.core.BaseRecycleControllerApiAdapter;
import com.leo.core.iapi.api.IRecycleApi;
import com.leo.core.iapi.core.IMNApi;
import com.leo.core.iapi.core.INorm;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.api.IShowDataApi;
import com.leo.core.other.Transformer;
import com.leo.core.util.SoftInputUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.controllerApi.core.RecycleControllerApiAdapter;
import com.ylink.fullgoal.norm.VgNorm;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;

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
        add(Completed.class, (type, baseUrl, path, map, what, msg, field, bean)
                -> vs(adapterDataApi(), DataApi::openEmptyListen));
    }

    @Override
    public void initView() {
        super.initView();
        getRecycleAdapter().setHasStableIds(true);
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

    public void initDataAction(IObjAction<List<IMNApi>> action) {
        if (action != null) {
            List<IMNApi> data;
            action.execute(data = new ArrayList<>());
            initActionData(data);
        }
    }

    public <A extends IMNApi> void initActionData(List<A> data) {
        Observable.create(subscriber -> {
            clear();
            if (!TextUtils.isEmpty(data)) {
                runOnUiThread(this::showContentView);
                execute(data, item -> {
                    item.createSearchText();
                    add(item);
                });
            }
            subscriber.onNext(null);
            subscriber.onCompleted();
        }).compose(Transformer.getInstance()).subscribe(obj -> {
            notifyDataSetChanged();
            dismissLoading();
            onInitActionData();
        });
    }

    protected void onInitActionData() {
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

    protected T addSmallVgNorm(INorm... args) {
        if (!TextUtils.isEmpty(args)) {
            add(new VgNorm(TextUtils.getListData(args))
                    .setLineLayoutResId(VgNorm.LINE_SMALL));
        }
        return getThis();
    }

    protected T addNoneSmallVgNorm(INorm... args) {
        if (!TextUtils.isEmpty(args)) {
            add(new VgNorm(TextUtils.getListData(args), VgNorm.LAYOUT_LINE_RES_ID)
                    .setLineLayoutResId(VgNorm.LINE_SMALL));
        }
        return getThis();
    }

    protected T addVgNorm(INorm... args) {
        if (!TextUtils.isEmpty(args)) {
            add(new VgNorm(TextUtils.getListData(args), VgNorm.LAYOUT_LINE_RES_ID)
                    .setLineLayoutResId(VgNorm.LINE_NORMAL));
        }
        return getThis();
    }

    public VgNorm addVgNorm(IObjAction<List<INorm>> api) {
        return addVgNorm(api, false);
    }

    public VgNorm addVgNorm(IObjAction<List<INorm>> api, boolean noLine) {
        return addVgNorm(api, noLine, false);
    }

    public VgNorm addVgNorm(IObjAction<List<INorm>> api, boolean noLine, boolean end) {
        if (api != null) {
            List<INorm> data = new ArrayList<>();
            api.execute(data);
            if (!TextUtils.isEmpty(data)) {
                VgNorm vb = new VgNorm(data, VgNorm.LAYOUT_LINE_RES_ID)
                        .setLineLayoutResId(noLine ? null : VgNorm.LINE_NORMAL)
                        .setEnd(end);
                add(vb);
                return vb;
            }
        }
        return null;
    }

}