package com.leo.core.core;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.leo.core.api.main.DataApi;
import com.leo.core.iapi.main.Adapter;
import com.leo.core.iapi.main.IAFVApi;
import com.leo.core.iapi.main.IApiBean;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.LogUtil;
import com.leo.core.util.TextUtils;

public class BaseRecycleControllerApiAdapter<T extends BaseRecycleControllerApiAdapter,
        C extends IControllerApi> extends RecyclerView.Adapter<ViewHolder>
        implements Adapter<T>, IAFVApi<T, C> {

    private Context context;
    private IControllerApi superControllerApi;
    private IControllerApi controllerApi;
    private DataApi<DataApi, IApiBean> dataApi;

    public BaseRecycleControllerApiAdapter(IControllerApi superControllerApi) {
        if (superControllerApi == null) {
            throw new NullPointerException("superControllerApi 不能为空");
        }
        this.superControllerApi = superControllerApi;
        dataApi = new DataApi<>().setAdapter(this);
        dataApi.setApi(obj -> {
            if (obj.getApiType() == null) {
                return true;
            }
            for (IApiBean bean : dataApi.getData()) {
                if (bean != null && TextUtils.equals(bean.getApiId(), obj.getApiId())) {
                    return true;
                }
            }
            return false;
        });
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

    public IApiBean getItem(int position) {
        return dataApi().getItem(position);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        context = parent.getContext();
        IApiBean bean = getItem(position);
        if (bean == null) {
            throw new NullPointerException("bean不能为空");
        }
        if (bean.getApiType() == null) {
            throw new NullPointerException("bean.getApiType()不能为空");
        }
        IControllerApi api = bean.getControllerApi(superControllerApi);
        if (api == null) {
            throw new NullPointerException("api不能为空");
        }
        api.setRootViewResId(bean.getApiType());
        api.initController(this);
        api.onCreateView(LayoutInflater.from(getContext()), parent, null);
        api.onViewCreated(api.getRootView(), null);
        return new ViewHolder(api.getRootView(), api);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        IControllerApi controllerApi = holder.controllerApi();
        if (controllerApi != null) {
            controllerApi.onBindViewHolder(getItem(position), position);
        }
    }

}