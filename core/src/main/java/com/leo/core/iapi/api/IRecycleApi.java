package com.leo.core.iapi.api;

import android.support.v7.widget.RecyclerView;

import com.leo.core.core.BaseRecycleControllerApiAdapter;
import com.leo.core.iapi.core.IApi;

public interface IRecycleApi<T extends IRecycleApi, A extends BaseRecycleControllerApiAdapter> extends IApi {

    RecyclerView getRecyclerView();

    RecyclerView.LayoutManager getLayoutManager();

    RecyclerView.LayoutManager newLayoutManager();

    A getRecycleAdapter();

    A newRecycleAdapter();

    T notifyDataSetChanged();

}
