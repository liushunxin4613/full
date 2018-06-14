package com.ylink.fullgoal.api.full;

import android.view.View;

import com.leo.core.adapter.BasePagerAdapter;
import com.leo.core.bean.Bol;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.controllerApi.surface.BarViewPagerControllerApi;
import com.ylink.fullgoal.fg.ImageFg;
import com.ylink.fullgoal.vo.BillVo;

import java.util.List;

public class FullBillControllerApi<T extends FullBillControllerApi, C> extends BarViewPagerControllerApi<T, C> {

    private List<BillVo> data;
    private BillVo vo;
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
        addRootType(ImageFg.class);
        executeBundle(bundle -> {
            executeNon(getBundleList(bundle, BillVo.class), data -> this.data = data);
            executeNon(getBundle(bundle, BillVo.class), vo -> this.vo = vo);
            executeNon(getBundle(bundle, Bol.class), bol -> this.bol = bol.isBol());
        });
        setTitle("票据");
        setRightTv("修改金额", v -> executeNon(vo, vo -> uApi().imageUpdateAmount(vo.getSerialNo(),
                vo.getId(), vo.getMoney())));
        setVisibility(getRightTv(), bol ? View.VISIBLE : View.INVISIBLE);
        initViewPager();
        add(ImageFg.class, (path, what, msg, bean) -> finishActivity(vo));
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        if (position < data.size()) {
            vo = data.get(position);
        }
    }

    private void initViewPager() {
        for (int i = 0; i < data.size(); i++) {
            BillVo itemVo = data.get(i);
            getPagerAdapter().add(getFullPhotoControllerApi(itemVo, i).getRootView());
            if (TextUtils.equals(encode(vo), encode(itemVo))) {
                vo = itemVo;
                getViewPager().setCurrentItem(i);
            }
        }
        getPagerAdapter().notifyDataSetChanged();
    }

    private FullPhotoControllerApi getFullPhotoControllerApi(BillVo vo, int position) {
        return (FullPhotoControllerApi) getViewControllerApi(FullPhotoControllerApi.class)
                .onBindViewHolder(vo.setShow(bol), position);
    }

}
