package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.norm.CCSQDNorm;
import com.ylink.fullgoal.norm.CCSQDNormV1;

import butterknife.Bind;

public class CCSQDControllerApiV1<C> extends OnClickControllerApi<CCSQDControllerApiV1, C, CCSQDNormV1> {

    @Bind(R.id.start_tv)
    TextView startTv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;

    public CCSQDControllerApiV1(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_ccsqd_v1;
    }

    @Override
    protected void onSafeNorm(@NonNull CCSQDNormV1 norm, int position) {
        super.onSafeNorm(norm, position);
        setText(nameTv, norm.getName())
                .setText(detailTv, norm.getDetail())
                .setText(startTv, norm.getStart())
                .execute(() -> executeNon(norm.getColorResId(), id -> setTextColor(detailTv, id)))
                .setEnableOnClickListener(norm.getOnClickListener());
    }

}