package com.leo.core.core;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leo.core.iapi.inter.IViewHolder;
import com.leo.core.iapi.main.IControllerApi;

public class ViewHolder extends RecyclerView.ViewHolder implements IViewHolder {

    private IControllerApi controllerApi;

    public ViewHolder(View itemView, IControllerApi controllerApi) {
        super(itemView);
        this.controllerApi = controllerApi;
    }

    @Override
    public IControllerApi controllerApi() {
        return controllerApi;
    }

}
