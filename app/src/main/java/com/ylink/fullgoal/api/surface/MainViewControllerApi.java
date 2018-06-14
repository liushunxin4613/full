package com.ylink.fullgoal.api.surface;

import android.os.Bundle;

import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.api.full.FullEvectionControllerApi;
import com.ylink.fullgoal.api.full.FullGeneralControllerApi;
import com.ylink.fullgoal.api.full.FullReimburseDataControllerApi;
import com.ylink.fullgoal.bean.IconTvMoreBean;
import com.ylink.fullgoal.controllerApi.surface.RecycleBarControllerApi;
import com.ylink.fullgoal.vo.ReimburseVo;

import static com.ylink.fullgoal.config.Config.DEBUG;
import static com.ylink.fullgoal.config.Config.FULL;
import static com.ylink.fullgoal.config.Config.SERIAL_NO;
import static com.ylink.fullgoal.config.Config.STATE;

/**
 * 主View视图
 */
@SuppressWarnings("MalformedFormatString")
public class MainViewControllerApi<T extends MainViewControllerApi, C> extends RecycleBarControllerApi<T, C> {

    public MainViewControllerApi(C controller) {
        super(controller);
    }

    @Override
    public void initView() {
        super.initView();
        hideBackIv().setTitle("我的报销");
        clear().addSmallVgBean(new IconTvMoreBean(R.mipmap.test_icon1, "一般费用报销", (bean, view)
                        -> general(ReimburseVo.STATE_INITIATE)),
                new IconTvMoreBean(R.mipmap.test_icon2, "出差费用报销", (bean, view)
                        -> evection(ReimburseVo.STATE_INITIATE)))
                .addSmallVgBean(new IconTvMoreBean(R.mipmap.test_icon2, "报销列表查询", (bean, view)
                        -> startSurfaceActivity(FullReimburseDataControllerApi.class)))
                .notifyDataSetChanged();
        if (FULL && DEBUG) {
            addSmallVgBean(new IconTvMoreBean(R.mipmap.test_icon1, "一般报销确认", (bean, view) -> {
                Bundle bundle = new Bundle();
                bundle.putString(STATE, ReimburseVo.STATE_CONFIRM);
                bundle.putString(SERIAL_NO, "201806120016000259");
                startSurfaceActivity(bundle, FullGeneralControllerApi.class);
            }), new IconTvMoreBean(R.mipmap.test_icon1, "出差报销确认", (bean, view) -> {
                Bundle bundle = new Bundle();
                bundle.putString(STATE, ReimburseVo.STATE_CONFIRM);
                bundle.putString(SERIAL_NO, "201806122916000263");
                startSurfaceActivity(bundle, FullEvectionControllerApi.class);
            }));
//            addSmallVgBean(new IconTvMoreBean(R.mipmap.test_icon1, "费用指标", (bean, view)
//                    -> startSurfaceActivity(FullCostIndexControllerApi.class)));
        }

    }

    //私有的

    /**
     * 一般费用普票报销
     */
    private void general(String state) {
        startSurfaceActivity(FULL ? FullGeneralControllerApi.class : GeneralControllerApi.class,
                ReimburseVo.REIMBURSE_TYPE_GENERAL, state);
    }

    /**
     * 出差费用普票报销
     */
    private void evection(String state) {
        startSurfaceActivity(FULL ? FullEvectionControllerApi.class : EvectionControllerApi.class,
                ReimburseVo.REIMBURSE_TYPE_EVECTION, state);
    }

    private void startSurfaceActivity(Class<? extends IControllerApi> clz, String type, String state) {
        Bundle bundle = new Bundle();
        bundle.putString(STATE, state);
        startSurfaceActivity(bundle, clz);
    }

}
