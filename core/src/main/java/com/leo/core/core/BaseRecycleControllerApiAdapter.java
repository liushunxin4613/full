package com.leo.core.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.leo.core.api.main.DataApi;
import com.leo.core.iapi.core.IMNApi;
import com.leo.core.iapi.core.IModel;
import com.leo.core.iapi.core.INorm;
import com.leo.core.iapi.main.Adapter;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.TextUtils;

public class BaseRecycleControllerApiAdapter extends RecyclerView.Adapter<ViewHolder> implements
        Adapter<BaseRecycleControllerApiAdapter> {

    private Context context;
    private DataApi<DataApi, IMNApi> dataApi;
    private IControllerApi superControllerApi;
    private SparseArray<INorm> sparseArray;

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

    private void put(int viewType, INorm norm) {
        if (norm != null) {
            sparseArray.put(viewType, norm);
        }
    }

    private INorm getPosition(int viewType) {
        return sparseArray.get(viewType);
    }

    private boolean contains(int viewType) {
        return sparseArray.indexOfKey(viewType) >= 0;
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

    @Override
    public long getItemId(int position) {
        return position;
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
            Integer viewType = mnApi.getViewType();
            if (viewType == null) {
                if (mnApi instanceof IModel && ((IModel) mnApi).norm() == null) {
                    mnApi.setController(this);
                    mnApi.setParentControllerApi(getSuperControllerApi());
                    ((IModel) mnApi).initNorm();
                    viewType = mnApi.getViewType();
                }
                if (viewType == null) {
                    throw new NullPointerException("viewType不能为空");
                }
            }
            if (!contains(viewType)) {
                INorm norm = null;
                if (mnApi instanceof IModel) {
                    norm = ((IModel) mnApi).norm();
                } else if (mnApi instanceof INorm) {
                    norm = (INorm) mnApi;
                }
                if (norm != null) {
                    norm.setController(this);
                    norm.setParentControllerApi(getSuperControllerApi());
                    put(viewType, norm);
                }
            }
            return viewType;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        INorm norm = getPosition(viewType);
        if (norm == null) {
            throw new NullPointerException("norm不能为空");
        }
        IControllerApi api = norm.createControllerApi();
        if (api == null) {
            throw new NullPointerException("api不能为空");
        }
        api.setRootContainer(parent);
        api.onCreate(null);
        return new ViewHolder(api.getRootView(), api);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        INorm norm = null;
        IMNApi mnApi = getItem(position);
        if (mnApi instanceof IModel) {
            norm = ((IModel) mnApi).norm();
        } else if (mnApi instanceof INorm) {
            norm = (INorm) mnApi;
        }
        if (norm != null && holder.api() != null) {
            norm.setControllerApi(holder.api());//替换ControllerApi
            norm.controllerApi().onNorm(norm, position);
        }
    }

}