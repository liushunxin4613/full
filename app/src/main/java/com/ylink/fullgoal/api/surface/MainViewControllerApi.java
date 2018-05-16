package com.ylink.fullgoal.api.surface;

import android.os.Bundle;

import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.IconTvMoreBean;
import com.ylink.fullgoal.controllerApi.surface.BillControllerApi;
import com.ylink.fullgoal.controllerApi.surface.RecycleBarControllerApi;
import com.ylink.fullgoal.vo.BillVo;
import com.ylink.fullgoal.vo.ReimburseVo;

import static com.ylink.fullgoal.config.Config.REIMBURSE_TYPE;
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
        clear().addSmallVgBean(new IconTvMoreBean(R.mipmap.test_icon1, "一般费用普票报销", (bean, view) -> generalCommon(ReimburseVo.STATE_INITIATE)),
                new IconTvMoreBean(R.mipmap.test_icon2, "一般费用专票报销", (bean, view) -> generalDedicated(ReimburseVo.STATE_INITIATE)))
                .addSmallVgBean(new IconTvMoreBean(R.mipmap.test_icon1, "出差费用普票报销", (bean, view) -> evectionCommon(ReimburseVo.STATE_INITIATE)),
                        new IconTvMoreBean(R.mipmap.test_icon2, "出差费用专票报销", (bean, view) -> evectionDedicated(ReimburseVo.STATE_INITIATE)))
                .addSmallVgBean(new IconTvMoreBean(R.mipmap.test_icon2, "报销列表查询", (bean, view) -> startSurfaceActivity(ReimburseDataControllerApi.class)))
                //测试
                .addSmallVgBean(new IconTvMoreBean(R.mipmap.test_icon2, "票据", (bean, view) -> {
                    bill();
                }))
                .addSmallVgBean(new IconTvMoreBean(R.mipmap.test_icon1, "一般费用经办人确认", (bean, view) -> {
                    generalCommon(ReimburseVo.STATE_CONFIRM);
                }), new IconTvMoreBean(R.mipmap.test_icon2, "一般费用经办人修改", (bean, view) -> {
                    generalCommon(ReimburseVo.STATE_ALTER);
                }), new IconTvMoreBean(R.mipmap.test_icon1, "一般费用报销详情", (bean, view) -> {
                    generalCommon(ReimburseVo.STATE_DETAIL);
                }))
                .addSmallVgBean(new IconTvMoreBean(R.mipmap.test_icon1, "出差费用经办人确认", (bean, view) -> {
                    evectionCommon(ReimburseVo.STATE_CONFIRM);
                }), new IconTvMoreBean(R.mipmap.test_icon2, "出差费用经办人修改", (bean, view) -> {
                    evectionCommon(ReimburseVo.STATE_ALTER);
                }), new IconTvMoreBean(R.mipmap.test_icon1, "出差费用报销详情", (bean, view) -> {
                    evectionCommon(ReimburseVo.STATE_DETAIL);
                })).notifyDataSetChanged();

        //test
//        evectionDedicated(ReimburseVo.STATE_INITIATE);
//        startSurfaceActivity(ReimburseDataControllerApi.class);
//        evectionCommon(ReimburseVo.STATE_DETAIL);
        bill();

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
    private void generalCommon(String state) {
        startSurfaceActivity(GeneralControllerApi.class, ReimburseVo.REIMBURSE_TYPE_GENERAL_COMMON, state);
    }

    /**
     * 一般费用专票报销
     */
    private void generalDedicated(String state) {
        startSurfaceActivity(GeneralControllerApi.class, ReimburseVo.REIMBURSE_TYPE_GENERAL_DEDICATED, state);
    }

    /**
     * 出差费用普票报销
     */
    private void evectionCommon(String state) {
        startSurfaceActivity(EvectionControllerApi.class, ReimburseVo.REIMBURSE_TYPE_EVECTION_COMMON, state);
    }

    /**
     * 出差费用专票报销
     */
    private void evectionDedicated(String state) {
        startSurfaceActivity(EvectionControllerApi.class, ReimburseVo.REIMBURSE_TYPE_EVECTION_DEDICATED, state);
    }

    private void startSurfaceActivity(Class<? extends IControllerApi> clz, String type, String state) {
        Bundle bundle = new Bundle();
        bundle.putString(REIMBURSE_TYPE, type);
        bundle.putString(STATE, state);
        startSurfaceActivity(bundle, clz);
    }

}
