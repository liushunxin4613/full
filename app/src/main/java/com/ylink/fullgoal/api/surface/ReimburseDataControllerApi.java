package com.ylink.fullgoal.api.surface;

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
import com.leo.core.iapi.IObjAction;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.ResUtil;
import com.leo.core.util.SoftInputUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.CCSQDBean;
import com.ylink.fullgoal.bean.DateArrayBean;
import com.ylink.fullgoal.bean.IconBean;
import com.ylink.fullgoal.bean.IndicatorBean;
import com.ylink.fullgoal.bean.ItemOperationBean;
import com.ylink.fullgoal.bean.ReimburseTypeBean;
import com.ylink.fullgoal.bean.TvHTv3Bean;
import com.ylink.fullgoal.bean.VgBean;
import com.ylink.fullgoal.controllerApi.surface.BarControllerApi;
import com.ylink.fullgoal.controllerApi.surface.RecycleControllerApi;
import com.ylink.fullgoal.hb.DataHb;
import com.ylink.fullgoal.hb.ReimburseHb;
import com.ylink.fullgoal.hb.ReimburseUpHb;
import com.ylink.fullgoal.vo.ReimburseVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

import static com.leo.core.util.TextUtils.getListData;
import static com.ylink.fullgoal.config.Config.APPROVAL_STATUS;
import static com.ylink.fullgoal.config.Config.BILL_TYPES;
import static com.ylink.fullgoal.config.Config.DATES;
import static com.ylink.fullgoal.config.Config.SERIAL_NO;
import static com.ylink.fullgoal.config.Config.STATE;
import static com.ylink.fullgoal.config.UrlConfig.REIMBURSE_QUERY_LIST;

public class ReimburseDataControllerApi<T extends ReimburseDataControllerApi, C> extends BarControllerApi<T, C> {

    @Bind(R.id.api_view)
    BaseControllerApiView apiView;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    private IndicatorControllerApi api;
    private Map<String, RecycleControllerApi> map;

    @SuppressLint("RtlHardcoded")
    private int gravity = Gravity.RIGHT;

    private String type;
    private String dateText;
    private String typeText;

    public ReimburseDataControllerApi(C controller) {
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
        add(DataHb.class, (path, what, msg, bean) -> execute(!TextUtils.isEmpty(msg), ()
                -> initReimburseVoData(getReiRecycleControllerApi(msg), bean.getReimburseList())));
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
        post(REIMBURSE_QUERY_LIST, map -> {
            map.put(getKey("经办人"), getUserName());
            map.put(getKey("审批状态"), getValue(APPROVAL_STATUS, type));
            map.put(getKey("时间"), getValue(DATES, dateText));
            map.put(getKey("报销类型"), getValue(BILL_TYPES, typeText));
        }, type);
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

    private void initReimburseVoData(RecycleControllerApi api, List<ReimburseHb> reimburseData) {
        if (api != null) {
            if (!TextUtils.isEmpty(reimburseData)) {
                api.clear().showContentView();
                execute(reimburseData, obj -> addVgBean(api, data -> {
                    String type = TextUtils.equals(obj.getBillType(), ReimburseUpHb.EVECTION_BILL_TYPE)
                            ? ReimburseVo.REIMBURSE_TYPE_EVECTION : ReimburseVo.REIMBURSE_TYPE_GENERAL;
                    data.add(new CCSQDBean(obj.getSerialNo(), type,
                            "报销批次号", "报销类型"));
                    data.add(new CCSQDBean(obj.getFillDate(), obj.getTotalAmountLower(),
                            "时间", "金额"));
                    data.add(new TvHTv3Bean("事由", obj.getCause()));
                    data.add(new ItemOperationBean("确认", "修改",
                            (bean, view) -> startSurfaceActivity(obj, ReimburseVo.STATE_CONFIRM),
                            (bean, view) -> startSurfaceActivity(obj, ReimburseVo.STATE_ALTER)));
                }, vg -> vg.setOnClickListener(v -> startSurfaceActivity(obj, ReimburseVo.STATE_DETAIL))));
            } else {
                api.showNullView(true);
            }
        }
    }

    private void startSurfaceActivity(ReimburseHb hb, String state) {
        if (hb != null && !TextUtils.isEmpty(state) && !TextUtils.isEmpty(hb.getBillType())
                && !TextUtils.isEmpty(hb.getSerialNo())) {
            switch (hb.getBillType()) {
                case ReimburseVo.BILL_TYPE_Y://一般费用报销
                    startSurfaceActivity(GeneralControllerApi.class, state, hb.getSerialNo());
                    break;
                case ReimburseVo.BILL_TYPE_C://出差费用报销
                    startSurfaceActivity(EvectionControllerApi.class, state, hb.getSerialNo());
                    break;
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
