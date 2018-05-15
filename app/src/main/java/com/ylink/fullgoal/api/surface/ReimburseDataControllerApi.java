package com.ylink.fullgoal.api.surface;

import android.support.v4.view.ViewPager;

import com.leo.core.adapter.BasePagerAdapter;
import com.leo.core.bean.BaseApiBean;
import com.leo.core.core.BaseControllerApiView;
import com.leo.core.iapi.IRunApi;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.CCSQDBean;
import com.ylink.fullgoal.bean.IndicatorBean;
import com.ylink.fullgoal.bean.TvHTv3Bean;
import com.ylink.fullgoal.controllerApi.surface.BarControllerApi;
import com.ylink.fullgoal.controllerApi.surface.RecycleControllerApi;

import java.util.List;

import butterknife.Bind;

public class ReimburseDataControllerApi<T extends ReimburseDataControllerApi, C> extends BarControllerApi<T, C> {

    @Bind(R.id.api_view)
    BaseControllerApiView apiView;
    @Bind(R.id.view_pager)
    ViewPager viewPager;

    private RecycleControllerApi item1Api;
    private RecycleControllerApi item2Api;
    private RecycleControllerApi item3Api;
    private RecycleControllerApi item4Api;

    public ReimburseDataControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getRootViewResId() {
        return R.layout.l_reimburse_data;
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("查询列表");
        IndicatorBean bean1 = getIndicatorBean("待处理");
        item1Api = (RecycleControllerApi) bean1.getApi();
        IndicatorBean bean2 = getIndicatorBean("审核中");
        item2Api = (RecycleControllerApi) bean2.getApi();
        IndicatorBean bean3 = getIndicatorBean("已完成");
        item3Api = (RecycleControllerApi) bean3.getApi();
        IndicatorBean bean4 = getIndicatorBean("已取消");
        item4Api = (RecycleControllerApi) bean4.getApi();
        ((IndicatorControllerApi) apiView.controllerApi())
                .setViewPager(viewPager)
                .setAdapter(new BasePagerAdapter())
                .addAll(bean1, bean2, bean3, bean4)
                .notifyDataSetChanged();
    }

    @Override
    public void initData() {
        super.initData();
        initItemApi(item1Api);
        initItemApi(item2Api);
        initItemApi(item3Api);
        initItemApi(item4Api);
    }

    private void initItemApi(RecycleControllerApi api){
        if(api != null){
            for (int i = 0; i < 5; i++) {
                addVgBean(api, data -> {
                    data.add(new CCSQDBean("FGMC20180508002", "FGMC-HC-20180508-0001", "报销批次号", "报销单号"));
                    data.add(new CCSQDBean("2018-05-08", "2400.00", "时间", "金额"));
                    data.add(new TvHTv3Bean("事由", "到上海出差"));
                });
            }
            api.notifyDataSetChanged();
        }
    }

    private void addVgBean(RecycleControllerApi controllerApi, IRunApi<List<BaseApiBean>> api){
        if(controllerApi != null && api != null){
            controllerApi.addVgBean(api);
        }
    }

    private IndicatorBean getIndicatorBean(String name) {
        return new IndicatorBean(getViewControllerApi(RecycleControllerApi.class), name);
    }

}
