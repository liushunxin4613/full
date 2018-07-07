package com.ylink.fullgoal.api.full;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;

import com.leo.core.adapter.BasePagerAdapter;
import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.core.BaseControllerApiView;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.inter.IPathMsgAction;
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
import com.ylink.fullgoal.core.BaseBiBean;
import com.ylink.fullgoal.cr.surface.BooleanController;
import com.ylink.fullgoal.fg.ApplicationtFg;
import com.ylink.fullgoal.fg.DataFg;
import com.ylink.fullgoal.vo.DDVo;
import com.ylink.fullgoal.vo.DItemVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

import static com.leo.core.util.TextUtils.check;
import static com.leo.core.util.TextUtils.getListData;
import static com.ylink.fullgoal.config.Config.D1;
import static com.ylink.fullgoal.config.Config.D2;
import static com.ylink.fullgoal.config.Config.D3;
import static com.ylink.fullgoal.config.Config.D4;
import static com.ylink.fullgoal.config.Config.D_BT1;
import static com.ylink.fullgoal.config.Config.D_BT2;
import static com.ylink.fullgoal.config.Config.D_DATE1;
import static com.ylink.fullgoal.config.Config.D_DATE2;
import static com.ylink.fullgoal.config.Config.D_DATE3;
import static com.ylink.fullgoal.config.Config.D_DATE4;
import static com.ylink.fullgoal.config.Config.D_DATE5;
import static com.ylink.fullgoal.config.Config.D_DATE6;
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

    private IndicatorControllerApi api;
    private ReimburseTypeBean typeBean;
    private DateArrayBean dateArrayBean;
    private int gravity = Gravity.RIGHT;
    private Map<String, RecycleControllerApi> map;

    public FullReimburseDataControllerApi(C controller) {
        super(controller);
        map = new HashMap<>();
    }

    @Override
    public DDVo getVo() {
        return super.getVo();
    }

    @Override
    public DDVo newVo() {
        return new DDVo();
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
        getReiRecycleControllerApi(D1);
        getReiRecycleControllerApi(D2);
        getReiRecycleControllerApi(D3);
        getReiRecycleControllerApi(D4);
    }

    private void query() {
        if (!getExecute(getItemValue().getOnce(), false, BooleanController::is)) {
            String type = getType();
            if (check(type)) {
                executeNon(getReiRecycleControllerApi(type), api
                        -> api.api().queryApplicationForm(getUBMap(), type));
            }
        }
    }

    private String getType() {
        return vr(api, IndicatorControllerApi::getCurrentItemName);
    }

    private DItemVo getItemValue() {
        return getVo().getItemValue(getType());
    }

    private Map<String, Object> getUBMap() {
        return vr(getItemValue(), v -> v.getCheckMap(getType()));
    }

    private RecycleControllerApi getReiRecycleControllerApi(String type) {
        if (check(type)) {
            RecycleControllerApi api = map.get(type);
            if (api == null) {
                api = getRecycleControllerApi(type);
                map.put(type, api);
            }
            return api;
        }
        return null;
    }

    private void initReimburseVoData(RecycleControllerApi api, List<ApplicationtFg> applicationtData) {
        if (api != null) {
            vs(getItemValue(), DItemVo::getOnce, obj -> obj.initDB(true));
            if (!TextUtils.isEmpty(applicationtData)) {
                api.clear().showContentView();
                execute(applicationtData, obj -> addVgBean(api, data -> {
                    data.add(new CCSQDBean(obj.getSerialNo(), obj.getBillType(),
                            "报销批次号", "报销类型"));
                    data.add(new CCSQDBean(obj.getFillDate(), obj.getStatus(),
                            "时间", "状态"));
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
        add(name, controllerApi);
        add(controllerApi, DataFg.class, (path, what, msg, bean)
                -> initReimburseVoData(getReiRecycleControllerApi(msg), bean.getApplicationtList()));
        controllerApi.hideViews();
        return controllerApi;
    }

    private <A> void add(CoreControllerApi api, Class<A> root, IPathMsgAction<A> action) {
        if (check(api, root, action)) {
            api.add(root, action);
        }
    }

    private void add(String name, RecycleControllerApi controllerApi) {
        if (check(api, name, controllerApi)) {
            api.add(new IndicatorBean(name, controllerApi)).notifyDataSetChanged();
            DItemVo vo = new DItemVo();
            vo.getAgent().initDB(getUId());
            vo.getStatus().initDB(name);
            vos(DDVo::getItem, obj -> obj.initDB(name, vo));
        }
    }

    //私有的

    @SuppressLint("RtlHardcoded")
    private void initDrawerLayout() {
        dateArrayBean = new DateArrayBean("查询时间", getListData(D_DATE1, D_DATE2,
                D_DATE3, D_DATE4, D_DATE5, D_DATE6), text -> vs(getItemValue(), DItemVo::getDate,
                obj -> obj.initDB(text)));
        typeBean = new ReimburseTypeBean(D_BT1, D_BT2, text ->
                vs(getItemValue(), DItemVo::getBillType, obj -> obj.initDB(text)));
        RecycleControllerApi api = getViewControllerApi(RecycleControllerApi.class, R.layout.l_sx);
        api.getRecyclerView().setBackgroundColor(ResUtil.getColor(R.color.white));
        setOnClickListener(api.findViewById(R.id.reset_tv), v -> {
            dateArrayBean.clean();
            typeBean.clean();
        }).setOnClickListener(api.findViewById(R.id.confirm_tv), v -> {
            drawerLayout.closeDrawer(gravity);
            vs(getItemValue(), DItemVo::getOnce, obj -> obj.initDB(false));
            query();
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
            setRightTv("筛选", v -> {
                if (check(dateArrayBean)) {
                    dateArrayBean.update(vr(getItemValue(), value -> value.getDate().getDB()));
                }
                if (check(typeBean)) {
                    typeBean.updateSelected(vr(getItemValue(), value -> value.getBillType().getDB()));
                }
                drawerLayout.openDrawer(gravity);
            });
        }
    }

    private void initIndicatorControllerApi() {
        api = ((IndicatorControllerApi) apiView.controllerApi())
                .setViewPager(viewPager)
                .setAdapter(new BasePagerAdapter())
                .setAction((what, msg, bean, args) -> query());
    }

    private void addVgBean(RecycleControllerApi controllerApi, IObjAction<List<BaseBiBean>> api,
                           IObjAction<VgBean> vgAction) {
        if (controllerApi != null && api != null) {
            executeNon(controllerApi.addVgBean(api), vgAction);
            controllerApi.notifyDataSetChanged();
        }
    }

}