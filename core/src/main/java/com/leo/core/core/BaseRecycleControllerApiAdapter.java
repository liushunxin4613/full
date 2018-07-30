package com.leo.core.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.leo.core.api.main.DataApi;
import com.leo.core.iapi.main.Adapter;
import com.leo.core.iapi.main.IAFVApi;
import com.leo.core.iapi.main.IApiBean;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.TextUtils;

public class BaseRecycleControllerApiAdapter<T extends BaseRecycleControllerApiAdapter,
        C extends IControllerApi> extends RecyclerView.Adapter<ViewHolder>
        implements Adapter<T>, IAFVApi<T, C> {

    private Context context;
    private IControllerApi superControllerApi;
    private IControllerApi controllerApi;
    private DataApi<DataApi, IApiBean> dataApi;
    private SparseArray<Integer> sparseArray;

    @SuppressLint("UseSparseArrays")
    public BaseRecycleControllerApiAdapter(IControllerApi superControllerApi) {
        if (superControllerApi == null) {
            throw new NullPointerException("superControllerApi 不能为空");
        }
        this.superControllerApi = superControllerApi;
        dataApi = new DataApi<>().setAdapter(this);
        dataApi.setApi(obj -> {
            if (!obj.checkApi()) {
                return true;
            }
            for (IApiBean bean : dataApi.getData()) {
                if (bean != null && TextUtils.equals(bean.getApiId(), obj.getApiId())) {
                    return true;
                }
            }
            return false;
        });
        sparseArray = new SparseArray<>();
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public IControllerApi<C, T> controllerApi() {
        if (controllerApi == null) {
            setControllerApi(newControllerApi());
        }
        return controllerApi;
    }

    @Override
    public void setControllerApi(IControllerApi<C, T> api) {
        controllerApi = api;
        if (controllerApi == null) {
            throw new NullPointerException("newControllerApi 不能为空");
        }
    }

    @Override
    public IControllerApi<C, T> newControllerApi() {
        return null;
    }

    public DataApi<DataApi, IApiBean> dataApi() {
        return dataApi;
    }

    @Override
    public int getItemCount() {
        return dataApi().getCount();
    }

    public IApiBean getItem(int position) {
        return dataApi().getItem(position);
    }

    @Override
    public int getItemViewType(int position) {
        IApiBean bean = getItem(position);
        if (bean == null) {
            throw new NullPointerException("bean不能为空");
        }
        int viewType = bean.hashCode();
        sparseArray.put(viewType, position);
        return viewType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        IApiBean bean = getItem(sparseArray.get(viewType));
        if (bean == null) {
            throw new NullPointerException("bean不能为空");
        }
        if (!bean.checkApi()) {
            throw new NullPointerException("bean.checkApi()不通过");
        }
        IControllerApi api = bean.getControllerApi(superControllerApi);
        if (api == null) {
            throw new NullPointerException("api不能为空");
        }
        api.setRootContainer(parent);
        api.setRootXmlResourceParser(bean.getApiXmlResourceParser());
        api.setRootViewResId(bean.getApiType());
        api.initController(this);
        api.onCreateView(LayoutInflater.from(getContext()), parent, null);
        api.onViewCreated(api.getRootView(), null);
        return new ViewHolder(api.getRootView(), api);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IControllerApi controllerApi = holder.controllerApi();
        if (controllerApi != null) {
            controllerApi.onBindViewHolder(getItem(position), position);
        }
    }

}