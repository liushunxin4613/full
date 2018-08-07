package com.leo.core.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.leo.core.api.main.DataApi;
import com.leo.core.iapi.core.IMNApi;
import com.leo.core.iapi.core.IModel;
import com.leo.core.iapi.core.INorm;
import com.leo.core.iapi.main.Adapter;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.TextUtils;

public class BaseRecycleControllerApiAdapter<T extends BaseRecycleControllerApiAdapter,
        C extends IControllerApi> extends RecyclerView.Adapter<ViewHolder> implements
        Adapter<BaseRecycleControllerApiAdapter>{

    private Context context;
    private DataApi<DataApi, IMNApi> dataApi;
    private SparseArray<Integer> sparseArray;
    private IControllerApi superControllerApi;

    @SuppressLint("UseSparseArrays")
    public BaseRecycleControllerApiAdapter(IControllerApi superControllerApi) {
        if (superControllerApi == null) {
            throw new NullPointerException("superControllerApi 不能为空");
        }
        this.superControllerApi = superControllerApi;
        sparseArray = new SparseArray<>();
        dataApi = new DataApi<>()
                .setAdapter(this);
        dataApi.setApi(obj -> {
            for (IMNApi model : dataApi.getData()) {
                if (model != null && TextUtils.equals(
                        model.getApiId(), obj.getApiId())) {
                    return true;
                }
            }
            return false;
        });
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
    }

    @Override
    public Context getContext() {
        return context;
    }

    private void put(int viewType, int position) {
        sparseArray.put(viewType, position);
    }

    private Integer getPosition(int viewType) {
        return sparseArray.get(viewType);
    }

    private IControllerApi getSuperControllerApi() {
        return superControllerApi;
    }

    public DataApi<DataApi, IMNApi> dataApi() {
        return dataApi;
    }

    @Override
    public int getItemCount() {
        return dataApi().getCount();
    }

    public IMNApi getItem(int position) {
        return dataApi().getItem(position);
    }

    @Override
    public int getItemViewType(int position) {
        IMNApi mnApi = getItem(position);
        if (mnApi == null) {
            throw new NullPointerException("mnApi不能为空");
        } else {
            int viewType = mnApi.getApiId() instanceof Integer
                    ? (int) mnApi.getApiId() : mnApi.hashCode();
            put(viewType, position);
            return viewType;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        IMNApi mnApi = getItem(getPosition(viewType));
        if (mnApi == null) {
            throw new NullPointerException("mnApi不能为空");
        }
        INorm norm = null;
        if (mnApi instanceof INorm) {
            norm = (INorm) mnApi;
            norm.setParentControllerApi(getSuperControllerApi());
            norm.setController(this);
            norm.initControllerApi();
        } else if (mnApi instanceof IModel) {
            IModel model = (IModel) mnApi;
            if (model.norm() == null) {//初始化
                model.setParentControllerApi(getSuperControllerApi());
                model.setController(this);
                model.initNorm();
            }
            norm = model.norm();
        }
        if (norm == null) {
            throw new NullPointerException("norm不能为空");
        }
        if (norm.controllerApi() == null) {
            throw new NullPointerException("norm.controllerApi()不能为空");
        }
        IControllerApi api = norm.controllerApi();
        api.setRootContainer(parent);
        api.setRootXmlResourceParser(norm.getApiXmlResourceParser());
        api.setRootViewResId(norm.getApiType());
        api.onCreateView(LayoutInflater.from(getContext()), parent, null);
        api.onViewCreated(api.getRootView(), null);
        return new ViewHolder(api.getRootView(), api);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IControllerApi api = holder.controllerApi();
        if (api == null) {
            throw new NullPointerException("api不能为空");
        }
        IMNApi mnApi = getItem(position);
        if (mnApi == null) {
            throw new NullPointerException("mnApi不能为空");
        }
        api.onNorm(mnApi instanceof INorm ? (INorm) mnApi : mnApi instanceof IModel
                ? ((IModel) mnApi).norm() : null, position);
    }

}