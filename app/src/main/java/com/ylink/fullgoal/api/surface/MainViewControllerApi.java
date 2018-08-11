package com.ylink.fullgoal.api.surface;

import android.content.Intent;
import android.os.Bundle;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.UserBean;
import com.ylink.fullgoal.controllerApi.surface.RecycleBarControllerApi;
import com.ylink.fullgoal.fg.DepartmentFg;
import com.ylink.fullgoal.fg.MessageBackFg;
import com.ylink.fullgoal.fg.StatusFg;
import com.ylink.fullgoal.fg.UserFg;
import com.ylink.fullgoal.norm.IconTvMoreNorm;
import com.ylink.fullgoal.norm.ImageNorm;

import static com.ylink.fullgoal.config.ComConfig.FQ;
import static com.ylink.fullgoal.config.Config.FULL_STATUS;

/**
 * 主View视图
 */
@SuppressWarnings("MalformedFormatString")
public class MainViewControllerApi<T extends MainViewControllerApi, C> extends RecycleBarControllerApi<T, C> {

    public MainViewControllerApi(C controller) {
        super(controller);
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String taskId = intent.getStringExtra("taskId");
        String cookie = intent.getStringExtra("cookie");
        String userId = intent.getStringExtra("userId");
        String username = intent.getStringExtra("username");
        String name = intent.getStringExtra("name");
        String cookieStr = intent.getStringExtra("cookieStr");
        String portalPac = intent.getStringExtra("portalPac");
        if (TextUtils.check(userId, username)) {//TODO 测试用
            api().queryUserName(userId);
            initUser(new UserBean(taskId, name, cookie, userId,
                    username, cookieStr, portalPac));
        }
        api().SSO(getCastgc());
        ii("user", getUser());
        if (TextUtils.check(taskId)) {
            api().queryNoShowLoadingMessageBack(taskId);
        }
    }

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        add(MessageBackFg.class, (fieldName, path, what, msg, bean) -> {
            if (TextUtils.check(bean.getBillType(), bean.getTaskType())) {
                String state = getValue(FULL_STATUS, bean.getTaskType(), bean.getTaskType());
                switch (bean.getBillType()) {
                    case "427"://一般报销
                        routeApi().generalMain(state, encode(bean));
                        break;
                    case "428"://出差报销
                        routeApi().evectionMain(state, encode(bean));
                        break;
                }
            }
        });
        add(StatusFg.class, (fieldName, path, what, msg, bean) -> ii(String.format("SSO认证%s",
                bean.isSuccess() ? "成功" : "失败")));
        addList(UserFg.class, (fieldName, path, what, msg, list) -> {
            if (TextUtils.check(list)) {
                UserFg fg = list.get(0);
                if (TextUtils.check(fg)) {
                    UserBean bean = getUser();
                    if (bean == null) {
                        bean = new UserBean();
                    }
                    bean.setUserId(fg.getUserCode());
                    bean.setUsername(fg.getUserName());
                    bean.setLevel(fg.getUserlevel());
                    initUser(bean);
                    initDepartment(new DepartmentFg(fg.getUserDepartmentCode(),
                            fg.getUserDepartment()));
                }
            }
        });
    }

    @Override
    public void initView() {
        super.initView();
        hideBackIv().setTitle("富国基金个人报销平台");
        add(new ImageNorm(R.mipmap.banner));
        addSmallVgNorm(new IconTvMoreNorm(R.mipmap.m1, "一般费用报销", (bean, view) -> routeApi().general(FQ)),
                new IconTvMoreNorm(R.mipmap.m2, "出差费用报销", (bean, view) -> routeApi().evection(FQ)),
                new IconTvMoreNorm(R.mipmap.m3, "报销列表查询", (bean, view) -> routeApi().queryReimburse()),
                new IconTvMoreNorm(R.mipmap.m4, "选择银行卡号", (bean, view) -> routeApi().selectBank()))
                .notifyDataSetChanged();
    }

}