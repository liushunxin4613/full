package com.ylink.fullgoal.api.surface;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

import com.leo.core.bean.BaseApiBean;
import com.leo.core.util.ResUtil;
import com.leo.core.util.SoftInputUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.CCSQDBean;
import com.ylink.fullgoal.bean.LineBean;
import com.ylink.fullgoal.bean.TvBean;
import com.ylink.fullgoal.bean.XCJPBean;
import com.ylink.fullgoal.config.Config;
import com.ylink.fullgoal.controllerApi.surface.RecycleControllerApi;
import com.ylink.fullgoal.vo.AirVo;
import com.ylink.fullgoal.vo.BusinessVo;
import com.ylink.fullgoal.vo.SearchVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;

public class SearchControllerApi<T extends SearchControllerApi, C> extends RecycleControllerApi<T, C> {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_et)
    EditText nameEt;
    @Bind(R.id.icon_iv)
    ImageView iconIv;

    private String search;

    public SearchControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_search;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getActivity().finish();
    }

    @Override
    public T finishActivity(Object obj) {
        SoftInputUtil.hidSoftInput(nameEt);
        return super.finishActivity(obj);
    }

    @Override
    public void initView() {
        super.initView();
        getRecyclerView().setBackgroundColor(ResUtil.getColor(R.color.white));
        setOnClickListener(backIv, view -> onBackPressed());
        setOnClickListener(iconIv, v -> setText(nameEt, null));
        nameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                setIcon(iconIv, count > 0).search(text.toString());
            }

            @Override
            public void afterTextChanged(Editable edit) {
            }
        });
        nameEt.setOnEditorActionListener((v, actionId, event) -> {
            switch (actionId) {
                default:
                    return false;
                case EditorInfo.IME_ACTION_SEARCH://搜索
                    SoftInputUtil.hidSoftInput(v);
                    search(v.getText().toString());
                    return true;
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        executeBundle(bundle -> executeNon(bundle.getString(Config.SEARCH), search -> {
            this.search = search;
            String[] args = null;
            List<BaseApiBean> dat = new ArrayList<>();
            switch (search) {
                case SearchVo.REIMBURSEMENT://报销人
                    args = new String[]{"张三", "李四", "王五", "陈六"};
                    break;
                case SearchVo.BUDGET_DEPARTMENT://预算归属部门
                    args = new String[]{"计划财务部", "信息技术部", "人事部", "行政部"};
                    break;
                case SearchVo.PROJECT://项目
                    args = new String[]{"富钱包", "富国工作平台", "富国基金", "经济论坛"};
                    break;
                case SearchVo.CONTRACT_BILL://合同付款申请单
                    args = new String[]{"一季度付款合同", "二季度付款合同", "三季度付款合同", "四季度付款合同"};
                    break;
                case SearchVo.SERVE_BILL://招待申请单
                    args = new String[]{"杨浦招待", "陆家嘴招待", "浦东招待", "松江招待"};
                    break;
                case SearchVo.COST_INDEX://费用指标
                    args = new String[]{"营销会议费", "招待费"};
                    break;
                case SearchVo.BUSINESS://出差申请单
                    List<BusinessVo> businessData = getTestData(getTestBusinessData(), new Random().nextInt(10) + 3);
                    dat.clear();
                    execute(businessData, item -> dat.add(new CCSQDBean(item.getSerialNo(),
                            item.getDays(), item.getStartDate(), item.getEndDate(),
                            (bean, view) -> finishActivity(new SearchVo<>(search, item)))));
                    break;
                case SearchVo.XC_AIR://携程机票
                    List<AirVo> airData = getTestData(getTestAirData(), new Random().nextInt(10) + 3);
                    dat.clear();
                    execute(airData, item -> dat.add(new XCJPBean(item.getUser(),
                            item.getMoney(), item.getType(), String.format("%s 开", item.getStartTime()),
                            String.format("%s 到", item.getEndTime()), String.format("%s - %s",
                            item.getStartPlace(), item.getEndPlace()),
                            (bean, view) -> finishActivity(new SearchVo<>(search, item)))));
                    break;
            }
            if (!TextUtils.isEmpty(args)) {
                List<String> data = getTestData(args, new Random().nextInt(10) + 3);
                dat.clear();
                execute(data, obj -> dat.add(new TvBean(obj, (bean, view) -> finishActivity(new SearchVo<>(search, obj)))));
            }
            initSearchData(dat);
        }));
    }

    /**
     * 搜索
     *
     * @param keyword 关键字
     */
    protected void search(String keyword) {
        initData();
    }

    private void initSearchData(List<BaseApiBean> data) {
        clear();
        execute(getLineData(data), this::add);
        notifyDataSetChanged();
    }

    public List<BaseApiBean> getLineData(List<BaseApiBean> data) {
        if (!TextUtils.isEmpty(data)) {
            List<BaseApiBean> dat = new ArrayList<>();
            int count = TextUtils.count(data);
            for (int i = 0; i < count; i++) {
                dat.add(data.get(i));
                dat.add(new LineBean());
            }
            return dat;
        }
        return null;
    }

    //test
    private <A> List<A> getTestData(A[] args, int size) {
        if (!TextUtils.isEmpty(args) && size > 0) {
            List<A> data = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                data.add(args[new Random().nextInt(args.length)]);
            }
            return data;
        }
        return null;
    }

    private <A> List<A> getTestData(List<A> list, int size) {
        if (!TextUtils.isEmpty(list) && size > 0) {
            List<A> data = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                data.add(list.get(new Random().nextInt(list.size())));
            }
            return data;
        }
        return null;
    }

    private List<AirVo> getTestAirData() {
        return TextUtils.getListData(new AirVo("张三", "单程", "1324.00", "2018-02-23 12:23", "次日 16:40", "上海", "北京"),
                new AirVo("李四", "单程", "1023.00", "2018-03-12 18:23", "次日 01:20", "深圳", "北京"),
                new AirVo("王五", "单程", "1390.00", "2018-05-04 08:00", "20:00", "上海", "深圳"),
                new AirVo("陈六", "单程", "900.00", "2018-04-26 10:55", "次日 02:30", "广州", "南京"));
    }

    private List<BusinessVo> getTestBusinessData() {
        return TextUtils.getListData(
                new BusinessVo("FGMC-P2018-00021", "3天", "2018-03-04", "2018-03-06"),
                new BusinessVo("FGMC-P2018-00105", "1天", "2018-03-09", "2018-03-10"),
                new BusinessVo("FGMC-P2018-00237", "5天", "2018-03-21", "2018-03-25"));
    }

}
