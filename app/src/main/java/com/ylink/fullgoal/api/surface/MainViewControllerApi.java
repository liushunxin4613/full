package com.ylink.fullgoal.api.surface;

import android.content.Intent;
import android.os.Bundle;

import com.leo.core.iapi.inter.IAction;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.api.full.FullBankControllerApiV1;
import com.ylink.fullgoal.api.full.FullCostIndexControllerApi;
import com.ylink.fullgoal.api.full.FullEvectionControllerApi;
import com.ylink.fullgoal.api.full.FullGeneralControllerApiV1;
import com.ylink.fullgoal.api.full.FullReimburseDataControllerApi;
import com.ylink.fullgoal.bean.IconTvMoreBean;
import com.ylink.fullgoal.bean.UserBean;
import com.ylink.fullgoal.controllerApi.surface.RecycleBarControllerApi;
import com.ylink.fullgoal.fg.CostFg;
import com.ylink.fullgoal.fg.StatusFg;

import static com.ylink.fullgoal.config.ComConfig.CC;
import static com.ylink.fullgoal.config.ComConfig.FQ;
import static com.ylink.fullgoal.config.ComConfig.QR;
import static com.ylink.fullgoal.config.ComConfig.YB;
import static com.ylink.fullgoal.config.Config.COST;
import static com.ylink.fullgoal.config.Config.DATA_QR;
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
        onAppData();
        hideBackIv().setTitle("我的报销");
        clear().addSmallVgBean(new IconTvMoreBean(R.mipmap.test_icon1, "一般费用报销", (bean, view)
                        -> general(FQ)),
                new IconTvMoreBean(R.mipmap.test_icon2, "出差费用报销", (bean, view)
                        -> evection(FQ)))
                .addSmallVgBean(new IconTvMoreBean(R.mipmap.test_icon1, "报销列表查询", (bean, view)
                                -> startSurfaceActivity(FullReimburseDataControllerApi.class)),
                        new IconTvMoreBean(R.mipmap.test_icon2, "选择银行卡号", (bean, view)
                                -> startSurfaceActivity(FullBankControllerApiV1.class)))
                .notifyDataSetChanged();
        if (DEBUG) {
            addSmallVgBean(new IconTvMoreBean(R.mipmap.test_icon1, "一般报销确认", (bean, view) -> {
                Bundle bundle = new Bundle();
                bundle.putString(STATE, QR);
                bundle.putString(SERIAL_NO, "2018062136000447");
                startSurfaceActivity(bundle, FullGeneralControllerApiV1.class);
            }), new IconTvMoreBean(R.mipmap.test_icon2, "出差报销确认", (bean, view) -> {
                Bundle bundle = new Bundle();
                bundle.putString(STATE, QR);
                bundle.putString(SERIAL_NO, "2018062136000449");
                startSurfaceActivity(bundle, FullEvectionControllerApi.class);
            }));
            addSmallVgBean(new IconTvMoreBean(R.mipmap.test_icon1, "测试", (bean, view) -> {
                showLoading();
                addUI(3000, (IAction) this::dismissLoading);
                start();
            }));
        }
        addSmallVgBean(new IconTvMoreBean(R.mipmap.test_icon2, "费用分摊", (bean, view) -> test()));
        showContentView();
    }

    @Override
    public void initData() {
        super.initData();
        add(StatusFg.class, (path, what, msg, bean) -> ii(String.format("SSO认证%s", bean.isSuccess() ? "成功" : "失败")));
        general(FQ);
    }

    private void test() {
        String json = "{\"statusCode\":\"SUCCESS\",\"paymentRequest\":{\"applicationDate\":\"2018-05-24\",\"leadDepartment\":\"部门3\",\"status\":\"已完成\",\"leader\":\"李磊\",\"fileNumber\":\"9993\",\"name\":\"xxxx合同\",\"code\":\"0003\"},\"imageList\":[{\"amount\":\"200.300\",\"imageURL\":\"http://192.168.8.108:8088/ssca/public/20180629\\\\3\\\\1530252663192344.jpg\",\"invoiceUse\":\"一般\",\"imageID\":\"402894816449939d01644a2a99a4000a\"}],\"agent\":{\"userCode\":\"3\",\"userName\":\"张3\"},\"process\":{\"advAmount\":\"20000\",\"applyDepartment\":\"1部门\",\"applicant\":\"李强\",\"date\":\"2018-05-24\",\"cause\":\"事由是\",\"code\":\"001\"},\"budgetDepartment\":{\"departmentCode\":\"3\",\"departmentName\":\"3部门\"},\"reimbursement\":{\"userCode\":\"3\",\"userName\":\"张3\"},\"project\":{\"projectName\":\"3项目\",\"applicationDate\":\"2018-06-04\",\"amount\":\"3000\",\"leadDepartment\":\"部门\",\"judtification\":\"测试用\",\"projectCode\":\"3\",\"leader\":\"丁杰\"},\"costList\":{\"amount\":\"200.00\",\"costCode\":\"002\",\"share\":\"需要分摊\",\"taxAmount\":\"0.00\",\"costIndex\":\"指标2\",\"exTaxAmount\":\"0.00\"},\"ruleList\":[],\"cause\":\"招待费\"}";
        Bundle os = new Bundle();
        os.putString(DATA_QR, json);
        startSurfaceActivity(os, FullCostIndexControllerApi.class);
    }

    //被调用
    private void onAppData() {
        Intent intent = getActivity().getIntent();
        String cookie = intent.getStringExtra("cookie");
        String userId = intent.getStringExtra("userId");
        String username = intent.getStringExtra("username");
        String name = intent.getStringExtra("name");
        String cookieStr = intent.getStringExtra("cookieStr");
        String portalPac = intent.getStringExtra("portalPac");
        if (TextUtils.check(userId, username)) {//TODO 测试用
            initUser(new UserBean(name, cookie, userId, username, cookieStr, portalPac));
        }
//        api().SSO(getCastgc());
        ii("user", getUser());
    }

    //私有的

    /**
     * 一般费用普票报销
     */
    private void general(String state) {
        startSurfaceActivity(FullGeneralControllerApiV1.class, YB, state);
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
