package com.ylink.fullgoal.bi;

import android.widget.EditText;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.MoneyBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class MoneyBi extends BaseApiBi<MoneyBi, MoneyBean> {

    @Override
    protected Integer getEnableDefLayoutResId() {
        return R.layout.l_h_money;
    }

    @Override
    protected Integer getEtEnableDefLayoutResId() {
        return R.layout.l_h_tv_money;
    }

    @Override
    public void onBindApi(SurfaceControllerApi api, MoneyBean bean) {
        super.onBindApi(api, bean);
        TextView nameTv = (TextView) api.findViewById(R.id.name_tv);
        TextView detailTv = (TextView) api.findViewById(R.id.detail_tv);
        EditText detailEt = (EditText) api.findViewById(R.id.detail_et);
        api.setText(nameTv, bean.getName())
                .execute(() -> {
                    if (detailEt != null) {
                        bean.setTextView(detailEt);
                    } else {
                        bean.setTextView(detailTv);
                    }
                })
                .setTextHint(detailEt, bean.getHint())
                .setText(detailEt, bean.getDetail())
                .setText(detailTv, bean.getDetail());
    }

}