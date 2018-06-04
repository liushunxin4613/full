package com.ylink.fullgoal.api.surface;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;

import com.leo.core.adapter.BasePagerAdapter;
import com.leo.core.bean.BaseApiBean;
import com.leo.core.core.BaseControllerApiView;
import com.leo.core.iapi.IObjAction;
import com.leo.core.util.ResUtil;
import com.leo.core.util.SoftInputUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.CCSQDBean;
import com.ylink.fullgoal.bean.DateArrayBean;
import com.ylink.fullgoal.bean.IconBean;
import com.ylink.fullgoal.bean.IndicatorBean;
import com.ylink.fullgoal.bean.SXBean;
import com.ylink.fullgoal.bean.TvHTv3Bean;
import com.ylink.fullgoal.controllerApi.surface.BarControllerApi;
import com.ylink.fullgoal.controllerApi.surface.RecycleControllerApi;
import com.ylink.fullgoal.vo.ReimburseVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

import static com.leo.core.util.TextUtils.count;
import static com.leo.core.util.TextUtils.getListData;
import static com.leo.core.util.TextUtils.getSetData;
import static com.ylink.fullgoal.config.Config.APPROVAL_STATUS;

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
        addList(ReimburseVo.class, (what, msg, data) -> execute(!TextUtils.isEmpty(msg), ()
                -> initReimburseVoData(getReiRecycleControllerApi(msg), data)));
        getReiRecycleControllerApi("待处理");
        getReiRecycleControllerApi("审核中");
        getReiRecycleControllerApi("已完成");
        getReiRecycleControllerApi("已取消");
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
                ReimburseVo vo = new ReimburseVo();
                vo.setAgent(getUserName());
                vo.setApprovalStatus(getApprovalStatus(type));
                Map<String, String> map = getCheck(vo, getSetData("经办人", "审批状态"));
                if (!TextUtils.isEmpty(map)) {
                    post("QuerytReimbursement", mp -> mp.putAll(map), type);
                }
            }
            return api;
        }
        return null;
    }

    private String getApprovalStatus(String type) {
        if (!TextUtils.isEmpty(type) && !TextUtils.isEmpty(APPROVAL_STATUS)) {
            for (String[] item : APPROVAL_STATUS) {
                if (count(item) == 2 && TextUtils.equals(item[0], type)) {
                    return item[1];
                }
            }
        }
        return null;
    }

    private void initReimburseVoData(RecycleControllerApi api, List<ReimburseVo> reimburseData) {
        if (api != null) {
            if (!TextUtils.isEmpty(reimburseData)) {
                api.showContentView();
                execute(reimburseData, obj -> addVgBean(api, data -> {
                    if (TextUtils.equals(obj.getBillType(), "1")) {
                        obj.setReimbursementState(ReimburseVo.REIMBURSE_TYPE_GENERAL);
                    } else if (TextUtils.equals(obj.getBillType(), "2")) {
                        obj.setReimbursementState(ReimburseVo.REIMBURSE_TYPE_EVECTION);
                    }
                    data.add(new CCSQDBean(obj.getSerialNo(), obj.getReimbursementState(), "报销批次号", "报销类型"));
                    data.add(new CCSQDBean(obj.getFillDate(), obj.getTotalAmountLower(), "时间", "金额"));
                    data.add(new TvHTv3Bean("事由", obj.getCause()));
                }));
            } else {
                api.showNullView(true);
            }
        }
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
        RecycleControllerApi api = getViewControllerApi(RecycleControllerApi.class, R.layout.l_sx);
        api.getRecyclerView().setBackgroundColor(ResUtil.getColor(R.color.white));
        setOnClickListener(api.findViewById(R.id.reset_tv), v -> {
            show("重置");
        }).setOnClickListener(api.findViewById(R.id.confirm_tv), v -> {
            show("确定");
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
        SXBean sx = new SXBean();
        api.add(new IconBean((bean, view) -> drawerLayout.closeDrawer(gravity)))
                .add(new DateArrayBean("查询时间", getListData(
                        "当天", "七天", "一个月", "三个月", "六个月", "一年")))
                .add(sx)
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
                .setAdapter(new BasePagerAdapter());
    }

    private void addVgBean(RecycleControllerApi controllerApi, IObjAction<List<BaseApiBean>> api) {
        if (controllerApi != null && api != null) {
            controllerApi.addVgBean(api);
            controllerApi.notifyDataSetChanged();
        }
    }

}
