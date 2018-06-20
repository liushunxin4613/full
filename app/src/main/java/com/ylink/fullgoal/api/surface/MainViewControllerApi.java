package com.ylink.fullgoal.api.surface;

import android.os.Bundle;

import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.api.full.FullBankControllerApi;
import com.ylink.fullgoal.api.full.FullCostIndexControllerApi;
import com.ylink.fullgoal.api.full.FullEvectionControllerApi;
import com.ylink.fullgoal.api.full.FullGeneralControllerApi;
import com.ylink.fullgoal.api.full.FullReimburseDataControllerApi;
import com.ylink.fullgoal.bean.IconTvMoreBean;
import com.ylink.fullgoal.controllerApi.surface.RecycleBarControllerApi;
import com.ylink.fullgoal.fg.CostFg;

import static com.ylink.fullgoal.config.ComConfig.CC;
import static com.ylink.fullgoal.config.ComConfig.FQ;
import static com.ylink.fullgoal.config.ComConfig.QR;
import static com.ylink.fullgoal.config.ComConfig.YB;
import static com.ylink.fullgoal.config.Config.COST;
import static com.ylink.fullgoal.config.Config.DEBUG;
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
                        -> general(FQ)),
                new IconTvMoreBean(R.mipmap.test_icon2, "出差费用报销", (bean, view)
                        -> evection(FQ)))
                .addSmallVgBean(new IconTvMoreBean(R.mipmap.test_icon2, "报销列表查询", (bean, view)
                                -> startSurfaceActivity(FullReimburseDataControllerApi.class)),
                        new IconTvMoreBean(R.mipmap.test_icon2, "选择银行卡号", (bean, view)
                                -> startSurfaceActivity(FullBankControllerApi.class)))
                .notifyDataSetChanged();
        if (DEBUG) {
            addSmallVgBean(new IconTvMoreBean(R.mipmap.test_icon1, "一般报销确认", (bean, view) -> {
                Bundle bundle = new Bundle();
                bundle.putString(STATE, QR);
                bundle.putString(SERIAL_NO, "201806150026000388");
                startSurfaceActivity(bundle, FullGeneralControllerApi.class);
            }), new IconTvMoreBean(R.mipmap.test_icon1, "出差报销确认", (bean, view) -> {
                Bundle bundle = new Bundle();
                bundle.putString(STATE, QR);
                bundle.putString(SERIAL_NO, "201806150026000390");
                startSurfaceActivity(bundle, FullEvectionControllerApi.class);
            }));
            addSmallVgBean(new IconTvMoreBean(R.mipmap.test_icon1, "费用指标", (bean, view)
                    -> execute(this::cost)));
            cost();
        }

    }

    //私有的

    /**
     * 一般费用普票报销
     */
    private void general(String state) {
        startSurfaceActivity(FullGeneralControllerApi.class, YB, state);
    }

    /**
     * 出差费用普票报销
     */
    private void evection(String state) {
        startSurfaceActivity(FullEvectionControllerApi.class, CC, state);
    }

    /**
     * 费用指标
     */
    private void cost() {
        Bundle bundle = new Bundle();
        bundle.putString(SERIAL_NO, "201806150026000388");
        CostFg fg = new CostFg();
        fg.setCostCode("002");
        fg.setCostIndex("指标2");
        fg.setAmount("1898.00");
        fg.setTaxAmount("1500.00");
        fg.setExTaxAmount("398.00");
        fg.setShare("需要分摊");
        fg.setExplain("分摊");
        bundle.putString(COST, encode(fg));
        startSurfaceActivity(bundle, FullCostIndexControllerApi.class);
    }

    private void startSurfaceActivity(Class<? extends IControllerApi> clz,
                                      String type, String state) {
        Bundle bundle = new Bundle();
        bundle.putString(STATE, state);
        startSurfaceActivity(bundle, clz);
    }

}
