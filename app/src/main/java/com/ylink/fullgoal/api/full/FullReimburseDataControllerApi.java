package com.ylink.fullgoal.api.full;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;

import com.leo.core.adapter.BasePagerAdapter;
import com.leo.core.bean.BaseApiBean;
import com.leo.core.core.BaseControllerApiView;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.ResUtil;
import com.leo.core.util.SoftInputUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.api.surface.IndicatorControllerApi;
import com.ylink.fullgoal.bean.CCSQDBean;
import com.ylink.fullgoal.bean.DateArrayBean;
import com.ylink.fullgoal.bean.IconBean;
import com.ylink.fullgoal.bean.IndicatorBean;
import com.ylink.fullgoal.bean.ReimburseTypeBean;
import com.ylink.fullgoal.bean.TvHTv3Bean;
import com.ylink.fullgoal.bean.VgBean;
import com.ylink.fullgoal.controllerApi.surface.BarControllerApi;
import com.ylink.fullgoal.controllerApi.surface.RecycleControllerApi;
import com.ylink.fullgoal.fg.ApplicationtFg;
import com.ylink.fullgoal.fg.DataFg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

import static com.leo.core.util.DateUtil.getDDate;
import static com.leo.core.util.DateUtil.getDateString;
import static com.leo.core.util.DateUtil.getMDate;
import static com.leo.core.util.DateUtil.getNowDate;
import static com.leo.core.util.DateUtil.getYDate;
import static com.leo.core.util.TextUtils.getListData;
import static com.ylink.fullgoal.config.Config.APPROVAL_STATUS;
import static com.ylink.fullgoal.config.Config.BILL_TYPES;
import static com.ylink.fullgoal.config.Config.FULL_STATUS;
import static com.ylink.fullgoal.config.Config.SERIAL_NO;
import static com.ylink.fullgoal.config.Config.STATE;
import static com.ylink.fullgoal.config.UrlConfig.REIMBURSE_LIST_QUERY_RETURN_BILL_TYPE_CC;
import static com.ylink.fullgoal.config.UrlConfig.REIMBURSE_LIST_QUERY_RETURN_BILL_TYPE_YB;

@SuppressLint("RtlHardcoded")
public class FullReimburseDataControllerApi<T extends FullReimburseDataControllerApi, C> extends BarControllerApi<T, C> {

    @Bind(R.id.api_view)
    BaseControllerApiView apiView;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    private String type;
    private String dateText;
    private String typeText;
    private IndicatorControllerApi api;
    private int gravity = Gravity.RIGHT;
    private Map<String, RecycleControllerApi> map;

    public FullReimburseDataControllerApi(C controller) {
        super(controller);
        map = new HashMap<>();
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_reimburse_data;
    }

    @Override
    public void initView() {
        super.initView();
        initDrawerLayout();
        initBar();
        initIndicatorControllerApi();
    }

    @Override
    public void initData() {
        super.initData();
        add(DataFg.class, (path, what, msg, bean) -> execute(!TextUtils.isEmpty(msg), () ->
                initReimburseVoData(getReiRecycleControllerApi(msg), bean.getApplicationtList())));
        getReiRecycleControllerApi("待处理");
        getReiRecycleControllerApi("审核中");
        getReiRecycleControllerApi("已完成");
        getReiRecycleControllerApi("已取消");
    }

    @Override
    public void onResume() {
        super.onResume();
        query(type);
    }

    private void query(String type) {
        uApi().queryApplicationForm(getValue(APPROVAL_STATUS, type), getDateText(),
                getValue(BILL_TYPES, typeText, ""), type);
    }

    private String getDateText() {
        if (!TextUtils.isEmpty(dateText)) {
            switch (dateText) {
                case "当天":
                    return getDateString(getNowDate());
                case "七天":
                    return getDateString(getDDate(-7));
                case "一个月":
                    return getDateString(getMDate(-1));
                case "三个月":
                    return getDateString(getMDate(-3));
                case "六个月":
                    return getDateString(getMDate(-6));
                case "一年":
                    return getDateString(getYDate(-1));
            }
        }
        return "";
    }

    private boolean checkTitle(String type) {
        return !TextUtils.isEmpty(type) && TextUtils.getSetData("待处理", "审核中", "已完成", "已取消")
                .contains(type);
    }

    private RecycleControllerApi getReiRecycleControllerApi(String type) {
        if (checkTitle(type)) {
            RecycleControllerApi api = map.get(type);
            if (api == null) {
                api = getRecycleControllerApi(type);
                map.put(type, api);
                query(type);
            }
            return api;
        }
        return null;
    }

    private void initReimburseVoData(RecycleControllerApi api, List<ApplicationtFg> applicationtData) {
        if (api != null) {
            if (!TextUtils.isEmpty(applicationtData)) {
                api.clear().showContentView();
                execute(applicationtData, obj -> addVgBean(api, data -> {
                    data.add(new CCSQDBean(obj.getSerialNo(), obj.getBillType(),
                            "报销批次号", "报销类型"));
                    data.add(new CCSQDBean(obj.getFillDate(), obj.getAmount(),
                            "时间", "金额"));
                    data.add(new TvHTv3Bean("事由", obj.getCause()));
                }, vg -> vg.setOnClickListener(v -> {
                    //暂不使用
                    String status = getValue(FULL_STATUS, obj.getStatus());
                    if (!TextUtils.isEmpty(obj.getSerialNo()) && !TextUtils.isEmpty(status)
                            && !TextUtils.isEmpty(obj.getBillType())) {
                        switch (obj.getBillType()) {
                            case REIMBURSE_LIST_QUERY_RETURN_BILL_TYPE_YB://一般报销
                                startSurfaceActivity(FullGeneralControllerApi.class,
                                        status, obj.getSerialNo());
                                break;
                            case REIMBURSE_LIST_QUERY_RETURN_BILL_TYPE_CC://出差报销
                                startSurfaceActivity(FullEvectionControllerApi.class,
                                        status, obj.getSerialNo());
                                break;
                        }
                    }
                })));
            } else {
                api.showNullView(true);
            }
        }
    }

    private void startSurfaceActivity(Class<? extends IControllerApi> clz,
                                      String state, String serialNo) {
        Bundle bundle = new Bundle();
        bundle.putString(STATE, state);
        bundle.putString(SERIAL_NO, serialNo);
        startSurfaceActivity(bundle, clz);
    }

    private RecycleControllerApi getRecycleControllerApi(String name) {
        RecycleControllerApi controllerApi = getViewControllerApi(RecycleControllerApi.class,
                R.layout.l_content_recycle);
        controllerApi.setText(controllerApi.findViewById(R.id.null_tv), "您还没有相关的报销");
        controllerApi.setNullView(controllerApi.findViewById(R.id.null_vg));
        api.add(new IndicatorBean(name, controllerApi)).notifyDataSetChanged();
        return controllerApi;
    }

    //私有的

    @SuppressLint("RtlHardcoded")
    private void initDrawerLayout() {
        DateArrayBean dateArrayBean = new DateArrayBean("查询时间", getListData("当天", "七天",
                "一个月", "三个月", "六个月", "一年"), text -> dateText = text);
        ReimburseTypeBean typeBean = new ReimburseTypeBean("一般费用报销", "出差费用报销",
                text -> typeText = text);
        RecycleControllerApi api = getViewControllerApi(RecycleControllerApi.class, R.layout.l_sx);
        api.getRecyclerView().setBackgroundColor(ResUtil.getColor(R.color.white));
        setOnClickListener(api.findViewById(R.id.reset_tv), v -> {
            dateArrayBean.clean();
            typeBean.clean();
        }).setOnClickListener(api.findViewById(R.id.confirm_tv), v -> {
            drawerLayout.closeDrawer(gravity);
            query(type);
        });
        DrawerLayout.LayoutParams lp = new DrawerLayout.LayoutParams(-1, -1);
        lp.gravity = gravity;
        drawerLayout.addView(api.getRootView(), lp);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                SoftInputUtil.hidSoftInput(drawerView);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
        api.add(new IconBean((bean, view)
                -> drawerLayout.closeDrawer(gravity)))
                .add(dateArrayBean)
                .add(typeBean)
                .notifyDataSetChanged();
    }

    @SuppressLint("RtlHardcoded")
    private void initBar() {
        setTitle("查询列表");
        if (getRightTv() != null) {
            getRightTv().setTextSize(15);
            getRightTv().setTextColor(ResUtil.getColor(R.color.tv));
            Drawable drawable = ResUtil.getDrawable(R.mipmap.icon_sx);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            getRightTv().setCompoundDrawables(null, null, drawable, null);
            setRightTv("筛选", v -> drawerLayout.openDrawer(gravity));
        }
    }

    private void initIndicatorControllerApi() {
        api = ((IndicatorControllerApi) apiView.controllerApi())
                .setViewPager(viewPager)
                .setAdapter(new BasePagerAdapter())
                .setAction(text -> type = text);
    }

    private void addVgBean(RecycleControllerApi controllerApi, IObjAction<List<BaseApiBean>> api,
                           IObjAction<VgBean> vgAction) {
        if (controllerApi != null && api != null) {
            executeNon(controllerApi.addVgBean(api), vgAction);
            controllerApi.notifyDataSetChanged();
        }
    }

}
