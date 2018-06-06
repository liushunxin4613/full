package com.leo.core.core;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.leo.core.api.main.DataApi;
import com.leo.core.iapi.main.Adapter;
import com.leo.core.iapi.main.CreateControllerApiCallback;
import com.leo.core.iapi.main.IAFVApi;
import com.leo.core.iapi.main.IApiBean;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.TextUtils;

public class BaseRecycleControllerApiAdapter<T extends BaseRecycleControllerApiAdapter,
        C extends IControllerApi> extends RecyclerView.Adapter<ViewHolder>
        implements Adapter<T>, IAFVApi<T, C> {

    private Context context;
    private IControllerApi controllerApi;
    private DataApi<DataApi, IApiBean> dataApi;
    private CreateControllerApiCallback callback;

    public BaseRecycleControllerApiAdapter() {
        dataApi = new DataApi<>();
        dataApi.setApi(obj -> {
            if(obj.getApiType() == null){
                return true;
            }
            for (IApiBean bean : dataApi.getData()) {
                if(bean != null && TextUtils.equals(bean.getApiId(), obj.getApiId())){
                    return true;
                }
            }
            return false;
        });
    }

    public T setCallback(CreateControllerApiCallback callback) {
        this.callback = callback;
        return (T)this;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public IControllerApi<C, T> controllerApi() {
        if (controllerApi == null) {
            controllerApi = newControllerApi();
            if (controllerApi == null) {
                throw new NullPointerException("newControllerApi 不能为空");
            }
        }
        return controllerApi;
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

    public IApiBean getItem(int position){
        return dataApi().getItem(position);
    }

    @Override
    public int getItemViewType(int position) {
        IApiBean bean = getItem(position);
        return bean == null ? 0 : bean.getApiType() == null ? 0 : bean.getApiType();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        if(callback == null){
            throw new NullPointerException("callback不能为空");
        }
        IControllerApi api = callback.createControllerApi(parent, viewType);
        if(api == null){
            throw new NullPointerException("api不能为空");
        }
        api.setRootViewResId(viewType);
        api.initController(this);
        api.onCreateView(LayoutInflater.from(getContext()), parent, null);
        api.onViewCreated(api.getRootView(), null);
        return new ViewHolder(api.getRootView(), api);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        IControllerApi controllerApi = holder.controllerApi();
        if(controllerApi != null){
            controllerApi.onBindViewHolder(getItem(position), position);
        }
    }

}
