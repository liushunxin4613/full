package com.ylink.fullgoal.api.full;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leo.core.adapter.BasePagerAdapter;
import com.leo.core.bean.BaseApiBean;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.util.SoftInputUtil;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.MoneyBean;
import com.ylink.fullgoal.controllerApi.surface.BarControllerApi;
import com.ylink.fullgoal.controllerApi.surface.RecycleControllerApi;
import com.ylink.fullgoal.view.MViewPager;
import com.ylink.fullgoal.vo1.CastTargetVo;
import com.ylink.fullgoal.vo1.SearchVo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 费用指标
 */
public class FullCostIndexControllerApi<T extends FullCostIndexControllerApi, C> extends BarControllerApi<T, C> {

    @Bind(R.id.view_pager)
    MViewPager viewPager;
    @Bind(R.id.search_vg)
    LinearLayout searchVg;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;
    @Bind(R.id.type_tv)
    TextView typeTv;
    @Bind(R.id.tax_tv)
    TextView taxTv;
    @Bind(R.id.none_tax_money_tv)
    TextView noneTaxMoneyTv;
    @Bind(R.id.yet_complete_tv)
    TextView yetCompleteTv;

    private int maxHeight;
    private int minHeight;
    private boolean isOpen;
    private int miniSpeed = 0;
    private int miniWidth = 120;
    private CastTargetVo targetVo;
    private BasePagerAdapter adapter;

    public FullCostIndexControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_cost_index;
    }

    @Override
    protected void startSearch(String search, ArrayList<String> filterData) {
        SoftInputUtil.hidSoftInput(getRootView());
        super.startSearch(search, filterData);
    }

    @Override
    public void onResume() {
        super.onResume();
        executeNon(getFinish(SearchVo.class), (SearchVo<String> obj)
                -> executeNon(obj.getSearch(), search -> {
            switch (search) {
                case SearchVo.COST_INDEX://费用指标
                    setText(nameTv, obj.getObj());
                    break;
            }
        }));
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("费用指标")
                .setRightTv("确认", v -> show("确认"))
                .setOnClickListener(nameTv, v -> startSearch(SearchVo.COST_INDEX))
                .setOnClickListener(searchVg, v -> startSearch(SearchVo.COST_INDEX));
        initViewPager();
    }

    @Override
    public void initData() {
        super.initData();
        testCastTargetVo();
        initCastTargetVo();
    }

    private void testCastTargetVo() {
        targetVo = new CastTargetVo("会议费", "20000.00", "需要分摊", "3500.00", "16500.00", "34.50%");
    }

    private void initCastTargetVo() {
        executeNon(targetVo, vo
                -> setText(nameTv, vo.getCastTarget())
                .setText(detailTv, vo.getMoney())
                .setText(typeTv, vo.getApportionState())
                .setText(taxTv, vo.getTaxMoney())
                .setText(noneTaxMoneyTv, vo.getHasCastMoney())
                .setText(yetCompleteTv, vo.getYetApportionPercent()));
    }

    private View getCurrentItemView() {
        return viewPager.getChildAt(viewPager.getCurrentItem());
    }

    private void initViewPager() {
        adapter = new BasePagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            int height = viewPager.getHeight();
            if (maxHeight < height) {
                maxHeight = height;
            }
            if (minHeight > height || minHeight == 0) {
                minHeight = height;
            }
            TextView tv = findViewById(getCurrentItemView(), R.id.delete_tv);
            isOpen = height >= minHeight && height < maxHeight;
            if (isOpen) {
                setVisibility(tv, isOpen ? View.GONE : View.VISIBLE);
            } else {
                executeNon(tv, obj -> obj.post(() -> setVisibility(obj, isOpen ? View.GONE : View.VISIBLE)));
            }
        });
        viewPager.setVerticalBeyondApi(is -> execute(!is, () -> adapter.add(
                getRecycleControllerApi().getRootView()).notifyDataSetChanged()));
        adapter.add(getRecycleControllerApi().getRootView()).notifyDataSetChanged();
    }

    private void addVgBean(RecycleControllerApi controllerApi, IObjAction<List<BaseApiBean>> api) {
        if (controllerApi != null && api != null) {
            controllerApi.addVgBean(api);
            controllerApi.notifyDataSetChanged();
        }
    }

    private RecycleControllerApi getRecycleControllerApi() {
        RecycleControllerApi api = getViewControllerApi(RecycleControllerApi.class,
                R.layout.l_cost_index_bottom);
        setOnClickListener(findViewById(api.getRootView(), R.id.delete_tv), v -> {
            adapter.remove(api.getRootView());
            if (adapter.getCount() == 0) {
                adapter.add(getRecycleControllerApi().getRootView());
            }
            adapter.notifyDataSetChanged();
        });
        addVgBean(api, data -> {
            data.add(new MoneyBean("金额", "13100.00", "请输入金额", text -> {
                ee("金额", text);
            }));
            data.add(new MoneyBean("分摊比例", "65.50", "请输入分摊比例", text -> {
                ee("分摊比例", text);
            }));
            /*data.add(new TvH2MoreBean("合同付款申请单", , "请选择合同付款申请单",
                    (bean, view) -> startSearch(SearchVo.CONTRACT_BILL)));*/
        });
        return api;
    }

}
