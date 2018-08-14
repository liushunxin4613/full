package com.ylink.fullgoal.norm;

import android.view.View;
import android.widget.TextView;

import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.api.item.ReimburseTypeControllerApi;

import static com.leo.core.util.TextUtils.check;

public class ReimburseTypeNorm extends BaseTestNorm<ReimburseTypeNorm>{

    @Override
    public Class<? extends IControllerApi> getControllerApiClass() {
        return ReimburseTypeControllerApi.class;
    }

    private transient TextView nameTv;
    private transient TextView detailTv;
    private transient View selectedView;
    private transient IObjAction<String> action;

    public ReimburseTypeNorm(String name, String detail, IObjAction<String> action) {
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
