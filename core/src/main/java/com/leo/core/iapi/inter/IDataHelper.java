package com.leo.core.iapi.inter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.leo.core.api.main.DataApi;

import java.util.List;

public interface IDataHelper<A> {
    void execute(@NonNull RecyclerView.Adapter adapter, @NonNull DataApi<DataApi, A> api,
                 @NonNull List<A> data);
}