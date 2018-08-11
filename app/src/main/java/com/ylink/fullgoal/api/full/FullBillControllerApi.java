package com.ylink.fullgoal.api.full;

import android.annotation.SuppressLint;
import android.view.View;

import com.leo.core.adapter.BasePagerAdapter;
import com.leo.core.bean.Bol;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.api.item.ImageVoControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.controllerApi.surface.BarViewPagerControllerApi;
import com.ylink.fullgoal.fg.ImageFg;
import com.ylink.fullgoal.norm.ImageVoNorm;
import com.ylink.fullgoal.vo.ImageVo;

import java.util.List;

import static com.leo.core.util.TextUtils.count;

@SuppressLint("DefaultLocale")
public class FullBillControllerApi<T extends FullBillControllerApi, C> extends BarViewPagerControllerApi<T, C> {

    private ImageVo vo;
    private boolean bol;
    private List<ImageVo> data;

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
        setRightTv("修改金额", v -> executeNon(getImageVo(), vo -> api().imageUpdateAmount(
                vo.getSerialNo(), vo.getImageID(), vo.getAmount())));
        setVisibility(getRightTv(), bol ? View.VISIBLE : View.INVISIBLE);
        initViewPager();
        add(ImageFg.class, (fieldName, path, what, msg, bean) -> {
            if (bean.isSuccess()) {
                finishActivity(getImageVo());
            } else {
                show(bean.getMessage());
            }
        });
    }

    private ImageVo getImageVo() {
        ImageVoNorm norm = getCurrentImageVoNorm();
        for (ImageVo vo : data) {
            if (TextUtils.equals(vo.getPhoto(), norm.getPhoto())) {
                vo.setAmount(norm.getAmount());
                return vo;
            }
        }
        return null;
    }

    private ImageVoNorm getCurrentImageVoNorm() {
        return (ImageVoNorm) getPagerAdapter().getItemApi(getViewPager()
                .getCurrentItem()).getNorm();
    }

    private void initViewPager() {
        executeNon(data, list -> {
            for (int i = 0; i < list.size(); i++) {
                ImageVo itemVo = list.get(i);
                getPagerAdapter().add(getControllerApi(itemVo, i));
                if (TextUtils.equals(encode(vo), encode(itemVo))) {
                    getViewPager().setCurrentItem(i);
                    initTitle(i);
                }
            }
            getPagerAdapter().notifyDataSetChanged();
        });
    }

    private IControllerApi getControllerApi(ImageVo vo, int position) {
        SurfaceControllerApi api = getViewControllerApi(ImageVoControllerApi.class);
        api.onNorm(new ImageVoNorm(vo.getPhoto(), vo.getAmount(), bol), position);
        return api;
    }

    private void initTitle(int position) {
        setTitle(String.format("票据(%d/%d)", position + 1, count(data)));
    }

}