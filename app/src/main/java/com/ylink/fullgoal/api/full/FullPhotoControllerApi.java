package com.ylink.fullgoal.api.full;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.vo.BillVo;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoView;

public class FullPhotoControllerApi<T extends FullPhotoControllerApi, C> extends SurfaceControllerApi<T, C> {

    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_et)
    EditText detailEt;
    @Bind(R.id.photo_iv)
    PhotoView photoIv;
    @Bind(R.id.vg)
    ViewGroup vg;

    public FullPhotoControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_photo;
    }

    @Override
    public void initView() {
        super.initView();
        putBindBeanApi(BillVo.class, (api, bean)
                -> setVisibility(bean.isShow() ? View.VISIBLE : View.GONE, vg)
                .setVisibility(TextUtils.isEmpty(bean.getPhoto()) ? View.INVISIBLE : View.VISIBLE, photoIv)
                .setText(nameTv, "金额")
                .setText(detailEt, bean.getMoney())
                .setImage(photoIv, bean.getPhoto())
                .execute(() -> detailEt.addTextChangedListener(getMoneyTextWatcher(bean::setMoney))));
    }

}
