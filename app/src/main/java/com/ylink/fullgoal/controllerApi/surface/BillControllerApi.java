package com.ylink.fullgoal.controllerApi.surface;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.vo.BillVo;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoView;

public class BillControllerApi<T extends BillControllerApi, C> extends BarControllerApi<T, C> {

    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;
    @Bind(R.id.photo_iv)
    PhotoView photoIv;
    @Bind(R.id.vg)
    ViewGroup vg;

    public BillControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getRootViewResId() {
        return R.layout.l_photo;
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("票据");
        executeBundle(bundle -> executeNon(getBundle(bundle, BillVo.class), vo -> setVisibility(TextUtils.isEmpty(vo.getMoney()) ? View.GONE : View.VISIBLE, vg)
                .setVisibility(TextUtils.isEmpty(vo.getPhoto()) ? View.INVISIBLE : View.VISIBLE, photoIv)
                .setText(nameTv, "金额")
                .setText(detailTv, vo.getMoney())
                .setImage(photoIv, vo.getPhoto())));
    }

}
