package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.norm.TvH2MoreNorm;

import butterknife.Bind;

public class TvH2MoreControllerApi<C> extends OnClickControllerApi<TvH2MoreControllerApi, C, TvH2MoreNorm>{

    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;

    private ImageView iconIv;

    public TvH2MoreControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_h_tv2_more;
    }

    @Override
    public void initView() {
        super.initView();
        iconIv = findViewById(R.id.icon_iv);
    }

    @Override
    protected void onSafeNorm(@NonNull TvH2MoreNorm norm, int position) {
        super.onSafeNorm(norm, position);
        setText(nameTv, norm.getName())
                .setText(detailTv, norm.getDetail())
                .execute(() -> norm.setTextView(detailTv))
                .setText(detailTv, TextUtils.isEmpty(norm.getDetail()) ? norm.getHint()
                        : norm.getDetail())
                .setTextColor(detailTv, getResTvColor(norm.getDetail()))
                .setImage(iconIv, TextUtils.isEmpty(norm.getDetail()) ? R.mipmap.more
                        : R.mipmap.cha)
                .setOnClickListener(iconIv, TextUtils.isEmpty(norm.getDetail())
                        ? norm.getOnClickListener() : norm.getIconOnClickListener());
    }

}