package com.ylink.fullgoal.api.full;

import android.view.View;

import com.leo.core.adapter.BasePagerAdapter;
import com.leo.core.bean.Bol;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.controllerApi.surface.BarViewPagerControllerApi;
import com.ylink.fullgoal.fg.ImageFg;
import com.ylink.fullgoal.vo.ImageVo;

import java.util.List;

public class FullBillControllerApi<T extends FullBillControllerApi, C> extends BarViewPagerControllerApi<T, C> {

    private List<ImageVo> data;
    private ImageVo vo;
    private boolean bol;

    public FullBillControllerApi(C controller) {
        super(controller);
    }

    @Override
    public BasePagerAdapter getPagerAdapter() {
        return super.getPagerAdapter();
    }

    @Override
    public BasePagerAdapter newPagerAdapter() {
        return new BasePagerAdapter();
    }

    @Override
    public void initView() {
        super.initView();
        executeBundle(bundle -> {
            executeNon(getBundleList(bundle, ImageVo.class), data -> this.data = data);
            executeNon(getBundle(bundle, ImageVo.class), vo -> this.vo = vo);
            executeNon(getBundle(bundle, Bol.class), bol -> this.bol = bol.isBol());
        });
        setTitle("票据");
        setRightTv("修改金额", v -> executeNon(vo, vo -> uApi().imageUpdateAmount(vo.getSerialNo(),
                vo.getImageID(), vo.getAmount())));
        setVisibility(getRightTv(), bol ? View.VISIBLE : View.INVISIBLE);
        initViewPager();
        add(ImageFg.class, (path, what, msg, bean) -> {
            if(bean.isSuccess()){
                finishActivity(vo);
            } else {
                show(bean.getMessage());
            }
        });
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        executeNon(data, list -> {
            if (position < list.size()) {
                vo = data.get(position);
            }
        });
    }

    private void initViewPager() {
        executeNon(data, list -> {
            for (int i = 0; i < list.size(); i++) {
                ImageVo itemVo = list.get(i);
                getPagerAdapter().add(getFullPhotoControllerApi(itemVo, i));
                if (TextUtils.equals(encode(vo), encode(itemVo))) {
                    vo = itemVo;
                    getViewPager().setCurrentItem(i);
                }
            }
            getPagerAdapter().notifyDataSetChanged();
        });
    }

    private FullPhotoControllerApi getFullPhotoControllerApi(ImageVo vo, int position) {
        return (FullPhotoControllerApi) getViewControllerApi(FullPhotoControllerApi.class)
                .onBindViewHolder(vo.setShow(bol), position);
    }

}
