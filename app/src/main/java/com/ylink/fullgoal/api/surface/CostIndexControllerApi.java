package com.ylink.fullgoal.api.surface;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leo.core.adapter.BasePagerAdapter;
import com.leo.core.bean.BaseApiBean;
import com.leo.core.iapi.IRunApi;
import com.leo.core.util.SoftInputUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.SearchWaterfall;
import com.ylink.fullgoal.bean.TvH2SBean;
import com.ylink.fullgoal.bean.TvHEtBean;
import com.ylink.fullgoal.bean.TvSBean;
import com.ylink.fullgoal.controllerApi.surface.BarControllerApi;
import com.ylink.fullgoal.controllerApi.surface.RecycleControllerApi;
import com.ylink.fullgoal.view.MViewPager;
import com.ylink.fullgoal.vo.CastTargetVo;
import com.ylink.fullgoal.vo.SearchVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;

/**
 * 费用指标
 */
public class CostIndexControllerApi<T extends CostIndexControllerApi, C> extends BarControllerApi<T, C> {

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

    public CostIndexControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_cost_index;
    }

    @Override
    protected void startSearch(String search) {
        super.startSearch(search);
        SoftInputUtil.hidSoftInput(getRootView());
    }

    @Override
    public void onResume() {
        super.onResume();
        executeNon(getFinish(SearchVo.class), (SearchVo<String> obj) -> executeNon(obj.getSearch(), search -> {
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
        setText(nameTv, targetVo.getCastTarget())
                .setText(detailTv, targetVo.getMoney())
                .setText(typeTv, targetVo.getApportionState())
                .setText(taxTv, targetVo.getTaxMoney())
                .setText(noneTaxMoneyTv, targetVo.getHasCastMoney())
                .setText(yetCompleteTv, targetVo.getYetApportionPercent());
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

    private void addVgBean(RecycleControllerApi controllerApi, IRunApi<List<BaseApiBean>> api) {
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
            data.add(new TvHEtBean("金额", "13100.00", "请输入金额"));
            data.add(new TvH2SBean("分摊比例", "65.50%"));
        });
        api.add(new SearchWaterfall("渠道维度", getTestData("富国直销", "确权销售商", "上海华信有限公司", "华安证券公司"),
                (ai, text) -> initApiText("渠道维度", ai, text)));
        api.add(new SearchWaterfall("产品维度", getTestData("富国稳健增强债券", "富国国有企业债", "富国收益宝", "太平洋人寿低等级信用债资产管理计划"),
                (ai, text) -> initApiText("产品维度", ai, text)));
        api.add(new SearchWaterfall("员工", getTestData("毕天宇", "曹晋", "陈士坤", "丛林", "何牧", "李辉"),
                (ai, text) -> initApiText("员工", ai, text)));
        api.add(new SearchWaterfall("营销活动", getTestData("日常维护", "年金、专户市场开拓", "公司品牌宣传", "2016-富国研究优选IPO"),
                (ai, text) -> initApiText("营销活动", ai, text)));
        return api;
    }

    private void initApiText(String type, RecycleControllerApi api, String text) {
        ee(type, text);
        List<String> data = new ArrayList<>();
        switch (type) {
            case "渠道维度":
                data = TextUtils.getListData("富国直销", "确权销售商", "上海华信有限公司", "华安证券公司");
                break;
            case "产品维度":
                data = TextUtils.getListData("富国稳健增强债券", "富国国有企业债", "富国收益宝", "太平洋人寿低等级信用债资产管理计划");
                break;
            case "员工":
                data = TextUtils.getListData("毕天宇", "曹晋", "陈士坤", "丛林", "何牧", "李辉");
                break;
            case "营销活动":
                data = TextUtils.getListData("日常维护", "年金、专户市场开拓", "公司品牌宣传", "2016-富国研究优选IPO");
                break;
        }
        api.replaceAll(getTestBeanData(getTestData(data, new Random().nextInt(10) + 3))).notifyDataSetChanged();
    }

    private List<TvSBean> getTestBeanData(List<String> data) {
        if (!TextUtils.isEmpty(data)) {
            List<TvSBean> beanData = new ArrayList<>();
            for (String item : data) {
                beanData.add(new TvSBean(item));
            }
            return beanData;
        }
        return null;
    }

    private List<String> getTestData(List<String> d, int count) {
        if (!TextUtils.isEmpty(d) && count > 0) {
            List<String> data = new ArrayList<>();
            count += 3;
            Random random = new Random();
            for (int i = 0; i < count; i++) {
                data.add(d.get(random.nextInt(d.size())));
            }
            return data;
        }
        return null;
    }

    private List<String> getTestData(String... args) {
        if (!TextUtils.isEmpty(args)) {
            List<String> data = new ArrayList<>();
            int count = 30;
            Random random = new Random();
            for (int i = 0; i < count; i++) {
                data.add(args[random.nextInt(args.length)]);
            }
            return data;
        }
        return null;
    }

}
