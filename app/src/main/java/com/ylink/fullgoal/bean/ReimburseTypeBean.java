package com.ylink.fullgoal.bean;

import android.view.View;
import android.widget.TextView;

import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.main.IBindControllerApi;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.bi.ReimburseTypeBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import static com.leo.core.util.TextUtils.check;

public class ReimburseTypeBean extends ApiBean<ReimburseTypeBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, ReimburseTypeBean> newDefApi() {
        return new ReimburseTypeBi();
    }

    private transient TextView nameTv;
    private transient TextView detailTv;
    private transient View selectedView;
    private transient IObjAction<String> action;

    public ReimburseTypeBean(String name, String detail, IObjAction<String> action) {
        super(name, detail);
        this.action = action;
    }

    public TextView getNameTv() {
        return nameTv;
    }

    public void setNameTv(TextView nameTv) {
        this.nameTv = nameTv;
    }

    public TextView getDetailTv() {
        return detailTv;
    }

    public void setDetailTv(TextView detailTv) {
        this.detailTv = detailTv;
    }

    public void execute(View view, String text) {
        this.selectedView = view;
        if (action != null) {
            action.execute(text);
        }
    }

    public void updateSelected(String selected) {
        if (check(getNameTv())) {
            getNameTv().setSelected(TextUtils.equals(selected, getName()));
        }
        if (check(getDetailTv())) {
            getDetailTv().setSelected(TextUtils.equals(selected, getDetail()));
        }
    }

    public void clean() {
        if (selectedView != null) {
            selectedView.setSelected(false);
            selectedView = null;
        }
        if (action != null) {
            action.execute(null);
        }
    }

}
