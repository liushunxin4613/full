package com.ylink.fullgoal.bean;

import android.view.View;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.ProjectBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBiBean;

public class ProjectBean extends SurfaceBiBean<ProjectBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, ProjectBean> newDefApi() {
        return new ProjectBi();
    }

    private String name;
    private String time;
    private String left;
    private String center;
    private String right;
    private String detail;
    private transient View.OnClickListener onClickListener;

    public ProjectBean(String name, String time, String left, String center, String right,
                       String detail, OnBVClickListener<ProjectBean> listener) {
        this.name = name;
        this.time = time;
        this.left = left;
        this.center = center;
        this.right = right;
        this.detail = detail;
        this.onClickListener = getOnBVClickListener(listener);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

}