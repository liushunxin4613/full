package com.leo.core.core;

import com.leo.core.iapi.core.IModel;
import com.leo.core.iapi.core.INorm;

public class Model extends MNApi implements IModel {

    private transient INorm norm;

    @Override
    public Integer getViewType() {
        return norm() == null ? null : norm().getViewType();
    }

    @Override
    public void initNorm() {
    }

    @Override
    public void setNorm(INorm norm) {
        this.norm = norm;
    }

    @Override
    public INorm norm() {
        return norm;
    }


    @Override
    public String getApiCode() {
        return null;
    }

    @Override
    public void setSelectedApiCode(String apiCode) {
        super.setSelectedApiCode(apiCode);
        if (norm() != null) {
            norm().setSelectedApiCode(apiCode);
        }
    }

}