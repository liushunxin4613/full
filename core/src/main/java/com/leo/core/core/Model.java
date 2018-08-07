package com.leo.core.core;

import com.leo.core.iapi.core.IModel;
import com.leo.core.iapi.core.INorm;
import com.leo.core.iapi.main.IControllerApi;

public class Model extends MNApi implements IModel {

    private transient INorm norm;

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

}