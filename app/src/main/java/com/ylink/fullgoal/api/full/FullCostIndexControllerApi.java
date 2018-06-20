package com.ylink.fullgoal.api.full;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.leo.core.adapter.BasePagerAdapter;
import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.bean.BaseApiBean;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.JavaTypeUtil;
import com.leo.core.util.SoftInputUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.MoneyBean;
import com.ylink.fullgoal.bean.TvH2MoreBean;
import com.ylink.fullgoal.controllerApi.surface.BarControllerApi;
import com.ylink.fullgoal.controllerApi.surface.RecycleControllerApi;
import com.ylink.fullgoal.cr.core.DoubleController;
import com.ylink.fullgoal.cr.surface.CostController;
import com.ylink.fullgoal.cr.surface.CostItemController;
import com.ylink.fullgoal.cr.surface.DimenListController;
import com.ylink.fullgoal.cr.surface.MoneyController;
import com.ylink.fullgoal.fg.CostFg;
import com.ylink.fullgoal.fg.DataFg;
import com.ylink.fullgoal.fg.DimenFg;
import com.ylink.fullgoal.fg.DimenListFg;
import com.ylink.fullgoal.view.MViewPager;
import com.ylink.fullgoal.vo.CostIndexVo;
import com.ylink.fullgoal.vo.CostVo;
import com.ylink.fullgoal.vo.SearchVo;

import java.util.List;
import java.util.Map;

import butterknife.Bind;

import static com.leo.core.util.TextUtils.check;
import static com.leo.core.util.TextUtils.getMoneyString;
import static com.leo.core.util.TextUtils.getPercentage;
import static com.ylink.fullgoal.config.Config.COST;
import static com.ylink.fullgoal.config.Config.SERIAL_NO;

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
    private BasePagerAdapter adapter;
    private String serialNo;
    private double allMoney;
    private CostVo vo;

    public FullCostIndexControllerApi(C controller) {
        super(controller);
    }

    public CostVo getVo() {
        if (vo == null) {
            vo = new CostVo();
        }
        return vo;
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_cost_index;
    }

    private String getDimenValue(RecycleControllerApi api, String code) {
        if (check(api, code)) {
            return no(no(getCostItemController(api), c -> c.getValue(code)),
                    DimenListFg::getName);
        }
        return null;
    }

    private CostItemController getCostItemController(IControllerApi api) {
        return gt(CostVo::getPager, obj -> obj.getValue(api), CostIndexVo::getItem);
    }

    private MViewPager getViewPager() {
        return viewPager;
    }

    @Override
    public void onResume() {
        super.onResume();
        //费用指标
        executeSearch(CostFg.class, fg -> iso(CostVo::getCost, obj -> obj.initDB(fg)));
        //费用指标维度
        execute(getFinish(), new TypeToken<SearchVo<DimenListFg>>() {
        }, vo -> {
            RecycleControllerApi api = getThisApi();
            no(getCostItemController(api), c -> c.initDB(vo.getValue(), vo.getObj()));
            initAddVgBean(api);
        });
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("费用指标")
                .setRightTv("确认", v -> submit())
                .setOnClickListener(nameTv, v -> startSearch(SearchVo.COST_INDEX))
                .setOnClickListener(searchVg, v -> startSearch(SearchVo.COST_INDEX));
        executeBundle(bundle -> {
            serialNo = bundle.getString(SERIAL_NO);
            CostFg cost = decode(bundle.getString(COST), CostFg.class);
            iso(CostVo::getCost, obj -> obj.initDB(cost));
            iso(CostVo::getMoney, obj -> obj.initDB(JavaTypeUtil.getdouble(gt(CostVo::getCost,
                    CostController::getDB, CostFg::getAmount), 0)));
            allMoney = JavaTypeUtil.getdouble(getExecute(cost, CostFg::getAmount), 0);
        });
        initCast();
        add(DataFg.class, (path, what, msg, bean) -> {
            iso(CostVo::getDimenData, obj -> obj.initDB(bean.getDimen()));
            initViewPager();
        });
    }

    @Override
    public void initData() {
        super.initData();
        uApi().queryDimensionList(gt(CostVo::getCost, CostController::getDB, CostFg::getCostCode));
    }

    private void submit(){
        if(checkSubmit()){
            show("确认");
        }
    }

    private boolean checkSubmit(){
        if(!checkToMore()){
            double poor = allMoney - getThisSumMoney();
            if(poor > 0){
                show(String.format("你还有%s没有分摊", getMoneyString(poor)));
                return false;
            }
            return true;
        }
        return false;
    }

    private void initCast() {
        executeNon(gtd(CostVo::getCost), (CostFg fg)
                -> setText(nameTv, fg.getCostIndex())
                .setText(detailTv, fg.getAmount())
                .setText(typeTv, fg.getShare())
                .setText(taxTv, fg.getTaxAmount())
                .setText(noneTaxMoneyTv, fg.getExTaxAmount())
                .setText(yetCompleteTv, getPercentage(getThisSumMoney(), allMoney)));
    }

    private void updateOtherMoney(MoneyController controller, double otherMoney) {
        controller.initDB(otherMoney);
        setText(yetCompleteTv, getPercentage(getThisSumMoney(), allMoney));
    }

    private void initViewPager() {
        adapter = new BasePagerAdapter();
        getViewPager().setAdapter(adapter);
        getViewPager().getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            int height = getViewPager().getHeight();
            if (maxHeight < height) {
                maxHeight = height;
            }
            if (minHeight > height || minHeight == 0) {
                minHeight = height;
            }
            TextView tv = findViewById(getThisView(), R.id.delete_tv);
            isOpen = height >= minHeight && height < maxHeight;
            if (isOpen) {
                setVisibility(tv, isOpen ? View.GONE : View.VISIBLE);
            } else {
                executeNon(tv, obj -> obj.post(() -> setVisibility(obj, isOpen
                        ? View.GONE : View.VISIBLE)));
            }
        });
        getViewPager().setHorizontalBeyondApi(is -> {
            if(!is && checkAddMore()){
                add(getRecycleControllerApi());
                return true;
            }
            return false;
        }).setHorizontalApi(this::checkToMore);
        add(getRecycleControllerApi());
    }

    private boolean checkAddMore() {
        IControllerApi api = getThisApi();
        double otherMoney = getOtherMoney(api);
        CostIndexVo vo = gt(CostVo::getPager, obj -> obj.getValue(api));
        if (vo != null) {
            double money = vo.getMoney().getdouble();
            if (money <= 0) {
                show("你还没有分摊金额");
                return false;
            }
            if (otherMoney + money >= allMoney) {
                show("你没有可分摊的金额");
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean checkToMore() {
        SoftInputUtil.hidSoftInput(getRootView());
        CostIndexVo vo = gt(CostVo::getPager, obj -> obj.getValue(getThisApi()));
        if (vo != null) {
            Map<String, DimenListFg> map = vo.getItem().getMap();
            List<DimenFg> data = gt(CostVo::getDimenData, DimenListController::getViewBean);
            if (!TextUtils.isEmpty(data)) {
                for (DimenFg fg : data) {
                    if (check(fg)) {
                        DimenListFg value = map.get(fg.getCode());
                        if (!check(value) || !check(value.getCode(), value.getName())) {
                            show(String.format("%s不能为空", fg.getName()));
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    private void addVgBean(RecycleControllerApi controllerApi, IObjAction<List<BaseApiBean>> api) {
        if (controllerApi != null && api != null) {
            controllerApi.addVgBean(api);
            controllerApi.notifyDataSetChanged();
        }
    }

    private void add(RecycleControllerApi controllerApi) {
        executeNon(controllerApi, api -> executeNon(adapter, adp -> adp.add(api).notifyDataSetChanged()));
    }

    private void initAddVgBean(RecycleControllerApi controllerApi) {
        executeNon(controllerApi, api -> {
            api.clear();
            addVgBean(api, data -> {
                MoneyController controller = gt(CostVo::getPager, obj -> obj.getValue(api),
                        CostIndexVo::getMoney);
                String money = no(controller, DoubleController::getViewBean);
                double md = JavaTypeUtil.getdouble(money, 0);
                updateOtherMoney(controller, md);
                String bl = getPercentage(md, allMoney);
                MoneyBean blBean = new MoneyBean("分摊比例", bl);
                double itemMax = allMoney - getOtherMoney(api);
                MoneyBean moneyBean = new MoneyBean("金额", money,
                        String.format("最多可分摊%s", TextUtils.getMoneyString(itemMax)), text -> {
                    double itemMoney = JavaTypeUtil.getdouble(text, 0);
                    updateOtherMoney(controller, itemMoney);
                    setText(blBean.getTextView(), getPercentage(JavaTypeUtil.getdouble(itemMoney, 0),
                            allMoney));
                    iso(CostVo::getPager, obj -> obj.getValue(api), CostIndexVo::getMoney, item
                            -> item.initDB(itemMoney));
                }).setMax(itemMax);
                data.add(moneyBean);
                data.add(blBean);
                execute((List<DimenFg>) gtv(CostVo::getDimenData), item
                        -> data.add(new TvH2MoreBean(item.getName(), getDimenValue(api, item.getCode()),
                        String.format("请选择%s", item.getName()), (bean, view)
                        -> startSearch(SearchVo.COST_INDEX_DIMEN, encode(item)))));
            });
            api.notifyDataSetChanged();
        });
    }

    private RecycleControllerApi getRecycleControllerApi() {
        RecycleControllerApi api = getViewControllerApi(RecycleControllerApi.class,
                R.layout.l_cost_index_bottom);
        CostIndexVo vo = new CostIndexVo();
        vo.getMoney().initDB(allMoney - getOtherMoney(api));
        gt(CostVo::getPager, obj -> obj.initDB(api, vo));
        initAddVgBean(api);
        setOnClickListener(findViewById(api.getRootView(), R.id.delete_tv), v -> {
            iso(CostVo::getPager, obj -> obj.remove(api));
            adapter.remove(api);
            if (adapter.getCount() == 0) {
                adapter.add(getRecycleControllerApi());
            }
            adapter.notifyDataSetChanged();
            initAddVgBean(getThisApi());
        });
        return api;
    }

    private double getOtherMoney(IControllerApi api) {
        return gt(CostVo::getPager, obj -> obj.getOtherMoney(api));
    }

    private RecycleControllerApi getThisApi() {
        if (check(adapter, getViewPager())) {
            return (RecycleControllerApi) adapter.getItemApi(getViewPager().getCurrentItem());
        }
        return null;
    }

    private View getThisView() {
        return no(getThisApi(), CoreControllerApi::getRootView);
    }

    private double getThisMoney() {
        Double d = no(gt(CostVo::getPager, obj -> obj.getValue(getThisApi()),
                CostIndexVo::getMoney), DoubleController::getdouble);
        return (d == null ? 0 : d);
    }

    private double getThisSumMoney() {
        return getThisMoney() + getOtherMoney(getThisApi());
    }

}