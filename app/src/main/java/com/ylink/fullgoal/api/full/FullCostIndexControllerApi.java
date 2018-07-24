package com.ylink.fullgoal.api.full;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.leo.core.adapter.BasePagerAdapter;
import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.core.BaseControllerApiActivity;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.other.Number;
import com.leo.core.util.HelperUtil;
import com.leo.core.util.JavaTypeUtil;
import com.leo.core.util.SoftInputUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.MoneyBean;
import com.ylink.fullgoal.bean.TvH2MoreBean;
import com.ylink.fullgoal.controllerApi.surface.BarControllerApi;
import com.ylink.fullgoal.controllerApi.surface.RecycleControllerApi;
import com.ylink.fullgoal.core.BaseBiBean;
import com.ylink.fullgoal.cr.core.DoubleController;
import com.ylink.fullgoal.cr.core.MapController;
import com.ylink.fullgoal.cr.surface.CostIndexController;
import com.ylink.fullgoal.cr.surface.CostItemController;
import com.ylink.fullgoal.cr.surface.DimenListController;
import com.ylink.fullgoal.cr.surface.RatioController;
import com.ylink.fullgoal.fg.CostFg;
import com.ylink.fullgoal.fg.DataFg;
import com.ylink.fullgoal.fg.DimenFg;
import com.ylink.fullgoal.fg.DimenListFg;
import com.ylink.fullgoal.main.SurfaceActivity;
import com.ylink.fullgoal.view.MViewPager;
import com.ylink.fullgoal.vo.CostIndexVo;
import com.ylink.fullgoal.vo.CostVo;
import com.ylink.fullgoal.vo.SearchVo;

import java.util.List;
import java.util.Map;

import butterknife.Bind;

import static com.leo.core.util.TextUtils.check;
import static com.leo.core.util.TextUtils.count;
import static com.leo.core.util.TextUtils.getMoneyString;
import static com.leo.core.util.TextUtils.getSMoneyString;
import static com.leo.core.util.TextUtils.toJSONMap;
import static com.ylink.fullgoal.config.ComConfig.QR;
import static com.ylink.fullgoal.config.Config.COST_LIST;
import static com.ylink.fullgoal.config.Config.DATA_QR;
import static com.ylink.fullgoal.config.Config.MONEY;
import static com.ylink.fullgoal.config.Config.SERIAL_NO;
import static com.ylink.fullgoal.config.UrlConfig.FULL_DIMENSION_LIST;
import static com.ylink.fullgoal.config.UrlConfig.FULL_REIMBURSE_SUBMIT;

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
    @Bind(R.id.type_tv)
    TextView typeTv;
    @Bind(R.id.yet_complete_tv)
    TextView yetCompleteTv;
    @Bind(R.id.detail_et)
    EditText detailEt;
    @Bind(R.id.tax_et)
    EditText taxEt;
    @Bind(R.id.none_tax_money_et)
    EditText noneTaxMoneyEt;

    private int maxHeight;
    private int minHeight;
    private boolean isOpen;
    private int miniSpeed = 0;
    private int miniWidth = 120;
    private String serialNo;
    private BasePagerAdapter<RecycleControllerApi> adapter;
    private Map<String, Object> dataMap;

    public FullCostIndexControllerApi(C controller) {
        super(controller);
    }

    @Override
    public CostVo getVo() {
        return super.getVo();
    }

    @Override
    public CostVo newVo() {
        return new CostVo();
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_cost_index;
    }

    private String getDimenValue(RecycleControllerApi api, String code) {
        if (check(api, code)) {
            return vr(getCostItemController(api), obj -> obj.getValue(code), DimenListFg::getName);
        }
        return null;
    }

    private CostIndexVo getCostIndexVo(IControllerApi api) {
        return vor(CostVo::getPager, obj -> obj.getValue(api));
    }

    private CostItemController getCostItemController(IControllerApi api) {
        return vr(getCostIndexVo(api), CostIndexVo::getItem);
    }

    private double getCostMoney(IControllerApi api) {
        return getExecute(vr(getCostIndexVo(api), CostIndexVo::getMoney), 0d,
                DoubleController::getdouble);
    }

    private String getCostRatio(IControllerApi api) {
        return vr(getCostIndexVo(api), CostIndexVo::getRatio, RatioController::getDB);
    }

    private MViewPager getViewPager() {
        return viewPager;
    }

    @Override
    public void onResume() {
        super.onResume();
        //费用指标
        executeSearch(CostFg.class, obj -> onCost(obj.getObj()));
        //费用指标维度
        execute(getFinish(), new TypeToken<SearchVo<DimenListFg>>() {
        }, vo -> {
            RecycleControllerApi api = getThisApi();
            vs(getCostItemController(api), c -> c.initDB(vo.getValue(), vo.getObj()));
            initAddVgBean(api);
        });
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("费用指标")
                .setRightTv("确认", v -> submit())
                .setOnClickListener(nameTv, v -> routeApi().search(SearchVo.COST_INDEX))
                .setOnClickListener(searchVg, v -> routeApi().search(SearchVo.COST_INDEX));
        HelperUtil.addMoneyTextChangedListener(detailEt, null, this::updateAllMoney);
        HelperUtil.addMoneyTextChangedListener(taxEt, null, this::updateTaxAmount);
        HelperUtil.addMoneyTextChangedListener(noneTaxMoneyEt, null, this::updateExTaxAmount);
        executeBundle(bundle -> {
            String text = bundle.getString(DATA_QR);
            dataMap = toJSONMap(text);
            if (!TextUtils.isEmpty(dataMap)) {
                ee("dataMap", dataMap);
                serialNo = (String) dataMap.get(SERIAL_NO);
                CostFg cost = decode(encode(dataMap.get(COST_LIST)), CostFg.class);
                if (cost != null) {
                    cost.setAmount((String) dataMap.get(MONEY));
                    cost.setTaxAmount("0");
                    cost.setExTaxAmount("0");
                    onCost(cost);
                }
            }
        });
        add(DataFg.class, (path, what, msg, bean) -> {
            if (bean.isSuccess()) {
                switch (path) {
                    case FULL_DIMENSION_LIST://分摊维度列表
                        vos(CostVo::getCost, obj -> obj.update(bean.getDimen()));
                        initCast();
                        vos(CostVo::getDimenData, obj -> obj.initDB(bean.getDimen()));
                        initViewPager();
                        break;
                    case FULL_REIMBURSE_SUBMIT://报销确认
                        show("报销确认成功");
                        activityLifecycleApi().remove(SurfaceActivity.class,
                                BaseControllerApiActivity::finish);
                        break;
                }
            } else {
                if (check(bean.getMessage())) {
                    show(bean.getMessage());
                }
            }
        });
    }

    private void updateAllMoney(String amount) {
        getVo().setAllMoney(amount);
        double allMoney = getAllMoney();
        Number offset = new Number(getSumMoney() - allMoney);
        setText(yetCompleteTv, vor(CostVo::getOtherRatio));
        if (check(adapter)) {
            List<RecycleControllerApi> data = adapter.getApi().getData();
            execute(count(data), false, data::get, api -> {
                double itemMoney = getCostMoney(api);
                if (offset.get() > 0) {
                    offset.add(-itemMoney);
                    if (offset.get() >= 0) {
                        remove(api);
                    } else {
                        vs(getCostIndexVo(api), obj -> obj.init(-offset.get(), allMoney));
                    }
                } else {
                    vs(getCostIndexVo(api), obj -> obj.init(itemMoney, allMoney));
                }
                initAddVgBean(api);
            });
        }
    }

    private void updateTaxAmount(String amount) {
        vos(CostVo::getCost, obj -> obj.updateTaxAmount(amount));
    }

    private void updateExTaxAmount(String amount) {
        vos(CostVo::getCost, obj -> obj.updateExTaxAmount(amount));
    }

    private void submit() {
        if (checkSubmit()) {
            Map<String, Object> map = getVo().getCheckMap(QR);
            if (check(map, dataMap)) {
                dataMap.putAll(map);
                api().submitReimburse(dataMap);
            }
        }
    }

    private void onCost(CostFg fg) {
        if (check(fg) && check(fg.getShare())) {
            //正常项
            vos(CostVo::getCost, obj -> obj.update(fg));
            getVo().updateAllMoney();
            initCast();
            api().queryDimensionList(vor(CostVo::getCost, CostIndexController::getDB,
                    CostFg::getCostCode));
        }
    }

    private boolean checkSubmit() {
        if (!checkToMore()) {
            if (getAllMoney() <= 0) {
                show("分摊金额不能为0");
                return false;
            }
            double poor = getVo().getRestMoney();
            if (poor > 0) {
                show(String.format("你还有%s没有分摊", getMoneyString(poor)));
                return false;
            }
            return true;
        }
        return false;
    }

    private void initCast() {
        vos(CostVo::getCost, CostIndexController::getDB, fg
                -> setText(nameTv, fg.getCostIndex())
                .setText(detailEt, fg.getAmount())
                .setText(typeTv, fg.getShare())
                .setText(taxEt, fg.getTaxAmount())
                .setText(noneTaxMoneyEt, fg.getExTaxAmount())
                .setText(yetCompleteTv, getVo().getOtherRatio()));
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
            if (!is && checkAddMore()) {
                add(getRecycleControllerApi());
                return true;
            }
            return false;
        }).setHorizontalApi(this::checkToMore);
        vos(CostVo::getPager, MapController::clear);
        add(getRecycleControllerApi());
    }

    private boolean checkAddMore() {
        CostIndexVo vo = vor(CostVo::getPager, obj -> obj.getValue(getThisApi()));
        if (vo != null) {
            if (vo.getMoney().getdouble() <= 0) {
                show("你还没有分摊金额");
                return false;
            }
            if (getVo().getRestMoney() <= 0) {
                show("你没有可分摊的金额");
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean checkToMore() {
        CostIndexVo vo = vor(CostVo::getPager, obj -> obj.getValue(getThisApi()));
        if (vo != null) {
            Map<String, DimenListFg> map = vo.getItem().getMap();
            List<DimenFg> data = vor(CostVo::getDimenData, DimenListController::getViewBean);
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
            SoftInputUtil.hidSoftInput(getRootView());
            return false;
        }
        return true;
    }

    private void addVgBean(RecycleControllerApi controllerApi, IObjAction<List<BaseBiBean>> api) {
        if (controllerApi != null && api != null) {
            controllerApi.addVgBean(api);
            controllerApi.notifyDataSetChanged();
        }
    }

    private void add(RecycleControllerApi controllerApi) {
        executeNon(controllerApi, api -> executeNon(adapter, adp -> adp.add(api).notifyDataSetChanged()));
    }

    private void updateOtherMoney(IControllerApi api, double otherMoney) {
        vos(CostVo::getPager, obj -> obj.getValue(api), obj -> obj.init(otherMoney, getAllMoney()));
        setText(yetCompleteTv, getVo().getOtherRatio());
    }

    private void initAddVgBean(RecycleControllerApi controllerApi) {
        executeNon(controllerApi, api -> {
            api.clear();
            List<DimenFg> list = vor(CostVo::getDimenData, DimenListController::getData);
            boolean empty = isEmpty(list);
            if (!empty) {
                addVgBean(api, data -> {
                    MoneyBean blBean = new MoneyBean("分摊比例", getCostRatio(api));
                    MoneyBean moneyBean = new MoneyBean("金额", getSMoneyString(getCostMoney(api)),
                            getVo().getRestMoney(api), (bean, text) -> {
                        if (TextUtils.isEmpty(text)) {
                            bean.setMax(getVo().getRestMoney(api));
                        }
                        double itemMoney = JavaTypeUtil.getdouble(text, 0);
                        updateOtherMoney(api, itemMoney);
                        setText(blBean.getTextView(), getVo().getRatio(itemMoney));
                        vos(CostVo::getPager, obj -> obj.update(api, itemMoney));
                    });
                    data.add(moneyBean);
                    data.add(blBean);
                    execute(list, item -> {
                        if (check(item.getCode(), item.getName())) {
                            data.add(new TvH2MoreBean(item.getName(), getDimenValue(api, item.getCode()),
                                    String.format("请选择%s", item.getName()), (bean, view) -> routeApi()
                                    .search(SearchVo.COST_INDEX_DIMEN, encode(item)), (bean, view) -> {
                                show("清除");
                            }));
                        }
                    });
                });
            }
            setVisibility(api.getRootView(), empty ? View.INVISIBLE : View.VISIBLE);
            api.notifyDataSetChanged();
        });
    }

    private RecycleControllerApi getRecycleControllerApi() {
        RecycleControllerApi api = getViewControllerApi(RecycleControllerApi.class,
                R.layout.l_cost_index_bottom);
        double allMoney = getAllMoney();
        double itemMax = allMoney - getOtherMoney(api);
        CostIndexVo vo = new CostIndexVo(itemMax, allMoney);
        Map<String, DimenListFg> map = vr(getCostItemController(getThisApi()),
                CostItemController::getMap);
        if (!TextUtils.isEmpty(map)) {
            vo.getItem().getMap().putAll(map);
        }
        vos(CostVo::getPager, obj -> obj.initDB(api, vo));
        initAddVgBean(api);
        setOnClickListener(findViewById(api.getRootView(), R.id.delete_tv), v -> {
            remove(api);
            initAddVgBean(getThisApi());
        });
        return api;
    }

    private void remove(RecycleControllerApi api) {
        if (check(api, adapter)) {
            vos(CostVo::getPager, obj -> obj.remove(api));
            adapter.remove(api);
            if (adapter.getCount() == 0) {
                add(getRecycleControllerApi());
            }
            adapter.notifyDataSetChanged();
        }
    }

    private RecycleControllerApi getThisApi() {
        if (check(adapter, getViewPager())) {
            return adapter.getItemApi(getViewPager().getCurrentItem());
        }
        return null;
    }

    private RecycleControllerApi getEndApi() {
        if (check(adapter)) {
            return adapter.getApi().getLastItem(0);
        }
        return null;
    }

    private View getThisView() {
        return vr(getThisApi(), CoreControllerApi::getRootView);
    }

    private double getAllMoney() {
        return vor(CostVo::getAllMoney, DoubleController::getdouble);
    }

    private double getThisMoney() {
        return getCostMoney(getThisApi());
    }

    private double getSumMoney() {
        return vor(CostVo::getPager, obj -> obj.getFilterMoney());
    }

    private double getOtherMoney(IControllerApi api) {
        return vor(CostVo::getPager, obj -> obj.getFilterMoney(api));
    }

    private boolean isEmpty(List<DimenFg> list) {
        if (!TextUtils.isEmpty(list)) {
            for (DimenFg fg : list) {
                if (check(fg) && check(fg.getName(), fg.getCode())) {
                    return false;
                }
            }
        }
        return true;
    }

}