package com.ylink.fullgoal.bean;

import android.view.View;
import android.widget.TextView;

import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.util.RunUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;

import static com.leo.core.util.TextUtils.check;
import static com.leo.core.util.TextUtils.getListData;

public class ReimburseTypeBean extends ApiBean<ReimburseTypeBean> {

    private transient TextView nameTv;
    private transient TextView detailTv;
    private transient View selectedView;
    private transient IObjAction<String> action;

    public ReimburseTypeBean(String name, String detail, IObjAction<String> action) {
        super(name, detail);
        this.action = action;
    }

    @Override
    public Integer getApiType() {
        return R.layout.l_sx_bottom;
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
