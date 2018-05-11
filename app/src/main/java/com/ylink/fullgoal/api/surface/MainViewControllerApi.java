package com.ylink.fullgoal.api.surface;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.IconTvMoreBean;
import com.ylink.fullgoal.controllerApi.surface.RecycleBarControllerApi;

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
        clear().addVgBean(new IconTvMoreBean(R.mipmap.test_icon1, "一般费用普票报销", (bean, view) -> {
            //一般费用报销
            startSurfaceActivity(CommonControllerApi.class);
        }), new IconTvMoreBean(R.mipmap.test_icon2, "一般费用专票报销", (bean, view) -> {
            //一般费用报销
            show(bean.getName());
        })).addVgBean(new IconTvMoreBean(R.mipmap.test_icon1, "出差费用普票报销", (bean, view) -> {
            //出差费用报销
            show(bean.getName());
        }), new IconTvMoreBean(R.mipmap.test_icon2, "出差费用专票报销", (bean, view) -> {
            //出差费用报销
            show(bean.getName());
        })).addVgBean(new IconTvMoreBean(R.mipmap.test_icon2, "报销列表查询", (bean, view) -> {
            //报销列表
            show(bean.getName());
        })).notifyDataSetChanged();

        //test
        startSurfaceActivity(CommonControllerApi.class);

    }

}
