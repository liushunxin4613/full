package com.ylink.fullgoal.api.surface;

import android.os.Bundle;

import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.IconTvMoreBean;
import com.ylink.fullgoal.controllerApi.surface.BillControllerApi;
import com.ylink.fullgoal.controllerApi.surface.RecycleBarControllerApi;
import com.ylink.fullgoal.vo.BillVo;
import com.ylink.fullgoal.vo.ReimburseVo;

import static com.ylink.fullgoal.config.Config.DEBUG;
import static com.ylink.fullgoal.config.Config.STATE;

/**
 * 主View视图
 */
public class MainViewControllerApi<T extends MainViewControllerApi, C> extends RecycleBarControllerApi<T, C> {

    public MainViewControllerApi(C controller) {
        super(controller);
    }


    @Override
    public void initView() {
        super.initView();
        hideBackIv().setTitle("我的报销");
        clear().addSmallVgBean(new IconTvMoreBean(R.mipmap.test_icon1, "一般费用普票", (bean, view) -> general(ReimburseVo.STATE_INITIATE)),
                new IconTvMoreBean(R.mipmap.test_icon2, "出差费用报销", (bean, view) -> evection(ReimburseVo.STATE_INITIATE)))
                .addSmallVgBean(new IconTvMoreBean(R.mipmap.test_icon2, "报销列表查询", (bean, view) -> startSurfaceActivity(ReimburseDataControllerApi.class)))
                .notifyDataSetChanged();
        if (DEBUG) {
            //测试
            addSmallVgBean(new IconTvMoreBean(R.mipmap.test_icon2, "费用指标", (bean, view) -> startSurfaceActivity(CostIndexControllerApi.class))).
                    addSmallVgBean(new IconTvMoreBean(R.mipmap.test_icon1, "一般费用经办人确认", (bean, view) -> {
                        general(ReimburseVo.STATE_CONFIRM);
                    }), new IconTvMoreBean(R.mipmap.test_icon2, "一般费用经办人修改", (bean, view) -> {
                        general(ReimburseVo.STATE_ALTER);
                    }), new IconTvMoreBean(R.mipmap.test_icon1, "一般费用报销详情", (bean, view) -> {
                        general(ReimburseVo.STATE_DETAIL);
                    })).addSmallVgBean(new IconTvMoreBean(R.mipmap.test_icon1, "出差费用经办人确认", (bean, view) -> {
                evection(ReimburseVo.STATE_CONFIRM);
            }), new IconTvMoreBean(R.mipmap.test_icon2, "出差费用经办人修改", (bean, view) -> {
                evection(ReimburseVo.STATE_ALTER);
            }), new IconTvMoreBean(R.mipmap.test_icon1, "出差费用报销详情", (bean, view) -> {
                evection(ReimburseVo.STATE_DETAIL);
            })).notifyDataSetChanged();
        }
    }

    //私有的

    /**
     * 票据
     */
    private void bill() {
        startSurfaceActivity(getBundle(new BillVo(R.mipmap.test_photo, "1239.00")), BillControllerApi.class);
    }

    /**
     * 一般费用普票报销
     */
    private void general(String state) {
        startSurfaceActivity(GeneralControllerApi.class, ReimburseVo.REIMBURSE_TYPE_GENERAL, state);
    }

    /**
     * 出差费用普票报销
     */
    private void evection(String state) {
        startSurfaceActivity(EvectionControllerApi.class, ReimburseVo.REIMBURSE_TYPE_EVECTION, state);
    }

    private void startSurfaceActivity(Class<? extends IControllerApi> clz, String type, String state) {
        Bundle bundle = new Bundle();
        bundle.putString(STATE, state);
        startSurfaceActivity(bundle, clz);
    }

}
