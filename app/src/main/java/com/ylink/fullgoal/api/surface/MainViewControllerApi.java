package com.ylink.fullgoal.api.surface;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.UserBean;
import com.ylink.fullgoal.controllerApi.surface.RecycleBarControllerApi;
import com.ylink.fullgoal.fg.DepartmentFg;
import com.ylink.fullgoal.fg.StatusFg;
import com.ylink.fullgoal.fg.UserFg;
import com.ylink.fullgoal.norm.IconTvMoreNorm;
import com.ylink.fullgoal.norm.ImageNorm;
import com.ylink.fullgoal.vo.RVo;

import static com.ylink.fullgoal.config.ComConfig.FQ;
import static com.ylink.fullgoal.config.Config.FULL_STATUS;

/**
 * 主View视图
 */
public class MainViewControllerApi<T extends MainViewControllerApi, C> extends RecycleBarControllerApi<T, C> {

    private boolean check;
    private Dialog mDialog;

    public MainViewControllerApi(C controller) {
        super(controller);
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        check = false;
        activityLifecycleApi().finishActivity(obj
                -> !TextUtils.equals(obj, getActivity()));
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        TextUtils.show(intent, this::ee);
        String taskId = intent.getStringExtra("taskId");
        String name = intent.getStringExtra("name");
        String cookie = intent.getStringExtra("cookie");
        String userId = intent.getStringExtra("userId");
        String username = intent.getStringExtra("username");
        String cookieStr = intent.getStringExtra("cookieStr");
        String portalPac = intent.getStringExtra("portalPac");
        if (TextUtils.check(userId)) {
            api().queryUserName(userId);
            initUser(new UserBean(taskId, name, cookie, userId,
                    username, cookieStr, portalPac));
        } else if (!TextUtils.check(getUserName(), getUId())) {
            check();
        }
        if (TextUtils.check(taskId)) {
            api().queryNoShowLoadingMessageBack(taskId);
        }
    }

    private void check() {
        check = false;
        mDialog = dialog("请从门户进入", "确定", null, (bean, v, dialog) -> {
            check = true;
            PackageManager pm = getContext().getPackageManager();
            Intent it = pm.getLaunchIntentForPackage("cn.com.fullgoal.portal");
            if (it == null) {
                show("手机未安装该应用");
            } else {
                dialog.dismiss();
                getActivity().startActivity(it);
            }
        }, null);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (check && !TextUtils.check(getUserName())) {
            check();
        }
    }

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        add(RVo.class, (type, baseUrl, path, map, what, serialNo, field, bean) -> {
            if (TextUtils.check(bean.getBillType(), bean.getTaskType())) {
                bean.setSerialNo(serialNo);
                String state = getValue(FULL_STATUS, bean.getTaskType(), bean.getTaskType());
                switch (bean.getBillType()) {
                    case "427"://一般报销
                        dismissLoading();
                        routeApi().generalMain(state, encode(bean));
                        break;
                    case "428"://出差报销
                        dismissLoading();
                        routeApi().evectionMain(state, encode(bean));
                        break;
                }
            }
        });
        add(StatusFg.class, (type, baseUrl, path, map, what, msg, field, bean)
                -> ii(String.format("SSO认证%s", bean.isSuccess() ? "成功" : "失败")));
        addList(UserFg.class, (type, baseUrl, path, map, what, msg, field, list) -> {
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
            dismissLoading();
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