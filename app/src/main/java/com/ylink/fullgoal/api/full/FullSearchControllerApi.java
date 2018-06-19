package com.ylink.fullgoal.api.full;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leo.core.bean.Completed;
import com.leo.core.iapi.api.IKeywordApi;
import com.leo.core.util.ResUtil;
import com.leo.core.util.SoftInputUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.CCSQDBean;
import com.ylink.fullgoal.bean.LineBean;
import com.ylink.fullgoal.bean.TvBean;
import com.ylink.fullgoal.bean.TvH2SBean;
import com.ylink.fullgoal.bean.XCJPBean;
import com.ylink.fullgoal.config.Config;
import com.ylink.fullgoal.controllerApi.surface.RecycleControllerApi;
import com.ylink.fullgoal.fg.ContractPaymentFg;
import com.ylink.fullgoal.fg.CostFg;
import com.ylink.fullgoal.fg.CtripTicketsFg;
import com.ylink.fullgoal.fg.DataFg;
import com.ylink.fullgoal.fg.DepartmentFg;
import com.ylink.fullgoal.fg.ProcessFg;
import com.ylink.fullgoal.fg.ProjectFg;
import com.ylink.fullgoal.fg.ResearchReportFg;
import com.ylink.fullgoal.fg.TravelFormFg;
import com.ylink.fullgoal.fg.UserFg;
import com.ylink.fullgoal.vo.SearchVo;

import java.util.List;

import butterknife.Bind;

import static com.ylink.fullgoal.vo.SearchVo.BUDGET_DEPARTMENT;
import static com.ylink.fullgoal.vo.SearchVo.BUSINESS;
import static com.ylink.fullgoal.vo.SearchVo.CONTRACT_BILL;
import static com.ylink.fullgoal.vo.SearchVo.COST_INDEX;
import static com.ylink.fullgoal.vo.SearchVo.PROJECT;
import static com.ylink.fullgoal.vo.SearchVo.REIMBURSEMENT;
import static com.ylink.fullgoal.vo.SearchVo.REPORT;
import static com.ylink.fullgoal.vo.SearchVo.SEARCHS;
import static com.ylink.fullgoal.vo.SearchVo.SERVE_BILL;
import static com.ylink.fullgoal.vo.SearchVo.XC_AIR;

public class FullSearchControllerApi<T extends FullSearchControllerApi, C> extends RecycleControllerApi<T, C> {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_et)
    EditText nameEt;
    @Bind(R.id.icon_iv)
    ImageView iconIv;
    @Bind(R.id.null_tv)
    TextView nullTv;
    @Bind(R.id.null_vg)
    LinearLayout nullVg;
    private String search;
    private String keyword;
    private List<String> filterData;

    public FullSearchControllerApi(C controller) {
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
        adapterDataApi().setEmptyAction(this::showView);
        setOnClickListener(backIv, view -> onBackPressed());
        setOnClickListener(iconIv, v -> setText(nameEt, null));
        setText(nullTv, "没有更多的数据");
        setNullView(nullVg);
        executeBundle(bundle -> {
            executeNon(bundle.getString(Config.SEARCH), search -> this.search = search);
            executeNon(bundle.getStringArrayList(Config.FILTERS), data -> this.filterData = data);
        });
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
        initAdds();
    }

    @Override
    public void initData() {
        super.initData();
        query(search);
    }

    private void initAdds() {
        //消息标志
        add(DataFg.class, (path, what, msg, bean) -> showView(bean.isSuccess()));
        //员工列表
        addList(UserFg.class, (path, what, msg, list) -> initDataAction(data -> execute(list, item
                -> data.add(new TvBean(item.getUserName(), (bean, view)
                -> finishActivity(new SearchVo<>(search, item)))))));
        //部门列表
        addList(DepartmentFg.class, (path, what, msg, list) -> initDataAction(data -> execute(list, item
                -> data.add(new TvBean(item.getDepartmentName(), (bean, view)
                -> finishActivity(new SearchVo<>(search, item)))))));
        //项目列表
        addList(ProjectFg.class, (path, what, msg, list) -> initDataAction(data -> execute(list, item
                -> data.add(new TvBean(item.getProjectName(), (bean, view)
                -> finishActivity(new SearchVo<>(search, item)))))));
        //合同付款申请单列表
        addList(ContractPaymentFg.class, (path, what, msg, list) -> initDataAction(data -> execute(list, item
                -> data.add(new TvBean(item.getName(), (bean, view)
                -> finishActivity(new SearchVo<>(search, item)))))));
        //招待申请单列表
        addList(ProcessFg.class, (path, what, msg, list) -> initDataAction(data -> execute(list, item
                -> data.add(new CCSQDBean(item.getApplicant(), item.getAdvAmount(), item.getCode(),
                item.getDate(), (bean, view) -> finishActivity(new SearchVo<>(search, item)))))));
        //费用指标列表
        addList(CostFg.class, (path, what, msg, list) -> initDataAction(data -> execute(list, item
                -> data.add(new TvBean(item.getCostIndex(), (bean, view)
                -> finishActivity(new SearchVo<>(search, item)))))));
        //出差申请单
        addList(TravelFormFg.class, (path, what, msg, list) -> initDataAction(data -> execute(list, item
                -> data.add(new CCSQDBean(item.getCode(), String.format("%s天", item.getDates()),
                item.getStartDate(), String.format("至%s", item.getEndDate()),
                (bean, view) -> finishActivity(new SearchVo<>(search, item)))
                .setFilter(item.getAmount())))));
        //投研报告列表
        addList(ResearchReportFg.class, (path, what, msg, list) -> initDataAction(data -> execute(list, item
                -> data.add(new TvH2SBean(item.getReportInfo(), item.getStockName(), (bean, view)
                -> finishActivity(new SearchVo<>(search, item)))
                .setFilter(item.getProjectCode())))));
        //携程机票列表
        addList(CtripTicketsFg.class, (path, what, msg, list) -> initDataAction(data -> execute(list, item
                -> data.add(new XCJPBean(item.getCrew(), item.getTicket(), item.getFlightNumber(),
                String.format("%s 开", item.getTakeOffDate()), String.format("%s 到", item.getArrivelTime()),
                String.format("%s - %s", item.getDeparture(), item.getDestination()),
                (bean, view) -> finishActivity(new SearchVo<>(search, item)))
                .setFilter(item.getFlightNumber())))));
        //完成
        add(Completed.class, (path, what, msg, bean) -> search(keyword));
    }

    /**
     * 数据请求
     */
    private void query(String search) {
        if (!TextUtils.isEmpty(search)) {
            switch (search) {
                case REIMBURSEMENT://员工
                    uApi().queryUserData();
                    break;
                case BUDGET_DEPARTMENT://预算归属部门
                    uApi().queryDepartmentData();
                    break;
                case PROJECT://项目
                    uApi().queryProjectData(getDepartmentCode());
                    break;
                case CONTRACT_BILL://合同付款申请单
                    uApi().queryContractPaymentData();
                    break;
                case SERVE_BILL://招待申请单
                    uApi().queryProcessData();
                    break;
                case COST_INDEX://费用指标
                    uApi().queryCostIndexData();
                    break;
                case BUSINESS://出差申请单
                    uApi().queryTravelFormData();
                    break;
                case REPORT://投研报告
                    uApi().queryResearchReportData();
                    break;
                case XC_AIR://携程机票
                    uApi().queryCtripTicketsData();
                    break;
            }
        }
    }

    /**
     * 搜索
     *
     * @param keyword 关键字
     */
    protected void search(String keyword) {
        this.keyword = keyword;
        adapterDataApi().initFilterAction(obj -> {
            if (obj instanceof LineBean) {
                return !(adapterDataApi().getLastItem(0) instanceof LineBean)
                        && adapterDataApi().getCount() != 0;
            } else if (obj instanceof IKeywordApi) {
                String key = ((IKeywordApi) obj).getKeyword();
                String fk = ((IKeywordApi) obj).getFilter();
                if (!TextUtils.isEmpty(filterData)) {
                    for (String filter : filterData) {
                        if (!TextUtils.isEmpty(filter) && !TextUtils.isEmpty(fk)
                                && fk.contains(filter)) {
                            return false;
                        }
                    }
                }
                return TextUtils.isEmpty(keyword) || !TextUtils.isEmpty(key)
                        && key.contains(keyword);
            }
            return true;
        });
        notifyDataSetChanged();
    }

    private String getSearchValue(String key) {
        if (!TextUtils.isEmpty(SEARCHS) && !TextUtils.isEmpty(key)) {
            for (String[] args : SEARCHS) {
                if (!TextUtils.isEmpty(args)) {
                    if (TextUtils.count(args) == 2 &&
                            TextUtils.equals(args[0], key)) {
                        return args[1];
                    }
                }
            }
        }
        return null;
    }

    private void showView(boolean enable) {
        if (enable) {
            showContentView();
        } else {
            showNullView(true);
        }
    }

}
