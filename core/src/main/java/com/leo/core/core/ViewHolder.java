package com.leo.core.core;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leo.core.iapi.main.IControllerApi;

public class ViewHolder extends RecyclerView.ViewHolder {

    private transient IControllerApi api;

    ViewHolder(View itemView, IControllerApi api) {
        super(itemView);
        this.api = api;
    }

    public IControllerApi api() {
        return api;
    }

}
