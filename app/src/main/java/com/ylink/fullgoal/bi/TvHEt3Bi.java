package com.ylink.fullgoal.bi;

import android.widget.EditText;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.TvHEt3Bean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class TvHEt3Bi extends BaseApiBi<TvHEt3Bi, TvHEt3Bean> {

    @Override
    protected Integer getEnableDefLayoutResId() {
        return R.layout.l_tv_et3;
    }

    @Override
    protected Integer getNoneDefLayoutResId() {
        return R.layout.l_tv_tv3_s;
    }

    @Override
    public void onBindApi(SurfaceControllerApi api, TvHEt3Bean bean) {
        super.onBindApi(api, bean);
        TextView nameTv = (TextView) api.findViewById(R.id.name_tv);
        TextView detailTv = (TextView) api.findViewById(R.id.detail_tv);
        EditText detailEt = (EditText) api.findViewById(R.id.detail_et);
        api.setText(nameTv, bean.getName())
                .setText(detailTv, bean.getDetail())
                .execute(() -> bean.setTextView(detailEt))
                .setText(detailEt, bean.getDetail())
                .setTextHint(detailEt, bean.getHint());
    }

}