package com.ylink.fullgoal.api.surface;

import android.content.Intent;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.IconTvMoreBean;
import com.ylink.fullgoal.bean.ImageBeanD1;
import com.ylink.fullgoal.bean.UserBean;
import com.ylink.fullgoal.controllerApi.surface.RecycleBarControllerApi;
import com.ylink.fullgoal.fg.StatusFg;

import static com.ylink.fullgoal.config.ComConfig.FQ;
import static com.ylink.fullgoal.config.ComConfig.QR;

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
        hideBackIv().setTitle("富国基金个人报销平台");
        add(new ImageBeanD1(R.mipmap.fg));
        addSmallVgBeanD1(new IconTvMoreBean(R.mipmap.m1, "一般费用报销", (bean, view) -> routeApi().general(FQ)),
                new IconTvMoreBean(R.mipmap.m2, "出差费用报销", (bean, view) -> routeApi().evection(FQ)),
                new IconTvMoreBean(R.mipmap.m3, "报销列表查询", (bean, view) -> routeApi().queryReimburse()),
                new IconTvMoreBean(R.mipmap.m4, "选择银行卡号", (bean, view) -> routeApi().selectBank()))
                .notifyDataSetChanged()
                .showContentView();
//        test();
    }

    private void test() {
        addSmallVgBean(new IconTvMoreBean(R.mipmap.test_icon1, "测试: 一般费用报销确认", (bean, view)
                -> clickTestGeneral()), new IconTvMoreBean(R.mipmap.test_icon2, "测试: 出差费用报销确认",
                (bean, view) -> clickTestEvection()));
        clickTestGeneral();
    }

    private void clickTestGeneral() {
        routeApi().general(QR, "20180726xijiong6000828");
    }

    private void clickTestEvection() {
//        routeApi().evection(QR, "20180725xijiong6000821");
    }

    @Override
    public void initData() {
        super.initData();
        add(StatusFg.class, (path, what, msg, bean) -> ii(String.format("SSO认证%s",
                bean.isSuccess() ? "成功" : "失败")));
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
        api().SSO(getCastgc());
        ii("user", getUser());
    }

}